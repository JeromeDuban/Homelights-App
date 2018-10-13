package com.jejefcgb.homelights;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Callback mCallback;
    private ArrayList<Server> mDataset;
    private List<Integer> selectedPos = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle;
        ImageView mIcon;


        MyViewHolder(ConstraintLayout v) {
            super(v);
            v.setOnClickListener(this);
            mTitle = v.findViewById(R.id.card_title);
            mIcon = v.findViewById(R.id.card_icon);
        }


        @Override
        public void onClick(View v) {

            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            //notifyItemChanged(selectedPos);
            if (selectedPos.contains(getAdapterPosition())) {
                selectedPos.remove(Integer.valueOf(getAdapterPosition()));
            } else {
                selectedPos.add(getAdapterPosition());
            }
            mCallback.update(selectedPos);
            notifyDataSetChanged();
        }
    }

    MyAdapter(ArrayList<Server> myDataset, Callback cb) {
        mDataset = myDataset;
        mCallback = cb;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.furniture, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemView.setSelected(selectedPos.contains(position));
        holder.itemView.setBackgroundColor(
                selectedPos.contains(position)
                        ? ContextCompat.getColor(holder.itemView.getContext(), R.color.tile_selected)
                        : ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));

        Server s = mDataset.get(position);
        holder.mTitle.setText(s.getName());
        holder.mIcon.setImageResource(s.getIcon());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}