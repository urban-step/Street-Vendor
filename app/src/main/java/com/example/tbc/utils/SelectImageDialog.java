package com.example.tbc.utils;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import androidx.core.content.FileProvider;

import com.example.tbc.R;
import com.example.tbc.activity.ServerForm;
import com.example.tbc.activity.VendorRegisteration;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class SelectImageDialog extends Dialog implements OnRequestPermissionsResultCallback {
    private static final int SELECT_PICTURE = 100;
    private int TAKE_PIC = 1;
    private AppCompatActivity context;
    public boolean selectImage = false;
    public boolean takePhoto = false;
    private TextView ui_tvcancel;
    private TextView ui_tvchoosegallery;
    private TextView ui_tvtakephoto;
    private String data;
    private static SelectImageDialog instance;


    private void setInstance(SelectImageDialog instance2) {
        instance = instance2;
    }

    public static SelectImageDialog getInstance() {
        return instance;
    }

    public SelectImageDialog(@NonNull AppCompatActivity context2, String konsipic) {
        super(context2);
        this.context = context2;
        this.data = konsipic;
        setInstance(this);
    }

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        getWindow().requestFeature(1);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_seclectimage_layout);
        uiInitialize();
    }

    private void uiInitialize() {
        this.ui_tvtakephoto = (TextView) findViewById(R.id.dialogtv_img_takephoto_option);
        this.ui_tvchoosegallery = (TextView) findViewById(R.id.dialogtv_img_gallery_option);
        this.ui_tvcancel = (TextView) findViewById(R.id.dialog_tvcancel);
        setClickListeners();
    }

    private void setClickListeners() {
        ui_tvcancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                SelectImageDialog.this.dismiss();
            }
        });
        ui_tvchoosegallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectImageDialog selectImageDialog = SelectImageDialog.this;
                selectImageDialog.takePhoto = false;
                selectImageDialog.isStoragePermissionGrantedRead();
                SelectImageDialog.this.dismiss();
            }
        });
        this.ui_tvtakephoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectImageDialog selectImageDialog = SelectImageDialog.this;
                selectImageDialog.takePhoto = true;
                selectImageDialog.isCameraPermissionsGranted();
                SelectImageDialog.this.dismiss();
            }
        });
    }

    public void isStoragePermissionGrantedRead() {
        if (RuntimePermissionsHelper.getInstance().verifySingleRuntimePermission(this.context, 2).booleanValue()) {
            if (this.takePhoto) {
                takeImageFromCamera();
            } else {
                choosefromgallery();
            }
            Log.d(getClass().getName(), "isStoragePermissionGrantedRead()   granted");
            return;
        }
        Log.d(getClass().getName(), "isStoragePermissionGrantedRead()   need");
    }

    private void takeImageFromCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory(), "userProfile.jpg");

        AppCompatActivity activity = this.context;
        StringBuilder sb = new StringBuilder();
        sb.append(this.context.getApplication().getPackageName());
        sb.append(".provider");
        if(ServerForm.getInstance()!=null) {
             ServerForm.getInstance().outPutfileUri = FileProvider.getUriForFile(activity, sb.toString(), file);
            intent.putExtra("output", ServerForm.getInstance().outPutfileUri);

        }
        if(VendorRegisteration.getInstance()!=null) {
            VendorRegisteration.getInstance().outPutfileUri = FileProvider.getUriForFile(activity, sb.toString(), file);
            intent.putExtra("output", VendorRegisteration.getInstance().outPutfileUri);

        }
        this.context.startActivityForResult(intent, this.TAKE_PIC);
    }

    private void choosefromgallery() {
        Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
        this.context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
    }

    public void isCameraPermissionsGranted() {
        if (RuntimePermissionsHelper.getInstance().verifySingleRuntimePermission(this.context, 0).booleanValue()) {
            isStoragePermissionGrantedForCamera();
            Log.d(getClass().getName(), "isCameraPermissionsGranted()   granted");
            return;
        }
        askForCameraPermissions();
        Log.d(getClass().getName(), "isCameraPermissionsGranted()   need");
    }

    public void isStoragePermissionGrantedForCamera() {
        if (RuntimePermissionsHelper.getInstance().verifySingleRuntimePermission(this.context, 2).booleanValue()) {
            if (this.takePhoto) {
                takeImageFromCamera();
            } else {
                choosefromgallery();
            }
            Log.d(getClass().getName(), "isStoragePermissionGrantedRead()   granted");
            return;
        }
        askForCameraPermissions();
        Log.d(getClass().getName(), "isStoragePermissionGrantedRead()   need");
    }

    /* access modifiers changed from: private */
    public void askForCameraPermissions() {
        if (RuntimePermissionsHelper.getInstance().verifySingleRuntimePermission(this.context, 0).booleanValue()) {
            askForStoragePermissions();
        } else {
            Log.d(getClass().getName(), "askForCameraPermissions denied ");
        }
    }

    private void askForStoragePermissions() {
        if (!RuntimePermissionsHelper.getInstance().verifySingleRuntimePermission(this.context, 1).booleanValue()) {
            Log.d(getClass().getName(), "askForStoragePermissions denied ");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RuntimePermissionsHelper.MY_PERMISSIONS_REQUEST_FOR_CAPTURE_IMAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Call whatever you want
                isStoragePermissionGrantedForCamera();
            } else if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                boolean showRationale = context.shouldShowRequestPermissionRationale(permissions[0]);

                if (!showRationale) {
                    // user also CHECKED "never ask again"
                    Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                            Snackbar.LENGTH_LONG).setAction("ENABLE",
                            v -> {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + context.getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                context.startActivity(intent);
                            }).show();

                }  // showRationale(permission, R.string.permission_denied_contacts);
                // user did NOT check "never ask again"
                // this is a good place to explain the user

            }
        }
        if (requestCode == RuntimePermissionsHelper.MY_PERMISSIONS_REQUEST_FOR_READ_EXTERNAL) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Call whatever you want
                isStoragePermissionGrantedForCamera();
            } else if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                boolean showRationale = context.shouldShowRequestPermissionRationale(permissions[0]);

                if (!showRationale) {
                    // user also CHECKED "never ask again"
                    Snackbar.make(findViewById(android.R.id.content), "Enable Permissions from settings",
                            Snackbar.LENGTH_LONG).setAction("ENABLE",
                            v -> {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + context.getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                context.startActivity(intent);
                            }).show();

                }   //showRationale(permission, R.string.permission_denied_contacts);
                // user did NOT check "never ask again"
                // this is a good place to explain the user

            }
        }
    }
}
