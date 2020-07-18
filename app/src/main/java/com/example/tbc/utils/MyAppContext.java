package com.example.tbc.utils;

import android.content.Context;
import android.util.Log;

public class MyAppContext {
    private static Context mContext = null;
    private static MyAppContext myInstance = null;

    private MyAppContext() {
    }

    public static Context getInstance() {
        if (myInstance == null) {
            myInstance = new MyAppContext();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("stored context ...");
        MyAppContext myAppContext = myInstance;
        sb.append(mContext.getClass().getName());
        Log.d("getContext", sb.toString());
        MyAppContext myAppContext2 = myInstance;
        return mContext;
    }

    public static void setInstance(String callerName, Context context) {
        if (myInstance == null) {
            myInstance = new MyAppContext();
        }
        String str = "callerName: ";
        String str2 = "setContext";
        if (context == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(callerName);
            sb.append(", Setting context ...null");
            Log.d(str2, sb.toString());
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(callerName);
            sb2.append(", Setting context ...");
            sb2.append(context.getPackageName());
            Log.d(str2, sb2.toString());
        }
        MyAppContext myAppContext = myInstance;
        mContext = context;
    }
}
