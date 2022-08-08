package com.example.gestaofinaceira.adpter

import android.graphics.Color
import android.location.GnssAntennaInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaofinaceira.R
import com.example.gestaofinaceira.entity.Despesa

class DespesaAdapter(var despesas: List<Despesa>):RecyclerView.Adapter<DespesaAdapter.DespesaViewHolder>() {

    private var listener: DespesaListener? = null

    class DespesaViewHolder(view: View, listener: DespesaListener?):RecyclerView.ViewHolder(view){
        val name:TextView = view.findViewById(R.id.negiciacao_nome)
        val valor:TextView = view.findViewById(R.id.negiciacao_valor)
        val cor: View = view.findViewById(R.id.cor)

        init {
            view.setOnClickListener {
                 listener?.onItemClickListenerD(it, adapterPosition)
            }
            view.setOnLongClickListener {
                listener?.onItemLongClickListenerD(it,adapterPosition)
                true
            }
        }

    }

    fun setOnItemListener(listener: DespesaListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespesaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.negociacao1, parent, false)
        return DespesaViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: DespesaViewHolder, position: Int) {
        holder.name.text = despesas[position].name
        holder.valor.text = despesas[position].valor
        holder.cor.setBackgroundColor(Color.RED)
    }

    override fun getItemCount(): Int {
        return despesas.size
    }


}