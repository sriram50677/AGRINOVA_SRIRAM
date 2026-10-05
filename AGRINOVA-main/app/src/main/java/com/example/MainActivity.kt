package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.data.local.HostPayDatabase
import com.example.data.repository.HostPayRepository
import com.example.ui.common.PaymentDetailDialog
import com.example.ui.screens.auth.AuthScreen
import com.example.ui.screens.host.HostAuditLogsScreen
import com.example.ui.screens.host.HostCreatePaymentScreen
import com.example.ui.screens.host.HostDashboardScreen
import com.example.ui.screens.host.HostNotificationsScreen
import com.example.ui.screens.host.HostPendingApprovalsScreen
import com.example.ui.screens.host.HostReportsScreen
import com.example.ui.screens.host.HostSettingsScreen
import com.example.ui.screens.host.HostUserDetailsScreen
import com.example.ui.screens.host.HostUsersScreen
import com.example.ui.screens.user.UserHomeScreen
import com.example.ui.screens.user.UserNotificationsScreen
import com.example.ui.screens.user.UserPaymentsScreen
import com.example.ui.screens.user.UserProfileScreen
import com.example.ui.theme.HostPayTheme
import com.example.ui.viewmodel.AppSession
import com.example.ui.viewmodel.HostPayViewModel
import com.example.ui.viewmodel.HostPayViewModelFactory
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val database = HostPayDatabase.getDatabase(applicationContext)
    val repository = HostPayRepository(
      hostDao = database.hostDao(),
      userDao = database.userDao(),
      paymentDao = database.paymentDao(),
      notificationDao = database.notificationDao(),
      auditLogDao = database.auditLogDao()
    )

    val viewModel: HostPayViewModel by viewModels {
      HostPayViewModelFactory(repository)
    }

    setContent {
      HostPayTheme {
        HostPayApp(viewModel = viewModel, onFinish = { finish() })
      }
    }
  }
}

@Composable
fun HostPayApp(
  viewModel: HostPayViewModel,
  onFinish: () -> Unit
) {
  val currentSession by viewModel.currentSession.collectAsState()
  val currentScreen by viewModel.currentScreen.collectAsState()
  val selectedPayment by viewModel.selectedPayment.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }

  // Listen to alerts from ViewModel
  LaunchedEffect(Unit) {
    viewModel.errorMessage.collectLatest { msg ->
      snackbarHostState.showSnackbar(
        message = msg,
        duration = SnackbarDuration.Short
      )
    }
  }

  LaunchedEffect(Unit) {
    viewModel.successMessage.collectLatest { msg ->
      snackbarHostState.showSnackbar(
        message = msg,
        duration = SnackbarDuration.Short
      )
    }
  }

  // Back handling
  BackHandler {
    if (selectedPayment != null) {
      viewModel.closePaymentDetails()
    } else {
      val handled = viewModel.navigateBack()
      if (!handled) {
        if (currentSession != AppSession.None) {
          viewModel.logout()
        } else {
          onFinish()
        }
      }
    }
  }

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    contentWindowInsets = WindowInsets.safeDrawing,
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .consumeWindowInsets(innerPadding)
        .imePadding()
    ) {
      when (val session = currentSession) {
        is AppSession.None -> {
          AuthScreen(viewModel = viewModel)
        }

        is AppSession.HostSession -> {
          when (val screen = currentScreen) {
            is Screen.HostDashboard -> HostDashboardScreen(host = session.host, viewModel = viewModel)
            is Screen.HostUsers -> HostUsersScreen(viewModel = viewModel)
            is Screen.HostUserDetails -> HostUserDetailsScreen(user = screen.user, viewModel = viewModel)
            is Screen.HostCreatePayment -> HostCreatePaymentScreen(preselectedUser = screen.preselectedUser, viewModel = viewModel)
            is Screen.HostPendingApprovals -> HostPendingApprovalsScreen(viewModel = viewModel)
            is Screen.HostReports -> HostReportsScreen(viewModel = viewModel)
            is Screen.HostAuditLogs -> HostAuditLogsScreen(viewModel = viewModel)
            is Screen.HostNotifications -> HostNotificationsScreen(viewModel = viewModel)
            is Screen.HostSettings -> HostSettingsScreen(host = session.host, viewModel = viewModel)
            else -> HostDashboardScreen(host = session.host, viewModel = viewModel)
          }
        }

        is AppSession.UserSession -> {
          when (currentScreen) {
            is Screen.UserHome -> UserHomeScreen(user = session.user, viewModel = viewModel)
            is Screen.UserPayments -> UserPaymentsScreen(viewModel = viewModel)
            is Screen.UserNotifications -> UserNotificationsScreen(viewModel = viewModel)
            is Screen.UserProfile -> UserProfileScreen(user = session.user, viewModel = viewModel)
            else -> UserHomeScreen(user = session.user, viewModel = viewModel)
          }
        }
      }

      // High-res payment inspection / approval workflow dialog
      selectedPayment?.let { payment ->
        val isCurrentUser = (currentSession as? AppSession.UserSession)?.user?.id == payment.userId
        PaymentDetailDialog(
          payment = payment,
          isCurrentUser = isCurrentUser,
          onDismiss = { viewModel.closePaymentDetails() },
          onApprove = { p -> viewModel.approvePayment(p) },
          onReject = { p, reason -> viewModel.rejectPayment(p, reason) }
        )
      }
    }
  }
}
