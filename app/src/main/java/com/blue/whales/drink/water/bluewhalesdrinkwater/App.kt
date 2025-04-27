package com.blue.whales.drink.water.bluewhalesdrinkwater

import android.app.Application
import android.content.Context
import com.blue.whales.drink.water.bluewhalesdrinkwater.data.DataAppBean

class App : Application() {
    companion object {
        lateinit var gameApp: DataAppBean
        lateinit var appBlue: Context
    }

    override fun onCreate() {
        super.onCreate()
        appBlue = this
        gameApp = DataAppBean()
    }
}