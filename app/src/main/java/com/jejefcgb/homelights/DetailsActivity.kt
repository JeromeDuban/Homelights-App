package com.jejefcgb.homelights

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.github.clans.fab.FloatingActionMenu
import com.jejefcgb.homelights.HomeLightsApplication.Companion.TYPE_FURNITURE
import com.jejefcgb.homelights.HomeLightsApplication.Companion.config
import com.jejefcgb.homelights.data.model.Room
import com.jejefcgb.homelights.ui.GridSpacingItemDecoration
import com.jejefcgb.homelights.ui.HomeAdapter
import com.jejefcgb.homelights.utils.APIHelper
import kotlinx.android.synthetic.main.activity_details.*
import okhttp3.OkHttpClient


class DetailsActivity : AppCompatActivity() {

    @BindView(R.id.menu)
    lateinit var menu: FloatingActionMenu

    lateinit var mRoom: Room

    @BindView(R.id.details_recycler_view)
    lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: HomeAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        ButterKnife.bind(this)

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent

        // FIXME : transition
//        postponeEnterTransition()
//        val sharedElementCallback = DetailSharedElementEnterCallback(intent,detail_name, detail_icon, detail_header)
//        setEnterSharedElementCallback(sharedElementCallback)

        // Set up header
        val roomId = intent?.extras?.get("EXTRA_ID") as Int
        mRoom = config.rooms.first { x -> x.id == roomId }
        detail_icon.setImageResource(resources.getIdentifier(mRoom.icon, "mipmap", packageName))
        detail_name.text = mRoom.name


        // Set up recycler view
        mRecyclerView.addItemDecoration(
                GridSpacingItemDecoration(
                        MainActivity.NB_COLUMNS,
                        resources.getDimensionPixelSize(R.dimen.default_margin),
                        true,
                        0))

        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = GridLayoutManager(this, MainActivity.NB_COLUMNS)

        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = HomeAdapter(this, mRoom.furniture, TYPE_FURNITURE)
        mRecyclerView.adapter = mAdapter
    }


    @OnClick(R.id.menu_color)
    internal fun openColorPicker() {

        if (menu.isOpened) {
            menu.close(true)
        }

        ColorPickerDialogBuilder
                .with(this@DetailsActivity)
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

        for (i in mAdapter.selectedItems) {
            APIHelper.switchOnWithColor(this@DetailsActivity, i, color, client)
        }

        //(mAdapter as RoomAdapter).resetSelectedPos()
    }

    @OnClick(R.id.menu_off)
    internal fun shutDown() {

        if (menu.isOpened) {
            menu.close(true)
        }

        val client = OkHttpClient() // FIXME : extract client

        for (i in mAdapter.selectedItems) {
            APIHelper.switchOff(this@DetailsActivity, i, client)
        }
    }

    // FIXMe : extract update and List
//    private val cb = object : Callback() {
//        override fun update(value: List<Int>) {
//
//            val menuHeight = menu.height / (menu.childCount + 1)
//
//            if (value.isNotEmpty() && menu.visibility == View.GONE) {
//                val `in` = ObjectAnimator.ofFloat(menu, "translationY", menuHeight + 100f, 0f)
//                `in`.addListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationStart(animation: Animator) {
//                        super.onAnimationStart(animation)
//                        menu.visibility = View.VISIBLE
//                    }
//                })
//                `in`.start()
//            } else if (value.isEmpty()) {
//
//                val out = ObjectAnimator.ofFloat(menu, "translationY", 0f, menuHeight + 100f)
//                out.addListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        super.onAnimationEnd(animation)
//                        menu.visibility = View.GONE
//                    }
//                })
//                out.start()
//            }
//
//        }
//    }

    fun toggleMenu() {
        val menuHeight = menu.height / (menu.childCount + 1)

        if (mAdapter.selectedItems.isNotEmpty() && menu.visibility == View.GONE) {
            val `in` = ObjectAnimator.ofFloat(menu, "translationY", menuHeight + 100f, 0f)
            `in`.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    menu.visibility = View.VISIBLE
                }
            })
            `in`.start()
        } else if (mAdapter.selectedItems.isEmpty()) {

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
