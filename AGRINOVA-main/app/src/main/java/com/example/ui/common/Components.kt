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
