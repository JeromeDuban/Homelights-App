package com.jejefcgb.homelights

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import okhttp3.OkHttpClient
import java.util.*

class MainActivity : AppCompatActivity() {

    private var list: ArrayList<Server>? = null
    @BindView(R.id.my_recycler_view)
    lateinit var mRecyclerView: RecyclerView

    @BindView(R.id.menu)
    lateinit var menu: FloatingActionMenu

    @BindView(R.id.menu_color)
    lateinit var menuColor: FloatingActionButton

    @BindView(R.id.menu_off)
    lateinit var menuOff: FloatingActionButton

    private var mAdapter: MyAdapter? = null
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

        val verres = Server()
        verres.name = "Verres"
        verres.icon = R.mipmap.ic_object_glasses
        verres.ip = "192.168.1.200"
        list!!.add(verres)

        val shelf = Server()
        shelf.name = "Etagère"
        shelf.icon = R.mipmap.ic_object_shelf
        shelf.ip = "192.168.1.201"
        list!!.add(shelf)

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

    private fun switchOn(color : Int){

        val client = OkHttpClient()

        for (i in mAdapter!!.getSelectedPos()){
            APIHelper.switchOnWithColor(this@MainActivity, list!![i], color, client)
        }

        //(mAdapter as MyAdapter).resetSelectedPos()
    }

    @OnClick(R.id.menu_off)
    internal fun shutDown() {

        if (menu.isOpened) {
            menu.close(true)
        }

        val client = OkHttpClient()

        for (i in mAdapter!!.getSelectedPos()){
            APIHelper.switchOff(this@MainActivity, list!![i], client)
        }

        //(mAdapter as MyAdapter).resetSelectedPos()
    }

}
