package com.jejefcgb.homelights

internal class GPIO {

    var red: Int = 0
    var green: Int = 0
    var blue: Int = 0

    override fun toString(): String {
        return "GPIO{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}'.toString()
    }
}
