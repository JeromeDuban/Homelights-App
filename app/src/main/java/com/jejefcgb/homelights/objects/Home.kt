package com.jejefcgb.homelights.objects

import com.squareup.moshi.Json

class Home {

    @Json(name = "name")
    var name: String? = null
    @Json(name = "rooms")
    var rooms: List<Room> = ArrayList()

}