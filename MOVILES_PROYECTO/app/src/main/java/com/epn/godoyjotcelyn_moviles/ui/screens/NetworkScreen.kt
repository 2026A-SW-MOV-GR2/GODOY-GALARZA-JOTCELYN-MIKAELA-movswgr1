package com.epn.godoyjotcelyn.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epn.godoyjotcelyn.ui.viewmodel.NetworkUiState
import com.epn.godoyjotcelyn.ui.viewmodel.NetworkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkScreen(viewModel: NetworkViewModel = viewModel()) {

    var postId by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is NetworkUiState.Success -> {
                title = state.post.title
                body = state.post.body
            }
            is NetworkUiState.Updated -> {
                title = state.post.title
                body = state.post.body
            }
            else -> {}
        }
    }

    val isLoading = uiState is NetworkUiState.Loading
    val hasData = uiState is NetworkUiState.Success || uiState is NetworkUiState.Updated

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Módulo 1 — Red HTTP") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Consultar Post", style = MaterialTheme.typography.titleMedium)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = postId,
                    onValueChange = { postId = it },
                    label = { Text("ID del Post (1-100)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading,
                    singleLine = true
                )
                Button(
                    onClick = {
                        val id = postId.toIntOrNull()
                        if (id != null) viewModel.getPost(id)
                    },
                    enabled = !isLoading && postId.isNotBlank()
                ) {
                    Text("Obtener")
                }
            }

            if (isLoading) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    Text("Cargando...", style = MaterialTheme.typography.bodySmall)
                }
            }

            if (uiState is NetworkUiState.Error) {
                Text(
                    text = (uiState as NetworkUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (uiState is NetworkUiState.Updated) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "✅ Post actualizado correctamente (200 OK)",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            if (hasData) {
                HorizontalDivider()
                Text("Editar Post", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                )

                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text("Cuerpo") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    minLines = 3
                )

                Button(
                    onClick = {
                        val id = postId.toIntOrNull()
                        if (id != null) viewModel.updatePost(id, title, body)
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Actualizar")
                }
            }
        }
    }
}