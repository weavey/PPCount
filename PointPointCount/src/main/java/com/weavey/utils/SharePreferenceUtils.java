package com.weavey.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.weavey.base.BaseApplication;

/**
 * create by Weavey
 * on date 2016-03-24
 */
public class SharePreferenceUtils {

    private static SharePreferenceUtils instance = null;

    private static SharedPreferences sharedPreferences;

    public static SharePreferenceUtils getInstance() {


        if (instance == null) {

            synchronized (SharePreferenceUtils.class) {

                if (instance == null) {
                    instance = new SharePreferenceUtils();
                    sharedPreferences = BaseApplication.getApplication()
                            .getSharedPreferences("ppcdata", Activity
                                    .MODE_PRIVATE);
                }
            }

        }

        return instance;
    }

    public void commit(String key, String value) {

        sharedPreferences.edit().putString(key, value).commit();

    }

    public void commit(String key, boolean value) {

        sharedPreferences.edit().putBoolean(key, value).commit();

    }

    public void commit(String key, int value) {

        sharedPreferences.edit().putInt(key, value).commit();

    }

    public String get(String key, String def) {

        return sharedPreferences.getString(key, def);
    }

    public boolean get(String key, boolean def) {

        return sharedPreferences.getBoolean(key, def);
    }

    public int get(String key, int def) {

        return sharedPreferences.getInt(key, def);
    }


}
