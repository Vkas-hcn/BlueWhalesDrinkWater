package com.blue.whales.drink.water.bluewhalesdrinkwater.ui.his

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blue.whales.drink.water.bluewhalesdrinkwater.RecordActivity
import com.blue.whales.drink.water.bluewhalesdrinkwater.data.WaterBean
import com.blue.whales.drink.water.bluewhalesdrinkwater.databinding.FragmentHisBinding
import com.blue.whales.drink.water.bluewhalesdrinkwater.jishuan.WaterUtils

class HisFragment : Fragment() {
    private var _binding: FragmentHisBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HisAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = HisAdapter(requireContext(), mutableListOf())
        binding.recyclerHis.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HisFragment.adapter
        }
    }

    private fun loadData() {
        val hisList = WaterUtils.getHistoryList()
        val waterList = WaterUtils.getWaterList()
        adapter = HisAdapter(requireContext(),hisList)
        binding.recyclerHis.adapter = adapter
        calculateAverages(waterList)
    }

    private fun calculateAverages(waterList: List<WaterBean>) {
        if (waterList.isEmpty()) {
            binding.tvAllNum.text = "0ml"
            binding.tvAllD.text = "0"
            return
        }

        // 按日期分组
        val groupedByDate = waterList.groupBy { it.date }

        // 计算总喝水量
        val totalWater = waterList.sumOf { it.drinkNum }

        // 计算总杯数
        val totalCups = waterList.size

        // 计算天数
        val totalDays = groupedByDate.size

        // 计算平均值
        val averageWater = totalWater / totalDays
        val averageCups = totalCups / totalDays

        // 更新UI
        binding.tvAllNum.text = "${averageWater}ml"
        binding.tvAllD.text = totalDays.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
