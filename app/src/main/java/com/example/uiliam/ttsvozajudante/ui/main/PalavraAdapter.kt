package com.example.uiliam.ttsvozajudante.ui.main

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.uiliam.ttsvozajudante.R
import com.example.uiliam.ttsvozajudante.model.Action
import com.example.uiliam.ttsvozajudante.model.Palavra
import kotlinx.android.synthetic.main.palavra_adapter.view.*


class PalavraAdapter(private val onItemClickListener: MainFragment.OnItemClickListener?) : PagedListAdapter<Palavra, PalavraAdapter.PalavraViewHolder>(DIFF_CALLBACK) {

    var onPopupMenuInteration: ((palavra: Palavra?, action: Action) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PalavraViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.palavra_adapter, parent, false)

        return PalavraViewHolder(view)
    }

    private fun onPopUpMenu(context: Context, view: View, palavra: Palavra?) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.pop_up_menu)
        popupMenu.gravity = Gravity.END
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.editar -> onPopupMenuInteration?.invoke(palavra, Action.EDITAR)
                R.id.excluir -> onPopupMenuInteration?.invoke (palavra, Action.EXCLUIR)

            }
            true }
        popupMenu.show()
    }



    override fun onBindViewHolder(holder: PalavraViewHolder, position: Int) {
        val palavra = getItem(position)
        holder.tvPalavra.text = palavra?.palavra
        holder.itemView.setOnClickListener { onItemClickListener?.onClickListener(palavra) }
        holder.itemView.setOnLongClickListener {
            onPopUpMenu(holder.itemView.context, holder.itemView, palavra)
            true
        }
    }


    inner class PalavraViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val tvPalavra: TextView = mView.tv_palavra
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Palavra>() {
            override fun areItemsTheSame(oldItem: Palavra, newItem: Palavra) =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Palavra, newItem: Palavra) =
                    oldItem == newItem

        }
    }
}