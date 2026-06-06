package com.epn.godoyjotcelyn_moviles.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epn.godoyjotcelyn_moviles.ui.viewmodel.StorageType
import com.epn.godoyjotcelyn_moviles.ui.viewmodel.StorageUiState
import com.epn.godoyjotcelyn_moviles.ui.viewmodel.StorageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageScreen(viewModel: StorageViewModel = viewModel()) {

    var key by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(StorageType.SHARED_PREFERENCES) }
    val uiState by viewModel.uiState.collectAsState()

    val mecanismos = listOf(
        StorageType.SHARED_PREFERENCES to "SharedPreferences",
        StorageType.DATA_STORE to "DataStore",
        StorageType.ENCRYPTED_SHARED_PREFERENCES to "EncryptedSharedPreferences"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Módulo 3 — Gestión de Secretos") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- Campos de entrada ---
            Text("Datos", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = key,
                onValueChange = { key = it },
                label = { Text("Llave (Key)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("ej: token_jwt") }
            )

            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text("Valor (solo para Guardar)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("ej: eyJhbGciOiJIUzI1...") }
            )

            // --- Selector de mecanismo ---
            HorizontalDivider()
            Text("Mecanismo de almacenamiento", style = MaterialTheme.typography.titleMedium)

            mecanismos.forEach { (type, label) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedType == type,
                        onClick = { selectedType = type }
                    )
                    Column {
                        Text(label, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = when (type) {
                                StorageType.SHARED_PREFERENCES ->
                                    "Sin cifrado — para preferencias simples"
                                StorageType.DATA_STORE ->
                                    "Asíncrono con coroutines — moderno y seguro"
                                StorageType.ENCRYPTED_SHARED_PREFERENCES ->
                                    "Cifrado AES256 — para tokens y datos sensibles"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            // --- Botones ---
            HorizontalDivider()
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (key.isNotBlank() && value.isNotBlank()) {
                            viewModel.guardar(key, value, selectedType)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("💾 Guardar")
                }
                OutlinedButton(
                    onClick = {
                        if (key.isNotBlank()) {
                            viewModel.recuperar(key, selectedType)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("🔍 Recuperar")
                }
            }

            // --- Resultado ---
            when (val state = uiState) {
                is StorageUiState.Found -> {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8F5E9)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "✅ Valor encontrado:",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFF2E7D32)
                            )
                            Text(
                                state.value,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }
                }
                is StorageUiState.NotFound -> {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "❌ Secreto no encontrado en este mecanismo",
                            modifier = Modifier.padding(12.dp),
                            color = Color(0xFFC62828)
                        )
                    }
                }
                is StorageUiState.Saved -> {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE3F2FD)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "💾 Guardado en ${state.mechanism}",
                            modifier = Modifier.padding(12.dp),
                            color = Color(0xFF1565C0)
                        )
                    }
                }
                else -> {}
            }
        }
    }
}