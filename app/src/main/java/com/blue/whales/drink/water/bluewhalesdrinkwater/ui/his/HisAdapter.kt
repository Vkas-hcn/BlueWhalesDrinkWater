package com.blue.whales.drink.water.bluewhalesdrinkwater.ui.his

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blue.whales.drink.water.bluewhalesdrinkwater.MainActivity
import com.blue.whales.drink.water.bluewhalesdrinkwater.R
import com.blue.whales.drink.water.bluewhalesdrinkwater.RecordActivity
import com.blue.whales.drink.water.bluewhalesdrinkwater.data.HistoryBean
import com.blue.whales.drink.water.bluewhalesdrinkwater.data.WaterBean
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HisAdapter(private val  context: Context,private val data: MutableList<HistoryBean>) :
    RecyclerView.Adapter<HisAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tv_date_h_his)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_d_his)
        val tvDrinkNum: TextView = itemView.findViewById(R.id.tv_his_num_his)
        val tvCupNumHis : TextView = itemView.findViewById(R.id.tv_cup_num_his)
        val progressBar: ProgressBar = itemView.findViewById(R.id.s_p_his)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_his, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.apply {
            tvTime.text = item.date
            tvDate.text = if (isToday(item.date)) "Today" else "Past"
            tvDrinkNum.text = "${item.totalDrink}ml"
            tvCupNumHis.text = "${item.cupCount}"
            progressBar.progress = item.progress
            itemView.setOnClickListener {
                val intent = Intent(context, RecordActivity::class.java)
                intent.putExtra("SELECTED_DATE", item.date)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = data.size





    private fun isToday(date: String): Boolean {
        return date == SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }
}

