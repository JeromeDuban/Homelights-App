package com.jejefcgb.homelights.data.model

import com.squareup.moshi.Json

class Home {

    @Json(name = "name")
    var name: String? = null
    @Json(name = "rooms")
    var rooms: ArrayList<Room> = ArrayList()

    constructor()

    constructor(name: String?, rooms: ArrayList<Room>) {
        this.name = name
        this.rooms = rooms
    }


}