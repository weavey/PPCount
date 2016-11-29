package com.weavey.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.weavey.base.BaseApplication;

/**
 * 单例模式 获取屏幕宽高的帮助类
 */

public class ScreenSizeUtils {

    private WindowManager manager;
    private DisplayMetrics dm;
    private static ScreenSizeUtils instance = null;
    private int screenWidth, screenHeigth, statusBarHeigth;

    public static ScreenSizeUtils getInstance() {

        if (instance == null) {
            synchronized (ScreenSizeUtils.class) {

                if (instance == null)
                    instance = new ScreenSizeUtils();

            }
        }
        return instance;
    }

    private ScreenSizeUtils() {

        manager = (WindowManager) BaseApplication.getApplication()
                .getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        screenHeigth = dm.heightPixels;

        statusBarHeigth = 0;
        int resourceId = BaseApplication.getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeigth = BaseApplication.getApplication().getResources().getDimensionPixelSize(resourceId);
        }

    }

    public int getScreenWidth() {

        return screenWidth;
    }

    public int getScreenHeight() {

        return screenHeigth;
    }

    public int getStatusBarHeight() {

        return statusBarHeigth;
    }

}
