package com.jejefcgb.homelights

import android.app.Application
import com.jejefcgb.homelights.data.model.Home

class HomeLightsApplication : Application() {

    companion object {

        var config: Home = Home()
        lateinit var PACKAGE_NAME: String
         var TYPE_ROOM : Int = 0
         var TYPE_FURNITURE : Int = 1
    }


    override fun onCreate() {
        super.onCreate()
        PACKAGE_NAME = applicationContext.packageName
    }
}

