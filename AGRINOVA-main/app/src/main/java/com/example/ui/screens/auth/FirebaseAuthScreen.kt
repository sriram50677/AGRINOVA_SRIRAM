package com.example.ui.screens.auth

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DangerRed
import com.example.ui.viewmodel.AuthUiState
import com.example.ui.viewmodel.AuthViewModel

@Composable
fun FirebaseAuthScreen(
  authViewModel: AuthViewModel,
  onAuthenticated: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val uiState by authViewModel.uiState.collectAsState()

  var isRegisterMode by remember { mutableStateOf(false) }
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var passwordVisible by remember { mutableStateOf(false) }

  // If already authenticated, invoke callback
  if (uiState is AuthUiState.Authenticated) {
    onAuthenticated()
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 24.dp, vertical = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "HostPay Authentication",
      style = MaterialTheme.typography.headlineMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Sign in to securely access your HostPay payments ledger",
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(24.dp))

    // Interactive Google Sign-In button with Credential Manager
    Button(
      onClick = { authViewModel.initiateGoogleSignIn(context) },
      modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .testTag("google_sign_in_button"),
      colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
      shape = RoundedCornerShape(14.dp)
    ) {
      Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(20.dp))
      Spacer(modifier = Modifier.width(10.dp))
      Text("Sign in with Google", fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(18.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      HorizontalDivider(modifier = Modifier.weight(1f))
      Text(
        text = "  OR EMAIL  ",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      HorizontalDivider(modifier = Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.height(18.dp))

    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isRegisterMode) "Create Account" else "Sign In",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          TextButton(onClick = { isRegisterMode = !isRegisterMode }) {
            Text(if (isRegisterMode) "Existing User? Sign In" else "New User? Register")
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("Email Address") },
          leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("auth_email_input"),
          shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = password,
          onValueChange = { password = it },
          label = { Text("Password") },
          leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
          trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
              Icon(
                if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = null
              )
            }
          },
          visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("auth_password_input"),
          shape = RoundedCornerShape(12.dp)
        )

        if (uiState is AuthUiState.Error) {
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = (uiState as AuthUiState.Error).message,
            color = DangerRed,
            style = MaterialTheme.typography.bodySmall
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = {
            if (isRegisterMode) {
              authViewModel.registerWithEmail(email, password)
            } else {
              authViewModel.signInWithEmail(email, password)
            }
          },
          enabled = uiState !is AuthUiState.Loading && email.isNotBlank() && password.isNotBlank(),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("auth_submit_button"),
          shape = RoundedCornerShape(12.dp)
        ) {
          if (uiState is AuthUiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
          } else {
            Text(if (isRegisterMode) "Register" else "Sign In", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
