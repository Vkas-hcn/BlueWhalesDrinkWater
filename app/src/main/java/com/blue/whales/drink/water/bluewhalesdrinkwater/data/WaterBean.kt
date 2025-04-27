package com.blue.whales.drink.water.bluewhalesdrinkwater.data

data class WaterBean (
    var id: String,
    var date: String,
    var goalNum: Int,
    var drinkNum: Int,
    var drinkTime: String // 新增字段，表示喝水时间，格式为 "HH:mm"
)