package com.weavey.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.weavey.base.BaseApplication;

public class ViewUtils {

    public static View inflate(int id) {

        View view = LayoutInflater.from(BaseApplication.getApplication())
                .inflate(id, null);

        return view;
    }

    //将view转化为context
    public static <T extends Context> T getContext(Object object){

        return (T)object;
    }

    public static <T extends View>T findViewById(View v,int id){


        return (T) v.findViewById(id);
    }

}