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
