package com.example

import com.example.data.security.SecurityUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testPasswordHashingAndVerification() {
    val password = "SecretPassword123!"
    val salt = SecurityUtils.generateSalt()
    val hash = SecurityUtils.hashPassword(password, salt)

    assertTrue(SecurityUtils.verifyPassword(password, salt, hash))
    assertFalse(SecurityUtils.verifyPassword("WrongPassword", salt, hash))
  }

  @Test
  fun testHostCodeGeneration() {
    val code = SecurityUtils.generateHostCode()
    assertTrue(code.startsWith("HP-"))
    assertEquals(7, code.length)
  }

  @Test
  fun testTransactionIdGeneration() {
    val txnId = SecurityUtils.generateTransactionId()
    assertTrue(txnId.startsWith("TXN-"))
  }
}
