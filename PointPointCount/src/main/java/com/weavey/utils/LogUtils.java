package com.weavey.utils;

import android.util.Log;

/**
 * create by Weavey
 * on date 2016-01-06
 * TODO
 */
public class LogUtils {

    private final static String LogTag = "mytag";
    private static boolean isOutputLog = false;

    public static void setOutputLog(boolean bool){

        isOutputLog = bool;
    }

    public static void log(Object object,String log) {

        if (isOutputLog) {
            Log.i(LogTag, object.getClass().toString()+"-->"+log);
        }
    }



}
