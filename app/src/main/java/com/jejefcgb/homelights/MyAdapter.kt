package com.jejefcgb.homelights

import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class MyAdapter internal constructor(private val mDataset: ArrayList<Server>, private val mCallback: Callback) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var selectedPos: MutableList<Int> = ArrayList()

    inner class MyViewHolder internal constructor(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {

        internal var mTitle: TextView
        internal var mIcon: ImageView


        init {
            v.setOnClickListener(this)
            mTitle = v.findViewById(R.id.card_title)
            mIcon = v.findViewById(R.id.card_icon)
        }


        override fun onClick(v: View) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            // Updating old as well as new positions
            //notifyItemChanged(selectedPos);
            if (selectedPos.contains(adapterPosition)) {
                selectedPos.remove(Integer.valueOf(adapterPosition))
            } else {
                selectedPos.add(adapterPosition)
            }
            mCallback.update(selectedPos)
            notifyDataSetChanged()
        }
    }

    fun getSelectedPos(): List<Int> {
        return selectedPos
    }

    fun resetSelectedPos() {
        this.selectedPos = ArrayList()
        mCallback.update(selectedPos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.furniture, parent, false) as ConstraintLayout

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.isSelected = selectedPos.contains(position)
        holder.itemView.setBackgroundColor(
                if (selectedPos.contains(position))
                    ContextCompat.getColor(holder.itemView.context, R.color.tile_selected)
                else
                    ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))

        val s = mDataset[position]
        holder.mTitle.text = s.name
        holder.mIcon.setImageResource(s.icon)

    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

}