package com.blue.whales.drink.water.bluewhalesdrinkwater.data

import androidx.annotation.Keep

@Keep
data class WaterBean (
    var id: String,
    var date: String,
    var goalNum: Int,
    var drinkNum: Int,
    var drinkTime: String
)