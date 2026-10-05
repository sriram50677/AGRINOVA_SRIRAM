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
