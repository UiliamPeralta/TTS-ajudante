package com.example.uiliam.ttsvozajudante.ui.main


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.uiliam.ttsvozajudante.R
import com.example.uiliam.ttsvozajudante.model.Palavra
import kotlinx.android.synthetic.main.fragment_detalhe.*

class DetalheFragment : Fragment() {
    private lateinit var viewModel: AppViewModel
    private var palavra: Palavra? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detalhe, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { act ->
            viewModel = ViewModelProviders.of(act).get(AppViewModel::class.java)
            viewModel.palavra.observe(act, Observer { p -> palavra = p })
            edDetalhe.setText(palavra?.palavra)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_detalhe, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.salvar -> { salvar(); return true }
            R.id.limpar -> { edDetalhe.setText(""); return true }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun salvar() {
        val text = edDetalhe.text.toString()
        if (text.isNotBlank()) {
            palavra?.let { p ->
                p.palavra = text
                viewModel.editarPalavra(p)
                Toast.makeText(activity, getString(R.string.sucesso), Toast.LENGTH_SHORT).show()
            }
            return
        }
        Toast.makeText(activity, getString(R.string.campo_vazio), Toast.LENGTH_SHORT).show()
    }
}
