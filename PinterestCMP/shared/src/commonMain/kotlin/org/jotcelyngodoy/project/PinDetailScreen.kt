package org.jotcelyngodoy.project
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
@Composable
fun PinDetailScreen(pin: Pin, onBack: () -> Unit, onSaveToBoard: () -> Unit) {
    var commentsExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PinterestColors.Cream)
            .verticalScroll(rememberScrollState())
    ) {

        // ── Imagen principal ──────────────────────────────────────────
        Box {
            AsyncImage(
                model = pin.imageUrl,
                contentDescription = pin.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 320.dp)
            )
            // Botón volver con fondo semitransparente
            Surface(
                modifier = Modifier.padding(14.dp).size(42.dp),
                shape = CircleShape,
                color = PinterestColors.RoseGold.copy(alpha = 0.85f),
                shadowElevation = 4.dp
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
            }
        }

        // ── Like ──────────────────────────────────────────────────────
        // Botones: like + guardar en tablero
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(onClick = { pin.isLiked = !pin.isLiked }) {
                Icon(
                    imageVector = if (pin.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    tint = if (pin.isLiked) PinterestColors.RoseGold else PinterestColors.TextSecondary
                )
            }
            Text(
                text = if (pin.isLiked) "Te gusta" else "Me gusta",
                style = MaterialTheme.typography.bodyMedium,
                color = if (pin.isLiked) PinterestColors.RoseGold else PinterestColors.TextSecondary,
                modifier = Modifier.weight(1f)
            )
            // Botón guardar en tablero
            FilledTonalButton(
                onClick = onSaveToBoard,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = PinterestColors.RoseGoldLight
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = PinterestColors.RoseGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Tablero",
                    color = PinterestColors.RoseGold,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        // ── Comentarios ───────────────────────────────────────────────
        // Botón desplegable de comentarios
        Surface(
            onClick = { commentsExpanded = !commentsExpanded },
            shape = RoundedCornerShape(12.dp),
            color = PinterestColors.CardSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Comentarios (${MockComments.list.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PinterestColors.RoseGold
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (commentsExpanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = PinterestColors.RoseGold
                )
            }
        }

// Comentarios con animación
        AnimatedVisibility(visible = commentsExpanded) {
            Column {
                MockComments.list.forEach { comment ->
                    CommentItem(comment)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Recomendados en cascada ───────────────────────────────────
        SectionTitle("Más como esto")

        val recomendados = MockPins.list.filter { it.id != pin.id }.take(6)
        val columnaIzq = recomendados.filterIndexed { i, _ -> i % 2 == 0 }
        val columnaDer = recomendados.filterIndexed { i, _ -> i % 2 != 0 }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                columnaIzq.forEach { rec ->
                    MiniPinCard(rec)
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                columnaDer.forEach { rec ->
                    MiniPinCard(rec)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


    }
}

// ── Título de sección reutilizable ────────────────────────────────────
@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = PinterestColors.RoseGold
        ),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp)
    )
}

// ── Tarjeta mini para recomendados ────────────────────────────────────
@Composable
fun MiniPinCard(pin: Pin) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = PinterestColors.CardSurface,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {
            AsyncImage(
                model = pin.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            // Corazón sobre la imagen mini
            IconButton(
                onClick = { pin.isLiked = !pin.isLiked },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .size(30.dp)
            ) {
                Icon(
                    imageVector = if (pin.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    tint = if (pin.isLiked) PinterestColors.RoseGold else Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

// ── Comentario con avatar ─────────────────────────────────────────────
@Composable
fun CommentItem(comment: Comment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Foto de perfil circular
        AsyncImage(
            model = comment.avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(PinterestColors.RoseGoldLight)
        )

        // Nombre + texto en tarjetita suave
        Surface(
            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 14.dp, bottomStart = 14.dp, bottomEnd = 14.dp),
            color = PinterestColors.CardSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                Text(
                    text = comment.author,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = PinterestColors.RoseGold
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = comment.text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = PinterestColors.TextSecondary
                    )
                )
            }
        }
    }
}