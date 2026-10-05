package com.example.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.data.model.PaymentEntity
import com.example.data.model.PaymentStatus
import com.example.ui.theme.DangerRed
import com.example.ui.theme.DangerRedLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import java.io.File

@Composable
fun PaymentDetailDialog(
  payment: PaymentEntity,
  isCurrentUser: Boolean,
  onDismiss: () -> Unit,
  onApprove: (PaymentEntity) -> Unit,
  onReject: (PaymentEntity, String) -> Unit
) {
  var showConfirmApproveDialog by remember { mutableStateOf(false) }
  var showRejectDialog by remember { mutableStateOf(false) }
  var rejectionReasonInput by remember { mutableStateOf("") }
  var rejectionError by remember { mutableStateOf<String?>(null) }
  var isZoomedImage by remember { mutableStateOf(false) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .clip(RoundedCornerShape(24.dp))
        .testTag("payment_detail_dialog"),
      color = MaterialTheme.colorScheme.surface,
      tonalElevation = 6.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
          .verticalScroll(rememberScrollState())
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Payment Details",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = payment.transactionId,
              style = MaterialTheme.typography.bodySmall,
              fontFamily = FontFamily.Monospace,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Big Amount Banner
        Surface(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          color = when (payment.status) {
            PaymentStatus.APPROVED -> SuccessGreenLight
            PaymentStatus.REJECTED_BY_USER -> DangerRedLight
            else -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
          }
        ) {
          Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "Amount",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "₹${"%,.2f".format(payment.amount)}",
              style = MaterialTheme.typography.headlineLarge,
              fontWeight = FontWeight.ExtraBold,
              color = when (payment.status) {
                PaymentStatus.APPROVED -> SuccessGreen
                PaymentStatus.REJECTED_BY_USER -> DangerRed
                else -> MaterialTheme.colorScheme.primary
              }
            )
            Spacer(modifier = Modifier.height(6.dp))
            PaymentStatusBadge(status = payment.status)
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Info List
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            DetailRow(label = "Host (Sender)", value = payment.hostName)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            DetailRow(label = "User (Recipient)", value = "${payment.userName} (${payment.userVillage})")
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            DetailRow(label = "Date & Time", value = "${payment.date} at ${payment.time}")

            if (!payment.note.isNullOrBlank()) {
              HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
              DetailRow(label = "Note", value = payment.note)
            }

            if (payment.status == PaymentStatus.REJECTED_BY_USER && !payment.rejectionReason.isNullOrBlank()) {
              HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
              Column {
                Text(
                  text = "Rejection Reason",
                  style = MaterialTheme.typography.labelSmall,
                  color = DangerRed,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = payment.rejectionReason,
                  style = MaterialTheme.typography.bodyMedium,
                  color = DangerRed
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Payment Proof Photo
        Text(
          text = "Payment Proof",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (!payment.proofPhotoUri.isNullOrEmpty()) {
          val imgModel: Any = if (payment.proofPhotoUri.startsWith("/")) File(payment.proofPhotoUri) else payment.proofPhotoUri
          Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp)
              .clip(RoundedCornerShape(12.dp))
              .clickable { isZoomedImage = true }
          ) {
            Box(contentAlignment = Alignment.BottomEnd) {
              AsyncImage(
                model = imgModel,
                contentDescription = "Payment proof document",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(200.dp)
              )
              Surface(
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(topStart = 8.dp),
                modifier = Modifier.padding(4.dp)
              ) {
                Text(
                  text = "Tap to enlarge",
                  color = Color.White,
                  fontSize = 11.sp,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        } else {
          Surface(
            modifier = Modifier
              .fillMaxWidth()
              .height(100.dp),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
          ) {
            Row(
              modifier = Modifier.padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(Icons.Default.Image, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "No proof photo attached to this record",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons or Explanations
        if (payment.status == PaymentStatus.PENDING_USER_APPROVAL) {
          if (isCurrentUser) {
            // User can approve or reject
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              OutlinedButton(
                onClick = { showRejectDialog = true },
                modifier = Modifier
                  .weight(1f)
                  .testTag("reject_payment_button"),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = DangerRed),
                shape = RoundedCornerShape(12.dp)
              ) {
                Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reject")
              }
              Button(
                onClick = { showConfirmApproveDialog = true },
                modifier = Modifier
                  .weight(1f)
                  .testTag("approve_payment_button"),
                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                shape = RoundedCornerShape(12.dp)
              ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Approve")
              }
            }
          } else {
            // Host view: explains policy
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  Icons.Default.Lock,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "Awaiting user confirmation. Only recipient (${payment.userName}) can approve this payment.",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        } else {
          Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text("Done")
          }
        }
      }
    }
  }

  // Final Confirmation Dialog for Approval
  if (showConfirmApproveDialog) {
    AlertDialog(
      onDismissRequest = { showConfirmApproveDialog = false },
      title = {
        Text("Confirm Payment Approval", fontWeight = FontWeight.Bold)
      },
      text = {
        Column {
          Text(
            text = "Are you sure you have received ₹${"%,.2f".format(payment.amount)} from ${payment.hostName}?",
            style = MaterialTheme.typography.bodyMedium
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Once approved, this amount will be permanently added to your confirmed earnings.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            showConfirmApproveDialog = false
            onApprove(payment)
          },
          colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
          modifier = Modifier.testTag("confirm_approve_button")
        ) {
          Text("Yes, I Received")
        }
      },
      dismissButton = {
        TextButton(onClick = { showConfirmApproveDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }

  // Rejection Dialog with Reason input
  if (showRejectDialog) {
    AlertDialog(
      onDismissRequest = { showRejectDialog = false },
      title = {
        Text("Reject Payment", fontWeight = FontWeight.Bold, color = DangerRed)
      },
      text = {
        Column {
          Text(
            text = "Please specify why you are rejecting this payment of ₹${"%,.2f".format(payment.amount)}.",
            style = MaterialTheme.typography.bodyMedium
          )
          Spacer(modifier = Modifier.height(12.dp))
          OutlinedTextField(
            value = rejectionReasonInput,
            onValueChange = {
              rejectionReasonInput = it
              if (it.isNotBlank()) rejectionError = null
            },
            label = { Text("Reason for Rejection *") },
            placeholder = { Text("e.g. Amount mismatch, not received, wrong recipient") },
            isError = rejectionError != null,
            supportingText = {
              if (rejectionError != null) {
                Text(text = rejectionError!!, color = MaterialTheme.colorScheme.error)
              }
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("rejection_reason_input"),
            maxLines = 3
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (rejectionReasonInput.isBlank()) {
              rejectionError = "Please enter a reason"
            } else {
              showRejectDialog = false
              onReject(payment, rejectionReasonInput.trim())
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
          modifier = Modifier.testTag("submit_rejection_button")
        ) {
          Text("Confirm Reject")
        }
      },
      dismissButton = {
        TextButton(onClick = { showRejectDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }

  // Zoomed Image Modal
  if (isZoomedImage && !payment.proofPhotoUri.isNullOrEmpty()) {
    Dialog(onDismissRequest = { isZoomedImage = false }) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(Color.Black)
          .padding(8.dp)
      ) {
        val imgModel: Any = if (payment.proofPhotoUri.startsWith("/")) File(payment.proofPhotoUri) else payment.proofPhotoUri
        AsyncImage(
          model = imgModel,
          contentDescription = "Full proof image",
          contentScale = ContentScale.Fit,
          modifier = Modifier.fillMaxWidth().heightIn(max = 500.dp)
        )
        IconButton(
          onClick = { isZoomedImage = false },
          modifier = Modifier.align(Alignment.TopEnd)
        ) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
        }
      }
    }
  }
}

@Composable
private fun DetailRow(label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Top
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}
