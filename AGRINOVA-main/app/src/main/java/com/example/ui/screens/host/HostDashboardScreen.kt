package com.example.ui.screens.host

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HostEntity
import com.example.data.model.PaymentStatus
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.common.HostCodeBadge
import com.example.ui.common.PaymentItemCard
import com.example.ui.common.StatCard
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberLight
import com.example.ui.viewmodel.HostPayViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostDashboardScreen(
  host: HostEntity,
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val metrics by viewModel.hostMetrics.collectAsState()
  val payments by viewModel.hostPayments.collectAsState()
  val pendingPayments by viewModel.hostPendingPayments.collectAsState()
  val notifications by viewModel.hostNotifications.collectAsState()
  val unreadNotifs = notifications.count { !it.isRead }

  var filterStatus by remember { mutableStateOf("ALL") }

  val filteredPayments = remember(payments, filterStatus) {
    when (filterStatus) {
      "APPROVED" -> payments.filter { it.status == PaymentStatus.APPROVED }
      "PENDING" -> payments.filter { it.status == PaymentStatus.PENDING_USER_APPROVAL }
      "REJECTED" -> payments.filter { it.status == PaymentStatus.REJECTED_BY_USER }
      else -> payments
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = host.name,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = host.organization,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        },
        actions = {
          HostCodeBadge(hostCode = host.hostCode)
          Spacer(modifier = Modifier.width(4.dp))
          IconButton(
            onClick = { viewModel.navigateTo(Screen.HostNotifications) },
            modifier = Modifier.testTag("host_notification_bell")
          ) {
            BadgedBox(
              badge = {
                if (unreadNotifs > 0) {
                  Badge { Text(unreadNotifs.toString()) }
                }
              }
            ) {
              Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            }
          }
          IconButton(
            onClick = { viewModel.navigateTo(Screen.HostSettings) },
            modifier = Modifier.testTag("host_settings_button")
          ) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = { viewModel.navigateTo(Screen.HostCreatePayment()) },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.testTag("fab_create_payment")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Add, contentDescription = null)
          Spacer(modifier = Modifier.width(6.dp))
          Text("New Payment", fontWeight = FontWeight.Bold)
        }
      }
    }
  ) { padding ->
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(padding),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // 4 Core Metrics
      item {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            StatCard(
              title = "Total Users",
              value = metrics.totalUsers.toString(),
              subtitle = "Registered under code",
              icon = Icons.Default.People,
              iconTint = BrandBlue,
              modifier = Modifier.weight(1f)
            )
            StatCard(
              title = "Total Disbursed",
              value = "₹${"%,.0f".format(metrics.totalConfirmedAmount)}",
              subtitle = "Approved payments",
              icon = Icons.Default.Payments,
              iconTint = SuccessGreen,
              modifier = Modifier.weight(1f)
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            StatCard(
              title = "Confirmed Txns",
              value = metrics.confirmedPaymentsCount.toString(),
              subtitle = "Approved by users",
              icon = Icons.Default.CheckCircle,
              iconTint = SuccessGreen,
              modifier = Modifier.weight(1f)
            )
            StatCard(
              title = "Pending Approvals",
              value = metrics.pendingApprovalsCount.toString(),
              subtitle = "₹${"%,.0f".format(metrics.pendingAmount)} pending",
              icon = Icons.Default.HourglassTop,
              iconTint = WarningAmber,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }

      // Quick Nav Buttons
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "Management & Actions",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ActionPill(
                label = "All Users",
                icon = Icons.Default.People,
                onClick = { viewModel.navigateTo(Screen.HostUsers) },
                modifier = Modifier.weight(1f)
              )
              ActionPill(
                label = "Pending (${pendingPayments.size})",
                icon = Icons.Default.HourglassTop,
                onClick = { viewModel.navigateTo(Screen.HostPendingApprovals) },
                modifier = Modifier.weight(1f)
              )
              ActionPill(
                label = "Reports",
                icon = Icons.Default.Assessment,
                onClick = { viewModel.navigateTo(Screen.HostReports) },
                modifier = Modifier.weight(1f)
              )
              ActionPill(
                label = "Audit Logs",
                icon = Icons.Default.History,
                onClick = { viewModel.navigateTo(Screen.HostAuditLogs) },
                modifier = Modifier.weight(1f)
              )
            }
          }
        }
      }

      // Pending Approvals Banner (if any)
      if (pendingPayments.isNotEmpty()) {
        item {
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = WarningAmberLight,
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(14.dp))
              .clickable { viewModel.navigateTo(Screen.HostPendingApprovals) }
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  Icons.Default.HourglassTop,
                  contentDescription = null,
                  tint = WarningAmber,
                  modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "${pendingPayments.size} Payment(s) Awaiting User Action",
                    fontWeight = FontWeight.Bold,
                    color = WarningAmber,
                    fontSize = 14.sp
                  )
                  Text(
                    text = "Awaiting user confirmation before funds count",
                    fontSize = 11.sp,
                    color = WarningAmber.copy(alpha = 0.9f)
                  )
                }
              }
              Text(
                text = "View All →",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = WarningAmber
              )
            }
          }
        }
      }

      // Recent Transactions Header & Filters
      item {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Payment Records",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "${filteredPayments.size} records",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val filters = listOf("ALL" to "All", "APPROVED" to "Approved", "PENDING" to "Pending", "REJECTED" to "Rejected")
            items(filters) { (key, label) ->
              FilterChip(
                selected = filterStatus == key,
                onClick = { filterStatus = key },
                label = { Text(label, fontSize = 12.sp) }
              )
            }
          }
        }
      }

      // Transaction List
      if (filteredPayments.isEmpty()) {
        item {
          EmptyPlaceholder(
            title = "No payments found",
            description = "Click 'New Payment' below to create a payment record for any registered user.",
            icon = Icons.Default.Payments
          )
        }
      } else {
        items(filteredPayments, key = { it.transactionId }) { payment ->
          PaymentItemCard(
            payment = payment,
            isHostView = true,
            onClick = { viewModel.openPaymentDetails(payment) }
          )
        }
      }

      item {
        Spacer(modifier = Modifier.height(60.dp))
      }
    }
  }
}

@Composable
private fun ActionPill(
  label: String,
  icon: ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() },
    color = MaterialTheme.colorScheme.surfaceVariant,
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(
      modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = label,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onSurface
      )
    }
  }
}
