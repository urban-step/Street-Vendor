package com.example.tbc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView webView ;
    String BASE_URL="file:///android_asset/betgames.js";
//    String BASE_URL="https://www.google.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);

        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(BASE_URL);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            webView.evaluateJavascript("var FunctionOne = function () {"
//                    + "  try{document.getElementsByClassName('test')[0].style.color='red';}catch(e){}"
//                    + "};", null);
//        } else {
//            webView.loadUrl("javascript:"
//                    + "var FunctionOne = function () {"
//                    + "  try{document.getElementsByClassName('test')[0].style.color='red';}catch(e){}"
//                    + "};");
//        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
 class MyBrowser extends WebViewClient {

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }



    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
