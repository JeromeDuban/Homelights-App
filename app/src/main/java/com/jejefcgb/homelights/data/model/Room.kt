package com.jejefcgb.homelights.data.model

import com.squareup.moshi.Json

class Room(
        @Json(name = "id") var id: Int,
        @Json(name = "name") var name: String?,
        @Json(name = "icon") var icon: String?,
        @Json(name = "furnitures") var furniture: List<Furniture>)