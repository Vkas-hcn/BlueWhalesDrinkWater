package com.blue.whales.drink.water.bluewhalesdrinkwater.data

import androidx.annotation.Keep


@Keep
data class HistoryBean(
    val date: String,
    val totalDrink: Int,
    val cupCount: Int,
    val progress: Int
)
