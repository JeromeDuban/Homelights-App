package com.jejefcgb.homelights

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.jejefcgb.homelights.HomeLightsApplication.Companion.config
import com.jejefcgb.homelights.objects.Home
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {

    @BindView(R.id.my_recycler_view)
    lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: MainAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        mRecyclerView.addItemDecoration(
                GridSpacingItemDecoration(
                        NB_COLUMNS,
                        resources.getDimensionPixelSize(R.dimen.default_margin),
                        true,
                        0))

        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = GridLayoutManager(this, NB_COLUMNS)

        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = MainAdapter(this)
        mRecyclerView.adapter = mAdapter

        swipe_container.setOnRefreshListener { getRemoteConfig() }
        swipe_container.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN)

        getRemoteConfig()

    }


    private fun getRemoteConfig() {

        swipe_container.isRefreshing = true
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("${APIHelper.API_ADDRESS}/config")
                .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@MainActivity.runOnUiThread {
                    swipe_container.isRefreshing = false

                    toast("Impossible de récupérer la configuration. Veuillez vérifier votre réseau wifi")
                    config = Home()
                    mAdapter.notifyDataSetChanged()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                this@MainActivity.runOnUiThread {
                    swipe_container.isRefreshing = false

                    if (response.isSuccessful) {

                        val jsonAdapter = Moshi.Builder().build().adapter(Home::class.java)
                        val json = response.body()?.string()

                        HomeLightsApplication.config = jsonAdapter.fromJson(json as String) as Home
                        mAdapter.notifyDataSetChanged()

                        toast("Configuration chargée")
                    } else {
                        val body = response.body()?.string()
                        toast("Une erreur est survenue lors de la récupération de la configuration : (${response.code()}) > $body")
                    }
                }
            }
        })
    }

    private fun toast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        internal const val NB_COLUMNS = 1
    }

}
