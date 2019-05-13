package com.jejefcgb.homelights.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jejefcgb.homelights.HomeLightsApplication.Companion.config
import com.jejefcgb.homelights.R
import com.jejefcgb.homelights.data.model.Furniture
import com.jejefcgb.homelights.databinding.FurnitureItemBinding


class FurnitureAdapter (val mActivity: Activity, val roomId: Int) : RecyclerView.Adapter<FurnitureAdapter.FurnitureViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {

        return FurnitureViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.furniture_item,parent,false))
    }

    override fun getItemCount(): Int = config.rooms[roomId].furniture.size

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) = holder.bind(config.rooms[roomId].furniture[position]) //FIXME : list of data


    inner class FurnitureViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val binding: FurnitureItemBinding = DataBindingUtil.bind(view)!!

        fun bind(furniture : Furniture) {
            binding.furniture = furniture
        }
    }
}

