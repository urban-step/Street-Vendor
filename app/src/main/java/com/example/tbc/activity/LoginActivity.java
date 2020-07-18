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

public class LoginActivity extends AppCompatActivity {
    EditText login;
    Button loginBt;
    EditText password;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_login);
        MyAppContext.setInstance("LoginActivity", this);
        this.loginBt = (Button) findViewById(R.id.login_bt_signin);
        this.login = (EditText) findViewById(R.id.activity_loginEt_loginId);
        this.password = (EditText) findViewById(R.id.activity_loginEt_pass);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("access_token", "test123test");
        jsonObject.addProperty("name", "testing");
        jsonObject.addProperty("password", "3344");
        this.loginBt.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String str = "";
                if (LoginActivity.this.login.getText().toString().equalsIgnoreCase(str)) {
                    Toast.makeText(LoginActivity.this, "Please Enter login id", Toast.LENGTH_SHORT).show();
                } else if (LoginActivity.this.password.getText().toString().equalsIgnoreCase(str)) {
                    Toast.makeText(LoginActivity.this, "Please Enter password ", Toast.LENGTH_SHORT).show();
                } else {
                    LoginActivity.this.loginApi();
                }
            }
        });
    }

    public void loginApi() {
        if(!AppHelper.getInstance().isInternetOn()){
            Toast.makeText(LoginActivity.this,"check your internet connection!",Toast.LENGTH_LONG).show();
            return;
        }
        ApiClient.getApiService().surveyorLogin("test123test", this.login.getText().toString(), this.password.getText().toString()).enqueue(new Callback<JsonObject>() {
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
                    String str = "surveyorId";
                    if (!((JsonObject) response.body()).has(str) || ((JsonObject) response.body()).get(str).isJsonNull()) {
                        Toast.makeText(LoginActivity.this, "oops! please try again ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AppSharedPrefernce.getInstance().setIsSurveyorLogin(true);
                    AppSharedPrefernce.getInstance().setPersistenceKeyPreSurveyorId(( response.body()).get(str).getAsString());
                    LoginActivity loginActivity = LoginActivity.this;
                    loginActivity.startActivity(new Intent(loginActivity, ServerForm.class));
                    LoginActivity.this.finish();
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                StringBuilder sb = new StringBuilder();
                sb.append("fail ");
                sb.append(t.getMessage());
                Log.e("api", sb.toString());
                Toast.makeText(LoginActivity.this, "something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
