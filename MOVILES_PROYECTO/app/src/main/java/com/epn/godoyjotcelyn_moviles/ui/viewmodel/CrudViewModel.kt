package com.epn.godoyjotcelyn_moviles.ui.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.epn.godoyjotcelyn_moviles.data.local.nosql.JsonNoSqlManager
import com.epn.godoyjotcelyn_moviles.data.local.nosql.NotaObject
import com.epn.godoyjotcelyn_moviles.data.local.sql.NotaDatabase
import com.epn.godoyjotcelyn_moviles.data.local.sql.NotaEntity
import com.epn.godoyjotcelyn_moviles.data.model.Nota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CrudViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val db = NotaDatabase.getDatabase(context)
    private val dao = db.notaDao()

    // true = SQL, false = NoSQL
    private val _useSql = MutableStateFlow(true)
    val useSql: StateFlow<Boolean> = _useSql

    private val _notas = MutableStateFlow<List<Nota>>(emptyList())
    val notas: StateFlow<List<Nota>> = _notas

    init {
        loadNotas()
    }

    fun toggleSource(useSql: Boolean) {
        _useSql.value = useSql
        loadNotas()
    }

    fun loadNotas() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_useSql.value) {
                dao.getAllNotas().collect { list ->
                    _notas.value = list.map { Nota(it.id.toLong(), it.titulo) }
                }
            } else {
                val list = JsonNoSqlManager.getAll(context)
                _notas.value = list.map { Nota(it.id, it.titulo) }
            }
        }
    }

    fun agregarNota(titulo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_useSql.value) {
                dao.insertNota(NotaEntity(titulo = titulo))
            } else {
                JsonNoSqlManager.insert(context, NotaObject(titulo = titulo))
                loadNotas()
            }
        }
    }

    fun eliminarNota(nota: Nota) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_useSql.value) {
                dao.deleteNota(NotaEntity(id = nota.id.toInt(), titulo = nota.titulo))
            } else {
                JsonNoSqlManager.delete(context, nota.id)
                loadNotas()
            }
        }
    }
}