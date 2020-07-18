package com.example.tbc.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.tbc.R;
import com.example.tbc.fragment.FormFragment1;
import com.example.tbc.fragment.FormFragment2;
import com.example.tbc.fragment.FormFragment3;
import com.example.tbc.fragment.FormFragment4;
import com.example.tbc.fragment.FormFragment5;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.model.VendorData;
import com.example.tbc.utils.ApiClient;
import com.example.tbc.utils.AppHelper;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.GpsUtils;
import com.example.tbc.utils.MyAppContext;
import com.example.tbc.utils.RuntimePermissionsHelper;
import com.example.tbc.utils.SelectImageDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerForm extends AppCompatActivity implements FormFragment1.OpenF1, FormFragment2.openF3, FormFragment3.OpenF4, FormFragment4.OpenF5, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int SELECT_PICTURE = 100;
    private static ServerForm instance;
    /* access modifiers changed from: private */
    public static int todayCash;
    /* access modifiers changed from: private */
    public static int todayVendor;
    /* access modifiers changed from: private */
    public static int totalCash;
    private int TAKE_PIC = 1;
    Button btNext;
    private Uri cropImageUri;
    /* access modifiers changed from: private */

    /* renamed from: dl */
    public DrawerLayout f47dl;
    private String fileUri = "";
    ImageView navigation;
    /* access modifiers changed from: private */

    /* renamed from: nv */
    public NavigationView f48nv;
    public Uri outPutfileUri;
    SurveyFormData surveyFormData = new SurveyFormData();

    /* renamed from: t */
    private ActionBarDrawerToggle f49t;

    public String registrationNo="";
    String number="";
    public String konsiPic="";
    public ProgressDialog progressDialog;


    private void setInstance(ServerForm instance2) {
        instance = instance2;
    }

    public static ServerForm getInstance() {
        return instance;
    }


    private FusedLocationProviderClient mFusedLocationClient;

    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private StringBuilder stringBuilder;
    private TextView locationOfVending;
    private boolean isContinue = false;
    public boolean isGPS = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_server_form);
        setInstance(this);
        MyAppContext.setInstance("SelectionActivity", this);
        hideKeyboard(this);
        this.f47dl = (DrawerLayout) findViewById(R.id.activity_main);
        this.navigation = (ImageView) findViewById(R.id.navigation);
        this.f49t = new ActionBarDrawerToggle(this, this.f47dl, R.string.Open, R.string.Close);
        this.f49t.getDrawerArrowDrawable().setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f47dl.addDrawerListener(this.f49t);
        this.f49t.syncState();
        this.navigation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ServerForm.this.f47dl.openDrawer((int) GravityCompat.START);
            }
        });
        getVendorDetails();
        loadFragment1(new FormFragment1());
        this.f48nv = (NavigationView) findViewById(R.id.nv);
        this.f48nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.edit_profile) {
                    Toast.makeText(ServerForm.this, "edit profile", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.logout) {
                    Toast.makeText(ServerForm.this, "Logout", Toast.LENGTH_SHORT).show();
                    AppSharedPrefernce.getInstance().setIsSurveyorLogin(false);
                    ServerForm.this.finish();
                } else if (id != R.id.my_profile) {
                    return true;
                } else {
                    Toast.makeText(ServerForm.this, "my profile", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGpsLocation();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.f49t.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        new Bundle();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
    }

    /* access modifiers changed from: private */
    public void loadFragment1(Fragment fragment) {
        new Bundle();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    public void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void openFragment2() {
        loadFragment(new FormFragment2());
    }

    public void callSubmitApi() {
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(ServerForm.this,"check your internet connection",Toast.LENGTH_LONG).show();
            return;
        }
        this.surveyFormData = new Gson().fromJson(AppSharedPrefernce.getInstance().getVendorData(), SurveyFormData.class);

        Map<String, String> map = new HashMap<>();
        map.put("adhar_card", surveyFormData.getAdharCard());
        map.put("vendor_name", surveyFormData.getVendorName());
        map.put("house_number", surveyFormData.getHomeNumber());
        map.put("locality", surveyFormData.getLocality());
        map.put("ward_zone", surveyFormData.getWardZone());
        map.put("state", surveyFormData.getState());
        map.put("city", surveyFormData.getCity());
        map.put("pin_code", surveyFormData.getPinCode());
        map.put("mobile_number", surveyFormData.getMobileNumber());
        map.put("age", surveyFormData.getAge());
        map.put("v_landmark", surveyFormData.getVendorHomeNumber());
        map.put("v_locality", surveyFormData.getVendorLocality());
        map.put("locationlatitude", String.valueOf(wayLatitude));
        map.put("locationlongitude", String.valueOf(wayLongitude));
        map.put("v_ward_zone", surveyFormData.getVendorWardZone());
        map.put("v_state", surveyFormData.getVendorState());
        map.put("v_city", surveyFormData.getVendorCity());
        map.put("v_pin_code", surveyFormData.getVendorPinCode());
        map.put("type_commodity", surveyFormData.getTypeCommodity());
        map.put("nature_of_vending", surveyFormData.getNatureOfVending());
        map.put("timing", surveyFormData.getTiming());
        map.put("license", surveyFormData.getLicense());
        map.put("license_number", surveyFormData.getLicenseNumber());
        map.put("daily_income", surveyFormData.getDailyIncome());
        map.put("abled", surveyFormData.getAbled());
        map.put("qualification", surveyFormData.getQualification());
        map.put("family_earning", surveyFormData.getFamilyEarning());
        map.put("date_of_vending",System.currentTimeMillis()+"");
        map.put("any_three", surveyFormData.getIdProof());
        map.put("bank_account", surveyFormData.getBankAccount());
        map.put("bank_name", surveyFormData.getBankName());
        map.put("branch_name", surveyFormData.getBranchName());
        map.put("govt_scheme", surveyFormData.getGovtScheme());
        map.put("name_nominee", surveyFormData.getNameNominee());
        map.put("nomiee_relationship", surveyFormData.getNomineeRelationship());
        map.put("nomiee_age", surveyFormData.getNomineeAge());
        map.put("adhar_card_nominee", surveyFormData.getAdharCardNominee());
        map.put("mobile_number_nominee", surveyFormData.getMobileNumberNominee());
        map.put("file", AppSharedPrefernce.getInstance().getPersistenceKeyVendorDpFile());
        map.put("payment_option", surveyFormData.getPaymentOption());
        map.put("registration_payment", surveyFormData.getRegistrationPayment());
        map.put("amount", surveyFormData.getAmount());
        map.put("surveyor", AppSharedPrefernce.getInstance().getPersistenceKeyPreSurveyorId()+"");
        map.put("file_callan",surveyFormData.getFile_callan());
        map.put("file_id",surveyFormData.getFile_id());
        map.put("other_commodity_option",surveyFormData.getOther_commodity_option());
        map.put("six_am",surveyFormData.getSix_am());
        map.put("nine_am",surveyFormData.getNine_am());
        map.put("twell_pm",surveyFormData.getTwell_pm());
        map.put("three_pm",surveyFormData.getThree_pm());
        map.put("six_pm",surveyFormData.getSix_pm());
        map.put("file_stall",AppSharedPrefernce.getInstance().getPersistenceKeyVendorStallFile());
        map.put("years_vending",surveyFormData.getYears_vending());
        map.put("reads_sec",surveyFormData.getReads_sec());
        map.put("file_license",surveyFormData.getFile_license());
        map.put("callan_number",surveyFormData.getCallan_number());
        map.put("other_qualification",surveyFormData.getOther_qualification());
        map.put("scheme",surveyFormData.getScheme());
        map.put("other_scheme_name",surveyFormData.getOther_scheme_name());
        map.put("types_of_abled",surveyFormData.getTypes_of_abled());

        Date d1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        map.put("c_date",sdf.format(d1));
        map.put("status", "1");

        Log.d("request :", map.toString()+" date :"+map.get("date_of_vending")+" file "+map.get("file"));
        ApiClient.getApiService().addSurvey(map).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                StringBuilder sb = new StringBuilder();
                sb.append(call.request());
                String str = "";
                sb.append(str);
                String str2 = "getVendorDetails : ";
                Log.e(str2, sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(response.body());
                sb2.append(str);
                Log.e(str2, sb2.toString());
                if (response.isSuccessful()) {
                    JsonObject jsonObject = (JsonObject) response.body();
                    String str3 = NotificationCompat.CATEGORY_STATUS;
                    if (!jsonObject.has(str3) || !((JsonObject) response.body()).get(str3).getAsString().equalsIgnoreCase("1")) {
                        Toast.makeText(ServerForm.this, "Seems your internet is slow ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    registrationNo = jsonObject.get("registration_no").getAsString();
                    number = surveyFormData.getMobileNumber();
                    Toast.makeText(ServerForm.this, "Congratulations!! Form submitted successfully", Toast.LENGTH_LONG).show();
                    ServerForm serverForm = ServerForm.this;
                    serverForm.hideKeyboard(serverForm);
                    ServerForm.this.clearBackStack();
                    ServerForm.this.loadFragment1(new FormFragment5());
                    ServerForm.this.getVendorDetails();
                    RequestTask requestTask = new RequestTask();
                    requestTask.execute();
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                StringBuilder sb = new StringBuilder();
                sb.append("request: ");
                sb.append(call.request());
                sb.append("response: ");
                sb.append(t.getMessage());
                sb.append("");
                Log.e("getVendorDetails : ", sb.toString());
                Toast.makeText(ServerForm.this, "Seems your internet is slow ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == TAKE_PIC && resultCode == RESULT_OK) {
            if (null != outPutfileUri) {
                crop_Method(outPutfileUri);
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    crop_Method(selectedImageUri);
                }
            }

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                cropImageUri = resultUri;
                upload(cropImageUri,konsiPic);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            }
        }
    }

    private void crop_Method(Uri imageUri) {
        CropImage.activity(imageUri).setAspectRatio(4, 5).start(this);
    }

    public void upload(Uri cropImageUri2,String konsiPic) {

        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(ServerForm.this,"check your internet connection! upload again",Toast.LENGTH_LONG).show();
            return;
        }
        String str = konsiPic;
        String str2 = "profile.png";
        ApiClient.getApiService().UploadFile(MultipartBody.Part.createFormData(str, str2, RequestBody.create(MediaType.parse("multipart/form-data"), new File(cropImageUri2.getPath()))), Integer.parseInt(AppSharedPrefernce.getInstance().getPersistenceKeyPreSurveyorId())).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UploadFile - ");
                    sb.append(response.body());
                    Log.d("server ", sb.toString());
                    String str = "url";
                    if (!((JsonObject) response.body()).has(str) || ((JsonObject) response.body()).get(str).getAsString().equalsIgnoreCase("")) {
                        Toast.makeText(ServerForm.this, "seems your internet is slow", Toast.LENGTH_LONG).show();
                        FormFragment4.getInstance().updateSelectedImage(konsiPic,cropImageUri2,"");
                        return;
                    }

                    Toast.makeText(ServerForm.this, "Image uploaded succesfully", Toast.LENGTH_LONG).show();
                    FormFragment4.getInstance().updateSelectedImage(konsiPic,cropImageUri2,response.body().get(str).getAsString());

                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                StringBuilder sb = new StringBuilder();
                sb.append("UploadFile -err ");
                sb.append(t.getMessage());
                Log.d("server ", sb.toString());
                //FormFragment4.getInstance().updateSelectedImage(konsiPic,cropImageUri2,"");
                Toast.makeText(ServerForm.this, "seems your internet is slow", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getVendorDetails() {
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(ServerForm.this,"check your internet connection! ",Toast.LENGTH_LONG).show();
            return;
        }
        ApiClient.getApiService().getVendorDetails(Integer.parseInt(AppSharedPrefernce.getInstance().getPersistenceKeyPreSurveyorId())).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(response.body());
                    String str = "";
                    sb.append(str);
                    String str2 = "getVendorDetails : ";
                    Log.e(str2, sb.toString());
                    Type listType = new TypeToken<ArrayList<VendorData>>() {
                    }.getType();
                    String str3 = "data";
                    if (((JsonObject) response.body()).has(str3) && ((JsonObject) response.body()).get(str3).toString() != "[]") {
                        List<VendorData> vendorDataList = (List) new Gson().fromJson(((JsonObject) response.body()).get(str3).toString(), listType);
                        ServerForm.this.countTodayCashAndRegistration(vendorDataList);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(vendorDataList.size());
                        sb2.append(str);
                        Log.e(str2, sb2.toString());
                        Menu menu = ServerForm.this.f48nv.getMenu();
                        MenuItem findItem = menu.findItem(R.id.total_vendor);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(ServerForm.this.getResources().getString(R.string.total_vendor_registration));
                        sb3.append(vendorDataList.size());
                        findItem.setTitle(sb3.toString());
                        MenuItem findItem2 = menu.findItem(R.id.total_cash);
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(ServerForm.this.getResources().getString(R.string.total_cash_collected));
                        sb4.append(ServerForm.totalCash);
                        findItem2.setTitle(sb4.toString());
                        MenuItem findItem3 = menu.findItem(R.id.today_cash);
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(ServerForm.this.getResources().getString(R.string.today_cash_collected));
                        sb5.append(ServerForm.todayCash);
                        findItem3.setTitle(sb5.toString());
                        MenuItem findItem4 = menu.findItem(R.id.todays_vendor);
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(ServerForm.this.getResources().getString(R.string.today_s_vendor_registration));
                        sb6.append(ServerForm.todayVendor);
                        findItem4.setTitle(sb6.toString());
                    }
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                StringBuilder sb = new StringBuilder();
                sb.append("request: ");
                sb.append(call.request());
                sb.append("response: ");
                sb.append(t.getMessage());
                sb.append("");
                Log.e("getVendorDetails : ", sb.toString());
            }
        });
    }

    public void openFragment3() {
        loadFragment(new FormFragment3());
    }

    public void openFragment4() {
        loadFragment(new FormFragment4());
    }

    public void countTodayCashAndRegistration(List<VendorData> list) {
        todayVendor = 0;
        todayCash = 0;
        totalCash = 0;
        long myhr = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
        int i = 0;
        while (i != list.size()) {
            if (((VendorData) list.get(i)).getDateOfVending().length() == 13) {
                try {
                    totalCash += Integer.parseInt(((VendorData) list.get(i)).getAmount());
                    long time = Long.parseLong(((VendorData) list.get(i)).getDateOfVending());
                    long hr = TimeUnit.MILLISECONDS.toHours(time);
                    long today = java.util.Calendar.getInstance().getTimeInMillis();

                    if (!DateUtils.isToday(time) || Math.abs(myhr - hr) >= 24) {
                        i++;
                    } else {
                        todayVendor++;
                        todayCash += Integer.parseInt(((VendorData) list.get(i)).getAmount());
                        i++;
                    }
                } catch (NumberFormatException e) {
                    i++;
                }
            } else {
                i++;
            }
        }
    }

    /* access modifiers changed from: private */
    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack(manager.getBackStackEntryAt(0).getId(), 1);
        }
    }

    @Override
    public void openFragment5() {
        loadFragment(new FormFragment5());
    }

    public void GlideImageViewer(String imageUrl, int placedrawable, ImageView view) {

        Glide.with(getApplicationContext()).load(imageUrl)
                .placeholder(placedrawable)
                .disallowHardwareConfig()
                .priority(Priority.NORMAL)
                .into(view);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RuntimePermissionsHelper.MY_PERMISSIONS_REQUEST_FOR_CAPTURE_IMAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Call whatever you want
                SelectImageDialog.getInstance().isStoragePermissionGrantedForCamera();
            } else if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                boolean showRationale = this.shouldShowRequestPermissionRationale(permissions[0]);

                if (!showRationale) {
                    // user also CHECKED "never ask again"
                    Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                            Snackbar.LENGTH_LONG).setAction("ENABLE",
                            v -> {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + this.getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                this.startActivity(intent);
                            }).show();

                }  // showRationale(permission, R.string.permission_denied_contacts);
                // user did NOT check "never ask again"
                // this is a good place to explain the user

            }
        }
        if (requestCode == RuntimePermissionsHelper.MY_PERMISSIONS_REQUEST_FOR_WRITE_EXTERNAL) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Call whatever you want
                SelectImageDialog.getInstance().isStoragePermissionGrantedForCamera();
            } else if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                boolean showRationale = this.shouldShowRequestPermissionRationale(permissions[0]);

                if (!showRationale) {
                    // user also CHECKED "never ask again"
                    Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                            Snackbar.LENGTH_LONG).setAction("ENABLE",
                            v -> {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + this.getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                this.startActivity(intent);
                            }).show();

                }   //showRationale(permission, R.string.permission_denied_contacts);
                // user did NOT check "never ask again"
                // this is a good place to explain the user

            }
        }
        if(requestCode== 1000) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (isContinue) {
                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                } else {
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(ServerForm.this, location -> {
                        if (location != null) {
                            wayLatitude = location.getLatitude();
                            wayLongitude = location.getLongitude();
                            Toast.makeText(ServerForm.this, String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude), Toast.LENGTH_LONG).show();
                        } else {
                            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                        }
                    });
                }
            } else if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                boolean showRationale = this.shouldShowRequestPermissionRationale(permissions[0]);

                if (!showRationale) {
                    // user also CHECKED "never ask again"
                    Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                            Snackbar.LENGTH_LONG).setAction("ENABLE",
                            v -> {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + this.getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                this.startActivity(intent);
                            }).show();

                }   //showRationale(permission, R.string.permission_denied_contacts);
                // user did NOT check "never ask again"
                // this is a good place to explain the user

            }
        }
    }




    private void getStateList(Spinner spinner) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");


    }

    public void getGpsLocation(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000); // 30 seconds
        locationRequest.setFastestInterval(15 * 1000); // 15 seconds

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        if (!isContinue) {
                            Toast.makeText(ServerForm.this,String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude),Toast.LENGTH_LONG).show();
                        } else {
                            stringBuilder.append(wayLatitude);
                            stringBuilder.append("-");
                            stringBuilder.append(wayLongitude);
                            stringBuilder.append("\n\n");
                            Toast.makeText(ServerForm.this,stringBuilder.toString(),Toast.LENGTH_LONG).show();
                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };


    }

    public void callForLocation(){
        if (!isGPS) {
            Toast.makeText(this, "Please turn on GPS", Toast.LENGTH_SHORT).show();
            return;
        }
        isContinue = false;
        getLocation();
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(ServerForm.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ServerForm.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ServerForm.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1000);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(ServerForm.this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        Toast.makeText(ServerForm.this,(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude)),Toast.LENGTH_LONG).show();
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            }
        }
    }

    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            String responseString = null;
            try {

                URL url = new URL("http://103.129.97.36/index.php/smsapi/httpapi/?uname=techfisms&password=123456&sender=VENDOR" +
                        "&receiver="+number+"&route=TA&msgtype=1&sms=You are registered successfully, your registeration no. is "+registrationNo+ " and login password is 12345");
                Log.d("sms", url.toString());
                ApiClient.getApiService().sendSms(url.toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("sms ",response.toString());

                        if(response.isSuccessful()){
                            Log.d("sms ",response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("sms ",t.getMessage());

                    }
                });


            } catch (Exception e) {
                //TODO Handle problems..
                Log.d("sms ","failed");
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    }

}
