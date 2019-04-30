package com.jejefcgb.homelights.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.jejefcgb.homelights.DetailsActivity
import com.jejefcgb.homelights.HomeLightsApplication.Companion.config
import com.jejefcgb.homelights.R
import com.jejefcgb.homelights.utils.IntentUtil
import kotlinx.android.synthetic.main.room_item.view.*

class MainAdapter internal constructor(val mActivity: Activity) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    inner class MainViewHolder internal constructor(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {


        internal var mTitle: TextView
        internal var mDetails: TextView
        internal var mIcon: ImageView
        internal var mBackground: View
        internal var mSwitch : Switch


        init {
            v.setOnClickListener(this)
            mTitle = v.room_name
            mIcon = v.room_icon
            mDetails = v.room_details
            mBackground = v.room_background
            mSwitch = v.room_switch
        }

        override fun onClick(v: View) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            // Déclare toutes les transitions ici via leur transitionName
            // Elles doivent être précisées dans le fichier shared_main_details.xml ( déclaré dans un style)
            // En plus de ça, certaines données doivent être transmises à un CallBack déclaré dans la seconde activité.

            // Custom Views
            val p1 = Pair(mIcon as View, mIcon.transitionName)
            var transitions = arrayOf(p1)
            transitions+= Pair(mTitle as View , mTitle.transitionName)
            transitions+= Pair(mBackground, mBackground.transitionName)

            // System views
            val decorView = mActivity.window.decorView

            val statusBackground = decorView.findViewById(android.R.id.statusBarBackground) as View
            transitions += Pair(statusBackground, statusBackground.transitionName)

            val navBackground = decorView.findViewById(android.R.id.navigationBarBackground) as View
            transitions += Pair(navBackground, navBackground.transitionName)

            // Bundle
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, *transitions)

            val intent = getDetailActivityStartIntent(mActivity, adapterPosition)
            intent.putExtra("EXTRA_ICON", "ic_object_tv") //FIXME
            intent.putExtra("EXTRA_TITLE", config.rooms[adapterPosition].name)
            mActivity.startActivity(intent, options.toBundle())
        }

        @NonNull
        private fun getDetailActivityStartIntent(host: Activity,
                                                 position: Int): Intent {


            // Ajoute des informations sur le textview tel qu'il l'est actuellement
            val intent = Intent(host, DetailsActivity::class.java)
            intent.action = Intent.ACTION_VIEW
            intent.putExtra(IntentUtil.SELECTED_ITEM_POSITION, position)
            intent.putExtra(IntentUtil.FONT_SIZE, mTitle.textSize)
            intent.putExtra(IntentUtil.PADDING,
                    Rect(mTitle.paddingLeft,
                            mTitle.paddingTop,
                            mTitle.paddingRight,
                            mTitle.paddingBottom))
            intent.putExtra(IntentUtil.TEXT_COLOR, mTitle.currentTextColor)
            return intent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.room_item, parent, false) as ConstraintLayout

        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val room = config.rooms[position]
        holder.mTitle.text = room.name
        holder.mIcon.setImageResource(R.mipmap.ic_object_tv)
        holder.mDetails.text = mActivity.resources.getQuantityString(R.plurals.number_devices, config.rooms[position].furnitures.size, config.rooms[position].furnitures.size)
        holder.mSwitch.setOnCheckedChangeListener { _, isChecked ->  Toast.makeText(mActivity, isChecked.toString(), Toast.LENGTH_SHORT).show()}

    }

    override fun getItemCount(): Int {
        return config.rooms.size
    }

}