package com.jejefcgb.homelights

import android.app.Activity
import android.graphics.Color
import android.widget.Toast
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException



internal class APIHelper {
    companion object {

        fun switchOnWithColor(context: Activity, server: Server, color: Int, client: OkHttpClient) {

            val url = String.format("http://192.168.1.114:8080/api/magic/%1\$s/%2\$d/%3\$d/%4\$d",
                    server.ip,
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

        fun switchOff(context: Activity, server: Server, client: OkHttpClient) {

            val url = String.format("http://192.168.1.114:8080/api/magic/%1\$s/0/0/0",
                    server.ip)

            val request = Request.Builder()
                    .url(url)
                    .build()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    context.runOnUiThread { Toast.makeText(context, "Impossible d'allumer " + server.name, Toast.LENGTH_SHORT).show() }
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    // No problem
                }
            })
        }
    }
}
