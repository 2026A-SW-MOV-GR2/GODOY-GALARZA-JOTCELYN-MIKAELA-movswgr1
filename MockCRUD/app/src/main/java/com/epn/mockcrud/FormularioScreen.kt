package com.epn.mockcrud

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    itemExistente: Item? = null,   // null = modo Crear, con item = modo Editar
    onGuardar: (Item) -> Unit,
    onCancelar: () -> Unit
) {
    // remember guarda el valor mientras la pantalla esté abierta
    var titulo    by remember { mutableStateOf(itemExistente?.titulo    ?: "") }
    var subtitulo by remember { mutableStateOf(itemExistente?.subtitulo ?: "") }
    var activo    by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (itemExistente == null) "Nuevo ítem" else "Editar ítem") },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = subtitulo,
                onValueChange = { subtitulo = it },
                label = { Text("Subtítulo") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Activo")
                Switch(checked = activo, onCheckedChange = { activo = it })
            }

            Button(
                onClick = {
                    val nuevoItem = Item(
                        id        = itemExistente?.id ?: (Math.random() * 1000).toInt(),
                        titulo    = titulo,
                        subtitulo = subtitulo
                    )
                    val mensaje = if (itemExistente == null) "\"$titulo\" creado" else "\"$titulo\" actualizado"
                    onGuardar(nuevoItem)
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()                },
                modifier = Modifier.fillMaxWidth(),
                enabled = titulo.isNotBlank()   // desactiva el botón si el título está vacío
            ) {
                Text("Guardar")
            }
        }
    }
}