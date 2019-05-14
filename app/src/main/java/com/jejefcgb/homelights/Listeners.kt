package com.jejefcgb.homelights

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.jejefcgb.homelights.data.model.Room

class RoomItemListener(private var source : Activity, private var roomIcon: View, var room : Room) : View.OnClickListener {

    override  fun onClick(v: View) {
        val p1 = androidx.core.util.Pair(roomIcon, "transition_icon")

        var transitions = arrayOf(p1)
        //            transitions+= androidx.core.util.Pair(mTitle as View , "transition_title")
        //            transitions+= androidx.core.util.Pair(mBackground as View , "transition_background")

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(source, *transitions)

        // FIXME : transition
        // val intent = getDetailActivityStartIntent(mActivity, adapterPosition)

        val intent = Intent(source, DetailsActivity::class.java)
        intent.putExtra("EXTRA_ID", room.id)
        source.startActivity(intent, options.toBundle())
    }

}