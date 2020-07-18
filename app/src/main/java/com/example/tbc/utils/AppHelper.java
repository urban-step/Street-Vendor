package com.example.tbc.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppHelper {
    private static AppHelper instance;
    private String LOG_TAG = "AppHelper()";

    private AppHelper() {
    }

    public static AppHelper getInstance() {
        if (instance == null) {
            instance = new AppHelper();
        }

        return instance;
    }
    public boolean isInternetOn() {
        boolean val = false;
        Context context = MyAppContext.getInstance();
        if (context != null) {
            ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // ARE WE CONNECTED TO THE NET
            if (connec != null) {
                if ((connec.getNetworkInfo(0) != null && connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)
                        || (connec.getNetworkInfo(0) != null && connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING)
                        || (connec.getNetworkInfo(1) != null && connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING)
                        || (connec.getNetworkInfo(1) != null && connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)) {
                    val = true;
                } else if ((connec.getNetworkInfo(0) != null && connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED)
                        || (connec.getNetworkInfo(1) != null && connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED)) {
                    val = false;
                }
                return val;
            }
        }
        return false;
    }
    public String getAppLanguageCode() {
        String appLanguage = AppSharedPrefernce.getInstance().getlanguage();
        if (appLanguage.equalsIgnoreCase("hi")) {
            return "hi";
        }
        return "en";
    }

}

