package com.jejefcgb.homelights.utils

import android.app.Activity
import android.graphics.Color
import android.widget.Toast
import com.jejefcgb.homelights.data.model.Furniture
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException



internal class APIHelper {
    companion object {
        
        const val API_ADDRESS : String = "http://192.168.1.114:8080/api"

        fun switchOnWithColor(context: Activity, furniture: Furniture, color: Int, client: OkHttpClient) {
           
            // TODO : check if furniture not null
            val url = String.format("$API_ADDRESS/magic/%1\$s/%2\$d/%3\$d/%4\$d",
                    furniture.ip,
                    Color.red(color),
                    Color.green(color),
                    Color.blue(color))

            val request = Request.Builder()
                    .url(url)
                    .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    context.runOnUiThread { Toast.makeText(context, "Une erreur est survenue : " + e.message, Toast.LENGTH_SHORT).show() }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    // No problem
                }
            })
        }

        fun switchOff(context: Activity, furniture: Furniture, client: OkHttpClient) {
            // TODO : check if furniture not null
            val url = String.format("$API_ADDRESS/magic/%1\$s/0/0/0",
                    furniture.ip)

            val request = Request.Builder()
                    .url(url)
                    .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    context.runOnUiThread { Toast.makeText(context, "Impossible d'allumer " + furniture.name, Toast.LENGTH_SHORT).show() }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    // No problem
                }
            })
        }
    }
}
