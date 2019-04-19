package com.jejefcgb.homelights

internal class Server {

    var name: String? = null
    var icon: Int = 0
    var ip: String? = null

    constructor()

    constructor(name: String?, icon: Int, ip: String?) {
        this.name = name
        this.icon = icon
        this.ip = ip
    }


    override fun toString(): String {
        return "Server{" +
                "name='" + name + '\''.toString() +
                ", icon=" + icon +
                '}'.toString()
    }
}
