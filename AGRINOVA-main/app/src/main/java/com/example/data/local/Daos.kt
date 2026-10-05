package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AuditLogEntity
import com.example.data.model.HostEntity
import com.example.data.model.NotificationEntity
import com.example.data.model.PaymentEntity
import com.example.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HostDao {
  @Query("SELECT * FROM hosts WHERE mobile = :mobile LIMIT 1")
  suspend fun getHostByMobile(mobile: String): HostEntity?

  @Query("SELECT * FROM hosts WHERE hostCode = :code LIMIT 1")
  suspend fun getHostByCode(code: String): HostEntity?

  @Query("SELECT * FROM hosts WHERE id = :id LIMIT 1")
  suspend fun getHostById(id: String): HostEntity?

  @Query("SELECT * FROM hosts WHERE id = :id LIMIT 1")
  fun observeHostById(id: String): Flow<HostEntity?>

  @Query("SELECT * FROM hosts")
  fun getAllHosts(): Flow<List<HostEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertHost(host: HostEntity)
}

@Dao
interface UserDao {
  @Query("SELECT * FROM users WHERE mobile = :mobile LIMIT 1")
  suspend fun getUserByMobile(mobile: String): UserEntity?

  @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
  suspend fun getUserById(id: String): UserEntity?

  @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
  fun observeUserById(id: String): Flow<UserEntity?>

  @Query("SELECT * FROM users WHERE hostId = :hostId ORDER BY name ASC")
  fun getUsersByHost(hostId: String): Flow<List<UserEntity>>

  @Query("SELECT COUNT(*) FROM users WHERE hostId = :hostId")
  fun getUserCountByHost(hostId: String): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUser(user: UserEntity)

  @Query("SELECT * FROM users")
  fun getAllUsers(): Flow<List<UserEntity>>
}

@Dao
interface PaymentDao {
  @Query("SELECT * FROM payments WHERE transactionId = :id LIMIT 1")
  suspend fun getPaymentById(id: String): PaymentEntity?

  @Query("SELECT * FROM payments WHERE transactionId = :id LIMIT 1")
  fun observePaymentById(id: String): Flow<PaymentEntity?>

  @Query("SELECT * FROM payments WHERE userId = :userId ORDER BY timestamp DESC")
  fun getPaymentsByUser(userId: String): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE hostId = :hostId ORDER BY timestamp DESC")
  fun getPaymentsByHost(hostId: String): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE hostId = :hostId AND status = 'PENDING_USER_APPROVAL' ORDER BY timestamp DESC")
  fun getPendingPaymentsByHost(hostId: String): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE userId = :userId AND status = 'PENDING_USER_APPROVAL' ORDER BY timestamp DESC")
  fun getPendingPaymentsByUser(userId: String): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE userId = :userId AND hostId = :hostId ORDER BY timestamp DESC")
  fun getPaymentsByUserAndHost(userId: String, hostId: String): Flow<List<PaymentEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPayment(payment: PaymentEntity)

  @Update
  suspend fun updatePayment(payment: PaymentEntity)

  @Query("UPDATE payments SET status = :status, approvedAt = :approvedAt WHERE transactionId = :id")
  suspend fun approvePayment(id: String, status: String, approvedAt: Long)

  @Query("UPDATE payments SET status = :status, rejectionReason = :reason, rejectedAt = :rejectedAt WHERE transactionId = :id")
  suspend fun rejectPayment(id: String, status: String, reason: String, rejectedAt: Long)

  @Query("SELECT SUM(amount) FROM payments WHERE userId = :userId AND status = 'APPROVED'")
  fun getTotalApprovedAmountForUser(userId: String): Flow<Double?>

  @Query("SELECT SUM(amount) FROM payments WHERE hostId = :hostId AND status = 'APPROVED'")
  fun getTotalApprovedAmountForHost(hostId: String): Flow<Double?>

  @Query("SELECT COUNT(*) FROM payments WHERE hostId = :hostId AND status = 'APPROVED'")
  fun getApprovedCountForHost(hostId: String): Flow<Int>

  @Query("SELECT COUNT(*) FROM payments WHERE hostId = :hostId AND status = 'PENDING_USER_APPROVAL'")
  fun getPendingCountForHost(hostId: String): Flow<Int>
}

@Dao
interface NotificationDao {
  @Query("SELECT * FROM notifications WHERE recipientType = :type AND recipientId = :id ORDER BY timestamp DESC")
  fun getNotifications(type: String, id: String): Flow<List<NotificationEntity>>

  @Query("SELECT COUNT(*) FROM notifications WHERE recipientType = :type AND recipientId = :id AND isRead = 0")
  fun getUnreadCount(type: String, id: String): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNotification(notification: NotificationEntity)

  @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
  suspend fun markAsRead(id: String)

  @Query("UPDATE notifications SET isRead = 1 WHERE recipientType = :type AND recipientId = :id")
  suspend fun markAllAsRead(type: String, id: String)
}

@Dao
interface AuditLogDao {
  @Query("SELECT * FROM audit_logs WHERE hostId = :hostId ORDER BY timestamp DESC")
  fun getLogsByHost(hostId: String): Flow<List<AuditLogEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertLog(log: AuditLogEntity)
}
