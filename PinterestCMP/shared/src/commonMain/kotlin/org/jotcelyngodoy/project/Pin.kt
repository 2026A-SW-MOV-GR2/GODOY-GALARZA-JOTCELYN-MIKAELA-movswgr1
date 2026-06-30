package org.jotcelyngodoy.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Pin(
    val id: Int,
    val imageUrl: String,
    val title: String = ""
) {
    var isLiked by mutableStateOf(false)
}