package org.jotcelyngodoy.project

data class Comment(
    val author: String,
    val text: String,
    val avatarUrl: String
)

object MockComments {
    val list = listOf(
        Comment("Ana García",      "¡Qué foto tan hermosa! 😍",           "https://i.pravatar.cc/150?img=1"),
        Comment("Carlos López",    "¿Dónde fue tomada esta foto?",         "https://i.pravatar.cc/150?img=3"),
        Comment("María Rodríguez", "Los colores son absolutamente mágicos","https://i.pravatar.cc/150?img=5"),
        Comment("Juan Pérez",      "Me recuerda a mis vacaciones 🏖️",      "https://i.pravatar.cc/150?img=7"),
        Comment("Laura Martínez",  "Guardada para mi próximo viaje ✈️",    "https://i.pravatar.cc/150?img=9")
    )
}