package com.example.kriptopararetrofitapp.adaptor

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kriptopararetrofitapp.R
import com.example.kriptopararetrofitapp.model.CryptoModel
import kotlinx.android.synthetic.main.recycler_view_row.view.*

class AdaptorRecycler(private val cryptoList : ArrayList<CryptoModel>, private val listener : Listener) : RecyclerView.Adapter<AdaptorRecycler.RowHolder>() {

    private val colors  = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")

    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }

    class RowHolder(view : View) : RecyclerView.ViewHolder(view) {

            fun bind(cryptoModel: CryptoModel, colors : Array<String>, position: Int, listener: Listener){
                itemView.setOnClickListener {
                    listener.onItemClick(cryptoModel)
                }
                itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
                itemView.tvCurrency.text =cryptoModel.currency
                itemView.tvPrice.text=cryptoModel.price
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row, parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position], colors, position, listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }
}