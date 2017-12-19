package com.testapp.socialuserinfo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;

    @Inject
    public MySharedPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences("private_prefs", Context.MODE_PRIVATE);
    }

    @SuppressLint("ApplySharedPref")
    public void putInt(String key, int data) {
        mSharedPreferences.edit().putInt(key, data).commit();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }
}
