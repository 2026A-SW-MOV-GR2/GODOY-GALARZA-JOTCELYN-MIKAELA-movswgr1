package org.jotcelyngodoy.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveToBoardSheet(
    pin: Pin,
    boards: List<Board>,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = PinterestColors.Cream
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 40.dp)
        ) {
            Text(
                text = "Guardar en tablero",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PinterestColors.RoseGold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            boards.forEach { board ->
                val yaGuardado = board.savedPins.contains(pin)

                Surface(
                    onClick = {
                        if (!yaGuardado) board.savedPins.add(pin)
                        onDismiss()
                    },
                    shape = RoundedCornerShape(14.dp),
                    color = if (yaGuardado) PinterestColors.RoseGoldLight else PinterestColors.CardSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Thumbnail del tablero
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.size(54.dp)
                        ) {
                            if (board.coverImageUrl != null) {
                                AsyncImage(
                                    model = board.coverImageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(PinterestColors.RoseGoldLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(board.emoji, style = MaterialTheme.typography.headlineSmall)
                                }
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${board.emoji} ${board.name}",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PinterestColors.TextPrimary
                                )
                            )
                            Text(
                                text = "${board.savedPins.size} pins guardados",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = PinterestColors.TextSecondary
                                )
                            )
                        }

                        if (yaGuardado) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Ya guardado",
                                tint = PinterestColors.RoseGold
                            )
                        }
                    }
                }
            }
        }
    }
}