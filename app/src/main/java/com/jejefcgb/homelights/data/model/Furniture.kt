package com.jejefcgb.homelights.data.model

import com.squareup.moshi.Json

class Furniture {
    @Json(name = "name")
    var name: String? = null
    @Json(name = "ip")
    var ip: String? = null
    @Json(name = "icon")
    var icon: String? = null

    val mipmapIcon:String
    get() = "@mipmap/$icon"

    constructor()

    constructor(name: String?, ip: String?, icon: String?) {
        this.name = name
        this.ip = ip
        this.icon = icon
    }




}