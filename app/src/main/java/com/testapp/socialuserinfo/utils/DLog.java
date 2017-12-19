package com.testapp.socialuserinfo.utils;

import android.text.TextUtils;
import android.util.Log;

import com.testapp.socialuserinfo.BuildConfig;

public class DLog {

    private static final String TAG = "Merov";

    private DLog() {
    }

    public static void i(String tag, String string) {
        if (BuildConfig.DEBUG) Log.i(tag, checkNotNull(string));
    }

    public static void e(String tag, String string) {
        if (BuildConfig.DEBUG) Log.e(tag, checkNotNull(string));
    }

    public static void e(String tag, Exception exception) {
        if (BuildConfig.DEBUG) Log.e(tag, checkNotNull(exception.getMessage()));
    }

    public static void e(String tag, String string, Throwable tr) {
        if (BuildConfig.DEBUG) Log.e(tag, checkNotNull(string), tr);
    }

    public static void d(String tag, String string) {
        if (BuildConfig.DEBUG) Log.d(tag, checkNotNull(string));
    }

    public static void d(String tag, String string, Throwable tr) {
        if (BuildConfig.DEBUG) Log.d(tag, checkNotNull(string), tr);
    }

    public static void v(String tag, String string) {
        if (BuildConfig.DEBUG) Log.v(tag, checkNotNull(string));
    }

    public static void w(String tag, String string) {
        if (BuildConfig.DEBUG) Log.w(tag, checkNotNull(string));
    }

    public static void i(String string) {
        if (BuildConfig.DEBUG) Log.i(TAG, checkNotNull(string));
    }

    public static void e(String string) {
        if (BuildConfig.DEBUG) Log.e(TAG, checkNotNull(string));
    }

    public static void e(Exception exception) {
        if (BuildConfig.DEBUG) Log.e(TAG, checkNotNull(exception.getMessage()));
    }

    public static void e(String string, Throwable tr) {
        if (BuildConfig.DEBUG) Log.e(TAG, checkNotNull(string), tr);
    }

    public static void d(String string) {
        if (BuildConfig.DEBUG) Log.d(TAG, checkNotNull(string));
    }

    public static void d(String string, Throwable tr) {
        if (BuildConfig.DEBUG) Log.d(TAG, checkNotNull(string), tr);
    }

    public static void v(String string) {
        if (BuildConfig.DEBUG) Log.v(TAG, checkNotNull(string));
    }

    public static void w(String string) {
        if (BuildConfig.DEBUG) Log.w(TAG, checkNotNull(string));
    }

    private static String checkNotNull(String string) {
        return TextUtils.isEmpty(string) ? "string for log is null" : string;
    }

}
