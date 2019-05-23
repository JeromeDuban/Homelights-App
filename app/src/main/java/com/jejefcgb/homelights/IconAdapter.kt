package com.jejefcgb.homelights

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class IconAdapter(c: Context) : BaseAdapter() {
    private val mContext: Context = c

    private val mThumbIds = arrayOf(
            R.mipmap.ic_object_bar,
            R.mipmap.ic_object_bed,
            R.mipmap.ic_object_desk,
            R.mipmap.ic_object_glasses,
            R.mipmap.ic_object_shelf,
            R.mipmap.ic_object_tv
            )


    override fun getCount(): Int {
        return mThumbIds.size
    }

    override fun getItem(position: Int): Any? {
        return mThumbIds[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(mContext)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }

        imageView.setImageResource(mThumbIds[position])
        return imageView
    }
}

