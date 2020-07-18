package com.example.tbc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tbc.R;
import com.example.tbc.fragment.FragmentAlert;
import com.example.tbc.fragment.FragmentHelp;
import com.example.tbc.fragment.FragmentTraining;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.MyAppContext;
import com.example.tbc.utils.RuntimePermissionsHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class VendorUtility extends AppCompatActivity implements View.OnClickListener {

    TextView training,help,alerts;
    NavigationView nv;
    ImageView navigation;
    public DrawerLayout f47dl;
    private ActionBarDrawerToggle f49t;
    public static VendorUtility instance;

    public static void setInstance(VendorUtility instance) {
        VendorUtility.instance = instance;
    }

    public static VendorUtility getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_utility);
        MyAppContext.setInstance("VendorUtility", this);
        uiIntialize();
        setInstance(this);
        askForCallPermissions();

    }

    public void uiIntialize() {
        training = findViewById(R.id.vendorUtility_training);
        alerts = findViewById(R.id.vendorUtility_alert);
        help = findViewById(R.id.vendorUtility_help);
        this.nv = (NavigationView) findViewById(R.id.nv);

        training.setOnClickListener(this);
        help.setOnClickListener(this);
        alerts.setOnClickListener(this);
        this.navigation = (ImageView) findViewById(R.id.navigation);
        this.f47dl = (DrawerLayout) findViewById(R.id.activity_main);
        this.f49t = new ActionBarDrawerToggle(this, this.f47dl, R.string.Open, R.string.Close);
        this.f49t.getDrawerArrowDrawable().setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f47dl.addDrawerListener(this.f49t);
        this.f49t.syncState();
        this.navigation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VendorUtility.this.f47dl.openDrawer((int) GravityCompat.START);
            }
        });
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.vendorlogout:
                        Toast.makeText(VendorUtility.this, "Logout", Toast.LENGTH_SHORT).show();
                        AppSharedPrefernce.getInstance().setIsVendorLogin(false);
                        VendorUtility.this.finish();
                        break;
                    case R.id.training:
                        loadFragment(new FragmentTraining());
                        f47dl.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.help:
                        loadFragment(new FragmentHelp());
                        f47dl.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.alerts:
                        loadFragment(new FragmentAlert());
                        f47dl.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vendorUtility_training:
                loadFragment(new FragmentTraining());
                 break;
            case R.id.vendorUtility_help:
                loadFragment(new FragmentHelp());
                 break;
            case R.id.vendorUtility_alert:
              //  watchYoutubeVideo("zouss3epKZk");
                loadFragment(new FragmentAlert());
                break;
        }
    }

    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    public void loadFragment(Fragment fragment) {
        LinearLayout linearLayout = findViewById(R.id.activity_vendor_selection_linlay);
        linearLayout.setVisibility(View.GONE);
        FrameLayout frameLayout = findViewById(R.id.activity_vendor_selection_frame);
        frameLayout.setVisibility(View.VISIBLE);
        new Bundle();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_vendor_selection_frame, fragment).commit();
    }


    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String permissions[],
                                             @NonNull int[] grantResults){

        switch (requestCode) {
            case RuntimePermissionsHelper.MY_PERMISSIONS_REQUEST_FOR_CALL:
                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // callNumber();


                } else if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);

                    if (!showRationale) {
                        // user also CHECKED "never ask again"
                        Snackbar.make(findViewById(android.R.id.content), "Enable call from settings",
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
                        Snackbar.make(findViewById(android.R.id.content), "Need call permission for proceed ",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                v -> {
                                    askForCallPermissions();
                                }).show();
                    }
                }
        }
    }
    public void askForCallPermissions() {
        int permissionsIndex = RuntimePermissionsHelper.MY_PERMISSIONS_REQUEST_FOR_CALL;
        if (RuntimePermissionsHelper.getInstance().verifySingleRuntimePermission(this, permissionsIndex)) {
            //  callNumber();
        } else {
            //logManager.logsForDebugging(LOG_TAG, "askForStoragePermissions denied ");
            // surveyorCard.setOnClickListener(null);

        }
    }
}
