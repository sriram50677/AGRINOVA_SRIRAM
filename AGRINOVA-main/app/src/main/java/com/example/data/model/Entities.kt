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
