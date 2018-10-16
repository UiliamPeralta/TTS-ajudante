package com.example.uiliam.ttsvozajudante.dataBase

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dev.peralta.voztransform.dataBase.PalavraDao
import com.example.uiliam.ttsvozajudante.model.Palavra
import java.util.concurrent.Executors

private const val DATA_BASE_PAGE_SIZE = 20

class Repository(application: Application) {
    private val palavraDao: PalavraDao?
    val palavras: LiveData<PagedList<Palavra>>?

    init {
        val dataBase = AppRoomDataBase.getDataBase(application)
        palavraDao = dataBase?.palavraDao()
        palavras = palavraDao?.getPalavras()?.getPagedListPalavras()
    }

    // constroi pagedList com uma instância de DataSource.Factory e Tamanho da Lista
    private fun DataSource.Factory<Int, Palavra>.getPagedListPalavras() =
            LivePagedListBuilder(this, DATA_BASE_PAGE_SIZE).build()



    // crud listas de compras
    fun inserir(palavra: Palavra) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            palavraDao?.inserir(palavra)
        }
    }

    fun excluir(palavra: Palavra) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            palavraDao?.delete(palavra)
        }
    }

    fun editarPalavra(palavra: Palavra) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            palavraDao?.atualizar(palavra)
        }
    }
}