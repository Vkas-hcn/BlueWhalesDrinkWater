package com.blue.whales.drink.water.bluewhalesdrinkwater.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blue.whales.drink.water.bluewhalesdrinkwater.R
import com.blue.whales.drink.water.bluewhalesdrinkwater.data.WaterBean
import com.blue.whales.drink.water.bluewhalesdrinkwater.databinding.FragmentHomeBinding
import com.blue.whales.drink.water.bluewhalesdrinkwater.jishuan.WaterUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class HomeFragment : Fragment(), AddNumAdapter.OnItemClickListener {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var homeViewModel = HomeViewModel()
    private lateinit var adapter: TodayWaterAdapter
    private lateinit var adapterAddNum: AddNumAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        clickFun()
        initRecyclerView()
        return root
    }

    private fun initRecyclerView() {
        adapter = TodayWaterAdapter()
        binding.recyTodayDrink.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HomeFragment.adapter
        }
        adapterAddNum = AddNumAdapter(this)
        binding.recyclerNum.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterAddNum
        }
        loadData()
        loadAddNumData()
        getGoalNum()
    }

    private fun loadData() {
        val todayList = WaterUtils.getTodayWaterList()
        adapter.submitList(todayList)
        getGoalNum()
    }

    private fun loadAddNumData() {
        val numList = WaterUtils.getAddNumList()
        adapterAddNum.submitList(numList)
    }

    private fun getGoalNum() {
        val data = WaterUtils.getTodayWaterList().lastOrNull()?.goalNum ?: 25000
        val proData = WaterUtils.getTodayProgress()
        binding.tvEditGo.text = "${data}ml"
        binding.inGoal.editNum.setText(data.toString())
        binding.tvZong.text = WaterUtils.getTodayTotalDrink().toString() + "ml"
        binding.sP.progress = proData
        binding.tvPro.text = if (proData >= 100) {
            "100%"
        } else {
            "${proData}%"
        }
    }

    private fun clickFun() {
        with(binding) {
            imgAdd.setOnClickListener {
                inWater.dialogCon.isVisible = true
            }
            inWater.tvCancel.setOnClickListener {
                inWater.dialogCon.isVisible = false
            }
            inWater.tvConfirm.setOnClickListener {
                inWater.editNum.text.toString().trim().toIntOrNull()?.let {
                    if (it <= 0) {
                        Toast.makeText(
                            requireContext(),
                            "Please enter the correct value",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    WaterUtils.saveAddNumList(it)
                    loadAddNumData()
                    inWater.dialogCon.isVisible = false
                    //关闭软键盘
                    val inputMethodManager =
                        requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(inGoal.editNum.windowToken, 0)
                } ?: run {
                    Toast.makeText(
                        requireContext(),
                        "Please enter the correct value",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            tvEditGo.setOnClickListener {
                inGoal.dialogCon.isVisible = true
            }
            inGoal.tvCancel.setOnClickListener {
                inGoal.dialogCon.isVisible = false
            }
            inGoal.tvConfirm.setOnClickListener {
                inGoal.editNum.text.toString().trim().toIntOrNull()?.let {
                    if (it <= 0) {
                        Toast.makeText(
                            requireContext(),
                            "Please enter the correct value",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    WaterUtils.updateTodayGoal(it)
                    loadData()
                    inGoal.dialogCon.isVisible = false
                    //关闭软键盘
                    val inputMethodManager =
                        requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(inGoal.editNum.windowToken, 0)
                } ?: run {
                    Toast.makeText(
                        requireContext(),
                        "Please enter the correct value",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


        }
    }

    private fun addWaterRecord(amount: Int) {
        binding.inGoal.editNum.text.toString().trim().toIntOrNull()?.let {
            if (it <= 0) {
                Toast.makeText(
                    requireContext(),
                    "Please enter the correct value",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val newRecord = WaterBean(
                id = System.currentTimeMillis().toString(),
                date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                goalNum = it,
                drinkNum = amount,
                drinkTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            )
            WaterUtils.addWaterBean(newRecord)
            loadData()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(amount: Int) {
        addWaterRecord(amount)
    }
}