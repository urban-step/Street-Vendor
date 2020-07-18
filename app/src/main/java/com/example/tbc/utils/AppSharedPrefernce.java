package com.example.tbc.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class AppSharedPrefernce {
    private static final String LOG_TAG = "AppSettingsSharedPrefs";
    private static final String MY_SHARED_PREFERS_NAME = "TBC";
    private static final String PERSISTENCE_KEY_BASE_URL_STR = "baseUrl";
    private static final String PERSISTENCE_KEY_PRE_IS_USER_LOGIN = "isUserLogin";
    private static final String PERSISTENCE_KEY_PRE_IS_VENDOR_LOGIN = "isVendorLogin";
    private static final String PERSISTENCE_KEY_PRE_IS_SURVEYOR_LOGIN = "isSurveyorLogin";
    private static final String PERSISTENCE_KEY_PRE_SURVEYOR_ID = "ID";

    private static final String PERSISTENCE_KEY_VENDOR_PROFILE_DATA = "vendorProfile";

    private static final String PERSISTENCE_KEY_VENDOR_DP_FILE = "dpfile";
    private static final String PERSISTENCE_KEY_VENDOR_STALL_FILE = "stallfile";
    private static final String PERSISTENCE_KEY_SURVEYOR_REGISTRATION_NO = "registration_no";
    public String DEVELOPER_KEY = "AIzaSyAc3isnjiRHxsktuQfsciVjJ40kph4Yy2U";
    private static final String PERSISTENCE_KEY_SELECT_LANGUAGE = "language";
    private static final String PERSISTENCE_KEY_USER_PROFILE_DATA = "userProfile";


    private static SharedPreferences mSharedPrefs = null;
    private static AppSharedPrefernce myInstance = null;

    public static AppSharedPrefernce getInstance() {
        if (myInstance == null) {
            myInstance = new AppSharedPrefernce();
            if (mSharedPrefs == null) {
                myInstance = null;
            }
        }
        return myInstance;
    }

    private AppSharedPrefernce() {
        if (MyAppContext.getInstance() != null) {
            mSharedPrefs = MyAppContext.getInstance().getSharedPreferences(MY_SHARED_PREFERS_NAME, 0);
            return;
        }
        mSharedPrefs = null;
        Log.e(LOG_TAG, "No valid context available ... ");
    }

    private void printDebugStatement(String msg) {
        Log.d("AppSharedPrefernce: ", msg);
    }

    private String getStringValueForKey(String keyName, String strDefaultVal) {
        String strValue = strDefaultVal;
        if (mSharedPrefs == null) {
            printDebugStatement("getStringValueForKey() - SharedPrefs not initialized");
        } else if (keyName == null || keyName.equals("")) {
            printDebugStatement("setStringValueForKey() - Invalid key");
            return strDefaultVal;
        } else {
            strValue = mSharedPrefs.getString(keyName, strDefaultVal);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("getStringValueForKey() - keyName: ");
        sb.append(keyName);
        sb.append(" , strValue - ");
        sb.append(strValue);
        printDebugStatement(sb.toString());
        return strValue;
    }

    private void setStringValueForKey(String keyName, String strVal) {
        if (mSharedPrefs == null) {
            printDebugStatement("setStringValueForKey() - SharedPrefs not initialized");
        } else if (keyName == null || keyName.equals("")) {
            printDebugStatement("setStringValueForKey() - Invalid key");
        } else if (strVal != null) {
            Editor settingEditor = mSharedPrefs.edit();
            settingEditor.putString(keyName, strVal);
            settingEditor.apply();
            StringBuilder sb = new StringBuilder();
            sb.append("setStringValueForKey() - keyName: ");
            sb.append(keyName);
            sb.append(" , strValue - ");
            sb.append(strVal);
            printDebugStatement(sb.toString());
        } else {
            printDebugStatement("setStringValueForKey() - Not a valid value: ");
        }
    }

    private long getLongValueForKey(String keyName, long iDefaultVal) {
        long iValue = iDefaultVal;
        if (keyName == null || keyName.equals("")) {
            printDebugStatement("getIntValueForKey() - Invalid key");
            return -1;
        }
        SharedPreferences sharedPreferences = mSharedPrefs;
        if (sharedPreferences != null) {
            iValue = sharedPreferences.getLong(keyName, iDefaultVal);
        } else {
            printDebugStatement("getIntValueForKey() - SharedPrefs not initialized");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("getIntValueForKey() - keyName: ");
        sb.append(keyName);
        sb.append(" , iValue: ");
        sb.append(iValue);
        printDebugStatement(sb.toString());
        return iValue;
    }

    private void setLongValueForKey(String keyName, long iValue) {
        if (mSharedPrefs == null) {
            printDebugStatement("setIntValueForKey() - SharedPrefs not initialized");
        } else if (keyName == null || keyName.equals("")) {
            printDebugStatement("setIntValueForKey() - Invalid key");
        } else if (iValue >= 0) {
            Editor settingEditor = mSharedPrefs.edit();
            settingEditor.putLong(keyName, iValue);
            settingEditor.apply();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("setIntValueForKey() - Not a valid value: ");
            sb.append(iValue);
            printDebugStatement(sb.toString());
        }
    }

    private Integer getIntValueForKey(String keyName, Integer iDefaultVal) {
        Integer iValue = iDefaultVal;
        if (keyName == null || keyName.equals("")) {
            printDebugStatement("getIntValueForKey() - Invalid key");
            return Integer.valueOf(-1);
        }
        SharedPreferences sharedPreferences = mSharedPrefs;
        if (sharedPreferences != null) {
            iValue = Integer.valueOf(sharedPreferences.getInt(keyName, iDefaultVal.intValue()));
        } else {
            printDebugStatement("getIntValueForKey() - SharedPrefs not initialized");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("getIntValueForKey() - keyName: ");
        sb.append(keyName);
        sb.append(" , iValue: ");
        sb.append(iValue);
        printDebugStatement(sb.toString());
        return iValue;
    }

    private void setIntValueForKey(String keyName, Integer iValue) {
        if (mSharedPrefs == null) {
            printDebugStatement("setIntValueForKey() - SharedPrefs not initialized");
        } else if (keyName == null || keyName.equals("")) {
            printDebugStatement("setIntValueForKey() - Invalid key");
        } else if (iValue.intValue() >= 0) {
            Editor settingEditor = mSharedPrefs.edit();
            settingEditor.putInt(keyName, iValue.intValue());
            settingEditor.apply();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("setIntValueForKey() - Not a valid value: ");
            sb.append(iValue);
            printDebugStatement(sb.toString());
        }
    }

    private Boolean getBooleanValueForKey(String keyName, Boolean bFlagDefault) {
        Boolean bFlag = bFlagDefault;
        if (keyName == null || keyName.equals("")) {
            printDebugStatement("getBooleanValueForKey() - Invalid key");
            return Boolean.valueOf(false);
        }
        SharedPreferences sharedPreferences = mSharedPrefs;
        if (sharedPreferences != null) {
            bFlag = Boolean.valueOf(sharedPreferences.getBoolean(keyName, bFlagDefault.booleanValue()));
        } else {
            printDebugStatement("getBooleanValueForKey() - SharedPrefs not initialized");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("getBooleanValueForKey() - keyName: ");
        sb.append(keyName);
        sb.append(" , bFlag: ");
        sb.append(bFlag);
        printDebugStatement(sb.toString());
        return bFlag;
    }

    private void setBooleanValueForKey(String keyName, Boolean bFlag) {
        if (mSharedPrefs == null) {
            printDebugStatement("setBooleanValueForKey() - SharedPrefs not initialized");
        } else if (keyName == null || keyName.equals("")) {
            printDebugStatement("setBooleanValueForKey() - Invalid key");
        } else {
            Editor settingEditor = mSharedPrefs.edit();
            settingEditor.putBoolean(keyName, bFlag.booleanValue());
            settingEditor.apply();
        }
    }

    private void setFloatValueForKey(String keyName, float bFlag) {
        if (mSharedPrefs == null) {
            printDebugStatement("setFloatValueForKey() - SharedPrefs not initialized");
        } else if (keyName == null || keyName.equals("")) {
            printDebugStatement("setFloatValueForKey() - Invalid key");
        } else {
            Editor settingEditor = mSharedPrefs.edit();
            settingEditor.putFloat(keyName, bFlag);
            settingEditor.apply();
        }
    }

    private float getFloatValueForKey(String keyName, float bFlagDefault) {
        float bFlag = bFlagDefault;
        if (keyName == null || keyName.equals("")) {
            printDebugStatement("getFloatValueForKey() - Invalid key");
            return -1.0f;
        }
        SharedPreferences sharedPreferences = mSharedPrefs;
        if (sharedPreferences != null) {
            bFlag = sharedPreferences.getFloat(keyName, bFlagDefault);
        } else {
            printDebugStatement("getFloatValueForKey() - SharedPrefs not initialized");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("getFloatValueForKey() - keyName: ");
        sb.append(keyName);
        sb.append(" , bFlag: ");
        sb.append(bFlag);
        printDebugStatement(sb.toString());
        return bFlag;
    }

    public void deletAllDataFromSharedPreference() {
        Editor settingEditor = mSharedPrefs.edit();
        settingEditor.clear();
        settingEditor.apply();
        Log.e(LOG_TAG, "deletAllDataFromSharedPreference -: ");
    }

    public void setIsUserLogin(boolean isUserLogin) {
        setBooleanValueForKey(PERSISTENCE_KEY_PRE_IS_USER_LOGIN, Boolean.valueOf(isUserLogin));
    }

    public boolean getIsUserLogin() {
        return getBooleanValueForKey(PERSISTENCE_KEY_PRE_IS_USER_LOGIN, Boolean.valueOf(false));
    }

    public void setPersistenceKeyPreSurveyorId(String id) {
        setStringValueForKey(PERSISTENCE_KEY_PRE_SURVEYOR_ID, id);
    }

    public String getPersistenceKeyPreSurveyorId() {
        return getStringValueForKey(PERSISTENCE_KEY_PRE_SURVEYOR_ID, "");
    }

    public String getVendorData() {
        return getStringValueForKey(PERSISTENCE_KEY_VENDOR_PROFILE_DATA, "");
    }

    public void setVendorData(String profileData) {
        setStringValueForKey(PERSISTENCE_KEY_VENDOR_PROFILE_DATA, profileData);
    }
    public String getPersistenceKeyVendorDpFile() {
        return getStringValueForKey(PERSISTENCE_KEY_VENDOR_DP_FILE,"");
    }
    public void setPersistenceKeyVendorDpFile(String file) {
        setStringValueForKey(PERSISTENCE_KEY_VENDOR_DP_FILE, file);
    }
    public String getPersistenceKeySurveyorRegistrationNo() {
        return getStringValueForKey(PERSISTENCE_KEY_SURVEYOR_REGISTRATION_NO,"");
    }
    public void setPersistenceKeySurveyorRegistrationNo(String no) {
        setStringValueForKey(PERSISTENCE_KEY_SURVEYOR_REGISTRATION_NO, no);
    }
    public void setIsVendorLogin(boolean isUserLogin) {
        setBooleanValueForKey(PERSISTENCE_KEY_PRE_IS_VENDOR_LOGIN, Boolean.valueOf(isUserLogin));
    }

    public boolean getIsVendorLogin() {
        return getBooleanValueForKey(PERSISTENCE_KEY_PRE_IS_VENDOR_LOGIN, Boolean.valueOf(false)).booleanValue();
    }

    public void setIsSurveyorLogin(boolean isUserLogin) {
        setBooleanValueForKey(PERSISTENCE_KEY_PRE_IS_SURVEYOR_LOGIN, Boolean.valueOf(isUserLogin));
    }

    public boolean getIsSurveyorLogin() {
        return getBooleanValueForKey(PERSISTENCE_KEY_PRE_IS_SURVEYOR_LOGIN, Boolean.valueOf(false)).booleanValue();
    }

    public String getPersistenceKeyVendorStallFile() {
        return getStringValueForKey(PERSISTENCE_KEY_VENDOR_STALL_FILE,"");
    }
    public void setPersistenceKeyVendorStallFile(String file) {
        setStringValueForKey(PERSISTENCE_KEY_VENDOR_STALL_FILE, file);
    }

    public void setlanguage(String languageCode) {
        setStringValueForKey(PERSISTENCE_KEY_SELECT_LANGUAGE, languageCode);
    }

    public String getlanguage() {
        return getStringValueForKey(PERSISTENCE_KEY_SELECT_LANGUAGE, "en");
    }

    public void setUserData(String data) {
        setStringValueForKey(PERSISTENCE_KEY_USER_PROFILE_DATA, data);
    }

    public String getUserData() {
        return getStringValueForKey(PERSISTENCE_KEY_USER_PROFILE_DATA, "");
    }
}
