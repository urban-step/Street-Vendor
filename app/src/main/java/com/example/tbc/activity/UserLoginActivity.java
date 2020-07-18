package com.example.tbc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tbc.R;
import com.example.tbc.model.UserData;
import com.example.tbc.utils.ApiClient;
import com.example.tbc.utils.AppHelper;
import com.example.tbc.utils.AppSharedPrefernce;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {

    EditText name,email,mobileNo,otp;
    Button next,bt_otp;
    String otpGenerated = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        name = findViewById(R.id.activity_UserLoginEt_name);
        email = findViewById(R.id.activity_UserLoginEt_emailId);
        mobileNo  = findViewById(R.id.activity_UserLoginEt_mobileNo);
        otp = findViewById(R.id.activity_UserLoginEt_otp);
        next = findViewById(R.id.userlogin_bt_signin);
        bt_otp = findViewById(R.id.userlogin_bt_otp);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equalsIgnoreCase("")||email.getText().toString().equalsIgnoreCase("")
                        ||mobileNo.getText().toString().equalsIgnoreCase("")||otp.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(UserLoginActivity.this,"please fill details",Toast.LENGTH_LONG).show();
                }else if(otpGenerated!=null && !otpGenerated.isEmpty()){
                    if(otp.getText().toString().equalsIgnoreCase(otpGenerated)){
                        userLogin();
                    }
                }else
                {
                    Toast.makeText(UserLoginActivity.this,"please try again after sometime",Toast.LENGTH_LONG).show();
                }
            }
        });

        bt_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobileNo.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(UserLoginActivity.this,"enter mobile number",Toast.LENGTH_LONG).show();
                }else{

                Random rnd = new Random();
                int number = rnd.nextInt(999999);

                // this will convert any number sequence into 6 character.
                otpGenerated =   String.format("%06d", number);
                Log.d("otp generated ::",number+"");
                try {

                    URL url = new URL("http://103.129.97.36/index.php/smsapi/httpapi/?uname=techfisms&password=123456&sender=VENDOR&receiver="+mobileNo.getText()+"&route=TA&msgtype=1&sms=Your one time password is "+otpGenerated);
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
            }
            }
        });

    }

    public void userLogin(){
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(UserLoginActivity.this,"check your internet connection!",Toast.LENGTH_LONG).show();
            return;
        }
        ApiClient.getApiService().userLogin(name.getText().toString(), email.getText().toString(),mobileNo.getText().toString(),"123456").enqueue(new Callback<JsonObject>() {
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
                        Toast.makeText(UserLoginActivity.this, "oops! please try again ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if((response.body()).get(str).getAsInt()!=1){
                        Toast.makeText(UserLoginActivity.this, "oops! login failed ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AppSharedPrefernce.getInstance().setIsUserLogin(true);
                    UserData userData = new UserData();
                    userData.setName(name.getText().toString());
                    userData.setEmailId(email.getText().toString());
                    userData.setNumber(mobileNo.getText().toString());
                    AppSharedPrefernce.getInstance().setUserData(new Gson().toJson(userData));
                    AppSharedPrefernce.getInstance().setPersistenceKeyPreSurveyorId(( response.body()).get(str).getAsString());
                    startActivity(new Intent(UserLoginActivity.this, UserHomeActivity.class));
                    UserLoginActivity.this.finish();
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                StringBuilder sb = new StringBuilder();
                sb.append("fail ");
                sb.append(t.getMessage());
                Log.e("api", sb.toString());
                Toast.makeText(UserLoginActivity.this, "something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
