package com.example.tbc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.tbc.R;
import com.example.tbc.model.AlertModel;

import java.util.ArrayList;

public class AlertAdapter extends RecyclerView.Adapter<AlertViewHolder> {
    private static final String TAG = YoutubeVideoAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<AlertModel> alertModelArrayList;


    public AlertAdapter(Context context, ArrayList<AlertModel> youtubeVideoModelArrayList) {
        this.context = context;
        this.alertModelArrayList = youtubeVideoModelArrayList;
    }

    @Override
    public AlertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.alert_custom_layout, parent, false);
        return new AlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlertViewHolder holder, final int position) {

        final AlertModel alertModel = alertModelArrayList.get(position);

       // holder.videoTitle.setText(alertModel.getTitle());

        Glide.with(context).load(alertModel.getImage())
                .placeholder(R.drawable.placeholder)
                .disallowHardwareConfig()
                .priority(Priority.NORMAL)
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return alertModelArrayList != null ? alertModelArrayList.size() : 0;
    }
}

class AlertViewHolder extends RecyclerView.ViewHolder {

   public ImageView imageView;
   public TextView videoTitle;

   public AlertViewHolder(View itemView) {
       super(itemView);
       imageView = itemView.findViewById(R.id.alert_thumbnail_image_view);
       videoTitle = itemView.findViewById(R.id.alert_title_label);
   }
}