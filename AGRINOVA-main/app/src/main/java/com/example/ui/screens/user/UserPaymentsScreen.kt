package com.example.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PaymentStatus
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.common.PaymentItemCard
import com.example.ui.viewmodel.HostPayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPaymentsScreen(
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val payments by viewModel.userPayments.collectAsState()
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
        title = { Text("Payment History", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("payments_back_button")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    }
  ) { padding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(padding)
    ) {
      // Filter Tabs
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        val filters = listOf(
          "ALL" to "All Records (${payments.size})",
          "APPROVED" to "Approved",
          "PENDING" to "Pending Approval",
          "REJECTED" to "Rejected"
        )
        items(filters) { (key, label) ->
          FilterChip(
            selected = filterStatus == key,
            onClick = { filterStatus = key },
            label = { Text(label, fontSize = 12.sp) }
          )
        }
      }

      if (filteredPayments.isEmpty()) {
        EmptyPlaceholder(
          title = "No payments found",
          description = "No payment records match the selected filter.",
          icon = Icons.Default.Payments
        )
      } else {
        LazyColumn(
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(filteredPayments, key = { it.transactionId }) { payment ->
            PaymentItemCard(
              payment = payment,
              isHostView = false,
              onClick = { viewModel.openPaymentDetails(payment) }
            )
          }
        }
      }
    }
  }
}
