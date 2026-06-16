// shared/src/commonMain/kotlin/com/epn/godoyjotcelynintents/App.kt
package com.epn.godoyjotcelynintents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

@Composable
fun App(
    // Callbacks que MainActivity implementa con lógica nativa
    onDialPhone: (String) -> Unit = {},
    onTakePhoto: () -> Unit = {},
    capturedBitmap: Any? = null,       // ImageBitmap en Android
    receivedText: String? = null,
    receivedImageUri: Any? = null,
    imageContent: @Composable () -> Unit = {} // slot para imagen entrante
) {
    MaterialTheme (
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        var selectedTab by remember { mutableIntStateOf(0) }
        val tabs = listOf("📤 Salientes", "📥 Entrantes")

        Column(Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            when (selectedTab) {
                0 -> OutgoingIntentsScreen(
                    onDialPhone = onDialPhone,
                    onTakePhoto = onTakePhoto,
                    capturedBitmap = capturedBitmap
                )
                1 -> IncomingIntentsScreen(
                    receivedText = receivedText,
                    receivedImageUri = receivedImageUri,
                    imageContent = imageContent
                )
            }
        }
    }
}