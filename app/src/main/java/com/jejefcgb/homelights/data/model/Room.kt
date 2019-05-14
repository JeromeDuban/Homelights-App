package com.jejefcgb.homelights.data.model

import androidx.annotation.NonNull
import com.squareup.moshi.Json

class Room {

    @NonNull
    @Json(name = "id")
    var id: Int? = null
    @NonNull
    @Json(name = "name")
    var name: String? = null

    @Json(name = "icon")
    var icon: String? = null
    @NonNull
    @Json(name = "furnitures")
    var furniture: List<Furniture> = ArrayList()

    var quantity : Int = furniture.size

    constructor()

    constructor(id: Int?, name: String?, icon: String?, furniture: List<Furniture>) {
        this.id = id
        this.name = name
        this.icon = icon
        this.furniture = furniture
    }


}