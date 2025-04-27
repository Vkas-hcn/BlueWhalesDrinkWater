package com.blue.whales.drink.water.bluewhalesdrinkwater

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blue.whales.drink.water.bluewhalesdrinkwater.App.Companion.gameApp
import com.blue.whales.drink.water.bluewhalesdrinkwater.data.WaterBean
import com.blue.whales.drink.water.bluewhalesdrinkwater.databinding.ActivityGuideBinding
import com.blue.whales.drink.water.bluewhalesdrinkwater.databinding.ActivityRecordBinding
import com.blue.whales.drink.water.bluewhalesdrinkwater.databinding.FragmentHisBinding
import com.blue.whales.drink.water.bluewhalesdrinkwater.jishuan.CustomMarker
import com.blue.whales.drink.water.bluewhalesdrinkwater.jishuan.WaterUtils
import com.blue.whales.drink.water.bluewhalesdrinkwater.ui.home.AddNumAdapter
import com.blue.whales.drink.water.bluewhalesdrinkwater.ui.home.TodayWaterAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class RecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordBinding
    private lateinit var adapter: RecordAdapter
    private lateinit var lineChart: LineChart
    var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 接收传递的日期
        selectedDate = intent.getStringExtra("SELECTED_DATE").toString()
        binding.imageView.setOnClickListener {
            finish()
        }

        // 初始化折线图
        lineChart = binding.lineChart
        initLineChart()

        initRecyclerView()
    }

    private fun initLineChart() {
        // 配置折线图
        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.setDrawGridBackground(false)
        lineChart.setPinchZoom(true)
        lineChart.setScaleEnabled(true)
        lineChart.legend.isEnabled = false

        // 配置X轴
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(getXAxisValues())

        // 配置Y轴
        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(false)
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 100f

        // 隐藏右侧Y轴
        lineChart.axisRight.isEnabled = false

        // 设置数据
        val entriesWithData = getChartData()
        val entries = entriesWithData.map { it.first } // 提取 Entry 列表
        val dataSet = LineDataSet(entries, "")
        dataSet.color = resources.getColor(R.color.chart_1)
        dataSet.valueTextColor = resources.getColor(R.color.white)
        dataSet.setCircleColor(resources.getColor(R.color.chart_1))
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 4f
        dataSet.setDrawValues(true)

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        // 设置自定义 Marker，并传递数据源
        val marker = CustomMarker(this, R.layout.custom_marker_layout, entriesWithData)
        lineChart.marker = marker

        lineChart.invalidate()
    }


    private fun getXAxisValues(): List<String> {
        return listOf(
            "00:00",
            "01:00",
            "02:00",
            "03:00",
            "04:00",
            "05:00",
            "06:00",
            "07:00",
            "08:00",
            "09:00",
            "10:00",
            "11:00",
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00",
            "19:00",
            "20:00",
            "21:00",
            "22:00",
            "23:00"
        )
    }


    private fun getChartData(): List<Pair<Entry, WaterBean>> {
        val todayList = WaterUtils.getTodayWaterList(selectedDate)
        val entries = mutableListOf<Pair<Entry, WaterBean>>()

        // 获取当日目标喝水量
        val goalNum = todayList.firstOrNull()?.goalNum ?: 1 // 如果目标量为0，默认为1，避免除零错误

        // 按时间排序
        val sortedList = todayList.sortedBy { it.drinkTime }

        // 累计喝水量
        var cumulativeDrinkNum = 0f

        for (bean in sortedList) {
            // 将时间转换为小时数（例如 "08:30" -> 8.5）
            val timeParts = bean.drinkTime.split(":")
            val hour = timeParts[0].toFloat()
            val minute = timeParts[1].toFloat()
            val timeInHours = hour + (minute / 60)

            // 累计喝水量
            cumulativeDrinkNum += bean.drinkNum

            // 计算百分比
            val progress = (cumulativeDrinkNum / goalNum) * 100
            val entry = if (progress >= 100) {
                Entry(timeInHours, 100f)
            } else {
                Entry(timeInHours, progress)
            }

            // 将 Entry 和 WaterBean 关联起来
            entries.add(Pair(entry, bean))
        }

        return entries
    }




    private fun initRecyclerView() {
        adapter = RecordAdapter()
        binding.recyclerRecord.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RecordActivity.adapter
        }
        loadData()
    }

    private fun loadData() {
        selectedDate?.let {
            Log.e("TAG", "loadData: rec")
            val todayList = WaterUtils.getTodayWaterList(it)
            adapter.submitList(todayList)
        }
    }
}

