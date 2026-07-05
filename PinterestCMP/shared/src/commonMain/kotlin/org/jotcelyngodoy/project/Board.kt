package org.jotcelyngodoy.project

import androidx.compose.runtime.mutableStateListOf

class Board(
    val id: Int,
    val name: String,
    val emoji: String = ""
) {
    val savedPins = mutableStateListOf<Pin>()
    val coverImageUrl: String? get() = savedPins.firstOrNull()?.imageUrl
}

object MockBoards {
    val list = mutableStateListOf(
        Board(1, "Viajes soñados",  "✈️"),
        Board(2, "Naturaleza",      "🌿"),
        Board(3, "Inspiración",     "✨"),
        Board(4, "Para el futuro",  "💫")
    )
}