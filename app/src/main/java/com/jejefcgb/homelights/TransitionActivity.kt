package com.jejefcgb.homelights

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_transition.*


class TransitionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)

        val iconValue  = intent?.extras?.get("EXTRA_ICON") as String
        val titleValue = intent?.extras?.get("EXTRA_TITLE") as String

        detail_icon.setImageResource(resources.getIdentifier(iconValue,
                "mipmap",
                packageName))
        detail_title.text = titleValue

    }
}
