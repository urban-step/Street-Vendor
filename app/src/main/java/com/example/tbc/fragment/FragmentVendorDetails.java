package com.example.tbc.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.tbc.R;
import com.example.tbc.activity.UserHomeActivity;
import com.example.tbc.activity.VendorDetailPage;
import com.example.tbc.adapter.VendorAdapter;
import com.example.tbc.model.VendorToUserModel;
import com.example.tbc.utils.ApiClient;
import com.example.tbc.utils.AppHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentVendorDetails extends Fragment implements OnMapReadyCallback {
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView textViewTitle, textViewShortDesc, textViewRating, textViewPayment,textViewTime,license;
    ImageView imageView;
    VendorToUserModel vendorModel;
    GoogleMap mMap;
    ScrollView mScrollView;
    private static final int REQUEST_CODE = 101;
    EditText comment;
    RatingBar bar;
    Button submit;
    Map<String, String> hashmap= new HashMap<>();
    AlertDialog.Builder builder;


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
        return inflater.inflate(R.layout.activity_vendor_detail_page, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewTitle = view.findViewById(R.id.vendorcard_name);
        textViewShortDesc = view.findViewById(R.id.vendorcard_location);
        textViewRating = view.findViewById(R.id.vendorcard_rating);
        textViewPayment = view.findViewById(R.id.vendorcard_payment);
        textViewTime = view.findViewById(R.id.vendorcard_time);
        imageView = view.findViewById(R.id.vendorcard_image);
        license = view.findViewById(R.id.vendorcard_licenseno);
        comment = view.findViewById(R.id.comment);
        bar = view.findViewById(R.id.rating);
        bar.setRating(1);
//        bar.setMin(1);
        //     bar.setMax(5);
        submit = view.findViewById(R.id.submitrating);
        builder = new AlertDialog.Builder(getContext());

        vendorModel = (VendorToUserModel) getArguments().getSerializable("vendor");
        Geocoder geocoder;
        List<Address> addresses= new ArrayList<>();
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Double.valueOf(vendorModel.getLat()), Double.valueOf(vendorModel.getLng()), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textViewTitle.setText(vendorModel.getVendor_name());
        textViewShortDesc.setText(addresses.get(0).getAddressLine(0));
        textViewTime.setText(vendorModel.getTiming_of_vendor());
        if(vendorModel.getRating().equalsIgnoreCase("")){
            textViewRating.setText("0");
        }else
            textViewRating.setText(String.valueOf(vendorModel.getRating()));
        textViewPayment.setText(String.valueOf(vendorModel.getPayment_option()));
        license.setText(vendorModel.getLicense_number());

        //  imageView.setImageDrawable(mCtx.getResources().getDrawable(vendorModel.getImage()));
        Glide.with(this).load(vendorModel.getVendor_img())
                .placeholder(R.drawable.placeholder)
                .disallowHardwareConfig()
                .priority(Priority.NORMAL)
                .into(imageView);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        fetchLocation();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hashmap.put("vendor_id",vendorModel.getId());
                hashmap.put("rating",bar.getRating()+"");
                hashmap.put("coment",comment.getText().toString());
                if(!AppHelper.getInstance().isInternetOn()){
                    Toast.makeText(getContext(),"check your internet connection",Toast.LENGTH_LONG).show();
                    return;
                }
                ApiClient.getApiService().submitRating(hashmap).enqueue(new Callback<JsonObject>() {
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.e("API",response.toString());
                        if(response.isSuccessful()){
                            Log.e("API",response.body().toString());
                            if(response.body().has("status") && response.body().get("status").getAsInt()==1) {
                                builder.setMessage(response.body().get("response").getAsString());

                                //Setting message manually and performing action on button click
                                builder.setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                comment.setText("");
                                                bar.setRating(1);
                                                dialog.dismiss();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                //Setting the title manually
                                alert.show();

                            }

                        }else{
                            Toast.makeText(getContext(),"Seems your internet is slow ",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("API",t.getMessage().toString());

                        Toast.makeText(getContext(),"Seems your internet is slow",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                   /* SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(VendorDetailPage.this);*/
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        //  MarkerOptions vendorOption = new MarkerOptions().
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(markerOptions);
    }
}
