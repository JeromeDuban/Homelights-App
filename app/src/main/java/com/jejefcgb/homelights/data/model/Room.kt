package com.jejefcgb.homelights.data.model

import com.squareup.moshi.Json

class Room {

    @Json(name = "name")
    var name: String? = null
    @Json(name = "furnitures")
    var furnitures: List<Furniture> = ArrayList()



    constructor(name: String?, furnitures: List<Furniture>) {
        this.name = name
        this.furnitures = furnitures
    }

    constructor()
}