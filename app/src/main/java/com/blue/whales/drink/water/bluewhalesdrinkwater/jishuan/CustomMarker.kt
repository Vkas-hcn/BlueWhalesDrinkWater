package com.blue.whales.drink.water.bluewhalesdrinkwater.jishuan

import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import android.content.Context
import android.widget.TextView
import com.blue.whales.drink.water.bluewhalesdrinkwater.R
import com.blue.whales.drink.water.bluewhalesdrinkwater.data.WaterBean

class CustomMarker(
    context: Context,
    layoutResource: Int,
    private val entriesWithData: List<Pair<Entry, WaterBean>>
) : MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent)
    private val tvContent1: TextView = findViewById(R.id.tvContent1)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e != null) {
            // 查找对应的 WaterBean
            val waterBean = entriesWithData.find { it.first == e }?.second
            if (waterBean != null) {
                // 设置显示内容，例如 "8:00 (1500ml)"
                tvContent.text = waterBean.drinkTime
                tvContent1.text = "(${waterBean.drinkNum}ml)"
            }
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        // 调整 Marker 的位置，使其居中显示在数据点上方
        return MPPointF(-(width / 2f), -(height.toFloat() + 20))
    }
}
