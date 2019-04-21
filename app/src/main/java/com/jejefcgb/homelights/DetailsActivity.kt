package com.jejefcgb.homelights

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.github.clans.fab.FloatingActionMenu
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    @BindView(R.id.menu)
    lateinit var menu: FloatingActionMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        ButterKnife.bind(this)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //val iconValue  = intent?.extras?.get("EXTRA_ICON") as String
        val titleValue = intent?.extras?.get("EXTRA_TITLE") as String

//        detail_icon.setImageResource(resources.getIdentifier(iconValue,
//                "mipmap",
//                packageName))
        detail_title.text = titleValue
    }

//    @OnClick(R.id.menu_color)
//    internal fun openColorPicker() {
//
//        if (menu.isOpened) {
//            menu.close(true)
//        }
//
//        ColorPickerDialogBuilder
//                .with(this@DetailsActivity)
//                .setTitle(getString(R.string.color_title))
//                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
//                .density(12)
//                .setOnColorSelectedListener { color ->
//                    switchOn(color)
//                    Log.d("TAG", color.toString())
//                }
//                .showAlphaSlider(false)
//                .setPositiveButton(getString(R.string.validate)) { dialogInterface, color, integers ->
//                    dialogInterface.dismiss()
//                }
//                .build()
//                .show()
//    }
//
//    private fun switchOn(color: Int) {
//
//        val client = OkHttpClient()
//
//        for (i in mAdapter!!.getSelectedPos()) {
//            APIHelper.switchOnWithColor(this@DetailsActivity, list[i], color, client)
//        }
//
//        //(mAdapter as MainAdapter).resetSelectedPos()
//    }
//
//    @OnClick(R.id.menu_off)
//    internal fun shutDown() {
//
//        if (menu.isOpened) {
//            menu.close(true)
//        }
//
//        val client = OkHttpClient()
//
//        for (i in mAdapter!!.getSelectedPos()) {
//            APIHelper.switchOff(this@DetailsActivity, list[i], client)
//        }
//    }
//
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
}
