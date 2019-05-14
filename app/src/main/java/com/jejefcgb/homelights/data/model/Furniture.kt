package com.jejefcgb.homelights.data.model

import com.squareup.moshi.Json

class Furniture(@Json(name = "name")
                 var name: String?, @Json(name = "ip")
                 var ip: String?, @Json(name = "icon")
                 var icon: String?)
