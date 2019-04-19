package com.jejefcgb.homelights.objects

import com.squareup.moshi.Json

class Room {

    @Json(name = "name")
    var name: String? = null
    @Json(name = "furnitures")
    var furnitures: List<Furniture> = ArrayList()

}