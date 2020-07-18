package com.example.tbc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tbc.R;

public class AlertDetailAdapter extends RecyclerView.Adapter<AlertDetailAdapter.MyViewHolder> {
        private String[] mDataset,description;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView textViewtitle,textViewdescription;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewtitle = itemView.findViewById(R.id.alert_detail_title);
                textViewdescription = itemView.findViewById(R.id.alert_detail_description);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public AlertDetailAdapter(String[] title,String[] description) {
            mDataset = title;
            this.description = description;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.alert_details_custom_layout, parent, false);
            return new MyViewHolder(view);
        }
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textViewtitle.setText(mDataset[position]);
            holder.textViewdescription.setText(description[position]);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
