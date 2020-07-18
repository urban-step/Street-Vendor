package com.example.tbc.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;

import java.util.ArrayList;
import java.util.List;

public class RuntimePermissionsHelper extends AppCompatActivity implements OnRequestPermissionsResultCallback {
    static final String LOG_TAG = RuntimePermissionsHelper.class.getSimpleName();
    public static final int MY_PERMISSIONS_REQUEST_FOR_CALL = 4;
    public static final int MY_PERMISSIONS_REQUEST_FOR_CAPTURE_IMAGE = 0;
    public static final int MY_PERMISSIONS_REQUEST_FOR_MULTIPLE = 1000;
    public static final int MY_PERMISSIONS_REQUEST_FOR_PHONE_STATE = 8;
    public static final int MY_PERMISSIONS_REQUEST_FOR_READ_EXTERNAL = 1;
    public static final int MY_PERMISSIONS_REQUEST_FOR_ACCESS_FINE_LOCATION = 3;
    public static final int MY_PERMISSIONS_REQUEST_FOR_READ_MIC = 7;
    public static final int MY_PERMISSIONS_REQUEST_FOR_WRITE_EXTERNAL = 2;


    public static String[] STR_ARRAY_ALL_NEEDED_PERMISSIONS = {
/* index 0 */        Manifest.permission.CAMERA,
/* index 1 */        Manifest.permission.READ_EXTERNAL_STORAGE,
/* index 2 */        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                     Manifest.permission.CALL_PHONE
    };

    public static String[] mArraySinglePermission;
    private static RuntimePermissionsHelper mInstance = null;
    public static String[] mPermissionKeys = {"captureImage", "readStorage", "writeExternal","ACCESS_FINE_LOCATION"};
    List<String> mListPermissionsRemaining = new ArrayList();

    private RuntimePermissionsHelper() {
    }

    public static RuntimePermissionsHelper getInstance() {
        if (mInstance == null) {
            mInstance = new RuntimePermissionsHelper();
        }
        return mInstance;
    }

    public Boolean verifySingleRuntimePermission(AppCompatActivity activity, int requestType) {

        int permissionAvailable = ActivityCompat.checkSelfPermission(activity, STR_ARRAY_ALL_NEEDED_PERMISSIONS[requestType]);
        if (permissionAvailable != PackageManager.PERMISSION_GRANTED) {
            mListPermissionsRemaining.add(STR_ARRAY_ALL_NEEDED_PERMISSIONS[requestType]);
            mArraySinglePermission = new String[1];

            mArraySinglePermission[0] = STR_ARRAY_ALL_NEEDED_PERMISSIONS[requestType];
            ActivityCompat.requestPermissions(activity, mArraySinglePermission, requestType);
        } else {
            Log.d(LOG_TAG, "Permission is granted for " + STR_ARRAY_ALL_NEEDED_PERMISSIONS[requestType]);
            return true;
        }
        return false;
    }

}
