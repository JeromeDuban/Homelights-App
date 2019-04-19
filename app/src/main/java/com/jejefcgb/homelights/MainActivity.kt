package com.jejefcgb.homelights

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.github.clans.fab.FloatingActionMenu
import com.jejefcgb.homelights.objects.Furniture
import com.jejefcgb.homelights.objects.Home
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    private var list: ArrayList<Furniture> = ArrayList()

    @BindView(R.id.my_recycler_view)
    lateinit var mRecyclerView: RecyclerView

    @BindView(R.id.menu)
    lateinit var menu: FloatingActionMenu


    private var mAdapter: MyAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var gettingConfig: Boolean = false
    private var config: Home? = null

    private val cb = object : Callback() {
        override fun update(value: List<Int>) {

            val menuHeight = menu.height / (menu.childCount + 1)

            if (value.isNotEmpty() && menu.visibility == View.GONE) {
                val `in` = ObjectAnimator.ofFloat(menu, "translationY", menuHeight + 100f, 0f)
                `in`.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        menu.visibility = View.VISIBLE
                    }
                })
                `in`.start()
            } else if (value.isEmpty()) {

                val out = ObjectAnimator.ofFloat(menu, "translationY", 0f, menuHeight + 100f)
                out.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        menu.visibility = View.GONE
                    }
                })
                out.start()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

//        checkWifi()

        mRecyclerView.addItemDecoration(
                GridSpacingItemDecoration(
                        NB_COLUMNS,
                        resources.getDimensionPixelSize(R.dimen.default_margin),
                        true,
                        0))

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true)

        // use a linear layout manager
        mLayoutManager = GridLayoutManager(this, NB_COLUMNS)


        mRecyclerView.layoutManager = mLayoutManager

//        setData()

        mAdapter = MyAdapter(list!!, cb, this)

        mRecyclerView.adapter = mAdapter

        getConfig()

    }


    private fun setConfig(home: Home?) {
        config = home
        if (home != null){
            for(room in home.rooms){
                for (furniture in room.furnitures){
                    list.add(furniture)
                }
            }
        }
    }

//    private fun setData() {
//        // specify an adapter (see also next example)
//        list = ArrayList()
//
//        val test = resources.getResourceEntryName(R.mipmap.ic_object_glasses)
//        val test2 = R.mipmap.ic_object_glasses
//        val test3 = resources.getIdentifier(test, "mipmap", packageName)
//
//        val glasses = Server()
//        glasses.name = "Verres"
//        glasses.icon = R.mipmap.ic_object_glasses
//        glasses.ip = "192.168.1.200"
//        list.add(glasses)
//
//        val shelf = Server()
//        shelf.name = "Etagère"
//        shelf.icon = R.mipmap.ic_object_shelf
//        shelf.ip = "192.168.1.201"
//        list.add(shelf)
//
//        for (i in 1..10) {
//            list.add(Server("Test" + i.toString(), R.mipmap.ic_object_bed, "192.168.1.1"))
//        }
//
//    }

    @OnClick(R.id.menu_color)
    internal fun openColorPicker() {

        if (menu.isOpened) {
            menu.close(true)
        }

        ColorPickerDialogBuilder
                .with(this@MainActivity)
                .setTitle(getString(R.string.color_title))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener { color ->
                    switchOn(color)
                    Log.d("TAG", color.toString())
                }
                .showAlphaSlider(false)
                .setPositiveButton(getString(R.string.validate)) { dialogInterface, color, integers ->
                    dialogInterface.dismiss()
                }
                .build()
                .show()
    }

    private fun switchOn(color: Int) {

        val client = OkHttpClient()

        for (i in mAdapter!!.getSelectedPos()) {
            APIHelper.switchOnWithColor(this@MainActivity, list[i], color, client)
        }

        //(mAdapter as MyAdapter).resetSelectedPos()
    }

    @OnClick(R.id.menu_off)
    internal fun shutDown() {

        if (menu.isOpened) {
            menu.close(true)
        }

        val client = OkHttpClient()

        for (i in mAdapter!!.getSelectedPos()) {
            APIHelper.switchOff(this@MainActivity, list[i], client)
        }

        //(mAdapter as MyAdapter).resetSelectedPos()
    }

    @OnClick(R.id.menu_refresh)
    internal fun getConfig() {

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
                    setConfig(if (json != null) jsonAdapter.fromJson(json) else null)
                    this@MainActivity.runOnUiThread{
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

        internal const val RC_LOCATION = 1000
        internal const val NB_COLUMNS = 2
    }

}
