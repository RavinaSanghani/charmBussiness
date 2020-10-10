package com.charm.business;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {


    private String PREFERENCE_NAME = "CharmBusiness";

    public static String KEY_REGISTRATION_STEP = "registration_step";
    public static String KEY_EMPLOYEE_ID = "employeeID";
    public static String KEY_LOGIN_TOKEN = "loginToken";
    public static String KEY_PROFILE_SELECT = "profileSelect";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context){
        if (context != null){
            sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void putInteger(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key,long value){
        editor.putLong(key,value);
        editor.commit();
    }

    public void deleteUserPrefData(){
/*

        editor.putString(PrefManager.KEY_USERID, "");
        editor.putString(PrefManager.KEY_USERNAME, "");
        editor.putString(PrefManager.KEY_EMAIL, "");
        editor.putString(PrefManager.KEY_NAME, "");
        editor.putString(PrefManager.KEY_FIRST_NAME, "");
        editor.putString(PrefManager.KEY_LAST_NAME, "");
        editor.putString(PrefManager.KEY_USER_IMAGE, "");
        editor.putString(PrefManager.KEY_FACEBOOK_ID, "");
        editor.putString(PrefManager.KEY_GOOGLE_ID, "");
        editor.putString(PrefManager.KEY_GENDER, "");
        editor.putString(PrefManager.KEY_USER_SAVED_LANGUAGES, "");
        editor.commit();
*/

    }
    public int getInteger(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

}
