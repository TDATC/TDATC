package com.skoruz.amwell.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.Editor;

import com.skoruz.amwell.BuildConfig;

/**
 * Created by SKoruz-Keerthi on 12-11-2015.
 */
public class PreferenceUtils {
    public static final String DONT_SHOW_EDIT_SUCCESS_DIALOG = "dont_show_edit_success_dialog";

    public static Boolean getBooleanPrefs(Context ctx, String key) {
        return Boolean.valueOf(PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean(key, false));
    }

    public static String getStringPrefs(Context ctx, String key) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, BuildConfig.FLAVOR);
    }

    public static String getStringPrefs(Context ctx, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, defaultValue);
    }

    public static int getIntegerPrefs(Context ctx, String key) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(key, 0);
    }

    public static int getIntegerPrefs(Context ctx, String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getInt(key, defaultValue);
    }

    public static long getLongPrefs(Context ctx, String key) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getLong(key, 0);
    }

    public static long getLongPrefs(Context ctx, String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getLong(key, (long) defaultValue);
    }

    public static float getFloatPrefs(Context ctx, String key) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getFloat(key, 0.0f);
    }

    public static void set(Context context, String key, Object value) {
        Editor sharedPreferenceEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        if (value instanceof String) {
            sharedPreferenceEditor.putString(key, (String) value);
        } else if (value instanceof Long) {
            sharedPreferenceEditor.putLong(key, ((Long) value).longValue());
        } else if (value instanceof Integer) {
            sharedPreferenceEditor.putInt(key, ((Integer) value).intValue());
        } else if (value instanceof Boolean) {
            sharedPreferenceEditor.putBoolean(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Float) {
            sharedPreferenceEditor.putFloat(key, ((Float) value).floatValue());
        }
        sharedPreferenceEditor.apply();
    }
}
