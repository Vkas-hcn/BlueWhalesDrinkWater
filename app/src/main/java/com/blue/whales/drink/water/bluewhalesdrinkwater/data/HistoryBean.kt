package com.blue.whales.drink.water.bluewhalesdrinkwater.data


/**
 * 历史记录数据类
 * @param date 日期（格式为 "yyyy-MM-dd"）
 * @param totalDrink 当天总喝水量（单位：ml）
 * @param cupCount 当天喝水杯数
 * @param progress 当天喝水目标进度值（百分比）
 */
data class HistoryBean(
    val date: String,
    val totalDrink: Int,
    val cupCount: Int,
    val progress: Int
)
