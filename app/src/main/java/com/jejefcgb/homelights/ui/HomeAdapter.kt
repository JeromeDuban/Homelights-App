package com.jejefcgb.homelights.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jejefcgb.homelights.DetailsActivity
import com.jejefcgb.homelights.HomeLightsApplication.Companion.TYPE_ROOM
import com.jejefcgb.homelights.R
import com.jejefcgb.homelights.data.model.Furniture
import com.jejefcgb.homelights.data.model.Room
import com.jejefcgb.homelights.databinding.FurnitureItemBinding
import com.jejefcgb.homelights.databinding.RoomItemBinding


class HomeAdapter(val mActivity: Activity, var data: List<Any>, private val dataType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (dataType) {
                TYPE_ROOM -> RoomViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.room_item, parent, false))
                else -> FurnitureViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.furniture_item, parent, false))
            }


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            when (dataType) {
                TYPE_ROOM -> (holder as RoomViewHolder).bind(data, position)
                else -> (holder as FurnitureViewHolder).bind(data, position)
            }


    inner class FurnitureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: FurnitureItemBinding = DataBindingUtil.bind(view)!!

        fun bind(data: List<Any>, position: Int) {
            binding.furniture = data[position] as Furniture
            binding.furnitureIcon.setOnClickListener { Toast.makeText(mActivity, (data[position] as Furniture).name, Toast.LENGTH_SHORT) .show()} //TODO EXTRACT
        }
    }

    inner class RoomViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding: RoomItemBinding = DataBindingUtil.bind(view)!!

        fun bind(data: List<Any>, position: Int) {
            binding.room = data[position] as Room

            view.setOnClickListener { //TODO : Extract
                val p1 = androidx.core.util.Pair(binding.roomIcon as View, "transition_icon")

                var transitions = arrayOf(p1)
    //            transitions+= androidx.core.util.Pair(mTitle as View , "transition_title")
    //            transitions+= androidx.core.util.Pair(mBackground as View , "transition_background")

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, *transitions)

                // FIXME : transition
                // val intent = getDetailActivityStartIntent(mActivity, adapterPosition)

                val intent = Intent(mActivity, DetailsActivity::class.java)
                intent.putExtra("EXTRA_ID", (data[position] as Room).id)
                mActivity.startActivity(intent, options.toBundle()) }
        }

    }


}


