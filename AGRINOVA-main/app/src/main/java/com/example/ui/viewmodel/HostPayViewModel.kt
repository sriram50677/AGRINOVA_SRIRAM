package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.AuditLogEntity
import com.example.data.model.HostEntity
import com.example.data.model.NotificationEntity
import com.example.data.model.PaymentEntity
import com.example.data.model.UserEntity
import com.example.data.repository.AuthResult
import com.example.data.repository.HostPayRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class AppSession {
  object None : AppSession()
  data class HostSession(val host: HostEntity) : AppSession()
  data class UserSession(val user: UserEntity) : AppSession()
}

sealed class Screen {
  object Splash : Screen()
  object Auth : Screen()

  // Host screens
  object HostDashboard : Screen()
  object HostUsers : Screen()
  data class HostUserDetails(val user: UserEntity) : Screen()
  data class HostCreatePayment(val preselectedUser: UserEntity? = null) : Screen()
  object HostPendingApprovals : Screen()
  object HostReports : Screen()
  object HostAuditLogs : Screen()
  object HostNotifications : Screen()
  object HostSettings : Screen()

  // User screens
  object UserHome : Screen()
  object UserPayments : Screen()
  object UserNotifications : Screen()
  object UserProfile : Screen()
}

data class HostDashboardMetrics(
  val totalUsers: Int = 0,
  val totalConfirmedAmount: Double = 0.0,
  val confirmedPaymentsCount: Int = 0,
  val pendingApprovalsCount: Int = 0,
  val pendingAmount: Double = 0.0
)

