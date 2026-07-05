package org.jotcelyngodoy.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import io.ktor.client.HttpClient
import org.jetbrains.compose.resources.painterResource
import pinterestcmp.shared.generated.resources.Res
import pinterestcmp.shared.generated.resources.logo
import androidx.compose.foundation.Image

sealed class Screen {
    object Feed   : Screen()
    data class Detail(val pin: Pin)     : Screen()
    object Boards : Screen()
    data class BoardDetail(val board: Board) : Screen()
}

val PinterestTheme = lightColorScheme(
    primary       = PinterestColors.RoseGold,
    onPrimary     = Color.White,
    secondary     = PinterestColors.Lavender,
    background    = PinterestColors.Cream,
    surface       = PinterestColors.CardSurface,
    onBackground  = PinterestColors.TextPrimary,
    onSurface     = PinterestColors.TextPrimary,
)

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory(httpClient = { HttpClient() })) }
            .build()
    }


    var screen    by remember { mutableStateOf<Screen>(Screen.Feed) }
    var showSheet by remember { mutableStateOf(false) }
    var pinToSave by remember { mutableStateOf<Pin?>(null) }

    val isTopLevel = screen is Screen.Feed || screen is Screen.Boards

    MaterialTheme(colorScheme = PinterestTheme) {
        Scaffold(
            topBar = {
                if (isTopLevel) PinterestTopBar()
            },
            bottomBar = {
                if (isTopLevel) {
                    PinterestBottomBar(
                        current       = screen,
                        onFeedClick   = { screen = Screen.Feed },
                        onBoardsClick = { screen = Screen.Boards }
                    )
                }
            },
            containerColor = PinterestColors.Cream
        ) { innerPadding ->

            when (val current = screen) {
                is Screen.Feed -> FeedScreen(
                    pins       = MockPins.list,
                    onPinClick = { pin -> screen = Screen.Detail(pin) },
                    modifier   = Modifier.fillMaxSize().padding(innerPadding)
                )
                is Screen.Detail -> PinDetailScreen(
                    pin          = current.pin,
                    onBack       = { screen = Screen.Feed },
                    onSaveToBoard = { pinToSave = current.pin; showSheet = true }
                )
                is Screen.Boards -> BoardsScreen(
                    boards       = MockBoards.list,
                    onBoardClick = { board -> screen = Screen.BoardDetail(board) },
                    modifier     = Modifier.fillMaxSize().padding(innerPadding)
                )
                is Screen.BoardDetail -> BoardDetailScreen(
                    board  = current.board,
                    onBack = { screen = Screen.Boards }
                )
            }

            if (showSheet && pinToSave != null) {
                SaveToBoardSheet(
                    pin      = pinToSave!!,
                    boards   = MockBoards.list,
                    onDismiss = { showSheet = false; pinToSave = null }
                )
            }
        }
    }
}

@Composable
fun PinterestTopBar() {
    Surface(
        color = PinterestColors.Cream,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.height(40.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun PinterestBottomBar(
    current: Screen,
    onFeedClick: () -> Unit,
    onBoardsClick: () -> Unit
) {
    NavigationBar(
        containerColor = PinterestColors.Cream,
        tonalElevation = 4.dp
    ) {
        val itemColors = NavigationBarItemDefaults.colors(
            selectedIconColor   = PinterestColors.RoseGold,
            selectedTextColor   = PinterestColors.RoseGold,
            indicatorColor      = PinterestColors.RoseGoldLight,
            unselectedIconColor = PinterestColors.TextSecondary,
            unselectedTextColor = PinterestColors.TextSecondary
        )
        NavigationBarItem(
            selected = current is Screen.Feed,
            onClick  = onFeedClick,
            icon     = { Icon(Icons.Default.Home, "Inicio") },
            label    = { Text("Inicio") },
            colors   = itemColors
        )
        NavigationBarItem(
            selected = current is Screen.Boards,
            onClick  = onBoardsClick,
            icon     = { Icon(Icons.Default.Bookmarks, "Tableros") },
            label    = { Text("Tableros") },
            colors   = itemColors
        )
    }
}