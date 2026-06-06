package com.epn.godoyjotcelyn_moviles.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epn.godoyjotcelyn_moviles.ui.viewmodel.CrudViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudScreen(viewModel: CrudViewModel = viewModel()) {

    val notas by viewModel.notas.collectAsState()
    val useSql by viewModel.useSql.collectAsState()
    var nuevoTitulo by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (useSql) "📋 SQL — Room/SQLite" else "📄 NoSQL — JSON Docs",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                    // Etiqueta visual del modo actual
                    Text(
                        text = if (useSql) "SQL" else "NoSQL",
                        color = if (useSql) Color(0xFF1565C0) else Color(0xFF2E7D32),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Switch(
                        checked = !useSql,
                        onCheckedChange = { viewModel.toggleSource(!it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF2E7D32),
                            uncheckedThumbColor = Color(0xFF1565C0)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Indicador visual del origen de datos
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (useSql)
                        Color(0xFFE3F2FD)
                    else
                        Color(0xFFE8F5E9)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (useSql)
                        "🔵 Origen: SQLite (Room) — Base de datos relacional"
                    else
                        "🟢 Origen: JSON (NoSQL) — Base de datos de documentos",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (useSql) Color(0xFF1565C0) else Color(0xFF2E7D32)
                )
            }

            // Campo para agregar nota
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = nuevoTitulo,
                    onValueChange = { nuevoTitulo = it },
                    label = { Text("Nueva nota") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Button(
                    onClick = {
                        if (nuevoTitulo.isNotBlank()) {
                            viewModel.agregarNota(nuevoTitulo)
                            nuevoTitulo = ""
                        }
                    }
                ) {
                    Text("Agregar")
                }
            }

            // Contador
            Text(
                text = "${notas.size} nota(s) en ${if (useSql) "SQLite" else "JSON"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )

            // Lista de notas
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(notas, key = { it.id }) { nota ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = nota.titulo,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { viewModel.eliminarNota(nota) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}