package com.example.ui.screens.host

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PaymentStatus
import com.example.ui.theme.DangerRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import com.example.ui.viewmodel.HostPayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostReportsScreen(
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val payments by viewModel.hostPayments.collectAsState()
  val users by viewModel.hostUsers.collectAsState()

  val approvedPayments = remember(payments) { payments.filter { it.status == PaymentStatus.APPROVED } }
  val pendingPayments = remember(payments) { payments.filter { it.status == PaymentStatus.PENDING_USER_APPROVAL } }
  val rejectedPayments = remember(payments) { payments.filter { it.status == PaymentStatus.REJECTED_BY_USER } }

  val totalDisbursed = remember(approvedPayments) { approvedPayments.sumOf { it.amount } }
  val totalPending = remember(pendingPayments) { pendingPayments.sumOf { it.amount } }
  val totalRejected = remember(rejectedPayments) { rejectedPayments.sumOf { it.amount } }

  val approvalRate = remember(payments) {
    if (payments.isEmpty()) 0f else (approvedPayments.size.toFloat() / payments.size.toFloat())
  }

  // Village wise distribution
  val villageStats = remember(approvedPayments) {
    approvedPayments.groupBy { it.userVillage }
      .mapValues { entry -> entry.value.sumOf { it.amount } }
      .toList()
      .sortedByDescending { it.second }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Financial Reports & Audit", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("reports_back")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
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
      // Big Disbursed Hero Card
      item {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Text(
              text = "Total Disbursed (Verified)",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "₹${"%,.2f".format(totalDisbursed)}",
              style = MaterialTheme.typography.headlineLarge,
              fontWeight = FontWeight.ExtraBold,
              color = SuccessGreen
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Progress bar
            Text(
              text = "Approval Rate: ${(approvalRate * 100).toInt()}% (${approvedPayments.size} of ${payments.size} txns)",
              style = MaterialTheme.typography.bodySmall,
              fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
              progress = { approvalRate },
              modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
              color = SuccessGreen,
              trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
          }
        }
      }

      // Breakdown by Status
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
              text = "Transaction Status Breakdown",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )

            StatusBreakdownRow(
              icon = Icons.Default.CheckCircle,
              color = SuccessGreen,
              title = "Approved Payments",
              count = approvedPayments.size,
              amount = totalDisbursed
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            StatusBreakdownRow(
              icon = Icons.Default.HourglassTop,
              color = WarningAmber,
              title = "Pending Approvals",
              count = pendingPayments.size,
              amount = totalPending
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            StatusBreakdownRow(
              icon = Icons.Default.Warning,
              color = DangerRed,
              title = "Rejected by Users",
              count = rejectedPayments.size,
              amount = totalRejected
            )
          }
        }
      }

      // Village Breakdown
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Disbursement by Village",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (villageStats.isEmpty()) {
              Text(
                text = "No approved disbursements yet to calculate village distribution.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            } else {
              villageStats.forEach { (village, amount) ->
                val ratio = if (totalDisbursed > 0) (amount / totalDisbursed).toFloat() else 0f
                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Text(text = village, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text(text = "₹${"%,.0f".format(amount)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  LinearProgressIndicator(
                    progress = { ratio },
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(6.dp)
                      .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun StatusBreakdownRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  color: androidx.compose.ui.graphics.Color,
  title: String,
  count: Int,
  amount: Double
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        Text(text = "$count transaction(s)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
    Text(
      text = "₹${"%,.2f".format(amount)}",
      fontWeight = FontWeight.Bold,
      color = color,
      fontSize = 14.sp
    )
  }
}
