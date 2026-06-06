package com.epn.godoyjotcelyn_moviles.data.local.nosql

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object JsonNoSqlManager {

    private const val FILE_NAME = "notas_nosql.json"
    private val gson = Gson()

    private fun getFile(context: Context): File {
        return File(context.filesDir, FILE_NAME)
    }

    fun getAll(context: Context): List<NotaObject> {
        val file = getFile(context)
        if (!file.exists()) return emptyList()
        val json = file.readText()
        val type = object : TypeToken<List<NotaObject>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun insert(context: Context, nota: NotaObject) {
        val notas = getAll(context).toMutableList()
        notas.add(nota)
        save(context, notas)
    }

    fun delete(context: Context, id: Long) {
        val notas = getAll(context).filter { it.id != id }
        save(context, notas)
    }

    private fun save(context: Context, notas: List<NotaObject>) {
        val file = getFile(context)
        file.writeText(gson.toJson(notas))
    }
}