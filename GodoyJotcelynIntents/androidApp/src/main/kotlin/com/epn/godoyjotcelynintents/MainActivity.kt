package com.epn.godoyjotcelynintents

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

class MainActivity : ComponentActivity() {

    private var capturedBitmap by mutableStateOf<Bitmap?>(null)
    private var receivedText by mutableStateOf<String?>(null)
    private var receivedImageUri by mutableStateOf<Uri?>(null)

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            @Suppress("DEPRECATION")
            val thumbnail = result.data?.extras?.get("data") as? Bitmap
            capturedBitmap = thumbnail
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            enableEdgeToEdge()
            handleIncomingIntent(intent)
            setContent {
                Box(modifier = Modifier.safeDrawingPadding()) {
                    App(
                        onDialPhone = { number -> dialPhone(number) },
                        onTakePhoto = { takePhoto() },
                        capturedBitmap = capturedBitmap?.asImageBitmap(),
                        receivedText = receivedText,
                        receivedImageUri = receivedImageUri,
                        imageContent = {
                            AsyncImage(
                                model = receivedImageUri,
                                contentDescription = "Imagen recibida",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )}
                    )
                }
            }
        }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIncomingIntent(intent)
    }

    private fun handleIncomingIntent(intent: Intent?) {
        if (intent?.action != Intent.ACTION_SEND) return
        val type = intent.type ?: return
        when {
            type == "text/plain" -> {
                receivedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                receivedImageUri = null
            }
            type.startsWith("image/") -> {
                receivedImageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM)
                receivedText = null
            }
        }
    }

    private fun dialPhone(number: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$number")
        }
        startActivity(intent)
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }
}