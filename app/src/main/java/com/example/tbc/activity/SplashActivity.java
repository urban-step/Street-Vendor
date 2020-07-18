package com.example.tbc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.tbc.MainActivity;
import com.example.tbc.R;
import com.example.tbc.utils.MyAppContext;

public class SplashActivity extends AppCompatActivity {
    TextView splashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        uiIntialize();
        MyAppContext.setInstance("SplashActivity", this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SelectionActivity.class));
                finish();
            }
        },2000);
    }

    private void uiIntialize() {
        splashText = findViewById(R.id.splash_text);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String black = "WELCOME TO ";
        SpannableString redSpannable= new SpannableString(black);
        redSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, black.length(), 0);
        builder.append(redSpannable);

        String green = "TBD";
        SpannableString whiteSpannable= new SpannableString(green);
        whiteSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, green.length(), 0);
        builder.append(whiteSpannable);

        splashText.setText(builder, TextView.BufferType.SPANNABLE);
    }
}
