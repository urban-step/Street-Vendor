package com.example.tbc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tbc.R;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.utils.AppHelper;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.MyAppContext;
import com.example.tbc.utils.RuntimePermissionsHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.Locale;

public class SelectionActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    CardView vendorCard, lookingCard, surveyorCard;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private LocationManager locationManager;
    private String provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        MyAppContext.setInstance("SelectionActivity", this);
        uiIntialize();
        askForLocationPermissions();

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {

        }
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        SurveyFormData surveyFormData = new Gson().fromJson(AppSharedPrefernce.getInstance().getVendorData(), SurveyFormData.class);
        if(surveyFormData==null){
            surveyFormData = new SurveyFormData();
        }
        surveyFormData.setLocationlatitude(location.getLatitude() + "");
        surveyFormData.setLocationlongitude(location.getLongitude() + "");
        AppSharedPrefernce.getInstance().setVendorData(new Gson().toJson(surveyFormData));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }


    private void uiIntialize() {
        vendorCard = findViewById(R.id.selection_card_vendor);
        lookingCard = findViewById(R.id.selection_card_looking);
        surveyorCard = findViewById(R.id.selection_card_surveyor);
        vendorCard.setOnClickListener(this);
        lookingCard.setOnClickListener(this);

    }
        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String permissions[],
        @NonNull int[] grantResults){

            switch (requestCode) {
                case RuntimePermissionsHelper.MY_PERMISSIONS_REQUEST_FOR_ACCESS_FINE_LOCATION:
                    // If request is cancelled, the result arrays are empty.

                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        surveyorCard.setOnClickListener(this);


                    } else if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                        boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);

                        if (!showRationale) {
                            // user also CHECKED "never ask again"
                            Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                                            intent.setData(Uri.parse("package:" + getPackageName()));
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                            startActivity(intent);
                                        }
                                    }).show();

                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "Need storage permission for proceed ",
                                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                    v -> {
                                        askForLocationPermissions();
                                    }).show();
                        }
                    }
            }
        }
    private void askForLocationPermissions() {
        int permissionsIndex = RuntimePermissionsHelper.MY_PERMISSIONS_REQUEST_FOR_ACCESS_FINE_LOCATION;
        if (RuntimePermissionsHelper.getInstance().verifySingleRuntimePermission(SelectionActivity.this, permissionsIndex)) {
            surveyorCard.setOnClickListener(this);
        } else {
            //logManager.logsForDebugging(LOG_TAG, "askForStoragePermissions denied ");
            surveyorCard.setOnClickListener(null);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selection_card_surveyor:
                if (!AppSharedPrefernce.getInstance().getIsSurveyorLogin()) {
                    if(AppSharedPrefernce.getInstance().getIsVendorLogin() || AppSharedPrefernce.getInstance().getIsUserLogin()){
                        Toast.makeText(SelectionActivity.this,"Seems you are already loged in different account",Toast.LENGTH_LONG).show();
                        return;
                    }
                    startActivity(new Intent(SelectionActivity.this, LoginActivity.class));
                }else {
                    startActivity(new Intent(SelectionActivity.this, ServerForm.class));
                }
                break;
            case R.id.selection_card_vendor:
                if (!AppSharedPrefernce.getInstance().getIsVendorLogin()) {
                    if(AppSharedPrefernce.getInstance().getIsSurveyorLogin() || AppSharedPrefernce.getInstance().getIsUserLogin()){
                        Toast.makeText(SelectionActivity.this,"Seems you are already loged in different account",Toast.LENGTH_LONG).show();
                        return;
                    }
                    startActivity(new Intent(SelectionActivity.this, VendorLoginActivity.class));
                }else {
                    startActivity(new Intent(SelectionActivity.this, VendorUtility.class));
                }
                break;
            case R.id.selection_card_looking:
                if (!AppSharedPrefernce.getInstance().getIsUserLogin()) {
                    if(AppSharedPrefernce.getInstance().getIsSurveyorLogin() || AppSharedPrefernce.getInstance().getIsVendorLogin()){
                        Toast.makeText(SelectionActivity.this,"Seems you are already loged in different account",Toast.LENGTH_LONG).show();
                        return;
                    }
                    startActivity(new Intent(SelectionActivity.this, UserLoginActivity.class));
                }else {
                    startActivity(new Intent(SelectionActivity.this, UserHomeActivity.class));
                }
                break;
        }
    }

    private void updateLanguage() {

        Resources res = this.getResources();
        String langCode = AppHelper.getInstance().getAppLanguageCode();
        Log.d("Selection ", "langCode::" + langCode);

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        setContentView(R.layout.activity_selection);

       /* if (sharedPrefs.isLaunchFirstTime()) {
            presentLanguageShowcase();
            sharedPrefs.setIsLaunchFirstTime(false);
        }*/
    }
}