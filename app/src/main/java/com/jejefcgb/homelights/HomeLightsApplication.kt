package com.jejefcgb.homelights

import android.app.Application
import com.jejefcgb.homelights.data.model.Home

class HomeLightsApplication : Application() {

    companion object {

        var config: Home = Home()
        lateinit var PACKAGE_NAME: String
    }


    override fun onCreate() {
        super.onCreate()
        PACKAGE_NAME = applicationContext.packageName
    }
}

