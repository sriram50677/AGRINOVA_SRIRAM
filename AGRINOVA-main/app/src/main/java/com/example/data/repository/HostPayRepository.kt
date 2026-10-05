package com.example.data.repository

import com.example.data.local.AuditLogDao
import com.example.data.local.HostDao
import com.example.data.local.NotificationDao
import com.example.data.local.PaymentDao
import com.example.data.local.UserDao
import com.example.data.model.AuditLogEntity
import com.example.data.model.HostEntity
import com.example.data.model.NotificationEntity
import com.example.data.model.PaymentEntity
import com.example.data.model.PaymentStatus
import com.example.data.model.UserEntity
import com.example.data.security.SecurityUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

sealed class AuthResult<out T> {
  data class Success<out T>(val data: T) : AuthResult<T>()
  data class Error(val message: String) : AuthResult<Nothing>()
}

class HostPayRepository(
  private val hostDao: HostDao,
  private val userDao: UserDao,
  private val paymentDao: PaymentDao,
  private val notificationDao: NotificationDao,
  private val auditLogDao: AuditLogDao
) {

  // ================= HOST AUTH & DATA =================

  suspend fun registerHost(
    name: String,
    organization: String,
    mobile: String,
    password: String
  ): AuthResult<HostEntity> {
    if (name.isBlank() || mobile.isBlank() || password.isBlank()) {
      return AuthResult.Error("Name, mobile number, and password are required.")
    }
    val existing = hostDao.getHostByMobile(mobile.trim())
    if (existing != null) {
      return AuthResult.Error("A host with this mobile number already exists.")
    }

    // Generate unique host code
    var hostCode = SecurityUtils.generateHostCode()
    while (hostDao.getHostByCode(hostCode) != null) {
      hostCode = SecurityUtils.generateHostCode()
    }

    val salt = SecurityUtils.generateSalt()
    val passwordHash = SecurityUtils.hashPassword(password, salt)
    val host = HostEntity(
      id = SecurityUtils.generateId("host"),
      hostCode = hostCode,
      name = name.trim(),
      organization = organization.trim().ifEmpty { "HostPay Partner" },
      mobile = mobile.trim(),
      passwordHash = passwordHash,
      salt = salt
    )
    hostDao.insertHost(host)

    // Log audit
    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = host.id,
        actorName = host.name,
        actorRole = "HOST",
        action = "Host Registered",
        details = "Host account created with Code: $hostCode, Org: ${host.organization}"
      )
    )

    return AuthResult.Success(host)
  }

  suspend fun loginHost(mobile: String, password: String): AuthResult<HostEntity> {
    val host = hostDao.getHostByMobile(mobile.trim())
      ?: return AuthResult.Error("Host account not found with this mobile number.")

    val valid = SecurityUtils.verifyPassword(password, host.salt, host.passwordHash)
    if (!valid) {
      return AuthResult.Error("Incorrect password. Please try again.")
    }

    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = host.id,
        actorName = host.name,
        actorRole = "HOST",
        action = "Host Logged In",
        details = "Session started for ${host.name}"
      )
    )

    return AuthResult.Success(host)
  }

  fun observeHost(hostId: String): Flow<HostEntity?> = hostDao.observeHostById(hostId)

  suspend fun findHostByCode(code: String): HostEntity? {
    return hostDao.getHostByCode(code.trim().uppercase())
  }

  // ================= USER AUTH & DATA =================

  suspend fun registerUser(
    hostCode: String,
    name: String,
    village: String,
    gender: String,
    mobile: String,
    password: String,
    profilePhotoUri: String? = null
  ): AuthResult<UserEntity> {
    if (name.isBlank() || mobile.isBlank() || password.isBlank() || village.isBlank()) {
      return AuthResult.Error("Please fill in all mandatory fields.")
    }

    val cleanCode = hostCode.trim().uppercase()
    val host = hostDao.getHostByCode(cleanCode)
      ?: return AuthResult.Error("Invalid Host Code '$cleanCode'. Please enter a valid Host Code.")

    val existingUser = userDao.getUserByMobile(mobile.trim())
    if (existingUser != null) {
      return AuthResult.Error("A user with mobile ${mobile.trim()} is already registered.")
    }

    val salt = SecurityUtils.generateSalt()
    val passwordHash = SecurityUtils.hashPassword(password, salt)
    val user = UserEntity(
      id = SecurityUtils.generateId("usr"),
      hostId = host.id,
      hostCode = cleanCode,
      name = name.trim(),
      village = village.trim(),
      gender = gender,
      mobile = mobile.trim(),
      passwordHash = passwordHash,
      salt = salt,
      profilePhotoUri = profilePhotoUri
    )
    userDao.insertUser(user)

    // Notify Host of new User joined
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "HOST",
        recipientId = host.id,
        title = "New User Joined",
        message = "${user.name} from ${user.village} joined using code $cleanCode.",
        timestamp = System.currentTimeMillis()
      )
    )

    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = host.id,
        actorName = user.name,
        actorRole = "USER",
        action = "User Joined Host",
        details = "${user.name} (${user.village}) joined using code $cleanCode"
      )
    )

    return AuthResult.Success(user)
  }

  suspend fun loginUser(mobile: String, password: String): AuthResult<UserEntity> {
    val user = userDao.getUserByMobile(mobile.trim())
      ?: return AuthResult.Error("No user account found with mobile ${mobile.trim()}.")

    val valid = SecurityUtils.verifyPassword(password, user.salt, user.passwordHash)
    if (!valid) {
      return AuthResult.Error("Incorrect password. Please try again.")
    }

    return AuthResult.Success(user)
  }

  fun observeUser(userId: String): Flow<UserEntity?> = userDao.observeUserById(userId)

  fun getUsersByHost(hostId: String): Flow<List<UserEntity>> = userDao.getUsersByHost(hostId)

  fun getUserCountByHost(hostId: String): Flow<Int> = userDao.getUserCountByHost(hostId)

  suspend fun getUserById(userId: String): UserEntity? = userDao.getUserById(userId)

  suspend fun getHostById(hostId: String): HostEntity? = hostDao.getHostById(hostId)

  // ================= PAYMENT WORKFLOW =================

  /**
   * Host creates payment record. Status is strictly PENDING_USER_APPROVAL.
   * Host cannot approve.
   */
  suspend fun createPayment(
    host: HostEntity,
    user: UserEntity,
    amount: Double,
    note: String?,
    proofPhotoUri: String?
  ): Result<PaymentEntity> {
    if (amount <= 0) {
      return Result.failure(IllegalArgumentException("Amount must be greater than 0"))
    }

    val txnId = SecurityUtils.generateTransactionId()
    val date = SecurityUtils.getCurrentDateFormatted()
    val time = SecurityUtils.getCurrentTimeFormatted()

    val payment = PaymentEntity(
      transactionId = txnId,
      userId = user.id,
      userName = user.name,
      userVillage = user.village,
      hostId = host.id,
      hostName = host.name,
      amount = amount,
      date = date,
      time = time,
      timestamp = System.currentTimeMillis(),
      proofPhotoUri = proofPhotoUri,
      note = note?.trim()?.ifEmpty { null },
      status = PaymentStatus.PENDING_USER_APPROVAL
    )

    paymentDao.insertPayment(payment)

    // Send notification to the user
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "USER",
        recipientId = user.id,
        title = "Payment Confirmation Request",
        message = "${host.name} has sent you a payment of ₹${"%.2f".format(amount)}. Please review and approve.",
        transactionId = txnId,
        timestamp = System.currentTimeMillis()
      )
    )

    // Audit log
    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = host.id,
        actorName = host.name,
        actorRole = "HOST",
        action = "Payment Created",
        details = "Created payment $txnId of ₹${"%.2f".format(amount)} for ${user.name} (Pending Approval)"
      )
    )

    return Result.success(payment)
  }

  /**
   * User approves payment.
   */
  suspend fun approvePayment(
    paymentId: String,
    user: UserEntity
  ): Result<Unit> {
    val payment = paymentDao.getPaymentById(paymentId)
      ?: return Result.failure(IllegalStateException("Payment not found"))

    if (payment.userId != user.id) {
      return Result.failure(SecurityException("Unauthorized: Only the assigned user can approve this payment"))
    }

    if (payment.status != PaymentStatus.PENDING_USER_APPROVAL) {
      return Result.failure(IllegalStateException("Payment is already in status ${payment.status}"))
    }

    val now = System.currentTimeMillis()
    paymentDao.approvePayment(paymentId, PaymentStatus.APPROVED, now)

    // Notify Host
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "HOST",
        recipientId = payment.hostId,
        title = "Payment Approved!",
        message = "${user.name} approved payment $paymentId of ₹${"%.2f".format(payment.amount)}.",
        transactionId = paymentId,
        timestamp = now
      )
    )

    // Notify User
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "USER",
        recipientId = user.id,
        title = "Confirmation Successful",
        message = "You approved payment of ₹${"%.2f".format(payment.amount)} from ${payment.hostName}.",
        transactionId = paymentId,
        timestamp = now
      )
    )

    // Audit log
    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = payment.hostId,
        actorName = user.name,
        actorRole = "USER",
        action = "Payment Approved",
        details = "${user.name} confirmed receipt of ₹${"%.2f".format(payment.amount)} ($paymentId)"
      )
    )

    return Result.success(Unit)
  }

  /**
   * User rejects payment with reason.
   */
  suspend fun rejectPayment(
    paymentId: String,
    user: UserEntity,
    reason: String
  ): Result<Unit> {
    val payment = paymentDao.getPaymentById(paymentId)
      ?: return Result.failure(IllegalStateException("Payment not found"))

    if (payment.userId != user.id) {
      return Result.failure(SecurityException("Unauthorized: Only the assigned user can reject this payment"))
    }

    if (payment.status != PaymentStatus.PENDING_USER_APPROVAL) {
      return Result.failure(IllegalStateException("Payment is already in status ${payment.status}"))
    }

    val cleanReason = reason.trim().ifEmpty { "Rejected by user without specific reason" }
    val now = System.currentTimeMillis()
    paymentDao.rejectPayment(paymentId, PaymentStatus.REJECTED_BY_USER, cleanReason, now)

    // Notify Host
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "HOST",
        recipientId = payment.hostId,
        title = "Payment Rejected",
        message = "${user.name} rejected payment $paymentId (₹${"%.2f".format(payment.amount)}). Reason: $cleanReason",
        transactionId = paymentId,
        timestamp = now
      )
    )

    // Audit log
    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = payment.hostId,
        actorName = user.name,
        actorRole = "USER",
        action = "Payment Rejected",
        details = "${user.name} rejected $paymentId (₹${"%.2f".format(payment.amount)}). Reason: $cleanReason"
      )
    )

    return Result.success(Unit)
  }

  fun getPaymentsByUser(userId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPaymentsByUser(userId)

  fun getPaymentsByHost(hostId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPaymentsByHost(hostId)

  fun getPendingPaymentsByHost(hostId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPendingPaymentsByHost(hostId)

  fun getPendingPaymentsByUser(userId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPendingPaymentsByUser(userId)

  fun getPaymentsByUserAndHost(userId: String, hostId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPaymentsByUserAndHost(userId, hostId)

  fun getTotalApprovedAmountForUser(userId: String): Flow<Double?> =
    paymentDao.getTotalApprovedAmountForUser(userId)

  fun getTotalApprovedAmountForHost(hostId: String): Flow<Double?> =
    paymentDao.getTotalApprovedAmountForHost(hostId)

  fun getApprovedCountForHost(hostId: String): Flow<Int> =
    paymentDao.getApprovedCountForHost(hostId)

  fun getPendingCountForHost(hostId: String): Flow<Int> =
    paymentDao.getPendingCountForHost(hostId)

  // ================= NOTIFICATIONS & AUDIT =================

  fun getNotifications(type: String, recipientId: String): Flow<List<NotificationEntity>> =
    notificationDao.getNotifications(type, recipientId)

  fun getUnreadCount(type: String, recipientId: String): Flow<Int> =
    notificationDao.getUnreadCount(type, recipientId)

  suspend fun markNotificationAsRead(id: String) = notificationDao.markAsRead(id)

  suspend fun markAllNotificationsAsRead(type: String, recipientId: String) =
    notificationDao.markAllAsRead(type, recipientId)

  fun getAuditLogs(hostId: String): Flow<List<AuditLogEntity>> =
    auditLogDao.getLogsByHost(hostId)

  // ================= INITIAL DEMO SEEDER =================

  suspend fun seedDemoDataIfEmpty() {
    val existingHosts = hostDao.getAllHosts().firstOrNull()
    if (!existingHosts.isNullOrEmpty()) return

    // Create demo host
    val hostSalt = SecurityUtils.generateSalt()
    val demoHost = HostEntity(
      id = "host_demo_ravi",
      hostCode = "HP-5520",
      name = "Ravi Sharma",
      organization = "AgriNova Logistics",
      mobile = "9876543210",
      passwordHash = SecurityUtils.hashPassword("host123", hostSalt),
      salt = hostSalt,
      createdAt = System.currentTimeMillis() - 86400000L * 7
    )
    hostDao.insertHost(demoHost)

    // Create 3 demo users
    val u1Salt = SecurityUtils.generateSalt()
    val user1 = UserEntity(
      id = "usr_suresh",
      hostId = demoHost.id,
      hostCode = demoHost.hostCode,
      name = "Suresh Patel",
      village = "Rampur",
      gender = "Male",
      mobile = "9123456780",
      passwordHash = SecurityUtils.hashPassword("user123", u1Salt),
      salt = u1Salt,
      createdAt = System.currentTimeMillis() - 86400000L * 5
    )
    val u2Salt = SecurityUtils.generateSalt()
    val user2 = UserEntity(
      id = "usr_anita",
      hostId = demoHost.id,
      hostCode = demoHost.hostCode,
      name = "Anita Devi",
      village = "Kalyanpur",
      gender = "Female",
      mobile = "9123456781",
      passwordHash = SecurityUtils.hashPassword("user123", u2Salt),
      salt = u2Salt,
      createdAt = System.currentTimeMillis() - 86400000L * 4
    )
    val u3Salt = SecurityUtils.generateSalt()
    val user3 = UserEntity(
      id = "usr_ramesh",
      hostId = demoHost.id,
      hostCode = demoHost.hostCode,
      name = "Ramesh Kumar",
      village = "Bishunpur",
      gender = "Male",
      mobile = "9123456782",
      passwordHash = SecurityUtils.hashPassword("user123", u3Salt),
      salt = u3Salt,
      createdAt = System.currentTimeMillis() - 86400000L * 3
    )
    userDao.insertUser(user1)
    userDao.insertUser(user2)
    userDao.insertUser(user3)

    // Demo Payments:
    // 1. Approved payment for Suresh (₹8,500)
    val approvedPayment = PaymentEntity(
      transactionId = "TXN-20261001-4912",
      userId = user1.id,
      userName = user1.name,
      userVillage = user1.village,
      hostId = demoHost.id,
      hostName = demoHost.name,
      amount = 8500.0,
      date = "01 Oct 2026",
      time = "11:30 AM",
      timestamp = System.currentTimeMillis() - 86400000L * 3,
      note = "Harvest labor advance payment for week 1",
      status = PaymentStatus.APPROVED,
      approvedAt = System.currentTimeMillis() - 86400000L * 3 + 3600000L
    )

    // 2. Pending payment for Suresh (₹4,200) - READY for user approval test!
    val pendingPayment = PaymentEntity(
      transactionId = "TXN-20261004-8194",
      userId = user1.id,
      userName = user1.name,
      userVillage = user1.village,
      hostId = demoHost.id,
      hostName = demoHost.name,
      amount = 4200.0,
      date = "04 Oct 2026",
      time = "09:15 AM",
      timestamp = System.currentTimeMillis() - 3600000L,
      note = "Grain transport compensation & fuel charges",
      status = PaymentStatus.PENDING_USER_APPROVAL
    )

    // 3. Approved payment for Anita (₹6,000)
    val approvedPayment2 = PaymentEntity(
      transactionId = "TXN-20261002-3104",
      userId = user2.id,
      userName = user2.name,
      userVillage = user2.village,
      hostId = demoHost.id,
      hostName = demoHost.name,
      amount = 6000.0,
      date = "02 Oct 2026",
      time = "04:45 PM",
      timestamp = System.currentTimeMillis() - 86400000L * 2,
      note = "Field maintenance & seed distribution payout",
      status = PaymentStatus.APPROVED,
      approvedAt = System.currentTimeMillis() - 86400000L * 2 + 1800000L
    )

    // 4. Rejected payment for Ramesh (₹3,000)
    val rejectedPayment = PaymentEntity(
      transactionId = "TXN-20260930-1928",
      userId = user3.id,
      userName = user3.name,
      userVillage = user3.village,
      hostId = demoHost.id,
      hostName = demoHost.name,
      amount = 3000.0,
      date = "30 Sep 2026",
      time = "02:10 PM",
      timestamp = System.currentTimeMillis() - 86400000L * 4,
      note = "Tractor rental reimbursement",
      status = PaymentStatus.REJECTED_BY_USER,
      rejectionReason = "Incorrect amount: Agreed fuel surcharge was ₹3,800, not ₹3,000.",
      rejectedAt = System.currentTimeMillis() - 86400000L * 4 + 7200000L
    )

    paymentDao.insertPayment(approvedPayment)
    paymentDao.insertPayment(pendingPayment)
    paymentDao.insertPayment(approvedPayment2)
    paymentDao.insertPayment(rejectedPayment)

    // Insert Notifications
    notificationDao.insertNotification(
      NotificationEntity(
        id = "notif_user_1",
        recipientType = "USER",
        recipientId = user1.id,
        title = "Action Needed: Payment Approval",
        message = "Ravi Sharma sent ₹4,200.00 for Grain transport. Tap to verify and approve.",
        transactionId = pendingPayment.transactionId,
        timestamp = pendingPayment.timestamp,
        isRead = false
      )
    )

    // Insert Audit Logs
    auditLogDao.insertLog(
      AuditLogEntity(
        id = "log_1",
        hostId = demoHost.id,
        actorName = demoHost.name,
        actorRole = "HOST",
        action = "Host Setup",
        details = "Host registered with code HP-5520",
        timestamp = demoHost.createdAt
      )
    )
    auditLogDao.insertLog(
      AuditLogEntity(
        id = "log_2",
        hostId = demoHost.id,
        actorName = user1.name,
        actorRole = "USER",
        action = "Payment Approved",
        details = "Suresh Patel approved TXN-20261001-4912 of ₹8,500.00",
        timestamp = approvedPayment.timestamp
      )
    )
    auditLogDao.insertLog(
      AuditLogEntity(
        id = "log_3",
        hostId = demoHost.id,
        actorName = demoHost.name,
        actorRole = "HOST",
        action = "Payment Created",
        details = "Created payment TXN-20261004-8194 of ₹4,200.00 for Suresh Patel",
        timestamp = pendingPayment.timestamp
      )
    )
  }
}
