package com.jejefcgb.homelights.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.jejefcgb.homelights.DetailsActivity
import com.jejefcgb.homelights.HomeLightsApplication.Companion.config
import com.jejefcgb.homelights.R
import kotlinx.android.synthetic.main.room_item.view.*

class RoomAdapter internal constructor(val mActivity: Activity) : RecyclerView.Adapter<RoomAdapter.MainViewHolder>() {


    inner class MainViewHolder internal constructor(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {

        internal var mTitle: TextView
        internal var mDetails: TextView
        internal var mIcon: ImageView
        internal var mBackground: View
        internal var mButton : ImageButton


        init {
            v.setOnClickListener(this)
            mTitle = v.room_name
            mIcon = v.room_icon
            mDetails = v.room_details
            mBackground = v.room_background
            mButton = v.room_button
        }

        override fun onClick(v: View) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val p1 = androidx.core.util.Pair(mIcon as View, "transition_icon")

            var transitions = arrayOf(p1)
//            transitions+= androidx.core.util.Pair(mTitle as View , "transition_title")
//            transitions+= androidx.core.util.Pair(mBackground as View , "transition_background")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, *transitions)

            // FIXME : transition
            // val intent = getDetailActivityStartIntent(mActivity, adapterPosition)

            val intent = Intent(mActivity, DetailsActivity::class.java)
            intent.putExtra("EXTRA_ID", config.rooms[adapterPosition].id)
            mActivity.startActivity(intent, options.toBundle())
        }

        //FIXME : Tranition
//        @NonNull
//        private fun getDetailActivityStartIntent(host: Activity,
//                                                 position: Int): Intent {
//            val intent = Intent(host, DetailsActivity::class.java)
//            intent.action = Intent.ACTION_VIEW
//            intent.putExtra(IntentUtil.SELECTED_ITEM_POSITION, position)
//            intent.putExtra(IntentUtil.FONT_SIZE, mTitle.textSize)
//            intent.putExtra(IntentUtil.PADDING,
//                    Rect(mTitle.paddingLeft,
//                            mTitle.paddingTop,
//                            mTitle.paddingRight,
//                            mTitle.paddingBottom))
//            intent.putExtra(IntentUtil.TEXT_COLOR, mTitle.currentTextColor)
//            return intent
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.room_item, parent, false) as ConstraintLayout

//        val v = ListItemBinding

        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val room = config.rooms[position]
        holder.mTitle.text = room.name
        holder.mIcon.setImageResource(mActivity.resources.getIdentifier(room.icon,"mipmap", mActivity.packageName))
        holder.mDetails.text = mActivity.resources.getQuantityString(R.plurals.number_devices, config.rooms[position].furniture.size, config.rooms[position].furniture.size)


    }

    override fun getItemCount(): Int {
        return config.rooms.size
    }

}