package com.salesforce.snapinssdkexample.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {
    @SafeVarargs
    public static <T> List<T> asMutableList(T... ts) {
        List<T> result = new ArrayList<T>();
        Collections.addAll(result, ts);
        return result;
    }

    static String getStringPref(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    public static Boolean getBooleanPref(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
    }
}
