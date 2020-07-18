package com.example.tbc.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.UnicodeSet;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tbc.R;
import com.example.tbc.adapter.CommodityImageAdapter;
import com.example.tbc.fragment.FragmentShowVendors;
import com.example.tbc.model.VendorToUserModel;
import com.example.tbc.model.YoutubeVideoModel;
import com.example.tbc.utils.ApiClient;
import com.example.tbc.utils.AppHelper;
import com.example.tbc.utils.AppSharedPrefernce;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends AppCompatActivity {
    private static String TAG = UserHomeActivity.class.getSimpleName();
    private static UserHomeActivity instance;
    GridView gridView;
    String selectedItem;
    LinearLayout GridViewItems, BackSelectedItem;
    int backposition = -1;
    ArrayList<VendorToUserModel> vendorList = new ArrayList<>();
    String commodity = "";
    public String[] mTexts = {"Clothes", "fruits and vegetables", "Prepared food", "Mobile Accessories", "Electronics Appliances", "Jewellery", "Shoes",
            "Leather Items", "Tea and Coffee", "Juices", "Make-up", "Home Utility","Other"};

    public LatLng searchedLocation;

    NavigationView nv;
    ImageView navigation;
    public DrawerLayout f47dl;
    private ActionBarDrawerToggle f49t;

    private void setInstance(UserHomeActivity instance2) {
        instance = instance2;
    }

    public static UserHomeActivity getInstance() {
        return instance;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        setInstance(this);
        Button nextButton = findViewById(R.id.nextButton);
        String apiKey = AppSharedPrefernce.getInstance().DEVELOPER_KEY;
        this.nv = (NavigationView) findViewById(R.id.nv);
        gridView = findViewById(R.id.userHome_gridview);
        gridView.setAdapter(new CommodityImageAdapter(this));
        setGridViewHeightBasedOnChildren(gridView, 2);

        this.navigation = (ImageView) findViewById(R.id.navigation);
        this.f47dl = (DrawerLayout) findViewById(R.id.activity_main);
        this.f49t = new ActionBarDrawerToggle(this, this.f47dl, R.string.Open, R.string.Close);
      //  this.f49t.getDrawerArrowDrawable().setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f47dl.addDrawerListener(this.f49t);
        this.f49t.syncState();
        this.navigation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UserHomeActivity.this.f47dl.openDrawer((int) GravityCompat.START);
            }
        });
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.userlogout:
                        Toast.makeText(UserHomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        AppSharedPrefernce.getInstance().setIsUserLogin(false);
                        UserHomeActivity.this.finish();
                        break;
                    case R.id.userHome:
//                        Toast.makeText(UserHomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        callHome();
                        f47dl.closeDrawer(GravityCompat.START);
                        break;


                }
                return true;
            }
        });

        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setCountry("IN");    //country type
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getLatLng());
                searchedLocation = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!commodity.equalsIgnoreCase("")) {
                    generateNearBy(commodity);
                } else {
                    Toast.makeText(UserHomeActivity.this, "select commodity !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //startActivity(new Intent(UserHomeActivity.this, VendorUtility.class)));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                //     commodity = mTexts[position];
                selectedItem = parent.getItemAtPosition(position).toString();

                GridViewItems = (LinearLayout) view;

                GridViewItems.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.background_custom_radio_buttons_selected_state));


                BackSelectedItem = (LinearLayout) gridView.getChildAt(backposition);

                if (backposition != -1) {

                    BackSelectedItem.setSelected(false);

                    BackSelectedItem.getChildAt(0).setBackground(getResources().getDrawable(R.drawable.white_background));

                }
                if(backposition==position){
                    backposition = -1;
                    commodity="";
                }else{

                    commodity = mTexts[position];
                }
                backposition = position;


            }
        });

    }

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight() +10;

        float x = 1;
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

    private ArrayList<VendorToUserModel> generateNearBy(String commodity) {
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(UserHomeActivity.this,"check your internet connection!",Toast.LENGTH_LONG).show();
            return null;
        }
        ApiClient.getApiService().getnearby(commodity).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("generateNearBy api : ", response.body().toString());
                if (response.isSuccessful()) {
                    if (response.body().has("data")) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body().toString());
                            Type type = new TypeToken<ArrayList<VendorToUserModel>>() {}.getType();
                            vendorList.clear();
                            vendorList = new Gson().fromJson(response.body().get("data"), type);                           // adapter.notifyDataSetChanged();
                            //recyclerView.notifyAll();
                            ArrayList<VendorToUserModel> list;
                            if(searchedLocation!=null) {
                                list = generateList(searchedLocation, vendorList);
                                Intent intent = new Intent(UserHomeActivity.this, ShowVendors.class);
                                Bundle args = new Bundle();
                                args.putSerializable("ARRAYLIST",(Serializable)list);
                                intent.putExtra("BUNDLE",args);
                                loadFragment(new FragmentShowVendors(),args);
                            }else{
                                Intent intent = new Intent(UserHomeActivity.this, ShowVendors.class);
                                Bundle args = new Bundle();
                                args.putSerializable("ARRAYLIST",(Serializable)vendorList);
                                intent.putExtra("BUNDLE",args);
                                loadFragment(new FragmentShowVendors(),args);
                                // Toast.makeText(UserHomeActivity.this, "please set location", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(UserHomeActivity.this, "something went wrong ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserHomeActivity.this, "something went wrong ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(UserHomeActivity.this, "something went wrong ", Toast.LENGTH_SHORT).show();

            }
        });


        //loop through all items and add them to arraylist


        return vendorList;
    }

    public ArrayList<VendorToUserModel> generateList(LatLng location,ArrayList<VendorToUserModel> vendorList) {
        ArrayList<VendorToUserModel> generatedList= new ArrayList<>();
        for(VendorToUserModel model:vendorList) {
            if(model.getLat()!=null && !model.getLat().equalsIgnoreCase("")){
                double dist = distance(location.latitude, location.longitude, Double.valueOf(model.getLat()), Double.valueOf(model.getLng()));
                if (dist < 5) { // if distance < 0.1 miles we take locations as equal
                    //do what you want to do...
                    System.out.println("okay " + dist);
                    generatedList.add(model);
                } else System.out.println("not okay " + dist);
            }
        }
        return generatedList;
    }

    /**
     * calculates the distance between two locations in MILES
     */
    private static double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist = earthRadius * c;

        return dist * 1.60934; // output distance, in MILES
    }

    public void loadFragment(Fragment frag,Bundle args){
        LinearLayout linearLayout = findViewById(R.id.activity_home_user_linlay);
        linearLayout.setVisibility(View.GONE);
        FrameLayout frameLayout = findViewById(R.id.frame);
        frameLayout.setVisibility(View.VISIBLE);
        frag.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, frag).commit();
    }

    public void loadFragmentbackstack(Fragment frag,Bundle args){
        frag.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, frag).addToBackStack(null).commit();
    }

    public void callHome(){
        LinearLayout linearLayout = findViewById(R.id.activity_home_user_linlay);
        linearLayout.setVisibility(View.VISIBLE);
        FrameLayout frameLayout = findViewById(R.id.frame);
        frameLayout.setVisibility(View.GONE);
    }
}
