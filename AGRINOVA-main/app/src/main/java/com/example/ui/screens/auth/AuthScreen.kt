package com.example.ui.screens.auth

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.HostEntity
import com.example.data.util.ImageUtils
import com.example.ui.common.HostCodeBadge
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.HostPayViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun AuthScreen(
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val isLoading by viewModel.isLoading.collectAsState()

  // 0 = User, 1 = Host
  var selectedRoleTab by remember { mutableIntStateOf(0) }
  // 0 = Login, 1 = Register
  var isRegisterMode by remember { mutableStateOf(false) }

  // Host form states
  var hostName by remember { mutableStateOf("") }
  var hostOrg by remember { mutableStateOf("") }
  var hostMobile by remember { mutableStateOf("") }
  var hostPassword by remember { mutableStateOf("") }
  var hostPasswordVisible by remember { mutableStateOf(false) }
  var newlyRegisteredHost by remember { mutableStateOf<HostEntity?>(null) }

  // User form states
  var userHostCode by remember { mutableStateOf("") }
  var verifiedHostForUser by remember { mutableStateOf<HostEntity?>(null) }
  var userName by remember { mutableStateOf("") }
  var userVillage by remember { mutableStateOf("") }
  var userGender by remember { mutableStateOf("Male") }
  var userMobile by remember { mutableStateOf("") }
  var userPassword by remember { mutableStateOf("") }
  var userPasswordVisible by remember { mutableStateOf(false) }
  var userPhotoUri by remember { mutableStateOf<String?>(null) }

  // Camera capture launcher for user profile photo
  val cameraLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.TakePicturePreview()
  ) { bitmap: Bitmap? ->
    if (bitmap != null) {
      val savedPath = ImageUtils.saveBitmapToInternalStorage(context, bitmap, "user_profiles")
      userPhotoUri = savedPath
      Toast.makeText(context, "Profile photo captured!", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(context, "Camera is unavailable. Please select from gallery.", Toast.LENGTH_SHORT).show()
      }
    } else {
      Toast.makeText(context, "Camera permission is required to capture a photo.", Toast.LENGTH_LONG).show()
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
        Toast.makeText(context, "Camera is unavailable. Please select from gallery.", Toast.LENGTH_SHORT).show()
      }
    } else {
      cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }
  }

  // Gallery picker for user profile photo
  val galleryLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    if (uri != null) {
      val savedPath = ImageUtils.saveUriToInternalStorage(context, uri, "user_profiles")
      if (savedPath != null) {
        userPhotoUri = savedPath
        Toast.makeText(context, "Profile photo selected!", Toast.LENGTH_SHORT).show()
      }
    }
  }

  // Verify host code in real-time when user types
  LaunchedEffect(userHostCode) {
    if (userHostCode.trim().length >= 5) {
      verifiedHostForUser = viewModel.checkHostCode(userHostCode.trim())
    } else {
      verifiedHostForUser = null
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 20.dp, vertical = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(12.dp))

    // App Brand Logo & Title
    Surface(
      modifier = Modifier.size(72.dp),
      shape = RoundedCornerShape(20.dp),
      color = MaterialTheme.colorScheme.primaryContainer,
      tonalElevation = 4.dp
    ) {
      Box(contentAlignment = Alignment.Center) {
        Image(
          painter = painterResource(id = R.drawable.arinova_logo),
          contentDescription = "AGRINOVA Logo",
          modifier = Modifier.size(72.dp),
          contentScale = ContentScale.Crop
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Text(
      text = "AGRINOVA",
      style = MaterialTheme.typography.headlineMedium,
      fontWeight = FontWeight.ExtraBold,
      color = MaterialTheme.colorScheme.onBackground
    )
    Text(
      text = "Verified Payment & User Records",
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Quick Test Helper Card for Reviewers
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("quick_test_banner"),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
      shape = RoundedCornerShape(14.dp)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Text(
          text = "🚀 Quick Test Logins (1-Tap Switch)",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(
            onClick = { viewModel.quickLoginDemoHost() },
            modifier = Modifier.weight(1f).testTag("quick_login_host_button"),
            shape = RoundedCornerShape(10.dp)
          ) {
            Text("Host (Ravi)", fontSize = 12.sp, maxLines = 1)
          }
          OutlinedButton(
            onClick = { viewModel.quickLoginDemoUser() },
            modifier = Modifier.weight(1f).testTag("quick_login_user_button"),
            shape = RoundedCornerShape(10.dp)
          ) {
            Text("User (Suresh)", fontSize = 12.sp, maxLines = 1)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Role Selection Tabs (User vs Host)
    TabRow(
      selectedTabIndex = selectedRoleTab,
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
      contentColor = MaterialTheme.colorScheme.primary,
      modifier = Modifier
        .clip(RoundedCornerShape(14.dp))
        .fillMaxWidth()
    ) {
      Tab(
        selected = selectedRoleTab == 0,
        onClick = { selectedRoleTab = 0 },
        text = {
          Text(
            "User Portal",
            fontWeight = if (selectedRoleTab == 0) FontWeight.Bold else FontWeight.Normal
          )
        },
        modifier = Modifier.testTag("tab_user_role")
      )
      Tab(
        selected = selectedRoleTab == 1,
        onClick = { selectedRoleTab = 1 },
        text = {
          Text(
            "Host / Admin",
            fontWeight = if (selectedRoleTab == 1) FontWeight.Bold else FontWeight.Normal
          )
        },
        modifier = Modifier.testTag("tab_host_role")
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Form Container Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Toggle Sign In vs Register
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isRegisterMode) {
              if (selectedRoleTab == 0) "Create User Account" else "Host Registration"
            } else {
              if (selectedRoleTab == 0) "User Secure Login" else "Host Secure Login"
            },
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          TextButton(
            onClick = { isRegisterMode = !isRegisterMode },
            modifier = Modifier.testTag("toggle_auth_mode_button")
          ) {
            Text(if (isRegisterMode) "Sign In instead" else "Register")
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // ================= USER FORMS =================
        if (selectedRoleTab == 0) {
          if (isRegisterMode) {
            // USER REGISTRATION
            OutlinedTextField(
              value = userHostCode,
              onValueChange = { userHostCode = it.uppercase() },
              label = { Text("Host Code * (to join)") },
              placeholder = { Text("e.g. HP-5520") },
              leadingIcon = { Icon(Icons.Default.QrCode, contentDescription = null) },
              modifier = Modifier.fillMaxWidth().testTag("user_reg_host_code"),
              singleLine = true
            )

            if (verifiedHostForUser != null) {
              Spacer(modifier = Modifier.height(4.dp))
              Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Host: ${verifiedHostForUser!!.name} (${verifiedHostForUser!!.organization})",
                  color = SuccessGreen,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = userName,
              onValueChange = { userName = it },
              label = { Text("Full Name *") },
              leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
              modifier = Modifier.fillMaxWidth().testTag("user_reg_name"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = userVillage,
              onValueChange = { userVillage = it },
              label = { Text("Village / Town *") },
              leadingIcon = { Icon(Icons.Default.LocationCity, contentDescription = null) },
              modifier = Modifier.fillMaxWidth().testTag("user_reg_village"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Gender selector
            Column(modifier = Modifier.fillMaxWidth()) {
              Text(
                text = "Gender *",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Spacer(modifier = Modifier.height(4.dp))
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                listOf("Male", "Female", "Other").forEach { g ->
                  FilterChip(
                    selected = userGender == g,
                    onClick = { userGender = g },
                    label = { Text(g) },
                    modifier = Modifier.weight(1f)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = userMobile,
              onValueChange = { userMobile = it },
              label = { Text("Mobile / Login ID *") },
              leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
              modifier = Modifier.fillMaxWidth().testTag("user_reg_mobile"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = userPassword,
              onValueChange = { userPassword = it },
              label = { Text("Password *") },
              leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
              trailingIcon = {
                IconButton(onClick = { userPasswordVisible = !userPasswordVisible }) {
                  Icon(
                    if (userPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null
                  )
                }
              },
              visualTransformation = if (userPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
              modifier = Modifier.fillMaxWidth().testTag("user_reg_password"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Profile photo section
            Column(
              modifier = Modifier.fillMaxWidth(),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "Profile Photo",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Spacer(modifier = Modifier.height(8.dp))
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                  contentAlignment = Alignment.Center
                ) {
                  if (userPhotoUri != null) {
                    val file = File(userPhotoUri!!)
                    AsyncImage(
                      model = file,
                      contentDescription = "Profile photo preview",
                      contentScale = ContentScale.Crop,
                      modifier = Modifier.size(60.dp)
                    )
                  } else {
                    Icon(
                      imageVector = Icons.Default.Person,
                      contentDescription = null,
                      tint = MaterialTheme.colorScheme.onSurfaceVariant,
                      modifier = Modifier.size(32.dp)
                    )
                  }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  OutlinedButton(
                    onClick = { checkAndLaunchCamera() },
                    modifier = Modifier.testTag("user_photo_camera_button"),
                    shape = RoundedCornerShape(10.dp)
                  ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Camera", fontSize = 12.sp)
                  }
                  OutlinedButton(
                    onClick = {
                      galleryLauncher.launch(
                        androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                      )
                    },
                    modifier = Modifier.testTag("user_photo_gallery_button"),
                    shape = RoundedCornerShape(10.dp)
                  ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Gallery", fontSize = 12.sp)
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
              onClick = {
                viewModel.registerUser(
                  hostCode = userHostCode,
                  name = userName,
                  village = userVillage,
                  gender = userGender,
                  mobile = userMobile,
                  password = userPassword,
                  profilePhotoUri = userPhotoUri
                )
              },
              enabled = !isLoading,
              modifier = Modifier.fillMaxWidth().height(50.dp).testTag("user_register_submit"),
              shape = RoundedCornerShape(14.dp)
            ) {
              if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
              } else {
                Text("Register & Join Host", fontWeight = FontWeight.Bold)
              }
            }
          } else {
            // USER LOGIN
            OutlinedTextField(
              value = userMobile,
              onValueChange = { userMobile = it },
              label = { Text("Mobile / Login ID") },
              leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
              modifier = Modifier.fillMaxWidth().testTag("user_login_mobile"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
              value = userPassword,
              onValueChange = { userPassword = it },
              label = { Text("Password") },
              leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
              trailingIcon = {
                IconButton(onClick = { userPasswordVisible = !userPasswordVisible }) {
                  Icon(
                    if (userPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null
                  )
                }
              },
              visualTransformation = if (userPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
              modifier = Modifier.fillMaxWidth().testTag("user_login_password"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
              onClick = { viewModel.loginUser(userMobile, userPassword) },
              enabled = !isLoading,
              modifier = Modifier.fillMaxWidth().height(50.dp).testTag("user_login_submit"),
              shape = RoundedCornerShape(14.dp)
            ) {
              if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
              } else {
                Text("Secure Login", fontWeight = FontWeight.Bold)
              }
            }
          }
        } else {
          // ================= HOST FORMS =================
          if (isRegisterMode) {
            // HOST REGISTRATION
            OutlinedTextField(
              value = hostName,
              onValueChange = { hostName = it },
              label = { Text("Host Full Name *") },
              leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
              modifier = Modifier.fillMaxWidth().testTag("host_reg_name"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = hostOrg,
              onValueChange = { hostOrg = it },
              label = { Text("Organization / Business Name") },
              placeholder = { Text("e.g. AgriNova Logistics") },
              leadingIcon = { Icon(Icons.Default.Apartment, contentDescription = null) },
              modifier = Modifier.fillMaxWidth().testTag("host_reg_org"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = hostMobile,
              onValueChange = { hostMobile = it },
              label = { Text("Mobile / Admin ID *") },
              leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
              modifier = Modifier.fillMaxWidth().testTag("host_reg_mobile"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
              value = hostPassword,
              onValueChange = { hostPassword = it },
              label = { Text("Password *") },
              leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
              trailingIcon = {
                IconButton(onClick = { hostPasswordVisible = !hostPasswordVisible }) {
                  Icon(
                    if (hostPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null
                  )
                }
              },
              visualTransformation = if (hostPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
              modifier = Modifier.fillMaxWidth().testTag("host_reg_password"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
              onClick = {
                viewModel.registerHost(
                  name = hostName,
                  organization = hostOrg,
                  mobile = hostMobile,
                  password = hostPassword,
                  onSuccess = { newlyRegisteredHost = it }
                )
              },
              enabled = !isLoading,
              modifier = Modifier.fillMaxWidth().height(50.dp).testTag("host_register_submit"),
              shape = RoundedCornerShape(14.dp)
            ) {
              if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
              } else {
                Text("Register Host & Generate Code", fontWeight = FontWeight.Bold)
              }
            }

            // Note on generated code
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "A unique 6-digit Host Code will be generated for your workers/users to join.",
              style = MaterialTheme.typography.bodySmall,
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          } else {
            // HOST LOGIN
            OutlinedTextField(
              value = hostMobile,
              onValueChange = { hostMobile = it },
              label = { Text("Host Mobile / Admin ID") },
              leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
              modifier = Modifier.fillMaxWidth().testTag("host_login_mobile"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
              value = hostPassword,
              onValueChange = { hostPassword = it },
              label = { Text("Host Password") },
              leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
              trailingIcon = {
                IconButton(onClick = { hostPasswordVisible = !hostPasswordVisible }) {
                  Icon(
                    if (hostPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null
                  )
                }
              },
              visualTransformation = if (hostPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
              modifier = Modifier.fillMaxWidth().testTag("host_login_password"),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
              onClick = { viewModel.loginHost(hostMobile, hostPassword) },
              enabled = !isLoading,
              modifier = Modifier.fillMaxWidth().height(50.dp).testTag("host_login_submit"),
              shape = RoundedCornerShape(14.dp)
            ) {
              if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
              } else {
                Text("Host Login", fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}
