package com.epn.mockcrud

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListaScreen(
    items: List<Item>,
    onItemClick: (Item) -> Unit,        // al hacer clic normal → va al formulario
    onLongPress: (Item) -> Unit,        // al mantener presionado → abre diálogo borrar
    onAgregarClick: () -> Unit          // botón + flotante → formulario vacío
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis ítems") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(
                items = items,
                key = { it.id }      // ayuda a Compose a identificar cada ítem
            ) { item ->
                ItemFila(
                    item = item,
                    onClick = { onItemClick(item) },
                    onLongClick = { onLongPress(item) }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemFila(
    item: Item,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    ListItem(
        headlineContent   = { Text(item.titulo) },
        supportingContent = { Text(item.subtitulo) },
        leadingContent    = {
            Icon(Icons.Default.Description, contentDescription = null)
        },
        modifier = Modifier.combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        )
    )
    HorizontalDivider()
}