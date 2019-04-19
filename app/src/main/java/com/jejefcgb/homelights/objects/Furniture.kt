package com.jejefcgb.homelights.objects

import com.squareup.moshi.Json

class Furniture {

    @Json(name = "name")
    var name: String? = null
    @Json(name = "ip")
    var ip: String? = null
    @Json(name = "icon")
    var icon: String? = null

}