package com.dev.peralta.voztransform.dataBase

import androidx.paging.DataSource
import androidx.room.*
import com.example.uiliam.ttsvozajudante.model.Palavra

@Dao
interface PalavraDao {
    @Insert
    fun inserir(palavra: Palavra)

    @Delete
    fun delete(palavra: Palavra)

    @Update
    fun atualizar(palavra: Palavra)

    @Query("SELECT * from palavra ORDER BY id DESC")
    fun getPalavras(): DataSource.Factory<Int, Palavra>
}