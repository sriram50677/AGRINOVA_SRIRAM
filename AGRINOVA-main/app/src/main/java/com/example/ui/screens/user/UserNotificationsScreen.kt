package com.example.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.screens.host.NotificationItemCard
import com.example.ui.viewmodel.HostPayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNotificationsScreen(
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val notifications by viewModel.userNotifications.collectAsState()
  val payments by viewModel.userPayments.collectAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Payment Alerts", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("user_notifications_back")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        actions = {
          if (notifications.any { !it.isRead }) {
            TextButton(onClick = { viewModel.markAllNotificationsAsRead() }) {
              Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Mark All Read", fontSize = 12.sp)
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    }
  ) { padding ->
    if (notifications.isEmpty()) {
      EmptyPlaceholder(
        title = "No Notifications",
        description = "You'll see alerts here when your Host creates a payment for you or updates your status.",
        icon = Icons.Default.NotificationsNone,
        modifier = modifier.padding(padding)
      )
    } else {
      LazyColumn(
        modifier = modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background)
          .padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(notifications, key = { it.id }) { notif ->
          NotificationItemCard(
            notif = notif,
            onItemClick = {
              viewModel.markNotificationAsRead(notif.id)
              // If notification has transaction ID, open payment details
              if (!notif.transactionId.isNullOrEmpty()) {
                val matchingPayment = payments.find { it.transactionId == notif.transactionId }
                if (matchingPayment != null) {
                  viewModel.openPaymentDetails(matchingPayment)
                }
              }
            }
          )
        }
      }
    }
  }
}
