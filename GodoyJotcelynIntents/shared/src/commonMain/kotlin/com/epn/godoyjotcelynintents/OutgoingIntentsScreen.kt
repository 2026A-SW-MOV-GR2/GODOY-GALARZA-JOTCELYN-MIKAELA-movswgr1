// shared/src/commonMain/kotlin/com/epn/godoyjotcelynintents/OutgoingIntentsScreen.kt
package com.epn.godoyjotcelynintents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun OutgoingIntentsScreen(
    onDialPhone: (String) -> Unit,
    onTakePhoto: () -> Unit,
    capturedBitmap: Any? // recibimos como Any para compilar en commonMain
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // ── Panel 1: Marcador ──────────────────────────────
        Text("📞 Llamador Misterioso", color = MaterialTheme.colorScheme.onSurfaceVariant,style = MaterialTheme.typography.titleMedium)

        var phone by remember { mutableStateOf("") }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Número telefónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = { onDialPhone(phone) }) {
                Text("Marcar")
            }
        }

        HorizontalDivider()

        // ── Panel 2: Cámara ────────────────────────────────
        Text("📷 Foto Express",color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.titleMedium)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFFDDDDDD)),
                contentAlignment = Alignment.Center
            ) {
                val bitmap = capturedBitmap as? ImageBitmap
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Foto tomada",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("Sin foto", color = Color.Gray)
                }
            }
            Spacer(Modifier.width(16.dp))
            Button(onClick = onTakePhoto) {
                Text("Tomar Foto")
            }
        }
    }
}