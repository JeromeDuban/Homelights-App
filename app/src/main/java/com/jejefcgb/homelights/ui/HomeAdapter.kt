package com.jejefcgb.homelights.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jejefcgb.homelights.HomeLightsApplication.Companion.TYPE_ROOM
import com.jejefcgb.homelights.RoomItemListener
import com.jejefcgb.homelights.data.model.Furniture
import com.jejefcgb.homelights.data.model.Room
import com.jejefcgb.homelights.databinding.FurnitureItemBinding
import com.jejefcgb.homelights.databinding.RoomItemBinding


class HomeAdapter(val mActivity: Activity, var data: List<Any>, private val dataType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (dataType) {
                TYPE_ROOM -> RoomViewHolder(LayoutInflater.from(mActivity).inflate(com.jejefcgb.homelights.R.layout.room_item, parent, false) )
                else -> FurnitureViewHolder(LayoutInflater.from(mActivity).inflate(com.jejefcgb.homelights.R.layout.furniture_item, parent, false))
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
            val furniture : Furniture = data[position] as Furniture
            // Binding
            binding.furniture = furniture
            // Listeners
            binding.furnitureIcon.setOnClickListener { Toast.makeText(mActivity, furniture.name, Toast.LENGTH_SHORT) .show()}
        }
    }

    inner class RoomViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding: RoomItemBinding = DataBindingUtil.bind(view)!!

        fun bind(data: List<Any>, position: Int) {
            val room : Room = data[position] as Room
            // Binding
            binding.room = room
            // Listeners
            binding.roomButton.setOnClickListener{Toast.makeText(mActivity, "LIGHT", Toast.LENGTH_SHORT).show()}
            view.setOnClickListener (RoomItemListener(mActivity, binding.roomIcon, room))

        }

    }

}


