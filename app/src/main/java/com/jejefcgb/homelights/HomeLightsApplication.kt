package com.jejefcgb.homelights

import android.app.Application
import com.jejefcgb.homelights.data.model.Home

class HomeLightsApplication : Application() {

    companion object {

        var config : Home = Home()
    }

}

