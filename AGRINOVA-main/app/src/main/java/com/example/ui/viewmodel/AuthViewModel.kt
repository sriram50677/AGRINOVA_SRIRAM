package com.example.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "AuthViewModel"

sealed class AuthUiState {
  object Unauthenticated : AuthUiState()
  object Loading : AuthUiState()
  data class Authenticated(val user: FirebaseUser) : AuthUiState()
  data class Error(val message: String) : AuthUiState()
}

class AuthViewModel : ViewModel() {

  private var firebaseAuth: FirebaseAuth? = null

  private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Unauthenticated)
  val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

  private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
    val user = auth.currentUser
    if (user != null) {
      _uiState.value = AuthUiState.Authenticated(user)
    } else {
      _uiState.value = AuthUiState.Unauthenticated
    }
  }

  init {
    try {
      firebaseAuth = FirebaseAuth.getInstance()
      firebaseAuth?.addAuthStateListener(authStateListener)
      val current = firebaseAuth?.currentUser
      if (current != null) {
        _uiState.value = AuthUiState.Authenticated(current)
      }
    } catch (e: Exception) {
      Log.w(TAG, "FirebaseAuth initialization notice (Backend not yet provisioned): ${e.message}")
    }
  }

  override fun onCleared() {
    super.onCleared()
    firebaseAuth?.removeAuthStateListener(authStateListener)
  }

  /**
   * Triggers the Jetpack Credential Manager Google Sign-In prompt.
   */
  fun initiateGoogleSignIn(context: Context) {
    viewModelScope.launch {
      _uiState.value = AuthUiState.Loading

      val credentialManager = CredentialManager.create(context)
      val serverClientId = try {
        context.getString(R.string.default_web_client_id)
      } catch (e: Exception) {
        "dummy_client_id.apps.googleusercontent.com"
      }

      val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(serverClientId)
        .build()

      val request = GetCredentialRequest.Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

      try {
        val result = credentialManager.getCredential(
          request = request,
          context = context
        )
        handleCredentialResponse(result)
      } catch (e: GetCredentialCancellationException) {
        Log.i(TAG, "User cancelled Google Sign-In flow")
        _uiState.value = AuthUiState.Unauthenticated
      } catch (e: GetCredentialException) {
        Log.e(TAG, "Google Sign-In failed: ${e.message}", e)
        _uiState.value = AuthUiState.Error(e.localizedMessage ?: "Google Sign-In failed")
      } catch (e: Exception) {
        Log.e(TAG, "Unexpected authentication error: ${e.message}", e)
        _uiState.value = AuthUiState.Error(e.localizedMessage ?: "Authentication failed")
      }
    }
  }

  private fun handleCredentialResponse(response: GetCredentialResponse) {
    val credential = response.credential
    if (credential is CustomCredential &&
      credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
    ) {
      try {
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
        val idToken = googleIdTokenCredential.idToken
        signInWithFirebaseGoogleToken(idToken)
      } catch (e: Exception) {
        Log.e(TAG, "Failed to parse Google ID token", e)
        _uiState.value = AuthUiState.Error("Invalid Google credentials")
      }
    } else {
      Log.e(TAG, "Unsupported credential type: ${credential.type}")
      _uiState.value = AuthUiState.Error("Unexpected credential type")
    }
  }

  fun signInWithFirebaseGoogleToken(idToken: String) {
    val auth = firebaseAuth
    if (auth == null) {
      _uiState.value = AuthUiState.Error("Firebase Auth is not initialized yet")
      return
    }

    _uiState.value = AuthUiState.Loading
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
      .addOnSuccessListener { result ->
        val user = result.user
        if (user != null) {
          _uiState.value = AuthUiState.Authenticated(user)
        }
      }
      .addOnFailureListener { exception ->
        Log.e(TAG, "Firebase credential sign-in error", exception)
        _uiState.value = AuthUiState.Error(exception.localizedMessage ?: "Firebase Sign-In failed")
      }
  }

  fun signInWithEmail(email: String, pass: String) {
    val auth = firebaseAuth ?: return
    _uiState.value = AuthUiState.Loading
    auth.signInWithEmailAndPassword(email.trim(), pass)
      .addOnSuccessListener { result ->
        result.user?.let { _uiState.value = AuthUiState.Authenticated(it) }
      }
      .addOnFailureListener { ex ->
        _uiState.value = AuthUiState.Error(ex.localizedMessage ?: "Sign-in failed")
      }
  }

  fun registerWithEmail(email: String, pass: String) {
    val auth = firebaseAuth ?: return
    _uiState.value = AuthUiState.Loading
    auth.createUserWithEmailAndPassword(email.trim(), pass)
      .addOnSuccessListener { result ->
        result.user?.let { _uiState.value = AuthUiState.Authenticated(it) }
      }
      .addOnFailureListener { ex ->
        _uiState.value = AuthUiState.Error(ex.localizedMessage ?: "Registration failed")
      }
  }

  fun signOut() {
    firebaseAuth?.signOut()
    _uiState.value = AuthUiState.Unauthenticated
  }

  fun clearError() {
    if (_uiState.value is AuthUiState.Error) {
      _uiState.value = AuthUiState.Unauthenticated
    }
  }
}
