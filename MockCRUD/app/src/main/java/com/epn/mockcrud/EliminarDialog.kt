
package com.epn.mockcrud

import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun EliminarDialog(
    item: Item,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    // LocalContext.current → acceso directo al Context nativo de Android
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text("¿Eliminar ítem?") },
        text  = { Text("Se eliminará '${item.titulo}'. Esta acción no se puede deshacer.") },
        confirmButton = {
            TextButton(onClick = {
                onConfirmar()
                // Toast nativo de Android, llamado directamente desde Compose
                Toast.makeText(context, "\"${item.titulo}\" eliminado", Toast.LENGTH_SHORT).show()
            }) {
                Text("Eliminar", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}