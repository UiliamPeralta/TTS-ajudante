package com.example.uiliam.ttsvozajudante.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.uiliam.ttsvozajudante.dataBase.Repository
import com.example.uiliam.ttsvozajudante.model.Palavra
import java.util.Locale


@Suppress("DEPRECATION")
class AppViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener{

    private var tts: TextToSpeech = TextToSpeech(application, this)
    private val repository: Repository = Repository(application)
    val palavras: LiveData<PagedList<Palavra>>? = repository.palavras
    val palavra: MutableLiveData<Palavra> = MutableLiveData()


    override fun onInit(status: Int) {
        tts.language = Locale.getDefault()
    }

    fun falar(txt: String) {
        if (txt.isNotEmpty()) tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onCleared() {
        super.onCleared()
        tts.shutdown()
    }

    fun stopFala() = tts.stop()

    fun inserirPalavra(palavra: Palavra) = repository.inserir(palavra)

    fun excluirPalavra(palavra: Palavra ) = repository.excluir(palavra)

    fun editarPalavra(palavra: Palavra) = repository.editarPalavra(palavra)

}