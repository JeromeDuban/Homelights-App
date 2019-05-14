package com.jejefcgb.homelights.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jejefcgb.homelights.HomeLightsApplication.Companion.TYPE_ROOM
import com.jejefcgb.homelights.R
import com.jejefcgb.homelights.data.model.Furniture
import com.jejefcgb.homelights.data.model.Room
import com.jejefcgb.homelights.databinding.FurnitureItemBinding
import com.jejefcgb.homelights.databinding.RoomItemBinding


class FurnitureAdapter(val mActivity: Activity, val data: List<Any>, val dataType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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
            binding.furnitureIcon.setOnClickListener { Toast.makeText(mActivity, (data[position] as Furniture).name, Toast.LENGTH_SHORT) .show()}
        }
    }

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: RoomItemBinding = DataBindingUtil.bind(view)!!

        fun bind(data: List<Any>, position: Int) {
            binding.room = data[position] as Room
        }
    }


}


