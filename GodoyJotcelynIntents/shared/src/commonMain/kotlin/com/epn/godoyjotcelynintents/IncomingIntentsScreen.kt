// shared/src/commonMain/kotlin/com/epn/godoyjotcelynintents/IncomingIntentsScreen.kt
package com.epn.godoyjotcelynintents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun IncomingIntentsScreen(
    receivedText: String?,
    receivedImageUri: Any?,
    imageContent: @Composable () -> Unit // slot: la imagen real viene de androidMain
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("📥 Intents Entrantes",color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.titleMedium)

        // Estado actual
        val status = when {
            receivedText != null -> "✅ Texto recibido"
            receivedImageUri != null -> "✅ Imagen recibida"
            else -> "⏳ Esperando datos externos..."
        }
        Text(status,color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)

        // Caja de texto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 80.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline)
                .padding(8.dp)
        ) {
            Text(
                text = when {
                    receivedText != null -> receivedText
                    receivedImageUri != null -> "[Dato binario — imagen recibida]"
                    else -> ""
                },
                color = if (receivedText != null)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Contenedor de imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline),
            contentAlignment = Alignment.Center
        ) {
            if (receivedImageUri != null) {
                imageContent() // renderiza AsyncImage desde androidMain
            } else {
                Text("Imagen compartida aparecerá aquí", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}