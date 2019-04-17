package com.jejefcgb.homelights

import android.Manifest.permission
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.github.clans.fab.FloatingActionMenu
import okhttp3.OkHttpClient
import pub.devrel.easypermissions.EasyPermissions
import java.util.*
import pub.devrel.easypermissions.PermissionRequest


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks{

    private var list: ArrayList<Server>? = null

    @BindView(R.id.my_recycler_view)
    lateinit var mRecyclerView: RecyclerView

    @BindView(R.id.menu)
    lateinit var menu: FloatingActionMenu


    private var mAdapter: MyAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

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

        checkWifi()

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

    private fun checkWifi() {
        val wifiMgr = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiMgr.connectionInfo
        val name = wifiInfo.ssid

        val perms = arrayOf(permission.ACCESS_COARSE_LOCATION)

        if (EasyPermissions.hasPermissions(this, *perms)) {
            if (name != getString(R.string.wifi_name)){
                MaterialDialog(this).show {
                    title(text = "Réseau WiFI")
                    message(text = "Vous n'êtes pas sur le bon réseau wifi.")
                    positiveButton ( text = "Rééssayer.") {
                        checkWifi()
                    }
                    negativeButton{  dismiss()}
                }
            }
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.location_rationale),
                    RC_LOCATION, *perms)
        }

    }

    private fun setData() {
        // specify an adapter (see also next example)
        list = ArrayList()

        val glasses = Server()
        glasses.name = "Verres"
        glasses.icon = R.mipmap.ic_object_glasses
        glasses.ip = "192.168.1.200"
        list!!.add(glasses)

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == RC_LOCATION){
            checkWifi()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == RC_LOCATION){
            checkWifi()
        }
    }

    companion object {

        internal const val RC_LOCATION = 1000
        internal const val NB_COLUMNS = 2
    }

}
