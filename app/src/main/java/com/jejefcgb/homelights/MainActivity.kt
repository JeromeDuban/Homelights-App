package com.jejefcgb.homelights

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private var list: ArrayList<Server>? = null
    @BindView(R.id.my_recycler_view)
    lateinit var mRecyclerView: RecyclerView

    @BindView(R.id.menu)
    lateinit var menu: FloatingActionMenu

    @BindView(R.id.menu_color)
    lateinit var menuColor: FloatingActionButton

    @BindView(R.id.menu_on)
    lateinit var menuOn: FloatingActionButton

    @BindView(R.id.menu_off)
    lateinit var menuOff: FloatingActionButton

    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private val NB_COLUMNS = 2

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

        setData()

        mAdapter = MyAdapter(list!!, cb)

        mRecyclerView.adapter = mAdapter

    }

    private fun setData() {
        // specify an adapter (see also next example)
        list = ArrayList()

        val meubleTv = Server()
        meubleTv.name = "Meuble TV"
        meubleTv.icon = R.mipmap.ic_object_tv
        list!!.add(meubleTv)

        val lit = Server()
        lit.name = "Lit"
        lit.icon = R.mipmap.ic_object_bed
        list!!.add(lit)

        val bar = Server()
        bar.name = "Bar"
        bar.icon = R.mipmap.ic_object_bar
        list!!.add(bar)

        val desk = Server()
        desk.name = "Bureau"
        desk.icon = R.mipmap.ic_object_desk
        list!!.add(desk)

    }

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
                .showAlphaSlider(false)
                .setPositiveButton(getString(R.string.validate)) { dialogInterface, i, integers ->
                    (mAdapter as MyAdapter).resetSelectedPos()
                    val client = OkHttpClient()


                    val url = String.format("http://192.168.1.41:8080/api/lights/%1\$d/%2\$d/%3\$d", Color.red(i), Color.green(i), Color.blue(i))

                    val request = Request.Builder()
                            .url(url)
                            .build()

                    client.newCall(request).enqueue(object : okhttp3.Callback {
                        override fun onFailure(call: Call, e: IOException) {

                        }

                        @Throws(IOException::class)
                        override fun onResponse(call: Call, response: Response) {

                        }
                    })
                }
                // TODO Send commands
                .setNegativeButton(getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }
                .build()
                .show()
    }

    @OnClick(R.id.menu_off)
    internal fun shutDown() {

        if (menu.isOpened) {
            menu.close(true)
        }

        (mAdapter as MyAdapter).resetSelectedPos()
        val client = OkHttpClient()


        val url = String.format("http://192.168.1.41:8080/api/lights/0/0/0")

        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

            }
        })
    }

}
