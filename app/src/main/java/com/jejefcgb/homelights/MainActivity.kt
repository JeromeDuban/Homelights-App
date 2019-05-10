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
import com.jejefcgb.homelights.data.model.Furniture
import com.jejefcgb.homelights.data.model.Home
import com.jejefcgb.homelights.data.model.Room
import com.jejefcgb.homelights.ui.GridSpacingItemDecoration
import com.jejefcgb.homelights.ui.RoomAdapter
import com.jejefcgb.homelights.utils.APIHelper
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {

    @BindView(R.id.main_recycler_view)
    lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: RoomAdapter
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
        mAdapter = RoomAdapter(this)
        mRecyclerView.adapter = mAdapter

        // Swipe to refresh
        swipe_container.setOnRefreshListener { getRemoteConfig() }
        swipe_container.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN)

        // Action bar
        supportActionBar?.setDisplayShowHomeEnabled(true)

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

                    config = Home("home", listOf(
                            Room(0, "Salon", "ic_object_tv", listOf(
                                    Furniture("Bar", "192.168.200.1", "ic_object_bar")
                            )),
                            Room(1, "Chambre du naze", "ic_object_bed", listOf(
                                    Furniture("Lit", "192.168.200.1", "ic_object_bed"),
                                    Furniture("Bureau", "192.168.200.1", "ic_object_desk")
                            )),
                            Room(2, "Chambre de la naze", "ic_object_bed", listOf(
                                    Furniture("Etagère", "192.168.200.1", "ic_object_shelf"),
                                    Furniture("Support écran", "192.168.200.1", "ic_object_tv")
                            ))))

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
