package com.blue.whales.drink.water.bluewhalesdrinkwater.data

import androidx.annotation.Keep
import com.blue.whales.drink.water.bluewhalesdrinkwater.App.Companion.appBlue


@Keep
class DataAppBean {
    var waterJson: String by DataStoreDelegate(appBlue, "waterJson", "")
    var addNumJson: String by DataStoreDelegate(appBlue, "addNumJson", "")
}