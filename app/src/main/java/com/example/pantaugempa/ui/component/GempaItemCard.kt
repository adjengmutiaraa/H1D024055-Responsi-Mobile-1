package com.example.pantaugempa.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pantaugempa.data.model.GempaItem
import com.example.pantaugempa.data.model.formattedMagnitude
import com.example.pantaugempa.data.model.formattedWaktu
import com.example.pantaugempa.ui.theme.StatusCritical
import com.example.pantaugempa.ui.theme.StatusLow
import com.example.pantaugempa.ui.theme.StatusSignificant
import com.example.pantaugempa.ui.theme.StatusWarning
import com.example.pantaugempa.ui.theme.TextSecondary

@Composable
fun GempaItemCard(
    gempa: GempaItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mag = gempa.magnitude.toDoubleOrNull() ?: 0.0
    val badgeColor = when {
        mag < 4.0 -> StatusLow
        mag < 5.0 -> StatusWarning
        mag < 7.0 -> StatusSignificant
        else -> StatusCritical
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Badge Skala Magnitude
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = badgeColor.copy(alpha = 0.15f),
                modifier = Modifier.size(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = gempa.formattedMagnitude(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Informasi Lokasi & Waktu
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = gempa.wilayah,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = gempa.formattedWaktu(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Kedalaman: ${gempa.kedalaman}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}