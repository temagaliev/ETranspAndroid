package com.example.kotlin_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.recycler_detail_row.view.*

class RecyclerDetailAdapter(private var detailInfoList: ArrayList<ItemStation>) :
    RecyclerView.Adapter<RecyclerDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_detail_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = detailInfoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetail = detailInfoList[position]
        holder.bind(itemDetail)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val snack: String = "Item Postion clciked: $adapterPosition"
                Snackbar.make(itemView, snack, Snackbar.LENGTH_SHORT).show()
            }
        }

        fun bind(modelItem: ItemStation) {
            itemView.numberTram.text = modelItem.numberTransport
            itemView.timeTram.text = modelItem.timeTransport
            itemView.meterTram.text = modelItem.meterTransport

            //itemView.ratingTextView.text = movie.searchName
        }
    }
}