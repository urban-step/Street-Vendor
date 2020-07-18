package com.example.tbc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tbc.R;
import com.example.tbc.adapter.VendorAdapter;
import com.example.tbc.model.VendorToUserModel;

import java.util.ArrayList;

public class ShowVendors extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<VendorToUserModel> vendorsList;
    TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vendors);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_listvendors);
        textView = findViewById(R.id.novendor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        vendorsList = (ArrayList<VendorToUserModel>) args.getSerializable("ARRAYLIST");
        //initializing the productlist

        //creating recyclerview adapter
        VendorAdapter adapter = new VendorAdapter(this, vendorsList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        if(vendorsList.size()==0){
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        else{
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
    }

}
