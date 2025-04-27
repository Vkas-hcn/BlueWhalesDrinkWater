package com.blue.whales.drink.water.bluewhalesdrinkwater.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blue.whales.drink.water.bluewhalesdrinkwater.R

class AddNumAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<AddNumAdapter.ViewHolder>() {
    private var items = listOf<Int>()

    interface OnItemClickListener {
        fun onItemClick(amount: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAddNum: TextView = itemView.findViewById(R.id.tvAddNum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_num, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            tvAddNum.text = "+${item}ml"
            itemView.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newList: List<Int>) {
        items = newList
        notifyDataSetChanged()
    }
}
