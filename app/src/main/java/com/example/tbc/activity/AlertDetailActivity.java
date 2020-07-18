package com.example.tbc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.tbc.R;
import com.example.tbc.adapter.AlertDetailAdapter;
import com.example.tbc.adapter.YoutubeVideoAdapter;
import com.example.tbc.model.AlertModel;
import com.example.tbc.model.YoutubeVideoModel;

import java.util.ArrayList;

public class AlertDetailActivity extends AppCompatActivity {

    AlertModel alertModel;
    ImageView imageView;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_detail);
        alertModel = (AlertModel) getIntent().getSerializableExtra("alert");
        imageView = findViewById(R.id.alert_detail_image);
        Glide.with(getApplicationContext()).load(alertModel.getImage())
                .placeholder(R.drawable.placeholder)
                .disallowHardwareConfig()
                .priority(Priority.NORMAL)
                .into(imageView);
        setUpRecyclerView();
        populateRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_alert_details);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * populate the recyclerview and implement the click event here
     */
    private void populateRecyclerView() {
        AlertDetailAdapter adapter = new AlertDetailAdapter(alertModel.getTitle().split(":::"),alertModel.getDescription().split(":::"));
        recyclerView.setAdapter(adapter);

    }
}