class HostPayViewModel(
  private val repository: HostPayRepository
) : ViewModel() {

  private val _currentSession = MutableStateFlow<AppSession>(AppSession.None)
  val currentSession: StateFlow<AppSession> = _currentSession.asStateFlow()

  private val _currentScreen = MutableStateFlow<Screen>(Screen.Auth)
  val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

  private val screenBackStack = mutableListOf<Screen>()

  private val _errorMessage = MutableSharedFlow<String>()
  val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

  private val _successMessage = MutableSharedFlow<String>()
  val successMessage: SharedFlow<String> = _successMessage.asSharedFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

  // Selected payment for inspection or approval
  private val _selectedPayment = MutableStateFlow<PaymentEntity?>(null)
  val selectedPayment: StateFlow<PaymentEntity?> = _selectedPayment.asStateFlow()

  init {
    viewModelScope.launch {
      repository.seedDemoDataIfEmpty()
    }
  }

  fun navigateTo(screen: Screen) {
    screenBackStack.add(_currentScreen.value)
    _currentScreen.value = screen
  }

  fun navigateBack(): Boolean {
    if (screenBackStack.isNotEmpty()) {
      val prev = screenBackStack.removeAt(screenBackStack.lastIndex)
      _currentScreen.value = prev
      return true
    }
    return false
  }

  fun logout() {
    screenBackStack.clear()
    _currentSession.value = AppSession.None
    _currentScreen.value = Screen.Auth
  }

  // ================= HOST AUTH & ACTIONS =================

  fun registerHost(
    name: String,
    organization: String,
    mobile: String,
    password: String,
    onSuccess: (HostEntity) -> Unit = {}
  ) {
    viewModelScope.launch {
      _isLoading.value = true
      when (val result = repository.registerHost(name, organization, mobile, password)) {
        is AuthResult.Success -> {
          _currentSession.value = AppSession.HostSession(result.data)
          screenBackStack.clear()
          _currentScreen.value = Screen.HostDashboard
          _successMessage.emit("Host registered! Your code is ${result.data.hostCode}")
          onSuccess(result.data)
        }
        is AuthResult.Error -> {
          _errorMessage.emit(result.message)
        }
      }
      _isLoading.value = false
    }
  }

  fun loginHost(mobile: String, password: String) {
    viewModelScope.launch {
      _isLoading.value = true
      when (val result = repository.loginHost(mobile, password)) {
        is AuthResult.Success -> {
          _currentSession.value = AppSession.HostSession(result.data)
          screenBackStack.clear()
          _currentScreen.value = Screen.HostDashboard
          _successMessage.emit("Welcome back, ${result.data.name}!")
        }
        is AuthResult.Error -> {
          _errorMessage.emit(result.message)
        }
      }
      _isLoading.value = false
    }
  }

  // ================= USER AUTH & ACTIONS =================

  fun registerUser(
    hostCode: String,
    name: String,
    village: String,
    gender: String,
    mobile: String,
    password: String,
    profilePhotoUri: String? = null
  ) {
    viewModelScope.launch {
      _isLoading.value = true
      when (val result = repository.registerUser(
        hostCode = hostCode,
        name = name,
        village = village,
        gender = gender,
        mobile = mobile,
        password = password,
        profilePhotoUri = profilePhotoUri
      )) {
        is AuthResult.Success -> {
          _currentSession.value = AppSession.UserSession(result.data)
          screenBackStack.clear()
          _currentScreen.value = Screen.UserHome
          _successMessage.emit("Welcome to HostPay, ${result.data.name}!")
        }
        is AuthResult.Error -> {
          _errorMessage.emit(result.message)
        }
      }
      _isLoading.value = false
    }
  }

  fun loginUser(mobile: String, password: String) {
    viewModelScope.launch {
      _isLoading.value = true
      when (val result = repository.loginUser(mobile, password)) {
        is AuthResult.Success -> {
          _currentSession.value = AppSession.UserSession(result.data)
          screenBackStack.clear()
          _currentScreen.value = Screen.UserHome
          _successMessage.emit("Welcome back, ${result.data.name}!")
        }
        is AuthResult.Error -> {
          _errorMessage.emit(result.message)
        }
      }
      _isLoading.value = false
    }
  }

  // Quick switch for demo testing in browser emulator
  fun quickLoginDemoHost() {
    loginHost("9876543210", "host123")
  }

  fun quickLoginDemoUser() {
    loginUser("9123456780", "user123")
  }

  // ================= PAYMENT WORKFLOW ACTIONS =================

  fun createPayment(
    user: UserEntity,
    amount: Double,
    note: String?,
    proofPhotoUri: String?,
    onComplete: () -> Unit
  ) {
    val session = _currentSession.value as? AppSession.HostSession ?: return
    viewModelScope.launch {
      _isLoading.value = true
      val res = repository.createPayment(
        host = session.host,
        user = user,
        amount = amount,
        note = note,
        proofPhotoUri = proofPhotoUri
      )
      res.onSuccess {
        _successMessage.emit("Payment sent to ${user.name} for approval!")
        onComplete()
      }.onFailure {
        _errorMessage.emit(it.message ?: "Failed to create payment")
      }
      _isLoading.value = false
    }
  }

  fun approvePayment(payment: PaymentEntity, onComplete: () -> Unit = {}) {
    val session = _currentSession.value as? AppSession.UserSession ?: return
    viewModelScope.launch {
      _isLoading.value = true
      val res = repository.approvePayment(payment.transactionId, session.user)
      res.onSuccess {
        _successMessage.emit("Payment ₹${"%.2f".format(payment.amount)} Approved Successfully!")
        _selectedPayment.value = null
        onComplete()
      }.onFailure {
        _errorMessage.emit(it.message ?: "Failed to approve payment")
      }
      _isLoading.value = false
    }
  }

  fun rejectPayment(payment: PaymentEntity, reason: String, onComplete: () -> Unit = {}) {
    val session = _currentSession.value as? AppSession.UserSession ?: return
    viewModelScope.launch {
      _isLoading.value = true
      val res = repository.rejectPayment(payment.transactionId, session.user, reason)
      res.onSuccess {
        _successMessage.emit("Payment rejected. Host notified.")
        _selectedPayment.value = null
        onComplete()
      }.onFailure {
        _errorMessage.emit(it.message ?: "Failed to reject payment")
      }
      _isLoading.value = false
    }
  }

  fun openPaymentDetails(payment: PaymentEntity) {
    _selectedPayment.value = payment
  }

  fun closePaymentDetails() {
    _selectedPayment.value = null
  }

  // ================= REACTIVE DATA STREAMS =================

  // Host Users
  val hostUsers: StateFlow<List<UserEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getUsersByHost(session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Host Payments
  val hostPayments: StateFlow<List<PaymentEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getPaymentsByHost(session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Host Pending Payments
  val hostPendingPayments: StateFlow<List<PaymentEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getPendingPaymentsByHost(session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Host Dashboard Metrics
  val hostMetrics: StateFlow<HostDashboardMetrics> = combine(
    hostUsers,
    hostPayments,
    hostPendingPayments
  ) { users, payments, pending ->
    val confirmedPayments = payments.filter { it.status == "APPROVED" }
    val totalConfirmed = confirmedPayments.sumOf { it.amount }
    val pendingTotal = pending.sumOf { it.amount }
    HostDashboardMetrics(
      totalUsers = users.size,
      totalConfirmedAmount = totalConfirmed,
      confirmedPaymentsCount = confirmedPayments.size,
      pendingApprovalsCount = pending.size,
      pendingAmount = pendingTotal
    )
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HostDashboardMetrics())

  // Host Notifications
  val hostNotifications: StateFlow<List<NotificationEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getNotifications("HOST", session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Host Audit Logs
  val hostAuditLogs: StateFlow<List<AuditLogEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getAuditLogs(session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // User Payments
  val userPayments: StateFlow<List<PaymentEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.UserSession) {
      repository.getPaymentsByUser(session.user.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // User Pending Payments
  val userPendingPayments: StateFlow<List<PaymentEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.UserSession) {
      repository.getPendingPaymentsByUser(session.user.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // User Total Approved Amount
  val userTotalApprovedAmount: StateFlow<Double> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.UserSession) {
      repository.getTotalApprovedAmountForUser(session.user.id)
    } else {
      flowOf(0.0)
    }
  }.flatMapLatest { amount ->
    flowOf(amount ?: 0.0)
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

  // User Notifications
  val userNotifications: StateFlow<List<NotificationEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.UserSession) {
      repository.getNotifications("USER", session.user.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun markNotificationAsRead(id: String) {
    viewModelScope.launch {
      repository.markNotificationAsRead(id)
    }
  }

  fun markAllNotificationsAsRead() {
    val session = _currentSession.value
    viewModelScope.launch {
      when (session) {
        is AppSession.HostSession -> repository.markAllNotificationsAsRead("HOST", session.host.id)
        is AppSession.UserSession -> repository.markAllNotificationsAsRead("USER", session.user.id)
        AppSession.None -> {}
      }
    }
  }

  suspend fun checkHostCode(code: String): HostEntity? {
    return repository.findHostByCode(code)
  }
}

class HostPayViewModelFactory(
  private val repository: HostPayRepository
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(HostPayViewModel::class.java)) {
      return HostPayViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
