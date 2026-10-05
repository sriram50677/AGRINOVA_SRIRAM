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
