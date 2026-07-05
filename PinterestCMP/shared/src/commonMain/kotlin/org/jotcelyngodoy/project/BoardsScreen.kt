package org.jotcelyngodoy.project
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun BoardsScreen(
    boards: List<Board>,
    onBoardClick: (Board) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.background(PinterestColors.Cream)
    ) {
        items(boards) { board ->
            BoardCard(board = board, onClick = { onBoardClick(board) })
        }
    }
}

@Composable
fun BoardCard(board: Board, onClick: () -> Unit = {}) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = PinterestColors.CardSurface,
        shadowElevation = 3.dp
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(130.dp),
                contentAlignment = Alignment.Center
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
                        modifier = Modifier.fillMaxSize().background(PinterestColors.RoseGoldLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(board.emoji, style = MaterialTheme.typography.displaySmall)
                    }
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "${board.emoji} ${board.name}",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = PinterestColors.TextPrimary
                    )
                )
                Text(
                    text = "${board.savedPins.size} pins",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = PinterestColors.TextSecondary
                    )
                )
            }
        }
    }
}