package com.jejefcgb.homelights

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.jejefcgb.homelights.HomeLightsApplication.Companion.TYPE_ROOM
import com.jejefcgb.homelights.HomeLightsApplication.Companion.config
import com.jejefcgb.homelights.data.model.Furniture
import com.jejefcgb.homelights.data.model.Home
import com.jejefcgb.homelights.data.model.Room
import com.jejefcgb.homelights.ui.GridSpacingItemDecoration
import com.jejefcgb.homelights.ui.HomeAdapter
import com.jejefcgb.homelights.utils.APIHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_custom_view.view.*
import okhttp3.OkHttpClient
import okhttp3.Request


class MainActivity : AppCompatActivity() {

    @BindView(R.id.main_recycler_view)
    lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: HomeAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        // Set up recycler view
        mRecyclerView.addItemDecoration(
                GridSpacingItemDecoration(
                        NB_COLUMNS,
                        resources.getDimensionPixelSize(R.dimen.default_margin),
                        true,
                        0))

        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = GridLayoutManager(this, NB_COLUMNS)

        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = HomeAdapter(this, config.rooms, TYPE_ROOM)
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

        //FIXME :  TO REMOVE
        swipe_container.isRefreshing = false
        config = Home("home", arrayListOf(
                Room(0, "Salon", "ic_object_tv", listOf(
                        Furniture(0, "Verres", "192.168.1.200", "ic_object_glasses")
                )),
                Room(1, "Chambre du naze", "ic_object_bed", listOf(
                        Furniture(1, "Lit", "192.168.200.1", "ic_object_bed"),
                        Furniture(2, "Bureau", "192.168.200.1", "ic_object_desk")
                )),
                Room(2, "Chambre de la naze", "ic_object_bed", listOf(
                        Furniture(3, "Etagère", "192.168.1.201", "ic_object_shelf"),
                        Furniture(4, "Support écran", "192.168.200.1", "ic_object_tv")
                ))))

        mAdapter.data = config.rooms
        mAdapter.notifyDataSetChanged()

        Toast.makeText(this@MainActivity, "FIXME", Toast.LENGTH_SHORT).show()
        // FIXME : END TO REMOVE

//        client.newCall(request).enqueue(object : okhttp3.Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                this@MainActivity.runOnUiThread {
//                    swipe_container.isRefreshing = false
//
//                    toast("Impossible de récupérer la configuration. Veuillez vérifier votre réseau wifi")
//
//                    config = Home("home", listOf(
//                            Room(0, "Salon", "ic_object_tv", listOf(
//                                    Furniture("Bar", "192.168.200.1", "ic_object_bar")
//                            )),
//                            Room(1, "Chambre du naze", "ic_object_bed", listOf(
//                                    Furniture("Lit", "192.168.200.1", "ic_object_bed"),
//                                    Furniture("Bureau", "192.168.200.1", "ic_object_desk")
//                            )),
//                            Room(2, "Chambre de la naze", "ic_object_bed", listOf(
//                                    Furniture("Etagère", "192.168.200.1", "ic_object_shelf"),
//                                    Furniture("Support écran", "192.168.200.1", "ic_object_tv")
//                            ))))
//
//                    mAdapter.notifyDataSetChanged()
//                }
//            }
//
//            @Throws(IOException::class)
//            override fun onResponse(call: Call, response: Response) {
//                this@MainActivity.runOnUiThread {
//                    swipe_container.isRefreshing = false
//
//                    if (response.isSuccessful) {
//
//                        val jsonAdapter = Moshi.Builder().build().adapter(Home::class.java)
//                        val json = response.body()?.string()
//
//                        HomeLightsApplication.config = jsonAdapter.fromJson(json as String) as Home //TODO : vérifier comportement si un champ manquant
//                        mAdapter.notifyDataSetChanged()
//
//                        toast("Configuration chargée")
//                    } else {
//                        val body = response.body()?.string()
//                        toast("Une erreur est survenue lors de la récupération de la configuration : (${response.code()}) > $body")
//                    }
//                }
//            }
//        })
    }

    @OnClick(R.id.main_fab)
    fun addRoom() {
        val dialog = MaterialDialog(this).show {
            customView(R.layout.my_custom_view)
            positiveButton(text = "Ajouter")
            negativeButton(text = "Annuler")
        }

        val v: View = dialog.getCustomView()
        val spinner: Spinner = v.custom_spinner
        spinner.adapter = IconAdapter(this)

        val nameEt : TextView = v.custom_et_name

        dialog.negativeButton { it.dismiss() }
        dialog.positiveButton {
            val icon = resources.getResourceEntryName(spinner.adapter.getItem(spinner.selectedItemPosition) as Int)
            val id: Int = config.rooms.map { x -> x.id }.max() as Int + 1
            val name = nameEt.text.toString().trim()

            config.rooms.add(Room(
                    id,
                    name,
                    icon,
                    ArrayList()
            ))

            mAdapter.notifyDataSetChanged()

        }


    }

    private fun toast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        internal const val NB_COLUMNS = 1
    }

}
