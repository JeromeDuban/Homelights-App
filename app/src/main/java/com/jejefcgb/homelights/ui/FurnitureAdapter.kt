package com.jejefcgb.homelights.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jejefcgb.homelights.HomeLightsApplication.Companion.config
import com.jejefcgb.homelights.R
import kotlinx.android.synthetic.main.furniture_item.view.*

class FurnitureAdapter internal constructor(val mActivity: Activity, val roomId:Int) : RecyclerView.Adapter<FurnitureAdapter.MainViewHolder>() {


    inner class MainViewHolder internal constructor(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {

        internal var mTitle: TextView
        internal var mIcon: ImageView

        init {
            v.setOnClickListener(this)
            mTitle = v.furniture_name
            mIcon = v.furniture_icon
        }

        override fun onClick(v: View) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            Toast.makeText(mActivity,config.rooms[roomId].furniture[adapterPosition].name,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.furniture_item, parent, false) as ConstraintLayout

        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val furniture = config.rooms[roomId].furniture[position]
        holder.mTitle.text = furniture.name
        holder.mIcon.setImageResource(mActivity.resources.getIdentifier(furniture.icon,"mipmap", mActivity.packageName))

    }

    override fun getItemCount(): Int {
        return config.rooms[roomId].furniture.size
    }

}