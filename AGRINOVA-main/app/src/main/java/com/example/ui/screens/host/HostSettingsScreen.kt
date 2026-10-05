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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HostEntity
import com.example.ui.common.HostCodeBadge
import com.example.ui.theme.DangerRed
import com.example.ui.viewmodel.HostPayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostSettingsScreen(
  host: HostEntity,
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Host Account & Settings", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("settings_back")
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
      // Host Code Feature Card
      item {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "Your Host Joining Code",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Workers/users enter this code during registration to join your organization.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
              fontSize = 12.sp,
              lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
            HostCodeBadge(hostCode = host.hostCode)
          }
        }
      }

      // Profile details
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
              text = "Host Details",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            SettingInfoRow(icon = Icons.Default.Person, label = "Host Name", value = host.name)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            SettingInfoRow(icon = Icons.Default.Apartment, label = "Organization", value = host.organization)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            SettingInfoRow(icon = Icons.Default.Phone, label = "Mobile / Login ID", value = host.mobile)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            SettingInfoRow(icon = Icons.Default.QrCode, label = "Unique Host Code", value = host.hostCode)
          }
        }
      }

      // Security & Authorization Rules
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Security, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Security & Ledger Integrity",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
            }
            Text(
              text = "• Role-Based Authorization: Hosts can record payments but cannot approve them.\n" +
                "• Recipient Verification: Only the registered user can approve each payment.\n" +
                "• Cryptographic Passwords: Passwords are protected using salted SHA-256 digests.\n" +
                "• Tamper-Proof Audit Trail: Every transaction, approval, and rejection is permanently timestamped.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 20.sp
            )
          }
        }
      }

      // Logout Action
      item {
        Button(
          onClick = { viewModel.logout() },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("host_logout_button"),
          colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Sign Out of Host Account", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
private fun SettingInfoRow(icon: ImageVector, label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
      Spacer(modifier = Modifier.width(10.dp))
      Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
    Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
  }
}
