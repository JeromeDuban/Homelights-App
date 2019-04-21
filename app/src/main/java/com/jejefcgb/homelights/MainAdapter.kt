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
import kotlinx.android.synthetic.main.furniture.view.*

class MainAdapter internal constructor(val mActivity: Activity) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    inner class MainViewHolder internal constructor(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {


        internal var mTitle: TextView
        internal var mIcon: ImageView
        internal var mBackground: View


        init {
            v.setOnClickListener(this)
            mTitle = v.card_title
            mIcon = v.card_icon
            mBackground = v.card_background
        }

        override fun onClick(v: View) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val p1 = androidx.core.util.Pair(mIcon as View, "transitionImage")
            val p3 = androidx.core.util.Pair(mBackground, "transitionBackground")

            val transitions = arrayOf(p1, p3)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, *transitions)

            val intent = Intent(mActivity, DetailsActivity::class.java)
            //intent.putExtra("EXTRA_ICON", config[adapterPosition].icon)
            intent.putExtra("EXTRA_TITLE", config.rooms[adapterPosition].name)
            mActivity.startActivity(intent, options.toBundle())

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MainAdapter.MainViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.furniture, parent, false) as ConstraintLayout

        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val s = config.rooms[position]
        holder.mTitle.text = s.name
        holder.mIcon.setImageResource(R.mipmap.ic_object_tv)

    }

    override fun getItemCount(): Int {
        return config.rooms.size
    }

}