package com.blue.whales.drink.water.bluewhalesdrinkwater.jishuan

import com.blue.whales.drink.water.bluewhalesdrinkwater.App.Companion.gameApp
import com.blue.whales.drink.water.bluewhalesdrinkwater.data.HistoryBean
import com.blue.whales.drink.water.bluewhalesdrinkwater.data.WaterBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WaterUtils {
     fun getAddNumList(): MutableList<Int> {
        if(gameApp.addNumJson.isEmpty()){
            gameApp.addNumJson = """
                [200,400,600]
            """.trimIndent()
        }
        return try {
            Gson().fromJson(gameApp.addNumJson, object : TypeToken<MutableList<Int>>() {}.type)
                ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }
    // 保存添加数量列表
     fun saveAddNumList(num: Int) {
        val list = getAddNumList().toMutableList()
        list.add(num)
        gameApp.addNumJson = Gson().toJson(list)
    }
    // 获取当前喝水列表
     fun getWaterList(): MutableList<WaterBean> {
        return try {
            Gson().fromJson(gameApp.waterJson, object : TypeToken<MutableList<WaterBean>>() {}.type)
                ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    // 保存喝水列表
    private fun saveWaterList(list: List<WaterBean>) {
        gameApp.waterJson = Gson().toJson(list)
    }

    // 新增喝水记录
    fun addWaterBean(bean: WaterBean) {
        val list = getWaterList().toMutableList()
        list.add(bean)
        saveWaterList(list)
    }

    // 删除喝水记录
    fun deleteWaterBean(id: String) {
        val list = getWaterList().toMutableList()
        list.removeIf { it.id == id }
        saveWaterList(list)
    }

    // 更新喝水记录
    fun updateWaterBean(bean: WaterBean) {
        val list = getWaterList().toMutableList()
        val index = list.indexOfFirst { it.id == bean.id }
        if (index != -1) {
            list[index] = bean
            saveWaterList(list)
        }
    }

    // 获取今日喝水列表（按日期过滤）
    fun getTodayWaterList(
        date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date()
        )
    ):
            List<WaterBean> {
        return getWaterList().filter { it.date == date }
    }

    // WaterUtils.kt 新增方法
    fun updateTodayGoal(newGoal: Int) {
        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val list = getWaterList().toMutableList()

        list.replaceAll { bean ->
            if (bean.date == todayDate) {
                bean.copy(goalNum = newGoal)
            } else {
                bean
            }
        }

        saveWaterList(list)
    }

    fun getTodayTotalDrink(): Int {
        return getTodayWaterList().sumOf { it.drinkNum }
    }

    fun getTodayProgress(): Int {
        val todayList = getTodayWaterList()
        if (todayList.isEmpty()) return 0
        val total = todayList.sumOf { it.drinkNum }
        val goal =
            todayList.first().goalNum
        if (goal == 0)
            return 0
        return (total.toFloat() / goal * 100).toInt()
    }

    /**
     * 获取历史记录列表，包含每天的总喝水量、杯数和进度值。
     *
     * @return 返回一个 MutableList<HistoryBean>，表示历史记录列表。
     */
    fun getHistoryList(): MutableList<HistoryBean> {
        val waterList = getWaterList()
        val historyList = mutableListOf<HistoryBean>()

        // 按日期分组
        val groupedByDate = waterList.groupBy { it.date }

        for ((date, beans) in groupedByDate) {
            // 计算当天总喝水量
            val totalDrink = beans.sumOf { it.drinkNum }

            // 计算当天杯数（数据个数）
            val cupCount = beans.size

            // 获取当天的目标喝水量（取第一个数据的目标值）
            val goalNum = beans.firstOrNull()?.goalNum ?: 0

            // 计算喝水目标进度值
            val progress = if (goalNum == 0) 0 else (totalDrink.toFloat() / goalNum * 100).toInt()

            // 创建 HistoryBean 并添加到列表
            historyList.add(HistoryBean(date, totalDrink, cupCount, progress))
        }

        // 按日期倒序排序（最近日期在前）
        historyList.sortByDescending { it.date }

        return historyList
    }

}
