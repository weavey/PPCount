package com.weavey.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weavey.base.BaseApplication;
import com.zqu.weavey.ppcount.R;

/**
 * create by Weavey
 * on date 2016-01-22
 * TODO
 */
public class ToastUtils {

    private static ToastUtils instance;
    private static Toast toast = null;
    private static Context context;
    private static View toastLayout;
    private static ImageView image;
    private static TextView tv;


    private static void initToast(){

        context = BaseApplication.getApplication();
        toast = new Toast(context);
        toastLayout = LayoutInflater.from(context).inflate(R.layout.base_toast,null);
        image = (ImageView)toastLayout.findViewById(R.id.toast_image);
        tv = (TextView) toastLayout.findViewById(R.id.toast_textview);
        toast.setGravity(Gravity.BOTTOM,0,UiUtils.dp2px(80));
        toast.setView(toastLayout);
    }

    public static void showLong(String str){

        if(toast ==null)
            initToast();
        toast.setDuration(Toast.LENGTH_SHORT);
        tv.setText(str);
        toast.show();

    }

    public static void showShort(String str){

        if(toast ==null)
            initToast();
        toast.setDuration(Toast.LENGTH_SHORT);
        tv.setText(str);
        toast.show();
    }

}
