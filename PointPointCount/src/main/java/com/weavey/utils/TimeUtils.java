package com.weavey.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by Weavey
 * on date 2016-03-25
 */
public class TimeUtils {

    private static TimeUtils instance=null;
    private String time;
    private SimpleDateFormat sdf;

    public static TimeUtils getInstance(){

        if(instance == null){

            synchronized (TimeUtils.class){
                if(instance == null){

                    instance = new TimeUtils();
                }

            }
        }

        return instance;
    }

    public String getDate(){

        sdf = new SimpleDateFormat("yyyy-MM-dd");

        time = sdf.format(new Date());

        return time;

    }

    public String getDateAndTime(){

        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        time = sdf.format(new Date());

        return time;
    }


}
