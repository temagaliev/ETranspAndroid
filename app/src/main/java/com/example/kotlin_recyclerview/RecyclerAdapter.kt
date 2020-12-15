package com.example.kotlin_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.recycler_row.view.*

class RecyclerAdapter(private var arrayTransportList: ArrayList<Transport>, private val onClickListener: MainOnClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = arrayTransportList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTransport = arrayTransportList[position]
        holder.bind(itemTransport)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(itemTransport.link, itemTransport.secondName)

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val snack: String = "Item Postion clciked: $adapterPosition"
                Snackbar.make(itemView, snack, Snackbar.LENGTH_SHORT).show()
            }
        }

        fun bind(model: Transport) {
            itemView.movieNameTextView.text = model.nameStation
            //itemView.ratingTextView.text = movie.searchName
        }
    }
}

interface MainOnClickListener {
    fun onClick(textName: String, nameStation: String)
}