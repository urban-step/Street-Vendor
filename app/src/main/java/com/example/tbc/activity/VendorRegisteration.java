package com.example.tbc.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tbc.R;
import com.example.tbc.fragment.FormFragment2;
import com.example.tbc.fragment.FormFragmentVendor1;
import com.example.tbc.fragment.FormFragmentVendor5;
import com.example.tbc.model.SurveyFormData;
import com.example.tbc.utils.ApiClient;
import com.example.tbc.utils.AppHelper;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.GpsUtils;
import com.example.tbc.utils.RuntimePermissionsHelper;
import com.example.tbc.utils.SelectImageDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorRegisteration extends AppCompatActivity implements FormFragmentVendor1.OpenF1 {

    private FusedLocationProviderClient mFusedLocationClient;

    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private StringBuilder stringBuilder;
    private TextView locationOfVending;
    private boolean isContinue = false;
    private boolean isGPS = false;
    private int TAKE_PIC = 1;
    private static final int SELECT_PICTURE = 100;
    public Uri outPutfileUri;
    private Uri cropImageUri;
    public String konsiPic="";
    String number="";

    public String registrationNo;
    private static VendorRegisteration instance;

    public static VendorRegisteration getInstance() {
        return instance;
    }
    private void setInstance(VendorRegisteration instance2) {
        instance = instance2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_registeration);
       // this.btnLocation = (Button) findViewById(R.id.btLocation);
       // locationOfVending = findViewById(R.id.locationOfVendingGps);
        setInstance(this);
        hideKeyboard(this);
        loadFragment1(new FormFragmentVendor1());



    }

    @Override
    protected void onResume() {
        super.onResume();
        getGpsLocation();
    }

    public void loadFragment1(Fragment fragment) {
        new Bundle();
        getSupportFragmentManager().beginTransaction().replace(R.id.vendorRegisterationframe, fragment).commit();
    }

    public void openFragment2() {
        loadFragment(new FormFragment2());
    }
    private void loadFragment(Fragment fragment) {
        new Bundle();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
    }


    public void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                            Toast.makeText(VendorRegisteration.this,String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude),Toast.LENGTH_LONG).show();
                        } else {
                            stringBuilder.append(wayLatitude);
                            stringBuilder.append("-");
                            stringBuilder.append(wayLongitude);
                            stringBuilder.append("\n\n");
                            Toast.makeText(VendorRegisteration.this,stringBuilder.toString(),Toast.LENGTH_LONG).show();
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
        if (ActivityCompat.checkSelfPermission(VendorRegisteration.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(VendorRegisteration.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VendorRegisteration.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1000);

        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(VendorRegisteration.this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        Toast.makeText(VendorRegisteration.this,(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude)),Toast.LENGTH_LONG).show();
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            }
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) {
                isGPS = true; // flag maintain before get location
            }
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    crop_Method(selectedImageUri);
                }
            }
        }

        if (requestCode == TAKE_PIC && resultCode == RESULT_OK) {
            if (null != outPutfileUri) {
                crop_Method(outPutfileUri);
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
            Toast.makeText(VendorRegisteration.this,"check your internet connection! upload again",Toast.LENGTH_LONG).show();
            return;
        }
        String str = konsiPic;
        String str2 = "profile.png";
        ApiClient.getApiService().UploadFile(MultipartBody.Part.createFormData(str, str2, RequestBody.create(MediaType.parse("multipart/form-data"), new File(cropImageUri2.getPath()))), 0).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UploadFile - ");
                    sb.append(response.body());
                    Log.d("server ", sb.toString());
                    String str = "url";
                    if (!((JsonObject) response.body()).has(str) || ((JsonObject) response.body()).get(str).getAsString().equalsIgnoreCase("")) {
                        Toast.makeText(VendorRegisteration.this, "Image upload failed", Toast.LENGTH_LONG).show();
                        FormFragmentVendor1.getInstance().updateSelectedImage(konsiPic,cropImageUri2,"");
                        return;
                    }

                    Toast.makeText(VendorRegisteration.this, "Image uploaded succesfully", Toast.LENGTH_LONG).show();
                    FormFragmentVendor1.getInstance().updateSelectedImage(konsiPic,cropImageUri2,response.body().get(str).getAsString());

                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                StringBuilder sb = new StringBuilder();
                sb.append("UploadFile -err ");
                sb.append(t.getMessage());
                Log.d("server ", sb.toString());
                //FormFragment4.getInstance().updateSelectedImage(konsiPic,cropImageUri2,"");
                Toast.makeText(VendorRegisteration.this, "something went wrong! try again", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void callSubmitApi() {
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(VendorRegisteration.this,"check your internet connection",Toast.LENGTH_LONG).show();
            return;
        }
        SurveyFormData surveyFormData = new Gson().fromJson(AppSharedPrefernce.getInstance().getVendorData(), SurveyFormData.class);

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
                        Toast.makeText(VendorRegisteration.this, "Seems your internet is slow ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    registrationNo = jsonObject.get("registration_no").getAsString();
                    number = surveyFormData.getMobileNumber();
                    Toast.makeText(VendorRegisteration.this, "Congratulations!! Form submitted successfully", Toast.LENGTH_LONG).show();
                    hideKeyboard(VendorRegisteration.this);
                    clearBackStack();
                    loadFragment1(new FormFragmentVendor5());
                    RequestTask request = new RequestTask();
                    request.execute();
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
                Toast.makeText(VendorRegisteration.this, "Seems your internet is slow ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack(manager.getBackStackEntryAt(0).getId(), 1);
        }
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
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(VendorRegisteration.this, location -> {
                        if (location != null) {
                            wayLatitude = location.getLatitude();
                            wayLongitude = location.getLongitude();
                            Toast.makeText(VendorRegisteration.this, String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude), Toast.LENGTH_LONG).show();
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
