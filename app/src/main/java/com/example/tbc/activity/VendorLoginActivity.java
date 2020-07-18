package com.example.tbc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tbc.R;
import com.example.tbc.utils.ApiClient;
import com.example.tbc.utils.AppHelper;
import com.example.tbc.utils.AppSharedPrefernce;
import com.example.tbc.utils.MyAppContext;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorLoginActivity extends AppCompatActivity {
    EditText login;
    Button loginBt,signup;
    EditText password;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_login_vendor);
        MyAppContext.setInstance("LoginActivity", this);
        this.loginBt = (Button) findViewById(R.id.vendorlogin_bt_signin);
        this.login = (EditText) findViewById(R.id.activity_loginEt_loginId);
        this.password = (EditText) findViewById(R.id.activity_loginEt_pass);
        this.signup = findViewById(R.id.vendorlogin_bt_signup);


        this.loginBt.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                if (VendorLoginActivity.this.login.getText().toString().equalsIgnoreCase(str)) {
                    Toast.makeText(VendorLoginActivity.this, "Please Enter Registration no", Toast.LENGTH_SHORT).show();
                } else if (VendorLoginActivity.this.password.getText().toString().equalsIgnoreCase(str)) {
                    Toast.makeText(VendorLoginActivity.this, "Please Enter password ", Toast.LENGTH_SHORT).show();
                } else {
                    VendorLoginActivity.this.loginApi();
                }
            }
        });

        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VendorLoginActivity.this,VendorRegisteration.class));
                finish();
            }
        });
    }

    public void loginApi() {
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(VendorLoginActivity.this,"check your internet connection!",Toast.LENGTH_LONG).show();
            return;
        }
        ApiClient.getApiService().vendorLogin(this.login.getText().toString(), this.password.getText().toString()).enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                StringBuilder sb = new StringBuilder();
                sb.append("success ");
                sb.append(response.body());
                sb.append("\n");
                sb.append(call.request().url());
                sb.append(" v ");
                sb.append(call.request().toString());
                Log.e("api", sb.toString());
                if (response.isSuccessful()) {
                    String str = "status";
                    if (!((JsonObject) response.body()).has(str) || ((JsonObject) response.body()).get(str).isJsonNull()) {
                        Toast.makeText(VendorLoginActivity.this, "oops! please try again ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if((response.body()).get(str).getAsInt()!=1){
                        Toast.makeText(VendorLoginActivity.this, "oops! login failed ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AppSharedPrefernce.getInstance().setIsVendorLogin(true);
                    AppSharedPrefernce.getInstance().setPersistenceKeyPreSurveyorId(( response.body()).get(str).getAsString());
                    startActivity(new Intent(VendorLoginActivity.this, VendorUtility.class));
                    VendorLoginActivity.this.finish();
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                StringBuilder sb = new StringBuilder();
                sb.append("fail ");
                sb.append(t.getMessage());
                Log.e("api", sb.toString());
                Toast.makeText(VendorLoginActivity.this, "something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
