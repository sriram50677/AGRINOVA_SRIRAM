package com.example.ui.screens.host

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PaymentStatus
import com.example.data.model.UserEntity
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.common.PaymentItemCard
import com.example.ui.common.UserAvatar
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.HostPayViewModel
import com.example.ui.viewmodel.Screen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostUserDetailsScreen(
  user: UserEntity,
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val allPayments by viewModel.hostPayments.collectAsState()
  val userPayments = remember(allPayments, user.id) {
    allPayments.filter { it.userId == user.id }
  }

  val totalApproved = remember(userPayments) {
    userPayments.filter { it.status == PaymentStatus.APPROVED }.sumOf { it.amount }
  }

  val approvedCount = remember(userPayments) {
    userPayments.count { it.status == PaymentStatus.APPROVED }
  }

  val pendingCount = remember(userPayments) {
    userPayments.count { it.status == PaymentStatus.PENDING_USER_APPROVAL }
  }

  val regDate = remember(user.createdAt) {
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(user.createdAt))
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(user.name, fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("user_details_back_button")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    },
    bottomBar = {
      Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Button(
          onClick = { viewModel.navigateTo(Screen.HostCreatePayment(user)) },
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
            .testTag("details_create_payment_button"),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(Icons.Default.Add, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Create Payment for ${user.name}", fontWeight = FontWeight.Bold)
        }
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
      // User Profile Card (Read Only)
      item {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            UserAvatar(photoUri = user.profilePhotoUri, name = user.name, sizeDp = 76)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = user.name,
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold
            )
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.padding(top = 4.dp)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Verified Read-Only Profile",
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(12.dp))

            Column(
              modifier = Modifier.fillMaxWidth(),
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              RowInfo(label = "Village / Town", value = user.village)
              RowInfo(label = "Gender", value = user.gender)
              RowInfo(label = "Mobile / Login ID", value = user.mobile)
              RowInfo(label = "Host Code Joined", value = user.hostCode)
              RowInfo(label = "Member Since", value = regDate)
            }
          }
        }
      }

      // Financial Stats for User
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "Total Paid",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = "₹${"%,.0f".format(totalApproved)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = SuccessGreen
              )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "Approved",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = approvedCount.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "Pending",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = pendingCount.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
              )
            }
          }
        }
      }

      // Payment History Header
      item {
        Text(
          text = "Payment History (${userPayments.size})",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }

      if (userPayments.isEmpty()) {
        item {
          EmptyPlaceholder(
            title = "No payments yet",
            description = "Click below to create the first payment record for ${user.name}.",
            icon = Icons.Default.Payments
          )
        }
      } else {
        items(userPayments, key = { it.transactionId }) { payment ->
          PaymentItemCard(
            payment = payment,
            isHostView = true,
            onClick = { viewModel.openPaymentDetails(payment) }
          )
        }
      }

      item {
        Spacer(modifier = Modifier.height(20.dp))
      }
    }
  }
}

@Composable
private fun RowInfo(label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}
