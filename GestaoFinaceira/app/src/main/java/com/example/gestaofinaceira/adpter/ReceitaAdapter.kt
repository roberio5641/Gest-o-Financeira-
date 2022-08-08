package com.example.gestaofinaceira.adpter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaofinaceira.R
import com.example.gestaofinaceira.entity.Receita

class ReceitaAdapter(var receitas: List<Receita>):RecyclerView.Adapter<ReceitaAdapter.ReceitaViewHolder>() {

    private var listener: ReceitaListener? = null

    class ReceitaViewHolder(view : View, listener: ReceitaListener?):RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.negiciacao_nome)
        val valor: TextView = view.findViewById(R.id.negiciacao_valor)
        val cor: View = view.findViewById(R.id.cor)

        init {
            view.setOnClickListener {
                listener?.onItemClickListenerR(it, adapterPosition)
            }
            view.setOnLongClickListener {
                listener?.onItemLongClickListenerR(it,adapterPosition)
                true
            }
        }
    }

    fun setOnItemListener(listener: ReceitaListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceitaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.negociacao1, parent, false)
        return ReceitaViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ReceitaViewHolder, position: Int) {
        holder.name.text = receitas[position].name
        holder.valor.text = receitas[position].valor
        holder.cor.setBackgroundColor(Color.GREEN)
    }

    override fun getItemCount(): Int {
        return receitas.size
    }


}