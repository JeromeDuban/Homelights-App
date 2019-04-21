package com.jejefcgb.homelights

import android.app.Activity

class DetailsAdapter internal constructor(
/*private val mDataset: ArrayList<Furniture>, private val mCallback: Callback, */private val mActivity: Activity) /*: RecyclerView.Adapter<DetailsAdapter.MainViewHolder>()*/ {

//    private var selectedPos: MutableList<Int> = ArrayList()
//
//    inner class MainViewHolder internal constructor(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {
//
//
//        internal var mTitle: TextView
//        internal var mIcon: ImageView
//        internal var mBackground: View
//
//
//        init {
//            v.setOnClickListener(this)
//            mTitle = v.card_title
//            mIcon = v.card_icon
//            mBackground = v.card_background
//        }
//
//
////        override fun onClick(v: View) {
////
////            if (adapterPosition == RecyclerView.NO_POSITION) return
////
////            // Updating old as well as new positions
////            //notifyItemChanged(selectedPos);
////            if (selectedPos.contains(adapterPosition)) {
////                selectedPos.remove(Integer.valueOf(adapterPosition))
////            } else {
////                selectedPos.add(adapterPosition)
////            }
////            mCallback.update(selectedPos)
////            notifyDataSetChanged()
////        }
//
//        override fun onClick(v: View) {
//
//            if (adapterPosition == RecyclerView.NO_POSITION) return
//
//            val p1 = androidx.core.util.Pair(mIcon as View, "transitionImage")
//            val p3 = androidx.core.util.Pair(mBackground, "transitionBackground")
//
//            val transitions = arrayOf(p1, p3)
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, *transitions)
//
//            val intent = Intent(mActivity, DetailsActivity::class.java)
//            intent.putExtra("EXTRA_ICON", mDataset[adapterPosition].icon)
//            intent.putExtra("EXTRA_TITLE", mDataset[adapterPosition].name)
//            mActivity.startActivity(intent, options.toBundle())
//
//        }
//    }
//
//    fun getSelectedPos(): List<Int> {
//        return selectedPos
//    }
//
//    fun resetSelectedPos() {
//        this.selectedPos = ArrayList()
//        mCallback.update(selectedPos)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup,
//                                    viewType: Int): DetailsAdapter.MainViewHolder {
//        val v = LayoutInflater.from(parent.context)
//                .inflate(R.layout.furniture, parent, false) as ConstraintLayout
//
//        return MainViewHolder(v)
//    }
//
//    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
//
//        holder.itemView.isSelected = selectedPos.contains(position)
//        holder.itemView.setBackgroundColor(
//                if (selectedPos.contains(position))
//                    ContextCompat.getColor(holder.itemView.context, R.color.tile_selected)
//                else
//                    ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))
//
//        val s = mDataset[position]
//        holder.mTitle.text = s.name
//        holder.mIcon.setImageResource(mActivity.resources.getIdentifier(s.icon,
//                "mipmap",
//                mActivity.packageName))
//
//    }
//
//    override fun getItemCount(): Int {
//        return mDataset.size
//    }

}