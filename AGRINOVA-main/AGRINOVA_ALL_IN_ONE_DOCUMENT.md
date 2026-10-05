# AGRINOVA - All-In-One Complete Application Document

**Application Name:** AGRINOVA  
**Platform:** Android (Jetpack Compose + Room + Material 3)  
**Architecture:** MVVM (Model-View-ViewModel) + Clean Data Layer  

---

## Table of Contents

- [metadata.json](#metadatajson)
- [settings.gradle.kts](#settingsgradlekts)
- [build.gradle.kts](#buildgradlekts)
- [app/build.gradle.kts](#appbuildgradlekts)
- [app/src/main/AndroidManifest.xml](#appsrcmainandroidmanifestxml)
- [app/src/main/res/values/strings.xml](#appsrcmainresvaluesstringsxml)
- [app/src/main/res/values/colors.xml](#appsrcmainresvaluescolorsxml)
- [app/src/main/res/values/themes.xml](#appsrcmainresvaluesthemesxml)
- [app/src/main/res/drawable/ic_launcher_background.xml](#appsrcmainresdrawableic_launcher_backgroundxml)
- [app/src/main/res/drawable/ic_launcher_foreground.xml](#appsrcmainresdrawableic_launcher_foregroundxml)
- [app/src/main/java/com/example/MainActivity.kt](#appsrcmainjavacomexamplemainactivitykt)
- [app/src/main/java/com/example/data/model/Entities.kt](#appsrcmainjavacomexampledatamodelentitieskt)
- [app/src/main/java/com/example/data/local/Daos.kt](#appsrcmainjavacomexampledatalocaldaoskt)
- [app/src/main/java/com/example/data/local/HostPayDatabase.kt](#appsrcmainjavacomexampledatalocalhostpaydatabasekt)
- [app/src/main/java/com/example/data/repository/HostPayRepository.kt](#appsrcmainjavacomexampledatarepositoryhostpayrepositorykt)
- [app/src/main/java/com/example/data/security/SecurityUtils.kt](#appsrcmainjavacomexampledatasecuritysecurityutilskt)
- [app/src/main/java/com/example/data/util/ImageUtils.kt](#appsrcmainjavacomexampledatautilimageutilskt)
- [app/src/main/java/com/example/ui/theme/Color.kt](#appsrcmainjavacomexampleuithemecolorkt)
- [app/src/main/java/com/example/ui/theme/Theme.kt](#appsrcmainjavacomexampleuithemethemekt)
- [app/src/main/java/com/example/ui/theme/Type.kt](#appsrcmainjavacomexampleuithemetypekt)
- [app/src/main/java/com/example/ui/common/Components.kt](#appsrcmainjavacomexampleuicommoncomponentskt)
- [app/src/main/java/com/example/ui/common/PaymentDetailDialog.kt](#appsrcmainjavacomexampleuicommonpaymentdetaildialogkt)
- [app/src/main/java/com/example/ui/viewmodel/HostPayViewModel.kt](#appsrcmainjavacomexampleuiviewmodelhostpayviewmodelkt)
- [app/src/main/java/com/example/ui/screens/auth/AuthScreen.kt](#appsrcmainjavacomexampleuiscreensauthauthscreenkt)
- [app/src/main/java/com/example/ui/screens/host/HostDashboardScreen.kt](#appsrcmainjavacomexampleuiscreenshosthostdashboardscreenkt)
- [app/src/main/java/com/example/ui/screens/host/HostUsersScreen.kt](#appsrcmainjavacomexampleuiscreenshosthostusersscreenkt)
- [app/src/main/java/com/example/ui/screens/host/HostUserDetailsScreen.kt](#appsrcmainjavacomexampleuiscreenshosthostuserdetailsscreenkt)
- [app/src/main/java/com/example/ui/screens/host/HostCreatePaymentScreen.kt](#appsrcmainjavacomexampleuiscreenshosthostcreatepaymentscreenkt)
- [app/src/main/java/com/example/ui/screens/host/HostPendingApprovalsScreen.kt](#appsrcmainjavacomexampleuiscreenshosthostpendingapprovalsscreenkt)
- [app/src/main/java/com/example/ui/screens/host/HostReportsScreen.kt](#appsrcmainjavacomexampleuiscreenshosthostreportsscreenkt)
- [app/src/main/java/com/example/ui/screens/host/HostAuditLogsScreen.kt](#appsrcmainjavacomexampleuiscreenshosthostauditlogsscreenkt)
- [app/src/main/java/com/example/ui/screens/host/HostNotificationsScreen.kt](#appsrcmainjavacomexampleuiscreenshosthostnotificationsscreenkt)
- [app/src/main/java/com/example/ui/screens/host/HostSettingsScreen.kt](#appsrcmainjavacomexampleuiscreenshosthostsettingsscreenkt)
- [app/src/main/java/com/example/ui/screens/user/UserHomeScreen.kt](#appsrcmainjavacomexampleuiscreensuseruserhomescreenkt)
- [app/src/main/java/com/example/ui/screens/user/UserProfileScreen.kt](#appsrcmainjavacomexampleuiscreensuseruserprofilescreenkt)
- [app/src/main/java/com/example/ui/screens/user/UserPaymentsScreen.kt](#appsrcmainjavacomexampleuiscreensuseruserpaymentsscreenkt)
- [app/src/main/java/com/example/ui/screens/user/UserNotificationsScreen.kt](#appsrcmainjavacomexampleuiscreensuserusernotificationsscreenkt)
- [app/src/test/java/com/example/ExampleRobolectricTest.kt](#appsrctestjavacomexampleexamplerobolectrictestkt)

---

<a id="metadatajson"></a>

### File: `metadata.json`

```json
{
  "name": "AGRINOVA",
  "description": "AGRINOVA is a professional platform for managing users, hosts, and payment records with photo proof verification and user approval workflow.",
  "requestFramePermissions": [],
  "majorCapabilities": ["MAJOR_CAPABILITY_SERVER_SIDE_GEMINI_API"]
}

```

---

<a id="settingsgradlekts"></a>

### File: `settings.gradle.kts`

```kotlin
pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "AGRINOVA"

include(":app")

```

---

<a id="buildgradlekts"></a>

### File: `build.gradle.kts`

```kotlin
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.google.devtools.ksp) apply false
  alias(libs.plugins.secrets) apply false
  alias(libs.plugins.google.services) apply false
}

```

---

<a id="appbuildgradlekts"></a>

### File: `app/build.gradle.kts`

```kotlin
import com.google.gms.googleservices.GoogleServicesPlugin.MissingGoogleServicesStrategy

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.google.devtools.ksp)
  alias(libs.plugins.secrets)
  alias(libs.plugins.google.services)
}

android {
  namespace = "com.example"
  compileSdk { version = release(36) { minorApiLevel = 1 } }

  defaultConfig {
    applicationId = "com.aistudio.hostpay.zkjrv"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  signingConfigs {
    create("release") {
      val keystorePath = System.getenv("KEYSTORE_PATH") ?: "${rootDir}/my-upload-key.jks"
      storeFile = file(keystorePath)
      storePassword = System.getenv("STORE_PASSWORD")
      keyAlias = "upload"
      keyPassword = System.getenv("KEY_PASSWORD")
    }
    create("debugConfig") {
      storeFile = file("${rootDir}/debug.keystore")
      storePassword = "android"
      keyAlias = "androiddebugkey"
      keyPassword = "android"
    }
  }

  buildTypes {
    release {
      isCrunchPngs = false
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("release")
    }
    debug { signingConfig = signingConfigs.getByName("debugConfig") }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  testOptions { unitTests { isIncludeAndroidResources = true } }
  dependenciesInfo {
    includeInApk = false
    includeInBundle = true
  }
}

// Configure the Secrets Gradle Plugin to use .env and .env.example files
// to match the convention used in Web projects.
secrets {
  propertiesFileName = ".env"
  defaultPropertiesFileName = ".env.example"
  ignoreList.add("FIREBASE_APPCHECK_DEBUG_TOKEN")
}

googleServices { missingGoogleServicesStrategy = MissingGoogleServicesStrategy.WARN }

// Some unused dependencies are commented out below instead of being removed.
// This makes it easy to add them back in the future if needed.
dependencies {
  implementation(platform(libs.androidx.compose.bom))
  implementation(platform(libs.firebase.bom))
  // implementation(libs.accompanist.permissions)
  implementation(libs.androidx.activity.compose)
  // implementation(libs.androidx.camera.camera2)
  // implementation(libs.androidx.camera.core)
  // implementation(libs.androidx.camera.lifecycle)
  // implementation(libs.androidx.camera.view)
  implementation(libs.androidx.compose.material.icons.core)
  implementation(libs.androidx.compose.material.icons.extended)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.core.ktx)
  // implementation(libs.androidx.datastore.preferences)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  // implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.room.ktx)
  implementation(libs.androidx.room.runtime)
  implementation(libs.coil.compose)
  implementation(libs.converter.moshi)
  implementation(libs.firebase.ai)
  // Uncomment to use Firestore:
  // implementation(libs.firebase.firestore)

  // Uncomment ALL FOUR of the following dependencies together to use Firebase Auth and Google
  // Sign-In via Credential Manager:
  implementation(libs.firebase.auth)
  implementation(libs.androidx.credentials)
  implementation(libs.androidx.credentials.play.services)
  implementation(libs.googleid)
  implementation(libs.firebase.appcheck.recaptcha)
  implementation(libs.firebase.appcheck.debug)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.logging.interceptor)
  implementation(libs.moshi.kotlin)
  implementation(libs.okhttp)
  // implementation(libs.play.services.location)
  implementation(libs.retrofit)
  testImplementation(libs.androidx.compose.ui.test.junit4)
  testImplementation(libs.androidx.core)
  testImplementation(libs.androidx.junit)
  testImplementation(libs.junit)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.robolectric)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.runner)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
  debugImplementation(libs.androidx.compose.ui.tooling)
  "ksp"(libs.androidx.room.compiler)
  "ksp"(libs.moshi.kotlin.codegen)
}

```

---

<a id="appsrcmainandroidmanifestxml"></a>

### File: `app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" android:required="false" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MyApplication">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:windowSoftInputMode="adjustResize"
            android:label="@string/app_name"
            android:theme="@style/Theme.MyApplication">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>

```

---

<a id="appsrcmainresvaluesstringsxml"></a>

### File: `app/src/main/res/values/strings.xml`

```xml
<resources>
    <string name="app_name">AGRINOVA</string>
    <string name="default_web_client_id">dummy_client_id.apps.googleusercontent.com</string>
</resources>

```

---

<a id="appsrcmainresvaluescolorsxml"></a>

### File: `app/src/main/res/values/colors.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="purple_200">#FFBB86FC</color>
    <color name="purple_500">#FF6200EE</color>
    <color name="purple_700">#FF3700B3</color>
    <color name="teal_200">#FF03DAC5</color>
    <color name="teal_700">#FF018786</color>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
</resources>

```

---

<a id="appsrcmainresvaluesthemesxml"></a>

### File: `app/src/main/res/values/themes.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <style name="Theme.MyApplication" parent="android:Theme.DeviceDefault.NoActionBar" />
</resources>

```

---

<a id="appsrcmainresdrawableic_launcher_backgroundxml"></a>

### File: `app/src/main/res/drawable/ic_launcher_background.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path
        android:fillColor="#0a0a0a"
        android:pathData="M0,0h108v108h-108z" />
</vector>

```

---

<a id="appsrcmainresdrawableic_launcher_foregroundxml"></a>

### File: `app/src/main/res/drawable/ic_launcher_foreground.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:width="66dp"
        android:height="66dp"
        android:drawable="@drawable/arinova_logo"
        android:gravity="center" />
</layer-list>

```

---

<a id="appsrcmainjavacomexamplemainactivitykt"></a>

### File: `app/src/main/java/com/example/MainActivity.kt`

```kotlin
package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.data.local.HostPayDatabase
import com.example.data.repository.HostPayRepository
import com.example.ui.common.PaymentDetailDialog
import com.example.ui.screens.auth.AuthScreen
import com.example.ui.screens.host.HostAuditLogsScreen
import com.example.ui.screens.host.HostCreatePaymentScreen
import com.example.ui.screens.host.HostDashboardScreen
import com.example.ui.screens.host.HostNotificationsScreen
import com.example.ui.screens.host.HostPendingApprovalsScreen
import com.example.ui.screens.host.HostReportsScreen
import com.example.ui.screens.host.HostSettingsScreen
import com.example.ui.screens.host.HostUserDetailsScreen
import com.example.ui.screens.host.HostUsersScreen
import com.example.ui.screens.user.UserHomeScreen
import com.example.ui.screens.user.UserNotificationsScreen
import com.example.ui.screens.user.UserPaymentsScreen
import com.example.ui.screens.user.UserProfileScreen
import com.example.ui.theme.HostPayTheme
import com.example.ui.viewmodel.AppSession
import com.example.ui.viewmodel.HostPayViewModel
import com.example.ui.viewmodel.HostPayViewModelFactory
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val database = HostPayDatabase.getDatabase(applicationContext)
    val repository = HostPayRepository(
      hostDao = database.hostDao(),
      userDao = database.userDao(),
      paymentDao = database.paymentDao(),
      notificationDao = database.notificationDao(),
      auditLogDao = database.auditLogDao()
    )

    val viewModel: HostPayViewModel by viewModels {
      HostPayViewModelFactory(repository)
    }

    setContent {
      HostPayTheme {
        HostPayApp(viewModel = viewModel, onFinish = { finish() })
      }
    }
  }
}

@Composable
fun HostPayApp(
  viewModel: HostPayViewModel,
  onFinish: () -> Unit
) {
  val currentSession by viewModel.currentSession.collectAsState()
  val currentScreen by viewModel.currentScreen.collectAsState()
  val selectedPayment by viewModel.selectedPayment.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }

  // Listen to alerts from ViewModel
  LaunchedEffect(Unit) {
    viewModel.errorMessage.collectLatest { msg ->
      snackbarHostState.showSnackbar(
        message = msg,
        duration = SnackbarDuration.Short
      )
    }
  }

  LaunchedEffect(Unit) {
    viewModel.successMessage.collectLatest { msg ->
      snackbarHostState.showSnackbar(
        message = msg,
        duration = SnackbarDuration.Short
      )
    }
  }

  // Back handling
  BackHandler {
    if (selectedPayment != null) {
      viewModel.closePaymentDetails()
    } else {
      val handled = viewModel.navigateBack()
      if (!handled) {
        if (currentSession != AppSession.None) {
          viewModel.logout()
        } else {
          onFinish()
        }
      }
    }
  }

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    contentWindowInsets = WindowInsets.safeDrawing,
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .consumeWindowInsets(innerPadding)
        .imePadding()
    ) {
      when (val session = currentSession) {
        is AppSession.None -> {
          AuthScreen(viewModel = viewModel)
        }

        is AppSession.HostSession -> {
          when (val screen = currentScreen) {
            is Screen.HostDashboard -> HostDashboardScreen(host = session.host, viewModel = viewModel)
            is Screen.HostUsers -> HostUsersScreen(viewModel = viewModel)
            is Screen.HostUserDetails -> HostUserDetailsScreen(user = screen.user, viewModel = viewModel)
            is Screen.HostCreatePayment -> HostCreatePaymentScreen(preselectedUser = screen.preselectedUser, viewModel = viewModel)
            is Screen.HostPendingApprovals -> HostPendingApprovalsScreen(viewModel = viewModel)
            is Screen.HostReports -> HostReportsScreen(viewModel = viewModel)
            is Screen.HostAuditLogs -> HostAuditLogsScreen(viewModel = viewModel)
            is Screen.HostNotifications -> HostNotificationsScreen(viewModel = viewModel)
            is Screen.HostSettings -> HostSettingsScreen(host = session.host, viewModel = viewModel)
            else -> HostDashboardScreen(host = session.host, viewModel = viewModel)
          }
        }

        is AppSession.UserSession -> {
          when (currentScreen) {
            is Screen.UserHome -> UserHomeScreen(user = session.user, viewModel = viewModel)
            is Screen.UserPayments -> UserPaymentsScreen(viewModel = viewModel)
            is Screen.UserNotifications -> UserNotificationsScreen(viewModel = viewModel)
            is Screen.UserProfile -> UserProfileScreen(user = session.user, viewModel = viewModel)
            else -> UserHomeScreen(user = session.user, viewModel = viewModel)
          }
        }
      }

      // High-res payment inspection / approval workflow dialog
      selectedPayment?.let { payment ->
        val isCurrentUser = (currentSession as? AppSession.UserSession)?.user?.id == payment.userId
        PaymentDetailDialog(
          payment = payment,
          isCurrentUser = isCurrentUser,
          onDismiss = { viewModel.closePaymentDetails() },
          onApprove = { p -> viewModel.approvePayment(p) },
          onReject = { p, reason -> viewModel.rejectPayment(p, reason) }
        )
      }
    }
  }
}

```

---

<a id="appsrcmainjavacomexampledatamodelentitieskt"></a>

### File: `app/src/main/java/com/example/data/model/Entities.kt`

```kotlin
package com.example.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "hosts",
  indices = [
    Index(value = ["hostCode"], unique = true),
    Index(value = ["mobile"], unique = true)
  ]
)
data class HostEntity(
  @PrimaryKey val id: String,
  val hostCode: String,
  val name: String,
  val organization: String,
  val mobile: String,
  val passwordHash: String,
  val salt: String,
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(
  tableName = "users",
  indices = [
    Index(value = ["hostId"]),
    Index(value = ["hostCode"]),
    Index(value = ["mobile"], unique = true)
  ]
)
data class UserEntity(
  @PrimaryKey val id: String,
  val hostId: String,
  val hostCode: String,
  val name: String,
  val village: String,
  val gender: String, // "Male", "Female", "Other"
  val mobile: String,
  val passwordHash: String,
  val salt: String,
  val profilePhotoUri: String? = null,
  val createdAt: Long = System.currentTimeMillis()
)

object PaymentStatus {
  const val PENDING_USER_APPROVAL = "PENDING_USER_APPROVAL"
  const val APPROVED = "APPROVED"
  const val REJECTED_BY_USER = "REJECTED_BY_USER"
}

@Entity(
  tableName = "payments",
  indices = [
    Index(value = ["userId"]),
    Index(value = ["hostId"]),
    Index(value = ["status"])
  ]
)
data class PaymentEntity(
  @PrimaryKey val transactionId: String,
  val userId: String,
  val userName: String,
  val userVillage: String,
  val hostId: String,
  val hostName: String,
  val amount: Double,
  val date: String,
  val time: String,
  val timestamp: Long = System.currentTimeMillis(),
  val proofPhotoUri: String? = null,
  val note: String? = null,
  val status: String = PaymentStatus.PENDING_USER_APPROVAL,
  val rejectionReason: String? = null,
  val approvedAt: Long? = null,
  val rejectedAt: Long? = null,
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(
  tableName = "notifications",
  indices = [
    Index(value = ["recipientType", "recipientId"])
  ]
)
data class NotificationEntity(
  @PrimaryKey val id: String,
  val recipientType: String, // "USER" or "HOST"
  val recipientId: String,
  val title: String,
  val message: String,
  val transactionId: String? = null,
  val timestamp: Long = System.currentTimeMillis(),
  val isRead: Boolean = false
)

@Entity(
  tableName = "audit_logs",
  indices = [
    Index(value = ["hostId"]),
    Index(value = ["timestamp"])
  ]
)
data class AuditLogEntity(
  @PrimaryKey val id: String,
  val hostId: String,
  val actorName: String,
  val actorRole: String, // "HOST" or "USER"
  val action: String,
  val details: String,
  val timestamp: Long = System.currentTimeMillis()
)

```

---

<a id="appsrcmainjavacomexampledatalocaldaoskt"></a>

### File: `app/src/main/java/com/example/data/local/Daos.kt`

```kotlin
package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AuditLogEntity
import com.example.data.model.HostEntity
import com.example.data.model.NotificationEntity
import com.example.data.model.PaymentEntity
import com.example.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HostDao {
  @Query("SELECT * FROM hosts WHERE mobile = :mobile LIMIT 1")
  suspend fun getHostByMobile(mobile: String): HostEntity?

  @Query("SELECT * FROM hosts WHERE hostCode = :code LIMIT 1")
  suspend fun getHostByCode(code: String): HostEntity?

  @Query("SELECT * FROM hosts WHERE id = :id LIMIT 1")
  suspend fun getHostById(id: String): HostEntity?

  @Query("SELECT * FROM hosts WHERE id = :id LIMIT 1")
  fun observeHostById(id: String): Flow<HostEntity?>

  @Query("SELECT * FROM hosts")
  fun getAllHosts(): Flow<List<HostEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertHost(host: HostEntity)
}

@Dao
interface UserDao {
  @Query("SELECT * FROM users WHERE mobile = :mobile LIMIT 1")
  suspend fun getUserByMobile(mobile: String): UserEntity?

  @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
  suspend fun getUserById(id: String): UserEntity?

  @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
  fun observeUserById(id: String): Flow<UserEntity?>

  @Query("SELECT * FROM users WHERE hostId = :hostId ORDER BY name ASC")
  fun getUsersByHost(hostId: String): Flow<List<UserEntity>>

  @Query("SELECT COUNT(*) FROM users WHERE hostId = :hostId")
  fun getUserCountByHost(hostId: String): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUser(user: UserEntity)

  @Query("SELECT * FROM users")
  fun getAllUsers(): Flow<List<UserEntity>>
}

@Dao
interface PaymentDao {
  @Query("SELECT * FROM payments WHERE transactionId = :id LIMIT 1")
  suspend fun getPaymentById(id: String): PaymentEntity?

  @Query("SELECT * FROM payments WHERE transactionId = :id LIMIT 1")
  fun observePaymentById(id: String): Flow<PaymentEntity?>

  @Query("SELECT * FROM payments WHERE userId = :userId ORDER BY timestamp DESC")
  fun getPaymentsByUser(userId: String): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE hostId = :hostId ORDER BY timestamp DESC")
  fun getPaymentsByHost(hostId: String): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE hostId = :hostId AND status = 'PENDING_USER_APPROVAL' ORDER BY timestamp DESC")
  fun getPendingPaymentsByHost(hostId: String): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE userId = :userId AND status = 'PENDING_USER_APPROVAL' ORDER BY timestamp DESC")
  fun getPendingPaymentsByUser(userId: String): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE userId = :userId AND hostId = :hostId ORDER BY timestamp DESC")
  fun getPaymentsByUserAndHost(userId: String, hostId: String): Flow<List<PaymentEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPayment(payment: PaymentEntity)

  @Update
  suspend fun updatePayment(payment: PaymentEntity)

  @Query("UPDATE payments SET status = :status, approvedAt = :approvedAt WHERE transactionId = :id")
  suspend fun approvePayment(id: String, status: String, approvedAt: Long)

  @Query("UPDATE payments SET status = :status, rejectionReason = :reason, rejectedAt = :rejectedAt WHERE transactionId = :id")
  suspend fun rejectPayment(id: String, status: String, reason: String, rejectedAt: Long)

  @Query("SELECT SUM(amount) FROM payments WHERE userId = :userId AND status = 'APPROVED'")
  fun getTotalApprovedAmountForUser(userId: String): Flow<Double?>

  @Query("SELECT SUM(amount) FROM payments WHERE hostId = :hostId AND status = 'APPROVED'")
  fun getTotalApprovedAmountForHost(hostId: String): Flow<Double?>

  @Query("SELECT COUNT(*) FROM payments WHERE hostId = :hostId AND status = 'APPROVED'")
  fun getApprovedCountForHost(hostId: String): Flow<Int>

  @Query("SELECT COUNT(*) FROM payments WHERE hostId = :hostId AND status = 'PENDING_USER_APPROVAL'")
  fun getPendingCountForHost(hostId: String): Flow<Int>
}

@Dao
interface NotificationDao {
  @Query("SELECT * FROM notifications WHERE recipientType = :type AND recipientId = :id ORDER BY timestamp DESC")
  fun getNotifications(type: String, id: String): Flow<List<NotificationEntity>>

  @Query("SELECT COUNT(*) FROM notifications WHERE recipientType = :type AND recipientId = :id AND isRead = 0")
  fun getUnreadCount(type: String, id: String): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNotification(notification: NotificationEntity)

  @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
  suspend fun markAsRead(id: String)

  @Query("UPDATE notifications SET isRead = 1 WHERE recipientType = :type AND recipientId = :id")
  suspend fun markAllAsRead(type: String, id: String)
}

@Dao
interface AuditLogDao {
  @Query("SELECT * FROM audit_logs WHERE hostId = :hostId ORDER BY timestamp DESC")
  fun getLogsByHost(hostId: String): Flow<List<AuditLogEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertLog(log: AuditLogEntity)
}

```

---

<a id="appsrcmainjavacomexampledatalocalhostpaydatabasekt"></a>

### File: `app/src/main/java/com/example/data/local/HostPayDatabase.kt`

```kotlin
package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.AuditLogEntity
import com.example.data.model.HostEntity
import com.example.data.model.NotificationEntity
import com.example.data.model.PaymentEntity
import com.example.data.model.UserEntity

@Database(
  entities = [
    HostEntity::class,
    UserEntity::class,
    PaymentEntity::class,
    NotificationEntity::class,
    AuditLogEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class HostPayDatabase : RoomDatabase() {
  abstract fun hostDao(): HostDao
  abstract fun userDao(): UserDao
  abstract fun paymentDao(): PaymentDao
  abstract fun notificationDao(): NotificationDao
  abstract fun auditLogDao(): AuditLogDao

  companion object {
    @Volatile
    private var INSTANCE: HostPayDatabase? = null

    fun getDatabase(context: Context): HostPayDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          HostPayDatabase::class.java,
          "hostpay_database"
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}

```

---

<a id="appsrcmainjavacomexampledatarepositoryhostpayrepositorykt"></a>

### File: `app/src/main/java/com/example/data/repository/HostPayRepository.kt`

```kotlin
package com.example.data.repository

import com.example.data.local.AuditLogDao
import com.example.data.local.HostDao
import com.example.data.local.NotificationDao
import com.example.data.local.PaymentDao
import com.example.data.local.UserDao
import com.example.data.model.AuditLogEntity
import com.example.data.model.HostEntity
import com.example.data.model.NotificationEntity
import com.example.data.model.PaymentEntity
import com.example.data.model.PaymentStatus
import com.example.data.model.UserEntity
import com.example.data.security.SecurityUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

sealed class AuthResult<out T> {
  data class Success<out T>(val data: T) : AuthResult<T>()
  data class Error(val message: String) : AuthResult<Nothing>()
}

class HostPayRepository(
  private val hostDao: HostDao,
  private val userDao: UserDao,
  private val paymentDao: PaymentDao,
  private val notificationDao: NotificationDao,
  private val auditLogDao: AuditLogDao
) {

  // ================= HOST AUTH & DATA =================

  suspend fun registerHost(
    name: String,
    organization: String,
    mobile: String,
    password: String
  ): AuthResult<HostEntity> {
    if (name.isBlank() || mobile.isBlank() || password.isBlank()) {
      return AuthResult.Error("Name, mobile number, and password are required.")
    }
    val existing = hostDao.getHostByMobile(mobile.trim())
    if (existing != null) {
      return AuthResult.Error("A host with this mobile number already exists.")
    }

    // Generate unique host code
    var hostCode = SecurityUtils.generateHostCode()
    while (hostDao.getHostByCode(hostCode) != null) {
      hostCode = SecurityUtils.generateHostCode()
    }

    val salt = SecurityUtils.generateSalt()
    val passwordHash = SecurityUtils.hashPassword(password, salt)
    val host = HostEntity(
      id = SecurityUtils.generateId("host"),
      hostCode = hostCode,
      name = name.trim(),
      organization = organization.trim().ifEmpty { "HostPay Partner" },
      mobile = mobile.trim(),
      passwordHash = passwordHash,
      salt = salt
    )
    hostDao.insertHost(host)

    // Log audit
    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = host.id,
        actorName = host.name,
        actorRole = "HOST",
        action = "Host Registered",
        details = "Host account created with Code: $hostCode, Org: ${host.organization}"
      )
    )

    return AuthResult.Success(host)
  }

  suspend fun loginHost(mobile: String, password: String): AuthResult<HostEntity> {
    val host = hostDao.getHostByMobile(mobile.trim())
      ?: return AuthResult.Error("Host account not found with this mobile number.")

    val valid = SecurityUtils.verifyPassword(password, host.salt, host.passwordHash)
    if (!valid) {
      return AuthResult.Error("Incorrect password. Please try again.")
    }

    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = host.id,
        actorName = host.name,
        actorRole = "HOST",
        action = "Host Logged In",
        details = "Session started for ${host.name}"
      )
    )

    return AuthResult.Success(host)
  }

  fun observeHost(hostId: String): Flow<HostEntity?> = hostDao.observeHostById(hostId)

  suspend fun findHostByCode(code: String): HostEntity? {
    return hostDao.getHostByCode(code.trim().uppercase())
  }

  // ================= USER AUTH & DATA =================

  suspend fun registerUser(
    hostCode: String,
    name: String,
    village: String,
    gender: String,
    mobile: String,
    password: String,
    profilePhotoUri: String? = null
  ): AuthResult<UserEntity> {
    if (name.isBlank() || mobile.isBlank() || password.isBlank() || village.isBlank()) {
      return AuthResult.Error("Please fill in all mandatory fields.")
    }

    val cleanCode = hostCode.trim().uppercase()
    val host = hostDao.getHostByCode(cleanCode)
      ?: return AuthResult.Error("Invalid Host Code '$cleanCode'. Please enter a valid Host Code.")

    val existingUser = userDao.getUserByMobile(mobile.trim())
    if (existingUser != null) {
      return AuthResult.Error("A user with mobile ${mobile.trim()} is already registered.")
    }

    val salt = SecurityUtils.generateSalt()
    val passwordHash = SecurityUtils.hashPassword(password, salt)
    val user = UserEntity(
      id = SecurityUtils.generateId("usr"),
      hostId = host.id,
      hostCode = cleanCode,
      name = name.trim(),
      village = village.trim(),
      gender = gender,
      mobile = mobile.trim(),
      passwordHash = passwordHash,
      salt = salt,
      profilePhotoUri = profilePhotoUri
    )
    userDao.insertUser(user)

    // Notify Host of new User joined
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "HOST",
        recipientId = host.id,
        title = "New User Joined",
        message = "${user.name} from ${user.village} joined using code $cleanCode.",
        timestamp = System.currentTimeMillis()
      )
    )

    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = host.id,
        actorName = user.name,
        actorRole = "USER",
        action = "User Joined Host",
        details = "${user.name} (${user.village}) joined using code $cleanCode"
      )
    )

    return AuthResult.Success(user)
  }

  suspend fun loginUser(mobile: String, password: String): AuthResult<UserEntity> {
    val user = userDao.getUserByMobile(mobile.trim())
      ?: return AuthResult.Error("No user account found with mobile ${mobile.trim()}.")

    val valid = SecurityUtils.verifyPassword(password, user.salt, user.passwordHash)
    if (!valid) {
      return AuthResult.Error("Incorrect password. Please try again.")
    }

    return AuthResult.Success(user)
  }

  fun observeUser(userId: String): Flow<UserEntity?> = userDao.observeUserById(userId)

  fun getUsersByHost(hostId: String): Flow<List<UserEntity>> = userDao.getUsersByHost(hostId)

  fun getUserCountByHost(hostId: String): Flow<Int> = userDao.getUserCountByHost(hostId)

  suspend fun getUserById(userId: String): UserEntity? = userDao.getUserById(userId)

  suspend fun getHostById(hostId: String): HostEntity? = hostDao.getHostById(hostId)

  // ================= PAYMENT WORKFLOW =================

  /**
   * Host creates payment record. Status is strictly PENDING_USER_APPROVAL.
   * Host cannot approve.
   */
  suspend fun createPayment(
    host: HostEntity,
    user: UserEntity,
    amount: Double,
    note: String?,
    proofPhotoUri: String?
  ): Result<PaymentEntity> {
    if (amount <= 0) {
      return Result.failure(IllegalArgumentException("Amount must be greater than 0"))
    }

    val txnId = SecurityUtils.generateTransactionId()
    val date = SecurityUtils.getCurrentDateFormatted()
    val time = SecurityUtils.getCurrentTimeFormatted()

    val payment = PaymentEntity(
      transactionId = txnId,
      userId = user.id,
      userName = user.name,
      userVillage = user.village,
      hostId = host.id,
      hostName = host.name,
      amount = amount,
      date = date,
      time = time,
      timestamp = System.currentTimeMillis(),
      proofPhotoUri = proofPhotoUri,
      note = note?.trim()?.ifEmpty { null },
      status = PaymentStatus.PENDING_USER_APPROVAL
    )

    paymentDao.insertPayment(payment)

    // Send notification to the user
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "USER",
        recipientId = user.id,
        title = "Payment Confirmation Request",
        message = "${host.name} has sent you a payment of ₹${"%.2f".format(amount)}. Please review and approve.",
        transactionId = txnId,
        timestamp = System.currentTimeMillis()
      )
    )

    // Audit log
    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = host.id,
        actorName = host.name,
        actorRole = "HOST",
        action = "Payment Created",
        details = "Created payment $txnId of ₹${"%.2f".format(amount)} for ${user.name} (Pending Approval)"
      )
    )

    return Result.success(payment)
  }

  /**
   * User approves payment.
   */
  suspend fun approvePayment(
    paymentId: String,
    user: UserEntity
  ): Result<Unit> {
    val payment = paymentDao.getPaymentById(paymentId)
      ?: return Result.failure(IllegalStateException("Payment not found"))

    if (payment.userId != user.id) {
      return Result.failure(SecurityException("Unauthorized: Only the assigned user can approve this payment"))
    }

    if (payment.status != PaymentStatus.PENDING_USER_APPROVAL) {
      return Result.failure(IllegalStateException("Payment is already in status ${payment.status}"))
    }

    val now = System.currentTimeMillis()
    paymentDao.approvePayment(paymentId, PaymentStatus.APPROVED, now)

    // Notify Host
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "HOST",
        recipientId = payment.hostId,
        title = "Payment Approved!",
        message = "${user.name} approved payment $paymentId of ₹${"%.2f".format(payment.amount)}.",
        transactionId = paymentId,
        timestamp = now
      )
    )

    // Notify User
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "USER",
        recipientId = user.id,
        title = "Confirmation Successful",
        message = "You approved payment of ₹${"%.2f".format(payment.amount)} from ${payment.hostName}.",
        transactionId = paymentId,
        timestamp = now
      )
    )

    // Audit log
    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = payment.hostId,
        actorName = user.name,
        actorRole = "USER",
        action = "Payment Approved",
        details = "${user.name} confirmed receipt of ₹${"%.2f".format(payment.amount)} ($paymentId)"
      )
    )

    return Result.success(Unit)
  }

  /**
   * User rejects payment with reason.
   */
  suspend fun rejectPayment(
    paymentId: String,
    user: UserEntity,
    reason: String
  ): Result<Unit> {
    val payment = paymentDao.getPaymentById(paymentId)
      ?: return Result.failure(IllegalStateException("Payment not found"))

    if (payment.userId != user.id) {
      return Result.failure(SecurityException("Unauthorized: Only the assigned user can reject this payment"))
    }

    if (payment.status != PaymentStatus.PENDING_USER_APPROVAL) {
      return Result.failure(IllegalStateException("Payment is already in status ${payment.status}"))
    }

    val cleanReason = reason.trim().ifEmpty { "Rejected by user without specific reason" }
    val now = System.currentTimeMillis()
    paymentDao.rejectPayment(paymentId, PaymentStatus.REJECTED_BY_USER, cleanReason, now)

    // Notify Host
    notificationDao.insertNotification(
      NotificationEntity(
        id = SecurityUtils.generateId("notif"),
        recipientType = "HOST",
        recipientId = payment.hostId,
        title = "Payment Rejected",
        message = "${user.name} rejected payment $paymentId (₹${"%.2f".format(payment.amount)}). Reason: $cleanReason",
        transactionId = paymentId,
        timestamp = now
      )
    )

    // Audit log
    auditLogDao.insertLog(
      AuditLogEntity(
        id = SecurityUtils.generateId("log"),
        hostId = payment.hostId,
        actorName = user.name,
        actorRole = "USER",
        action = "Payment Rejected",
        details = "${user.name} rejected $paymentId (₹${"%.2f".format(payment.amount)}). Reason: $cleanReason"
      )
    )

    return Result.success(Unit)
  }

  fun getPaymentsByUser(userId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPaymentsByUser(userId)

  fun getPaymentsByHost(hostId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPaymentsByHost(hostId)

  fun getPendingPaymentsByHost(hostId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPendingPaymentsByHost(hostId)

  fun getPendingPaymentsByUser(userId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPendingPaymentsByUser(userId)

  fun getPaymentsByUserAndHost(userId: String, hostId: String): Flow<List<PaymentEntity>> =
    paymentDao.getPaymentsByUserAndHost(userId, hostId)

  fun getTotalApprovedAmountForUser(userId: String): Flow<Double?> =
    paymentDao.getTotalApprovedAmountForUser(userId)

  fun getTotalApprovedAmountForHost(hostId: String): Flow<Double?> =
    paymentDao.getTotalApprovedAmountForHost(hostId)

  fun getApprovedCountForHost(hostId: String): Flow<Int> =
    paymentDao.getApprovedCountForHost(hostId)

  fun getPendingCountForHost(hostId: String): Flow<Int> =
    paymentDao.getPendingCountForHost(hostId)

  // ================= NOTIFICATIONS & AUDIT =================

  fun getNotifications(type: String, recipientId: String): Flow<List<NotificationEntity>> =
    notificationDao.getNotifications(type, recipientId)

  fun getUnreadCount(type: String, recipientId: String): Flow<Int> =
    notificationDao.getUnreadCount(type, recipientId)

  suspend fun markNotificationAsRead(id: String) = notificationDao.markAsRead(id)

  suspend fun markAllNotificationsAsRead(type: String, recipientId: String) =
    notificationDao.markAllAsRead(type, recipientId)

  fun getAuditLogs(hostId: String): Flow<List<AuditLogEntity>> =
    auditLogDao.getLogsByHost(hostId)

  // ================= INITIAL DEMO SEEDER =================

  suspend fun seedDemoDataIfEmpty() {
    val existingHosts = hostDao.getAllHosts().firstOrNull()
    if (!existingHosts.isNullOrEmpty()) return

    // Create demo host
    val hostSalt = SecurityUtils.generateSalt()
    val demoHost = HostEntity(
      id = "host_demo_ravi",
      hostCode = "HP-5520",
      name = "Ravi Sharma",
      organization = "AgriNova Logistics",
      mobile = "9876543210",
      passwordHash = SecurityUtils.hashPassword("host123", hostSalt),
      salt = hostSalt,
      createdAt = System.currentTimeMillis() - 86400000L * 7
    )
    hostDao.insertHost(demoHost)

    // Create 3 demo users
    val u1Salt = SecurityUtils.generateSalt()
    val user1 = UserEntity(
      id = "usr_suresh",
      hostId = demoHost.id,
      hostCode = demoHost.hostCode,
      name = "Suresh Patel",
      village = "Rampur",
      gender = "Male",
      mobile = "9123456780",
      passwordHash = SecurityUtils.hashPassword("user123", u1Salt),
      salt = u1Salt,
      createdAt = System.currentTimeMillis() - 86400000L * 5
    )
    val u2Salt = SecurityUtils.generateSalt()
    val user2 = UserEntity(
      id = "usr_anita",
      hostId = demoHost.id,
      hostCode = demoHost.hostCode,
      name = "Anita Devi",
      village = "Kalyanpur",
      gender = "Female",
      mobile = "9123456781",
      passwordHash = SecurityUtils.hashPassword("user123", u2Salt),
      salt = u2Salt,
      createdAt = System.currentTimeMillis() - 86400000L * 4
    )
    val u3Salt = SecurityUtils.generateSalt()
    val user3 = UserEntity(
      id = "usr_ramesh",
      hostId = demoHost.id,
      hostCode = demoHost.hostCode,
      name = "Ramesh Kumar",
      village = "Bishunpur",
      gender = "Male",
      mobile = "9123456782",
      passwordHash = SecurityUtils.hashPassword("user123", u3Salt),
      salt = u3Salt,
      createdAt = System.currentTimeMillis() - 86400000L * 3
    )
    userDao.insertUser(user1)
    userDao.insertUser(user2)
    userDao.insertUser(user3)

    // Demo Payments:
    // 1. Approved payment for Suresh (₹8,500)
    val approvedPayment = PaymentEntity(
      transactionId = "TXN-20261001-4912",
      userId = user1.id,
      userName = user1.name,
      userVillage = user1.village,
      hostId = demoHost.id,
      hostName = demoHost.name,
      amount = 8500.0,
      date = "01 Oct 2026",
      time = "11:30 AM",
      timestamp = System.currentTimeMillis() - 86400000L * 3,
      note = "Harvest labor advance payment for week 1",
      status = PaymentStatus.APPROVED,
      approvedAt = System.currentTimeMillis() - 86400000L * 3 + 3600000L
    )

    // 2. Pending payment for Suresh (₹4,200) - READY for user approval test!
    val pendingPayment = PaymentEntity(
      transactionId = "TXN-20261004-8194",
      userId = user1.id,
      userName = user1.name,
      userVillage = user1.village,
      hostId = demoHost.id,
      hostName = demoHost.name,
      amount = 4200.0,
      date = "04 Oct 2026",
      time = "09:15 AM",
      timestamp = System.currentTimeMillis() - 3600000L,
      note = "Grain transport compensation & fuel charges",
      status = PaymentStatus.PENDING_USER_APPROVAL
    )

    // 3. Approved payment for Anita (₹6,000)
    val approvedPayment2 = PaymentEntity(
      transactionId = "TXN-20261002-3104",
      userId = user2.id,
      userName = user2.name,
      userVillage = user2.village,
      hostId = demoHost.id,
      hostName = demoHost.name,
      amount = 6000.0,
      date = "02 Oct 2026",
      time = "04:45 PM",
      timestamp = System.currentTimeMillis() - 86400000L * 2,
      note = "Field maintenance & seed distribution payout",
      status = PaymentStatus.APPROVED,
      approvedAt = System.currentTimeMillis() - 86400000L * 2 + 1800000L
    )

    // 4. Rejected payment for Ramesh (₹3,000)
    val rejectedPayment = PaymentEntity(
      transactionId = "TXN-20260930-1928",
      userId = user3.id,
      userName = user3.name,
      userVillage = user3.village,
      hostId = demoHost.id,
      hostName = demoHost.name,
      amount = 3000.0,
      date = "30 Sep 2026",
      time = "02:10 PM",
      timestamp = System.currentTimeMillis() - 86400000L * 4,
      note = "Tractor rental reimbursement",
      status = PaymentStatus.REJECTED_BY_USER,
      rejectionReason = "Incorrect amount: Agreed fuel surcharge was ₹3,800, not ₹3,000.",
      rejectedAt = System.currentTimeMillis() - 86400000L * 4 + 7200000L
    )

    paymentDao.insertPayment(approvedPayment)
    paymentDao.insertPayment(pendingPayment)
    paymentDao.insertPayment(approvedPayment2)
    paymentDao.insertPayment(rejectedPayment)

    // Insert Notifications
    notificationDao.insertNotification(
      NotificationEntity(
        id = "notif_user_1",
        recipientType = "USER",
        recipientId = user1.id,
        title = "Action Needed: Payment Approval",
        message = "Ravi Sharma sent ₹4,200.00 for Grain transport. Tap to verify and approve.",
        transactionId = pendingPayment.transactionId,
        timestamp = pendingPayment.timestamp,
        isRead = false
      )
    )

    // Insert Audit Logs
    auditLogDao.insertLog(
      AuditLogEntity(
        id = "log_1",
        hostId = demoHost.id,
        actorName = demoHost.name,
        actorRole = "HOST",
        action = "Host Setup",
        details = "Host registered with code HP-5520",
        timestamp = demoHost.createdAt
      )
    )
    auditLogDao.insertLog(
      AuditLogEntity(
        id = "log_2",
        hostId = demoHost.id,
        actorName = user1.name,
        actorRole = "USER",
        action = "Payment Approved",
        details = "Suresh Patel approved TXN-20261001-4912 of ₹8,500.00",
        timestamp = approvedPayment.timestamp
      )
    )
    auditLogDao.insertLog(
      AuditLogEntity(
        id = "log_3",
        hostId = demoHost.id,
        actorName = demoHost.name,
        actorRole = "HOST",
        action = "Payment Created",
        details = "Created payment TXN-20261004-8194 of ₹4,200.00 for Suresh Patel",
        timestamp = pendingPayment.timestamp
      )
    )
  }
}

```

---

<a id="appsrcmainjavacomexampledatasecuritysecurityutilskt"></a>

### File: `app/src/main/java/com/example/data/security/SecurityUtils.kt`

```kotlin
package com.example.data.security

import java.security.MessageDigest
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object SecurityUtils {

  private val secureRandom = SecureRandom()

  fun generateSalt(): String {
    val bytes = ByteArray(16)
    secureRandom.nextBytes(bytes)
    return bytes.joinToString("") { "%02x".format(it) }
  }

  fun hashPassword(password: String, salt: String): String {
    val input = "$salt:$password"
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(input.toByteArray(Charsets.UTF_8))
    return digest.joinToString("") { "%02x".format(it) }
  }

  fun verifyPassword(password: String, salt: String, expectedHash: String): Boolean {
    val computed = hashPassword(password, salt)
    return computed == expectedHash
  }

  /**
   * Generates a memorable 6-digit Host Code with HP prefix, e.g. "HP-4892" or "HP-7204"
   */
  fun generateHostCode(): String {
    val number = 1000 + secureRandom.nextInt(9000)
    return "HP-$number"
  }

  fun generateTransactionId(): String {
    val datePrefix = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
    val randomSuffix = (1000 + secureRandom.nextInt(9000)).toString()
    return "TXN-$datePrefix-$randomSuffix"
  }

  fun generateId(prefix: String): String {
    return "${prefix}_${UUID.randomUUID().toString().replace("-", "").take(12)}"
  }

  fun getCurrentDateFormatted(): String {
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
  }

  fun getCurrentTimeFormatted(): String {
    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
  }
}

```

---

<a id="appsrcmainjavacomexampledatautilimageutilskt"></a>

### File: `app/src/main/java/com/example/data/util/ImageUtils.kt`

```kotlin
package com.example.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

object ImageUtils {

  fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap, folderName: String = "photos"): String {
    val dir = File(context.filesDir, folderName)
    if (!dir.exists()) {
      dir.mkdirs()
    }
    val file = File(dir, "img_${System.currentTimeMillis()}_${UUID.randomUUID().toString().take(6)}.jpg")
    FileOutputStream(file).use { out ->
      bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
    }
    return file.absolutePath
  }

  fun saveUriToInternalStorage(context: Context, sourceUri: Uri, folderName: String = "photos"): String? {
    return try {
      val dir = File(context.filesDir, folderName)
      if (!dir.exists()) {
        dir.mkdirs()
      }
      val file = File(dir, "img_${System.currentTimeMillis()}_${UUID.randomUUID().toString().take(6)}.jpg")
      context.contentResolver.openInputStream(sourceUri)?.use { input: InputStream ->
        FileOutputStream(file).use { output ->
          input.copyTo(output)
        }
      }
      file.absolutePath
    } catch (e: Exception) {
      null
    }
  }

  fun loadSampleProofBitmap(context: Context): Bitmap? {
    return try {
      val inputStream = context.assets.open("sample_receipt.jpg")
      BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
      null
    }
  }
}

```

---

<a id="appsrcmainjavacomexampleuithemecolorkt"></a>

### File: `app/src/main/java/com/example/ui/theme/Color.kt`

```kotlin
package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Brand palette - HostPay Fintech Trust
val BrandNavyDark = Color(0xFF0F172A)
val BrandNavy = Color(0xFF1E293B)
val BrandBlue = Color(0xFF2563EB)
val BrandBlueLight = Color(0xFF3B82F6)
val BrandBlueContainer = Color(0xFFDBEAFE)

val SuccessGreen = Color(0xFF059669)
val SuccessGreenLight = Color(0xFFD1FAE5)
val WarningAmber = Color(0xFFD97706)
val WarningAmberLight = Color(0xFFFEF3C7)
val DangerRed = Color(0xFFDC2626)
val DangerRedLight = Color(0xFFFEE2E2)

// Light Theme
val LightPrimary = Color(0xFF1E40AF)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = Color(0xFFDBEAFE)
val LightOnPrimaryContainer = Color(0xFF1E3A8A)

val LightSecondary = Color(0xFF0D9488)
val LightOnSecondary = Color(0xFFFFFFFF)
val LightSecondaryContainer = Color(0xFFCCFBF1)
val LightOnSecondaryContainer = Color(0xFF115E59)

val LightTertiary = Color(0xFFD97706)
val LightBackground = Color(0xFFF8FAFC)
val LightOnBackground = Color(0xFF0F172A)
val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color(0xFF0F172A)
val LightSurfaceVariant = Color(0xFFF1F5F9)
val LightOnSurfaceVariant = Color(0xFF475569)
val LightOutline = Color(0xFFCBD5E1)

// Dark Theme
val DarkPrimary = Color(0xFF60A5FA)
val DarkOnPrimary = Color(0xFF0F172A)
val DarkPrimaryContainer = Color(0xFF1E3A8A)
val DarkOnPrimaryContainer = Color(0xFFDBEAFE)

val DarkSecondary = Color(0xFF2DD4BF)
val DarkOnSecondary = Color(0xFF0F172A)
val DarkSecondaryContainer = Color(0xFF115E59)
val DarkOnSecondaryContainer = Color(0xFFCCFBF1)

val DarkTertiary = Color(0xFFFBBF24)
val DarkBackground = Color(0xFF0B0F19)
val DarkOnBackground = Color(0xFFF1F5F9)
val DarkSurface = Color(0xFF111827)
val DarkOnSurface = Color(0xFFF8FAFC)
val DarkSurfaceVariant = Color(0xFF1E293B)
val DarkOnSurfaceVariant = Color(0xFF94A3B8)
val DarkOutline = Color(0xFF334155)

```

---

<a id="appsrcmainjavacomexampleuithemethemekt"></a>

### File: `app/src/main/java/com/example/ui/theme/Theme.kt`

```kotlin
package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
  )

@Composable
fun HostPayTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Use our branded theme for consistent fintech identity
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

```

---

<a id="appsrcmainjavacomexampleuithemetypekt"></a>

### File: `app/src/main/java/com/example/ui/theme/Type.kt`

```kotlin
package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography =
  Typography(
    bodyLarge =
      TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
      )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
  )

```

---

<a id="appsrcmainjavacomexampleuicommoncomponentskt"></a>

### File: `app/src/main/java/com/example/ui/common/Components.kt`

```kotlin
package com.example.ui.common

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.PaymentEntity
import com.example.data.model.PaymentStatus
import com.example.ui.theme.DangerRed
import com.example.ui.theme.DangerRedLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberLight
import java.io.File

@Composable
fun PaymentStatusBadge(
  status: String,
  modifier: Modifier = Modifier
) {
  val (bgColor, textColor, label, icon) = when (status) {
    PaymentStatus.APPROVED -> Quad(
      SuccessGreenLight,
      SuccessGreen,
      "APPROVED",
      Icons.Default.CheckCircle
    )
    PaymentStatus.REJECTED_BY_USER -> Quad(
      DangerRedLight,
      DangerRed,
      "REJECTED",
      Icons.Default.Warning
    )
    else -> Quad(
      WarningAmberLight,
      WarningAmber,
      "PENDING APPROVAL",
      Icons.Default.HourglassTop
    )
  }

  Surface(
    modifier = modifier.testTag("status_badge_$status"),
    shape = RoundedCornerShape(12.dp),
    color = bgColor
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = textColor,
        modifier = Modifier.size(13.dp)
      )
      Text(
        text = label,
        color = textColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
      )
    }
  }
}

private data class Quad<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)

@Composable
fun HostCodeBadge(
  hostCode: String,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  Surface(
    modifier = modifier
      .testTag("host_code_badge")
      .clip(RoundedCornerShape(12.dp))
      .clickable {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Host Code", hostCode)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Host Code $hostCode copied to clipboard!", Toast.LENGTH_SHORT).show()
      },
    shape = RoundedCornerShape(12.dp),
    color = MaterialTheme.colorScheme.primaryContainer,
    tonalElevation = 2.dp
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Text(
        text = "Host Code:",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
      )
      Text(
        text = hostCode,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Monospace,
        color = MaterialTheme.colorScheme.onPrimaryContainer
      )
      Icon(
        imageVector = Icons.Default.ContentCopy,
        contentDescription = "Copy Host Code",
        modifier = Modifier.size(16.dp),
        tint = MaterialTheme.colorScheme.onPrimaryContainer
      )
    }
  }
}

@Composable
fun UserAvatar(
  photoUri: String?,
  name: String,
  modifier: Modifier = Modifier,
  sizeDp: Int = 44
) {
  Box(
    modifier = modifier
      .size(sizeDp.dp)
      .clip(CircleShape)
      .background(MaterialTheme.colorScheme.primaryContainer),
    contentAlignment = Alignment.Center
  ) {
    if (!photoUri.isNullOrEmpty()) {
      val imageModel: Any = if (photoUri.startsWith("/")) File(photoUri) else photoUri
      AsyncImage(
        model = imageModel,
        contentDescription = "Profile photo of $name",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(sizeDp.dp)
      )
    } else {
      val initial = name.firstOrNull()?.uppercaseChar()?.toString() ?: "U"
      Text(
        text = initial,
        fontWeight = FontWeight.Bold,
        fontSize = (sizeDp * 0.45).sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer
      )
    }
  }
}

@Composable
fun StatCard(
  title: String,
  value: String,
  icon: ImageVector,
  iconTint: Color,
  modifier: Modifier = Modifier,
  subtitle: String? = null
) {
  Card(
    modifier = modifier.testTag("stat_card_${title.lowercase().replace(" ", "_")}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier.padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(iconTint.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(18.dp)
          )
        }
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      if (subtitle != null) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 11.sp
        )
      }
    }
  }
}

@Composable
fun PaymentItemCard(
  payment: PaymentEntity,
  isHostView: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("payment_card_${payment.transactionId}")
      .clip(RoundedCornerShape(16.dp))
      .clickable { onClick() },
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Photo thumbnail or placeholder
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
      ) {
        if (!payment.proofPhotoUri.isNullOrEmpty()) {
          val imgModel: Any = if (payment.proofPhotoUri.startsWith("/")) File(payment.proofPhotoUri) else payment.proofPhotoUri
          AsyncImage(
            model = imgModel,
            contentDescription = "Payment proof",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp)
          )
        } else {
          Icon(
            imageVector = Icons.Default.Image,
            contentDescription = "No photo",
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(24.dp)
          )
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isHostView) payment.userName else payment.hostName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = "₹${"%,.2f".format(payment.amount)}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (payment.status == PaymentStatus.APPROVED) SuccessGreen else MaterialTheme.colorScheme.onSurface
          )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${payment.date} • ${payment.time}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          PaymentStatusBadge(status = payment.status)
        }

        if (!payment.note.isNullOrBlank()) {
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = payment.note,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }
    }
  }
}

@Composable
fun EmptyPlaceholder(
  title: String,
  description: String,
  icon: ImageVector,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Box(
      modifier = Modifier
        .size(64.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.surfaceVariant),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier.size(32.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = title,
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = description,
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      fontSize = 13.sp,
      lineHeight = 18.sp
    )
  }
}

```

---

<a id="appsrcmainjavacomexampleuicommonpaymentdetaildialogkt"></a>

### File: `app/src/main/java/com/example/ui/common/PaymentDetailDialog.kt`

```kotlin
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

```

---

<a id="appsrcmainjavacomexampleuiviewmodelhostpayviewmodelkt"></a>

### File: `app/src/main/java/com/example/ui/viewmodel/HostPayViewModel.kt`

```kotlin
package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.AuditLogEntity
import com.example.data.model.HostEntity
import com.example.data.model.NotificationEntity
import com.example.data.model.PaymentEntity
import com.example.data.model.UserEntity
import com.example.data.repository.AuthResult
import com.example.data.repository.HostPayRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class AppSession {
  object None : AppSession()
  data class HostSession(val host: HostEntity) : AppSession()
  data class UserSession(val user: UserEntity) : AppSession()
}

sealed class Screen {
  object Splash : Screen()
  object Auth : Screen()

  // Host screens
  object HostDashboard : Screen()
  object HostUsers : Screen()
  data class HostUserDetails(val user: UserEntity) : Screen()
  data class HostCreatePayment(val preselectedUser: UserEntity? = null) : Screen()
  object HostPendingApprovals : Screen()
  object HostReports : Screen()
  object HostAuditLogs : Screen()
  object HostNotifications : Screen()
  object HostSettings : Screen()

  // User screens
  object UserHome : Screen()
  object UserPayments : Screen()
  object UserNotifications : Screen()
  object UserProfile : Screen()
}

data class HostDashboardMetrics(
  val totalUsers: Int = 0,
  val totalConfirmedAmount: Double = 0.0,
  val confirmedPaymentsCount: Int = 0,
  val pendingApprovalsCount: Int = 0,
  val pendingAmount: Double = 0.0
)

class HostPayViewModel(
  private val repository: HostPayRepository
) : ViewModel() {

  private val _currentSession = MutableStateFlow<AppSession>(AppSession.None)
  val currentSession: StateFlow<AppSession> = _currentSession.asStateFlow()

  private val _currentScreen = MutableStateFlow<Screen>(Screen.Auth)
  val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

  private val screenBackStack = mutableListOf<Screen>()

  private val _errorMessage = MutableSharedFlow<String>()
  val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

  private val _successMessage = MutableSharedFlow<String>()
  val successMessage: SharedFlow<String> = _successMessage.asSharedFlow()

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

  // Selected payment for inspection or approval
  private val _selectedPayment = MutableStateFlow<PaymentEntity?>(null)
  val selectedPayment: StateFlow<PaymentEntity?> = _selectedPayment.asStateFlow()

  init {
    viewModelScope.launch {
      repository.seedDemoDataIfEmpty()
    }
  }

  fun navigateTo(screen: Screen) {
    screenBackStack.add(_currentScreen.value)
    _currentScreen.value = screen
  }

  fun navigateBack(): Boolean {
    if (screenBackStack.isNotEmpty()) {
      val prev = screenBackStack.removeAt(screenBackStack.lastIndex)
      _currentScreen.value = prev
      return true
    }
    return false
  }

  fun logout() {
    screenBackStack.clear()
    _currentSession.value = AppSession.None
    _currentScreen.value = Screen.Auth
  }

  // ================= HOST AUTH & ACTIONS =================

  fun registerHost(
    name: String,
    organization: String,
    mobile: String,
    password: String,
    onSuccess: (HostEntity) -> Unit = {}
  ) {
    viewModelScope.launch {
      _isLoading.value = true
      when (val result = repository.registerHost(name, organization, mobile, password)) {
        is AuthResult.Success -> {
          _currentSession.value = AppSession.HostSession(result.data)
          screenBackStack.clear()
          _currentScreen.value = Screen.HostDashboard
          _successMessage.emit("Host registered! Your code is ${result.data.hostCode}")
          onSuccess(result.data)
        }
        is AuthResult.Error -> {
          _errorMessage.emit(result.message)
        }
      }
      _isLoading.value = false
    }
  }

  fun loginHost(mobile: String, password: String) {
    viewModelScope.launch {
      _isLoading.value = true
      when (val result = repository.loginHost(mobile, password)) {
        is AuthResult.Success -> {
          _currentSession.value = AppSession.HostSession(result.data)
          screenBackStack.clear()
          _currentScreen.value = Screen.HostDashboard
          _successMessage.emit("Welcome back, ${result.data.name}!")
        }
        is AuthResult.Error -> {
          _errorMessage.emit(result.message)
        }
      }
      _isLoading.value = false
    }
  }

  // ================= USER AUTH & ACTIONS =================

  fun registerUser(
    hostCode: String,
    name: String,
    village: String,
    gender: String,
    mobile: String,
    password: String,
    profilePhotoUri: String? = null
  ) {
    viewModelScope.launch {
      _isLoading.value = true
      when (val result = repository.registerUser(
        hostCode = hostCode,
        name = name,
        village = village,
        gender = gender,
        mobile = mobile,
        password = password,
        profilePhotoUri = profilePhotoUri
      )) {
        is AuthResult.Success -> {
          _currentSession.value = AppSession.UserSession(result.data)
          screenBackStack.clear()
          _currentScreen.value = Screen.UserHome
          _successMessage.emit("Welcome to HostPay, ${result.data.name}!")
        }
        is AuthResult.Error -> {
          _errorMessage.emit(result.message)
        }
      }
      _isLoading.value = false
    }
  }

  fun loginUser(mobile: String, password: String) {
    viewModelScope.launch {
      _isLoading.value = true
      when (val result = repository.loginUser(mobile, password)) {
        is AuthResult.Success -> {
          _currentSession.value = AppSession.UserSession(result.data)
          screenBackStack.clear()
          _currentScreen.value = Screen.UserHome
          _successMessage.emit("Welcome back, ${result.data.name}!")
        }
        is AuthResult.Error -> {
          _errorMessage.emit(result.message)
        }
      }
      _isLoading.value = false
    }
  }

  // Quick switch for demo testing in browser emulator
  fun quickLoginDemoHost() {
    loginHost("9876543210", "host123")
  }

  fun quickLoginDemoUser() {
    loginUser("9123456780", "user123")
  }

  // ================= PAYMENT WORKFLOW ACTIONS =================

  fun createPayment(
    user: UserEntity,
    amount: Double,
    note: String?,
    proofPhotoUri: String?,
    onComplete: () -> Unit
  ) {
    val session = _currentSession.value as? AppSession.HostSession ?: return
    viewModelScope.launch {
      _isLoading.value = true
      val res = repository.createPayment(
        host = session.host,
        user = user,
        amount = amount,
        note = note,
        proofPhotoUri = proofPhotoUri
      )
      res.onSuccess {
        _successMessage.emit("Payment sent to ${user.name} for approval!")
        onComplete()
      }.onFailure {
        _errorMessage.emit(it.message ?: "Failed to create payment")
      }
      _isLoading.value = false
    }
  }

  fun approvePayment(payment: PaymentEntity, onComplete: () -> Unit = {}) {
    val session = _currentSession.value as? AppSession.UserSession ?: return
    viewModelScope.launch {
      _isLoading.value = true
      val res = repository.approvePayment(payment.transactionId, session.user)
      res.onSuccess {
        _successMessage.emit("Payment ₹${"%.2f".format(payment.amount)} Approved Successfully!")
        _selectedPayment.value = null
        onComplete()
      }.onFailure {
        _errorMessage.emit(it.message ?: "Failed to approve payment")
      }
      _isLoading.value = false
    }
  }

  fun rejectPayment(payment: PaymentEntity, reason: String, onComplete: () -> Unit = {}) {
    val session = _currentSession.value as? AppSession.UserSession ?: return
    viewModelScope.launch {
      _isLoading.value = true
      val res = repository.rejectPayment(payment.transactionId, session.user, reason)
      res.onSuccess {
        _successMessage.emit("Payment rejected. Host notified.")
        _selectedPayment.value = null
        onComplete()
      }.onFailure {
        _errorMessage.emit(it.message ?: "Failed to reject payment")
      }
      _isLoading.value = false
    }
  }

  fun openPaymentDetails(payment: PaymentEntity) {
    _selectedPayment.value = payment
  }

  fun closePaymentDetails() {
    _selectedPayment.value = null
  }

  // ================= REACTIVE DATA STREAMS =================

  // Host Users
  val hostUsers: StateFlow<List<UserEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getUsersByHost(session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Host Payments
  val hostPayments: StateFlow<List<PaymentEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getPaymentsByHost(session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Host Pending Payments
  val hostPendingPayments: StateFlow<List<PaymentEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getPendingPaymentsByHost(session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Host Dashboard Metrics
  val hostMetrics: StateFlow<HostDashboardMetrics> = combine(
    hostUsers,
    hostPayments,
    hostPendingPayments
  ) { users, payments, pending ->
    val confirmedPayments = payments.filter { it.status == "APPROVED" }
    val totalConfirmed = confirmedPayments.sumOf { it.amount }
    val pendingTotal = pending.sumOf { it.amount }
    HostDashboardMetrics(
      totalUsers = users.size,
      totalConfirmedAmount = totalConfirmed,
      confirmedPaymentsCount = confirmedPayments.size,
      pendingApprovalsCount = pending.size,
      pendingAmount = pendingTotal
    )
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HostDashboardMetrics())

  // Host Notifications
  val hostNotifications: StateFlow<List<NotificationEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getNotifications("HOST", session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Host Audit Logs
  val hostAuditLogs: StateFlow<List<AuditLogEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.HostSession) {
      repository.getAuditLogs(session.host.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // User Payments
  val userPayments: StateFlow<List<PaymentEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.UserSession) {
      repository.getPaymentsByUser(session.user.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // User Pending Payments
  val userPendingPayments: StateFlow<List<PaymentEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.UserSession) {
      repository.getPendingPaymentsByUser(session.user.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // User Total Approved Amount
  val userTotalApprovedAmount: StateFlow<Double> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.UserSession) {
      repository.getTotalApprovedAmountForUser(session.user.id)
    } else {
      flowOf(0.0)
    }
  }.flatMapLatest { amount ->
    flowOf(amount ?: 0.0)
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

  // User Notifications
  val userNotifications: StateFlow<List<NotificationEntity>> = _currentSession.flatMapLatest { session ->
    if (session is AppSession.UserSession) {
      repository.getNotifications("USER", session.user.id)
    } else {
      flowOf(emptyList())
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun markNotificationAsRead(id: String) {
    viewModelScope.launch {
      repository.markNotificationAsRead(id)
    }
  }

  fun markAllNotificationsAsRead() {
    val session = _currentSession.value
    viewModelScope.launch {
      when (session) {
        is AppSession.HostSession -> repository.markAllNotificationsAsRead("HOST", session.host.id)
        is AppSession.UserSession -> repository.markAllNotificationsAsRead("USER", session.user.id)
        AppSession.None -> {}
      }
    }
  }

  suspend fun checkHostCode(code: String): HostEntity? {
    return repository.findHostByCode(code)
  }
}

class HostPayViewModelFactory(
  private val repository: HostPayRepository
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(HostPayViewModel::class.java)) {
      return HostPayViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}

```

---

<a id="appsrcmainjavacomexampleuiscreensauthauthscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/auth/AuthScreen.kt`

```kotlin
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

```

---

<a id="appsrcmainjavacomexampleuiscreenshosthostdashboardscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/host/HostDashboardScreen.kt`

```kotlin
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

```

---

<a id="appsrcmainjavacomexampleuiscreenshosthostusersscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/host/HostUsersScreen.kt`

```kotlin
package com.example.ui.screens.host

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserEntity
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.common.UserAvatar
import com.example.ui.viewmodel.HostPayViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostUsersScreen(
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val users by viewModel.hostUsers.collectAsState()
  var searchQuery by remember { mutableStateOf("") }

  val filteredUsers = remember(users, searchQuery) {
    if (searchQuery.isBlank()) users
    else {
      val q = searchQuery.trim().lowercase()
      users.filter {
        it.name.lowercase().contains(q) ||
          it.village.lowercase().contains(q) ||
          it.mobile.contains(q)
      }
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text("Registered Users (${users.size})", fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("users_back_button")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { padding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(padding)
    ) {
      // Search Bar
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search by name, village, or mobile...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(Icons.Default.Clear, contentDescription = "Clear")
            }
          }
        },
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .testTag("user_search_input"),
        shape = RoundedCornerShape(12.dp)
      )

      if (filteredUsers.isEmpty()) {
        EmptyPlaceholder(
          title = if (searchQuery.isNotEmpty()) "No matching users" else "No users registered yet",
          description = if (searchQuery.isNotEmpty()) "Try a different search keyword"
          else "Share your Host Code with users so they can register and appear here.",
          icon = Icons.Default.People
        )
      } else {
        LazyColumn(
          contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(filteredUsers, key = { it.id }) { user ->
            UserCardItem(
              user = user,
              onViewDetails = { viewModel.navigateTo(Screen.HostUserDetails(user)) },
              onCreatePayment = { viewModel.navigateTo(Screen.HostCreatePayment(user)) }
            )
          }
        }
      }
    }
  }
}

@Composable
private fun UserCardItem(
  user: UserEntity,
  onViewDetails: () -> Unit,
  onCreatePayment: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("user_item_${user.id}")
      .clip(RoundedCornerShape(16.dp))
      .clickable { onViewDetails() },
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        UserAvatar(photoUri = user.profilePhotoUri, name = user.name, sizeDp = 48)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = user.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.height(2.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = "${user.village} • ${user.gender}",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = user.mobile,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 11.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedButton(
          onClick = onViewDetails,
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text("Profile & History", fontSize = 12.sp)
        }
        Button(
          onClick = onCreatePayment,
          modifier = Modifier.weight(1f).testTag("pay_user_button_${user.id}"),
          shape = RoundedCornerShape(10.dp)
        ) {
          Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Pay User", fontSize = 12.sp)
        }
      }
    }
  }
}

```

---

<a id="appsrcmainjavacomexampleuiscreenshosthostuserdetailsscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/host/HostUserDetailsScreen.kt`

```kotlin
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

```

---

<a id="appsrcmainjavacomexampleuiscreenshosthostcreatepaymentscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/host/HostCreatePaymentScreen.kt`

```kotlin
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

```

---

<a id="appsrcmainjavacomexampleuiscreenshosthostpendingapprovalsscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/host/HostPendingApprovalsScreen.kt`

```kotlin
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
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.common.PaymentItemCard
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberLight
import com.example.ui.viewmodel.HostPayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostPendingApprovalsScreen(
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val pendingPayments by viewModel.hostPendingPayments.collectAsState()
  val totalPendingAmount = pendingPayments.sumOf { it.amount }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Pending Approvals (${pendingPayments.size})", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("pending_approvals_back")
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
      // Summary strip
      Surface(
        shape = RoundedCornerShape(0.dp),
        color = WarningAmberLight,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.HourglassTop, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Total Pending Disbursal:",
              fontWeight = FontWeight.Medium,
              color = WarningAmber,
              fontSize = 13.sp
            )
          }
          Text(
            text = "₹${"%,.2f".format(totalPendingAmount)}",
            fontWeight = FontWeight.Bold,
            color = WarningAmber,
            fontSize = 15.sp
          )
        }
      }

      if (pendingPayments.isEmpty()) {
        EmptyPlaceholder(
          title = "No Pending Approvals",
          description = "All submitted payments have been approved or rejected by users.",
          icon = Icons.Default.HourglassTop
        )
      } else {
        LazyColumn(
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          item {
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "Per HostPay security rules, only the assigned recipient user can approve each transaction from their device.",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontSize = 12.sp
                )
              }
            }
          }

          items(pendingPayments, key = { it.transactionId }) { payment ->
            PaymentItemCard(
              payment = payment,
              isHostView = true,
              onClick = { viewModel.openPaymentDetails(payment) }
            )
          }
        }
      }
    }
  }
}

```

---

<a id="appsrcmainjavacomexampleuiscreenshosthostreportsscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/host/HostReportsScreen.kt`

```kotlin
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

```

---

<a id="appsrcmainjavacomexampleuiscreenshosthostauditlogsscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/host/HostAuditLogsScreen.kt`

```kotlin
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AuditLogEntity
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.viewmodel.HostPayViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostAuditLogsScreen(
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val logs by viewModel.hostAuditLogs.collectAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Audit Trail & Activity", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("audit_logs_back")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    }
  ) { padding ->
    if (logs.isEmpty()) {
      EmptyPlaceholder(
        title = "No Audit Logs Yet",
        description = "Activity logs will record all transactions, registrations, and approvals.",
        icon = Icons.Default.History,
        modifier = modifier.padding(padding)
      )
    } else {
      LazyColumn(
        modifier = modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background)
          .padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(logs, key = { it.id }) { log ->
          AuditLogItem(log = log)
        }
      }
    }
  }
}

@Composable
private fun AuditLogItem(log: AuditLogEntity) {
  val formattedDate = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(log.timestamp))

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth().testTag("audit_item_${log.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(
            if (log.actorRole == "HOST") MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.secondaryContainer
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Security,
          contentDescription = null,
          modifier = Modifier.size(20.dp),
          tint = if (log.actorRole == "HOST") MaterialTheme.colorScheme.onPrimaryContainer
          else MaterialTheme.colorScheme.onSecondaryContainer
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = log.action,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
          )
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
          ) {
            Text(
              text = log.actorRole,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = log.details,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "By ${log.actorName}",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Text(
            text = formattedDate,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }
  }
}

```

---

<a id="appsrcmainjavacomexampleuiscreenshosthostnotificationsscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/host/HostNotificationsScreen.kt`

```kotlin
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NotificationEntity
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.viewmodel.HostPayViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostNotificationsScreen(
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val notifications by viewModel.hostNotifications.collectAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Notifications", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("notifications_back")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        actions = {
          if (notifications.any { !it.isRead }) {
            TextButton(onClick = { viewModel.markAllNotificationsAsRead() }) {
              Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Mark All Read", fontSize = 12.sp)
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    }
  ) { padding ->
    if (notifications.isEmpty()) {
      EmptyPlaceholder(
        title = "No Notifications",
        description = "You will receive real-time alerts when users approve or reject payments.",
        icon = Icons.Default.NotificationsNone,
        modifier = modifier.padding(padding)
      )
    } else {
      LazyColumn(
        modifier = modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background)
          .padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(notifications, key = { it.id }) { notif ->
          NotificationItemCard(
            notif = notif,
            onItemClick = { viewModel.markNotificationAsRead(notif.id) }
          )
        }
      }
    }
  }
}

@Composable
fun NotificationItemCard(
  notif: NotificationEntity,
  onItemClick: () -> Unit
) {
  val timeText = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(notif.timestamp))

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (!notif.isRead) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
      else MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .clickable { onItemClick() }
      .testTag("notif_item_${notif.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Notifications,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = notif.title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = if (!notif.isRead) FontWeight.Bold else FontWeight.SemiBold
          )
          if (!notif.isRead) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = notif.message,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = timeText,
          style = MaterialTheme.typography.bodySmall,
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

```

---

<a id="appsrcmainjavacomexampleuiscreenshosthostsettingsscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/host/HostSettingsScreen.kt`

```kotlin
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

```

---

<a id="appsrcmainjavacomexampleuiscreensuseruserhomescreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/user/UserHomeScreen.kt`

```kotlin
package com.example.ui.screens.user

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserEntity
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.common.PaymentItemCard
import com.example.ui.common.UserAvatar
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberLight
import com.example.ui.viewmodel.HostPayViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(
  user: UserEntity,
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val payments by viewModel.userPayments.collectAsState()
  val pendingPayments by viewModel.userPendingPayments.collectAsState()
  val totalApprovedAmount by viewModel.userTotalApprovedAmount.collectAsState()
  val notifications by viewModel.userNotifications.collectAsState()
  val unreadCount = notifications.count { !it.isRead }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            UserAvatar(
              photoUri = user.profilePhotoUri,
              name = user.name,
              sizeDp = 40,
              modifier = Modifier.clickable { viewModel.navigateTo(Screen.UserProfile) }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${user.village} • Host: ${user.hostCode}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        },
        actions = {
          IconButton(
            onClick = { viewModel.navigateTo(Screen.UserNotifications) },
            modifier = Modifier.testTag("user_notification_bell")
          ) {
            BadgedBox(
              badge = {
                if (unreadCount > 0) {
                  Badge { Text(unreadCount.toString()) }
                }
              }
            ) {
              Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            }
          }
          IconButton(
            onClick = { viewModel.navigateTo(Screen.UserProfile) },
            modifier = Modifier.testTag("user_profile_button")
          ) {
            Icon(Icons.Default.Person, contentDescription = "Profile")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    },
    bottomBar = {
      NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
          selected = true,
          onClick = { /* Already on Home */ },
          icon = { Icon(Icons.Default.Payments, contentDescription = "Home") },
          label = { Text("Home") },
          modifier = Modifier.testTag("nav_user_home")
        )
        NavigationBarItem(
          selected = false,
          onClick = { viewModel.navigateTo(Screen.UserPayments) },
          icon = { Icon(Icons.Default.HourglassTop, contentDescription = "Payments") },
          label = { Text("Payments") },
          modifier = Modifier.testTag("nav_user_payments")
        )
        NavigationBarItem(
          selected = false,
          onClick = { viewModel.navigateTo(Screen.UserNotifications) },
          icon = {
            BadgedBox(
              badge = {
                if (unreadCount > 0) Badge { Text(unreadCount.toString()) }
              }
            ) {
              Icon(Icons.Default.Notifications, contentDescription = "Alerts")
            }
          },
          label = { Text("Alerts") },
          modifier = Modifier.testTag("nav_user_notifications")
        )
        NavigationBarItem(
          selected = false,
          onClick = { viewModel.navigateTo(Screen.UserProfile) },
          icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
          label = { Text("Profile") },
          modifier = Modifier.testTag("nav_user_profile")
        )
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
      // Host info banner
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Apartment, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Linked to Host Code: ",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = user.hostCode,
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
          }
        }
      }

      // Hero Total Received Amount Card
      item {
        Card(
          shape = RoundedCornerShape(22.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(22.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Total Received Amount",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
              ) {
                Text(
                  text = "Verified Only",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "₹${"%,.2f".format(totalApprovedAmount)}",
              style = MaterialTheme.typography.displaySmall,
              fontWeight = FontWeight.ExtraBold,
              color = SuccessGreen
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = "• Only payments you have approved count toward your total earnings.",
              style = MaterialTheme.typography.bodySmall,
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      // Action Required: Pending Approval Banner
      if (pendingPayments.isNotEmpty()) {
        item {
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = WarningAmberLight),
            modifier = Modifier.testTag("user_pending_alert_card")
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(WarningAmber.copy(alpha = 0.2f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(Icons.Default.HourglassTop, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "Action Required: Payment Received",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = WarningAmber
                  )
                  Text(
                    text = "You have ${pendingPayments.size} pending confirmation request(s).",
                    style = MaterialTheme.typography.bodySmall,
                    color = WarningAmber.copy(alpha = 0.9f)
                  )
                }
              }

              Spacer(modifier = Modifier.height(12.dp))

              // Show the first pending payment preview
              val topPending = pendingPayments.first()
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(text = "From: ${topPending.hostName}", fontWeight = FontWeight.SemiBold)
                    Text(
                      text = "₹${"%,.2f".format(topPending.amount)}",
                      fontWeight = FontWeight.Bold,
                      fontSize = 18.sp,
                      color = MaterialTheme.colorScheme.primary
                    )
                  }
                  if (!topPending.note.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Note: ${topPending.note}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                  }
                  Spacer(modifier = Modifier.height(10.dp))
                  Button(
                    onClick = { viewModel.openPaymentDetails(topPending) },
                    modifier = Modifier
                      .fillMaxWidth()
                      .testTag("review_top_pending_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp)
                  ) {
                    Text("Review, View Proof & Approve / Reject")
                  }
                }
              }
            }
          }
        }
      }

      // Recent Activity Header
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Your Payment History",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          if (payments.isNotEmpty()) {
            OutlinedButton(
              onClick = { viewModel.navigateTo(Screen.UserPayments) },
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("View All", fontSize = 12.sp)
            }
          }
        }
      }

      if (payments.isEmpty()) {
        item {
          EmptyPlaceholder(
            title = "No payments recorded yet",
            description = "When your Host creates a payment for you, it will appear here for your verification and approval.",
            icon = Icons.Default.Payments
          )
        }
      } else {
        items(payments.take(5), key = { it.transactionId }) { payment ->
          PaymentItemCard(
            payment = payment,
            isHostView = false,
            onClick = { viewModel.openPaymentDetails(payment) }
          )
        }
      }

      item {
        Spacer(modifier = Modifier.height(40.dp))
      }
    }
  }
}

```

---

<a id="appsrcmainjavacomexampleuiscreensuseruserprofilescreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/user/UserProfileScreen.kt`

```kotlin
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserEntity
import com.example.ui.common.UserAvatar
import com.example.ui.theme.DangerRed
import com.example.ui.viewmodel.HostPayViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
  user: UserEntity,
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val regDate = remember(user.createdAt) {
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(user.createdAt))
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("My Profile", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("user_profile_back")
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
      // Header profile photo & lock notice
      item {
        Card(
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
            UserAvatar(photoUri = user.profilePhotoUri, name = user.name, sizeDp = 84)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
              text = user.name,
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "${user.village} • Joined under Host ${user.hostCode}",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Notice about read-only policy
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  Icons.Default.Lock,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp),
                  tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Profile is verified and read-only. Cannot be edited by user.",
                  style = MaterialTheme.typography.bodySmall,
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
              verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              ProfileItemRow("Full Name", user.name)
              ProfileItemRow("Village", user.village)
              ProfileItemRow("Gender", user.gender)
              ProfileItemRow("Mobile / Login ID", user.mobile)
              ProfileItemRow("Host Join Code", user.hostCode)
              ProfileItemRow("Registration Date", regDate)
            }
          }
        }
      }

      // Security and rights info
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Security, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("User Rights & Protection", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
            }
            Text(
              text = "• Only you have the authority to confirm or reject payments sent to you.\n" +
                "• No funds count towards your confirmed totals until you verify and approve them.\n" +
                "• If a payment amount or proof is inaccurate, you can reject it with a reason.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 18.sp
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
            .testTag("user_logout_button"),
          colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Sign Out of Account", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
private fun ProfileItemRow(label: String, value: String) {
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
      fontWeight = FontWeight.SemiBold
    )
  }
}

```

---

<a id="appsrcmainjavacomexampleuiscreensuseruserpaymentsscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/user/UserPaymentsScreen.kt`

```kotlin
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

```

---

<a id="appsrcmainjavacomexampleuiscreensuserusernotificationsscreenkt"></a>

### File: `app/src/main/java/com/example/ui/screens/user/UserNotificationsScreen.kt`

```kotlin
package com.example.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.common.EmptyPlaceholder
import com.example.ui.screens.host.NotificationItemCard
import com.example.ui.viewmodel.HostPayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNotificationsScreen(
  viewModel: HostPayViewModel,
  modifier: Modifier = Modifier
) {
  val notifications by viewModel.userNotifications.collectAsState()
  val payments by viewModel.userPayments.collectAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Payment Alerts", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.testTag("user_notifications_back")
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        },
        actions = {
          if (notifications.any { !it.isRead }) {
            TextButton(onClick = { viewModel.markAllNotificationsAsRead() }) {
              Icon(Icons.Default.DoneAll, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Mark All Read", fontSize = 12.sp)
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
      )
    }
  ) { padding ->
    if (notifications.isEmpty()) {
      EmptyPlaceholder(
        title = "No Notifications",
        description = "You'll see alerts here when your Host creates a payment for you or updates your status.",
        icon = Icons.Default.NotificationsNone,
        modifier = modifier.padding(padding)
      )
    } else {
      LazyColumn(
        modifier = modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background)
          .padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(notifications, key = { it.id }) { notif ->
          NotificationItemCard(
            notif = notif,
            onItemClick = {
              viewModel.markNotificationAsRead(notif.id)
              // If notification has transaction ID, open payment details
              if (!notif.transactionId.isNullOrEmpty()) {
                val matchingPayment = payments.find { it.transactionId == notif.transactionId }
                if (matchingPayment != null) {
                  viewModel.openPaymentDetails(matchingPayment)
                }
              }
            }
          )
        }
      }
    }
  }
}

```

---

<a id="appsrctestjavacomexampleexamplerobolectrictestkt"></a>

### File: `app/src/test/java/com/example/ExampleRobolectricTest.kt`

```kotlin
package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("AGRINOVA", appName)
  }
}

```

---

