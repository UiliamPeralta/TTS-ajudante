package com.example.uiliam.ttsvozajudante.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uiliam.ttsvozajudante.R
import com.example.uiliam.ttsvozajudante.model.Action
import com.example.uiliam.ttsvozajudante.model.Palavra
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment() {

    private lateinit var viewModel: AppViewModel
    private var onItemClickListener: OnItemClickListener? = null
    private var adapter: PalavraAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        setHasOptionsMenu(true)
        with(view.recyclerView) {
            layoutManager = LinearLayoutManager(activity)
            this@MainFragment.adapter = PalavraAdapter(onItemClickListener)
            adapter = this@MainFragment.adapter
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { act ->
            viewModel = ViewModelProviders.of(act).get(AppViewModel::class.java)
            viewModel.palavras?.observe(act, Observer { pagedList ->
                adapter?.submitList(pagedList)
            })

            adapter?.onPopupMenuInteration = { palavra, action ->
                when (action) {
                    Action.EDITAR -> {
                        palavra?.let {
                            p -> viewModel.palavra.value = p
                            findNavController().navigate(R.id.action_mainFragment_to_detalheFragment)
                        }

                    }
                    Action.EXCLUIR -> {
                        palavra?.let { p -> viewModel.excluirPalavra(p) }
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.falar -> { viewModel.falar(edFalar.text.toString()); return true }
            R.id.salvar -> {inserir(edFalar.text.toString()); return true}
            R.id.idiomas -> {getIdiomas(); return true}
            R.id.limpar -> {limpar(); return true}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun limpar() {
        edFalar.setText("")
        viewModel.stopFala()
    }

    private fun getIdiomas() {
        activity?.packageManager?.let { pm ->
            val intent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA)
            if (intent.resolveActivity(pm) != null) startActivity(intent)
        }
    }

    private fun inserir(txt: String) {
        if (txt.isNotBlank()) {
            viewModel.inserirPalavra(Palavra(palavra = txt))
            Toast.makeText(activity, getString(R.string.sucesso), Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(activity, getText(R.string.campo_vazio), Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onItemClickListener = object : OnItemClickListener {
            @SuppressLint("SetTextI18n")
            override fun onClickListener(palavra: Palavra?) {
                edFalar.setText("${edFalar.text} ${palavra?.palavra}")
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        onItemClickListener = null
    }

    interface OnItemClickListener {
        fun onClickListener(palavra: Palavra?)
    }
}

