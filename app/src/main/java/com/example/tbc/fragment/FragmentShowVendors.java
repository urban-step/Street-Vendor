package com.example.tbc.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tbc.R;
import com.example.tbc.activity.UserHomeActivity;
import com.example.tbc.activity.YouTubePlayerActivity;
import com.example.tbc.adapter.VendorAdapter;
import com.example.tbc.adapter.YoutubeVideoAdapter;
import com.example.tbc.model.VendorToUserModel;
import com.example.tbc.model.YoutubeVideoModel;
import com.example.tbc.utils.ApiClient;
import com.example.tbc.utils.AppHelper;
import com.example.tbc.utils.RecyclerViewOnClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentShowVendors extends Fragment {

    RecyclerView recyclerView;
    ArrayList<VendorToUserModel> vendorsList;
    TextView textView ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_show_vendors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_listvendors);
        textView = view.findViewById(R.id.novendor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Intent intent = new Intent(getActivity(), UserHomeActivity.class);
        vendorsList = (ArrayList<VendorToUserModel>) getArguments().getSerializable("ARRAYLIST");
        //initializing the productlist

        //creating recyclerview adapter
        VendorAdapter adapter = new VendorAdapter(getContext(), vendorsList);

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
