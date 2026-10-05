package com.example.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserEntity
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.common.PaymentItemCard
import com.example.ui.common.UserAvatar
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberLight
import com.example.ui.viewmodel.HostPayViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(
  user: UserEntity,
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val payments by viewModel.userPayments.collectAsState()
  val pendingPayments by viewModel.userPendingPayments.collectAsState()
  val totalApprovedAmount by viewModel.userTotalApprovedAmount.collectAsState()
  val notifications by viewModel.userNotifications.collectAsState()
  val unreadCount = notifications.count { !it.isRead }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            UserAvatar(
              photoUri = user.profilePhotoUri,
              name = user.name,
              sizeDp = 40,
              modifier = Modifier.clickable { viewModel.navigateTo(Screen.UserProfile) }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${user.village} • Host: ${user.hostCode}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        },
        actions = {
          IconButton(
            onClick = { viewModel.navigateTo(Screen.UserNotifications) },
            modifier = Modifier.testTag("user_notification_bell")
          ) {
            BadgedBox(
              badge = {
                if (unreadCount > 0) {
                  Badge { Text(unreadCount.toString()) }
                }
              }
            ) {
              Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            }
          }
          IconButton(
            onClick = { viewModel.navigateTo(Screen.UserProfile) },
            modifier = Modifier.testTag("user_profile_button")
          ) {
            Icon(Icons.Default.Person, contentDescription = "Profile")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    },
    bottomBar = {
      NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
          selected = true,
          onClick = { /* Already on Home */ },
          icon = { Icon(Icons.Default.Payments, contentDescription = "Home") },
          label = { Text("Home") },
          modifier = Modifier.testTag("nav_user_home")
        )
        NavigationBarItem(
          selected = false,
          onClick = { viewModel.navigateTo(Screen.UserPayments) },
          icon = { Icon(Icons.Default.HourglassTop, contentDescription = "Payments") },
          label = { Text("Payments") },
          modifier = Modifier.testTag("nav_user_payments")
        )
        NavigationBarItem(
          selected = false,
          onClick = { viewModel.navigateTo(Screen.UserNotifications) },
          icon = {
            BadgedBox(
              badge = {
                if (unreadCount > 0) Badge { Text(unreadCount.toString()) }
              }
            ) {
              Icon(Icons.Default.Notifications, contentDescription = "Alerts")
            }
          },
          label = { Text("Alerts") },
          modifier = Modifier.testTag("nav_user_notifications")
        )
        NavigationBarItem(
          selected = false,
          onClick = { viewModel.navigateTo(Screen.UserProfile) },
          icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
          label = { Text("Profile") },
          modifier = Modifier.testTag("nav_user_profile")
        )
      }
    }
  ) { padding ->
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(padding),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Host info banner
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Apartment, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Linked to Host Code: ",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = user.hostCode,
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
          }
        }
      }

      // Hero Total Received Amount Card
      item {
        Card(
          shape = RoundedCornerShape(22.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(22.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Total Received Amount",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
              ) {
                Text(
                  text = "Verified Only",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "₹${"%,.2f".format(totalApprovedAmount)}",
              style = MaterialTheme.typography.displaySmall,
              fontWeight = FontWeight.ExtraBold,
              color = SuccessGreen
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "• Only payments you have approved count toward your total earnings.",
              style = MaterialTheme.typography.bodySmall,
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      // Action Required: Pending Approval Banner
      if (pendingPayments.isNotEmpty()) {
        item {
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = WarningAmberLight),
            modifier = Modifier.testTag("user_pending_alert_card")
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(WarningAmber.copy(alpha = 0.2f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(Icons.Default.HourglassTop, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "Action Required: Payment Received",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = WarningAmber
                  )
                  Text(
                    text = "You have ${pendingPayments.size} pending confirmation request(s).",
                    style = MaterialTheme.typography.bodySmall,
                    color = WarningAmber.copy(alpha = 0.9f)
                  )
                }
              }

              Spacer(modifier = Modifier.height(12.dp))

              // Show the first pending payment preview
              val topPending = pendingPayments.first()
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(text = "From: ${topPending.hostName}", fontWeight = FontWeight.SemiBold)
                    Text(
                      text = "₹${"%,.2f".format(topPending.amount)}",
                      fontWeight = FontWeight.Bold,
                      fontSize = 18.sp,
                      color = MaterialTheme.colorScheme.primary
                    )
                  }
                  if (!topPending.note.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Note: ${topPending.note}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                  }
                  Spacer(modifier = Modifier.height(10.dp))
                  Button(
                    onClick = { viewModel.openPaymentDetails(topPending) },
                    modifier = Modifier
                      .fillMaxWidth()
                      .testTag("review_top_pending_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp)
                  ) {
                    Text("Review, View Proof & Approve / Reject")
                  }
                }
              }
            }
          }
        }
      }

      // Recent Activity Header
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Your Payment History",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          if (payments.isNotEmpty()) {
            OutlinedButton(
              onClick = { viewModel.navigateTo(Screen.UserPayments) },
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("View All", fontSize = 12.sp)
            }
          }
        }
      }

      if (payments.isEmpty()) {
        item {
          EmptyPlaceholder(
            title = "No payments recorded yet",
            description = "When your Host creates a payment for you, it will appear here for your verification and approval.",
            icon = Icons.Default.Payments
          )
        }
      } else {
        items(payments.take(5), key = { it.transactionId }) { payment ->
          PaymentItemCard(
            payment = payment,
            isHostView = false,
            onClick = { viewModel.openPaymentDetails(payment) }
          )
        }
      }

      item {
        Spacer(modifier = Modifier.height(40.dp))
      }
    }
  }
}
