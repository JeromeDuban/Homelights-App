package com.jejefcgb.homelights

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
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

        getRemoteConfig()

    }


    @OnClick(R.id.menu_refresh)
    internal fun getRemoteConfig() {

        menu_refresh.visibility = View.GONE
        menu_refreshing.visibility = View.VISIBLE

        val client = OkHttpClient()
        val request = Request.Builder()
                .url("http://192.168.1.114:8080/api/config") //FIXME Variabiliser
                .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {

                this@MainActivity.runOnUiThread {
                    menu_refresh.visibility = View.VISIBLE
                    menu_refreshing.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Impossible de récupérer la configuration. Veuillez vérifier votre réseau wifi", Toast.LENGTH_SHORT).show()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter(Home::class.java)

                if (response.isSuccessful) {
                    val json = response.body()?.string()

                    this@MainActivity.runOnUiThread{
                        if (json != null ){
                            config = jsonAdapter.fromJson(json) as Home
                        }
                        menu_refresh.visibility = View.GONE
                        menu_refreshing.visibility = View.GONE
                    }

                } else {

                    val body = response.body()?.string()
                    this@MainActivity.runOnUiThread {
                        Toast.makeText(this@MainActivity, "Une erreur est survenue lors de la récupération de la configuration : (${response.code()}) > $body", Toast.LENGTH_SHORT).show()
                        menu_refresh.visibility = View.VISIBLE
                        menu_refreshing.visibility = View.GONE
                    }
                }
            }
        })
    }


    companion object {
        internal const val NB_COLUMNS = 2
    }

}
