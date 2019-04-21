package com.jejefcgb.homelights

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.jejefcgb.homelights.HomeLightsApplication.Companion.config
import kotlinx.android.synthetic.main.room.view.*

class MainAdapter internal constructor(val mActivity: Activity) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    inner class MainViewHolder internal constructor(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {


        internal var mTitle: TextView
        internal var mDetails: TextView
        internal var mIcon: ImageView
        internal var mBackground: View


        init {
            v.setOnClickListener(this)
            mTitle = v.room_title
            mIcon = v.room_icon
            mDetails = v.room_details
            mBackground = v.room_background
        }

        override fun onClick(v: View) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val p1 = androidx.core.util.Pair(mIcon as View, "transitionImage")

            var transitions = arrayOf(p1)
            transitions+= androidx.core.util.Pair(mTitle as View , "transitionTitle")
            transitions+= androidx.core.util.Pair(mBackground, "transitionBackground")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, *transitions)

            val intent = Intent(mActivity, DetailsActivity::class.java)
            intent.putExtra("EXTRA_ICON", "ic_object_tv") //FIXME
            intent.putExtra("EXTRA_TITLE", config.rooms[adapterPosition].name)
            mActivity.startActivity(intent, options.toBundle())

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MainAdapter.MainViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.room, parent, false) as ConstraintLayout

        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val s = config.rooms[position]
        holder.mTitle.text = s.name
        holder.mIcon.setImageResource(R.mipmap.ic_object_tv)
        holder.mDetails.text = mActivity.resources.getQuantityString(R.plurals.number_devices, config.rooms[position].furnitures.size, config.rooms[position].furnitures.size)

    }

    override fun getItemCount(): Int {
        return config.rooms.size
    }

}