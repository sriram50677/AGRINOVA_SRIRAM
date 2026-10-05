package com.example.ui.screens.host

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.UserEntity
import com.example.data.security.SecurityUtils
import com.example.data.util.ImageUtils
import com.example.ui.common.UserAvatar
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberLight
import com.example.ui.viewmodel.HostPayViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostCreatePaymentScreen(
  preselectedUser: UserEntity?,
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val users by viewModel.hostUsers.collectAsState()
  val isLoading by viewModel.isLoading.collectAsState()

  var selectedUser by remember { mutableStateOf(preselectedUser ?: users.firstOrNull()) }
  var userDropdownExpanded by remember { mutableStateOf(false) }

  var amountText by remember { mutableStateOf("") }
  var noteText by remember { mutableStateOf("") }
  var proofPhotoPath by remember { mutableStateOf<String?>(null) }
  var showReviewDialog by remember { mutableStateOf(false) }

  val currentDate = remember { SecurityUtils.getCurrentDateFormatted() }
  val currentTime = remember { SecurityUtils.getCurrentTimeFormatted() }

  // Camera launcher
  val cameraLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.TakePicturePreview()
  ) { bitmap: Bitmap? ->
    if (bitmap != null) {
      val path = ImageUtils.saveBitmapToInternalStorage(context, bitmap, "payment_proofs")
      proofPhotoPath = path
      Toast.makeText(context, "Proof photo captured!", Toast.LENGTH_SHORT).show()
    }
  }

  // Camera permission launcher
  val cameraPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted: Boolean ->
    if (isGranted) {
      try {
        cameraLauncher.launch(null)
      } catch (e: Exception) {
        Toast.makeText(context, "Camera is unavailable. Please upload from gallery.", Toast.LENGTH_SHORT).show()
      }
    } else {
      Toast.makeText(context, "Camera permission is required to take proof photos. You can also upload from gallery.", Toast.LENGTH_LONG).show()
    }
  }

  fun checkAndLaunchCamera() {
    val hasPermission = androidx.core.content.ContextCompat.checkSelfPermission(
      context,
      android.Manifest.permission.CAMERA
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    if (hasPermission) {
      try {
        cameraLauncher.launch(null)
      } catch (e: Exception) {
        Toast.makeText(context, "Camera is unavailable. Please upload from gallery.", Toast.LENGTH_SHORT).show()
      }
    } else {
      cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }
  }

  // Gallery launcher
  val galleryLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    if (uri != null) {
      val path = ImageUtils.saveUriToInternalStorage(context, uri, "payment_proofs")
      if (path != null) {
        proofPhotoPath = path
        Toast.makeText(context, "Proof photo attached!", Toast.LENGTH_SHORT).show()
      }
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Create Payment Record", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("create_payment_back")
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
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Step 1: Select Recipient User
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "1. Recipient User *",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(10.dp))

          if (users.isEmpty()) {
            Text(
              text = "No registered users available. Share your Host Code with users first.",
              color = MaterialTheme.colorScheme.error,
              fontSize = 13.sp
            )
          } else {
            ExposedDropdownMenuBox(
              expanded = userDropdownExpanded,
              onExpandedChange = { userDropdownExpanded = !userDropdownExpanded }
            ) {
              OutlinedTextField(
                value = selectedUser?.let { "${it.name} (${it.village})" } ?: "Select User",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = userDropdownExpanded) },
                leadingIcon = {
                  selectedUser?.let {
                    UserAvatar(photoUri = it.profilePhotoUri, name = it.name, sizeDp = 28)
                  } ?: Icon(Icons.Default.Person, contentDescription = null)
                },
                modifier = Modifier
                  .menuAnchor()
                  .fillMaxWidth()
                  .testTag("user_select_dropdown"),
                shape = RoundedCornerShape(12.dp)
              )

              ExposedDropdownMenu(
                expanded = userDropdownExpanded,
                onDismissRequest = { userDropdownExpanded = false }
              ) {
                users.forEach { u ->
                  DropdownMenuItem(
                    text = {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        UserAvatar(photoUri = u.profilePhotoUri, name = u.name, sizeDp = 32)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                          Text(u.name, fontWeight = FontWeight.SemiBold)
                          Text("${u.village} • ${u.mobile}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                      }
                    },
                    onClick = {
                      selectedUser = u
                      userDropdownExpanded = false
                    },
                    modifier = Modifier.testTag("dropdown_item_${u.id}")
                  )
                }
              }
            }
          }
        }
      }

      // Step 2: Payment Details (Amount, Note)
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "2. Payment Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = amountText,
            onValueChange = { input ->
              // Only allow numbers and decimal point
              if (input.isEmpty() || input.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                amountText = input
              }
            },
            label = { Text("Amount (₹) *") },
            placeholder = { Text("e.g. 5000.00") },
            prefix = { Text("₹ ", fontWeight = FontWeight.Bold) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("payment_amount_input"),
            shape = RoundedCornerShape(12.dp)
          )

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Note / Description (Optional)") },
            placeholder = { Text("e.g. Weekly wages, tractor work, crop payout") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("payment_note_input"),
            maxLines = 3,
            shape = RoundedCornerShape(12.dp)
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Auto-recorded timestamp info
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Auto-Recorded: $currentDate at $currentTime",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }

      // Step 3: Payment Proof Photo
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "3. Payment Proof Photo",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Capture cash handover receipt, voucher, or bank transfer screenshot.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(12.dp))

          if (proofPhotoPath != null) {
            val file = File(proofPhotoPath!!)
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
              AsyncImage(
                model = file,
                contentDescription = "Captured proof",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
              )
              IconButton(
                onClick = { proofPhotoPath = null },
                modifier = Modifier
                  .align(Alignment.TopEnd)
                  .padding(8.dp)
                  .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))
              ) {
                Icon(Icons.Default.Clear, contentDescription = "Remove Photo")
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = { checkAndLaunchCamera() },
              modifier = Modifier
                .weight(1f)
                .testTag("camera_capture_button"),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Take Photo")
            }
            OutlinedButton(
              onClick = {
                galleryLauncher.launch(
                  androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
              },
              modifier = Modifier
                .weight(1f)
                .testTag("gallery_pick_button"),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Upload")
            }
          }
        }
      }

      // Review & Submit Button
      Button(
        onClick = {
          val amt = amountText.toDoubleOrNull()
          if (selectedUser == null) {
            Toast.makeText(context, "Please select a recipient user", Toast.LENGTH_SHORT).show()
          } else if (amt == null || amt <= 0) {
            Toast.makeText(context, "Please enter a valid amount greater than 0", Toast.LENGTH_SHORT).show()
          } else {
            showReviewDialog = true
          }
        },
        enabled = !isLoading && selectedUser != null && (amountText.toDoubleOrNull() ?: 0.0) > 0,
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("review_payment_button"),
        shape = RoundedCornerShape(14.dp)
      ) {
        Text("Review & Send for Approval", fontWeight = FontWeight.Bold)
      }

      Spacer(modifier = Modifier.height(20.dp))
    }
  }

  // REVIEW DIALOG BEFORE SUBMITTING
  if (showReviewDialog && selectedUser != null) {
    val finalAmt = amountText.toDoubleOrNull() ?: 0.0
    AlertDialog(
      onDismissRequest = { showReviewDialog = false },
      title = {
        Text("Review Payment Record", fontWeight = FontWeight.Bold)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier.padding(12.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text("Amount to Disburse", style = MaterialTheme.typography.labelSmall)
              Text(
                text = "₹${"%,.2f".format(finalAmt)}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            }
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "Recipient: ${selectedUser!!.name} (${selectedUser!!.village})", fontWeight = FontWeight.SemiBold)
          Text(text = "Date & Time: $currentDate at $currentTime", style = MaterialTheme.typography.bodySmall)

          if (noteText.isNotBlank()) {
            Text(text = "Note: $noteText", style = MaterialTheme.typography.bodySmall)
          }

          Text(
            text = if (proofPhotoPath != null) "Proof Photo: Attached ✓" else "Proof Photo: Not attached",
            style = MaterialTheme.typography.bodySmall,
            color = if (proofPhotoPath != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
          )

          Spacer(modifier = Modifier.height(4.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = WarningAmberLight,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Lock, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "This will be marked PENDING_USER_APPROVAL. Only ${selectedUser!!.name} can approve it.",
                fontSize = 11.sp,
                color = WarningAmber,
                lineHeight = 15.sp
              )
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            showReviewDialog = false
            viewModel.createPayment(
              user = selectedUser!!,
              amount = finalAmt,
              note = noteText,
              proofPhotoUri = proofPhotoPath,
              onComplete = { viewModel.navigateBack() }
            )
          },
          modifier = Modifier.testTag("confirm_submit_payment")
        ) {
          if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = MaterialTheme.colorScheme.onPrimary)
          } else {
            Text("Confirm & Send")
          }
        }
      },
      dismissButton = {
        TextButton(onClick = { showReviewDialog = false }) {
          Text("Cancel / Edit")
        }
      }
    )
  }
}
