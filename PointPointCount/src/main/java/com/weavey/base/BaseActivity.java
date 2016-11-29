package com.weavey.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.weavey.utils.LogUtils;
import com.zqu.weavey.ppcount.R;

/**
 * create by Weavey
 * on date 2016-03-21
 */
public abstract class BaseActivity extends AppCompatActivity{

    private boolean isDelayLoad = true;// 是否异步加载
    private Handler handler = new Handler();
    private Runnable loadRunnable = new Runnable() {

        @Override
        public void run() {

            initView(); // 初始化布局

            presenterDo(); //初始化参数
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        doBeforeSetcontentView();
        initRootView(savedInstanceState);
        doAfterSetcontentView();

        // 即APP第二帧在屏幕上显示时异步加载数据（异步加载数据最佳时机）
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                handler.post(loadRunnable);
            }
        });

    }

    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        ((BaseApplication) getApplication()).addActivity(this);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 4.4以上系统设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 透明导航栏
//			getWindow().addFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void doAfterSetcontentView(){

        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
//        tintManager.setNavigationBarTintEnabled(true);

        tintManager.setStatusBarTintColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
//        tintManager.setNavigationBarTintColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    public abstract void initRootView(Bundle savedInstanceState);

    public abstract void initView();

    public abstract void presenterDo();







}
