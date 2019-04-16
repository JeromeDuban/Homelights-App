package com.jejefcgb.homelights

internal class Server {

    var name: String? = null
    var gpios: List<GPIO>? = null
    var icon: Int = 0
    var ip: String? = null

    override fun toString(): String {
        return "Server{" +
                "name='" + name + '\''.toString() +
                ", gpios=" + gpios +
                ", icon=" + icon +
                '}'.toString()
    }
}
