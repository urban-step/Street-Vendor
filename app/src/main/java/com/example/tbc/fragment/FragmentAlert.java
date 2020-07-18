package com.example.tbc.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tbc.R;
import com.example.tbc.activity.AlertDetailActivity;
import com.example.tbc.adapter.AlertAdapter;
import com.example.tbc.model.AlertModel;
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

public class FragmentAlert extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<AlertModel> alertModelList = new ArrayList<>();
    AlertAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_vendor_alert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_alert);
        setUpRecyclerView();
        populateRecyclerView();
    }
    /**
     * setup the recyclerview here
     */
    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * populate the recyclerview and implement the click event here
     */
    private void populateRecyclerView() {
        final ArrayList<AlertModel> alertModelArrayList = generateDummyAlertList();
        adapter = new AlertAdapter(getContext(), alertModelArrayList);
        recyclerView.setAdapter(adapter);

        //set click event
        recyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(getContext(), new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //start youtube player activity by passing selected video id via intent
                startActivity(new Intent(getContext(), AlertDetailActivity.class)
                        .putExtra("alert", alertModelArrayList.get(position)));

            }
        }));
    }

    private ArrayList<AlertModel> generateDummyAlertList() {
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(getContext(),"check your internet connection!",Toast.LENGTH_LONG).show();
            return null;
        }
        ApiClient.getApiService().getVendorAlertDetails(0).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("training api : ",response.body().toString());
                if(response.isSuccessful()){
                    if(response.body().has("REAL_ESTATE_APP")){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("REAL_ESTATE_APP");
                            Type type = new TypeToken<ArrayList<AlertModel>>(){}.getType();
                            alertModelList.clear();
                            alertModelList.addAll((new Gson()).fromJson(jsonArray.toString(), type));
                            adapter.notifyDataSetChanged();
                            //recyclerView.notifyAll();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else Toast.makeText(getContext(), "something went wrong ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "something went wrong ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong ", Toast.LENGTH_SHORT).show();

            }
        });


        //loop through all items and add them to arraylist
        return alertModelList;
    }
}
