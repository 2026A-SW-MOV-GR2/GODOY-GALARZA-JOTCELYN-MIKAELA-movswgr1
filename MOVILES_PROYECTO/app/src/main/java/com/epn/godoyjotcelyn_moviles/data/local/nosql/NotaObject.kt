package com.epn.godoyjotcelyn_moviles.data.local.nosql

data class NotaObject(
    val id: Long = System.currentTimeMillis(),
    val titulo: String = ""
)