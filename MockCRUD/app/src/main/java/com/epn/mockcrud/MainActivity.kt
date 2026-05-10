package com.epn.mockcrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.epn.mockcrud.ui.theme.MockCRUDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MockCRUDTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    // Lista de ítems hardcoded (sin base de datos)
    val items = remember {
        mutableStateListOf(
            Item(1, "Tarea de Redes",   "Pendiente para el viernes"),
            Item(2, "Proyecto Flutter", "Pantallas de listado"),
            Item(3, "Examen Móviles",  "Semana 8"),
            Item(4, "Lab de Compose",  "Este taller")
        )
    }

    // Controlan qué pantalla se muestra
    var pantallaActual   by remember { mutableStateOf("lista") }
    var itemSeleccionado by remember { mutableStateOf<Item?>(null) }
    var itemAEliminar    by remember { mutableStateOf<Item?>(null) }

    // Navegación simple: una variable decide qué pantalla mostrar
    when (pantallaActual) {
        "lista" -> ListaScreen(
            items          = items,
            onItemClick    = { item ->
                itemSeleccionado = item
                pantallaActual   = "formulario"
            },
            onLongPress    = { item -> itemAEliminar = item },
            onAgregarClick = {
                itemSeleccionado = null
                pantallaActual   = "formulario"
            }
        )
        "formulario" -> FormularioScreen(
            itemExistente = itemSeleccionado,
            onGuardar     = { nuevoItem ->
                if (itemSeleccionado == null) {
                    items.add(nuevoItem)              // modo Crear
                } else {
                    val i = items.indexOfFirst { it.id == nuevoItem.id }
                    if (i >= 0) items[i] = nuevoItem  // modo Editar
                }
                pantallaActual = "lista"
            },
            onCancelar = { pantallaActual = "lista" }
        )
    }

    // Diálogo de eliminar (aparece encima de cualquier pantalla)
    itemAEliminar?.let { item ->
        EliminarDialog(
            item        = item,
            onConfirmar = {
                items.remove(item)
                itemAEliminar = null
            },
            onCancelar  = { itemAEliminar = null }
        )
    }
}