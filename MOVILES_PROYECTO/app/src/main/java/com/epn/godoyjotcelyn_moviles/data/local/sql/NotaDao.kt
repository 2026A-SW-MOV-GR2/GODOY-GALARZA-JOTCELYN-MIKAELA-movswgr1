package com.epn.godoyjotcelyn_moviles.data.local.sql

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    @Query("SELECT * FROM notas ORDER BY id DESC")
    fun getAllNotas(): Flow<List<NotaEntity>>

    @Insert
    suspend fun insertNota(nota: NotaEntity)

    @Delete
    suspend fun deleteNota(nota: NotaEntity)
}