package com.jejefcgb.homelights.data.model

import androidx.annotation.NonNull
import com.squareup.moshi.Json

class Furniture(
        @NonNull
        @Json(name= "id")
        var id : Int?,
        @Json(name = "name")
        var name: String?, @Json(name = "ip")
        var ip: String?, @Json(name = "icon")
        var icon: String?)
