package com.weavey.base;

import android.content.pm.ActivityInfo;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zqu.weavey.ppcount.R;
import com.weavey.utils.ScreenSizeUtils;

/**
 * 基类FragmentActivity
 *
 * @author jayden 2015年7月22日
 */
public abstract class BaseTopBarActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup childView;
    private View topBar;
    private LinearLayout.LayoutParams lp;
    private Button leftButton;
    private Button rightButton;
    private TextView tv;
    private LinearLayout root;
    private int paddingTop;

    // Android应用启动优化:一种DelayLoad的实现和原理（异步加载）
    private boolean isDelayLoad = true;// 是否异步加载
    private Handler handler = new Handler();
    private Runnable loadRunnable = new Runnable() {

        @Override
        public void run() {

            initView(); // 初始化布局
            // 加载数据
            initData();
        }

    };

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        doBeforeSetcontentView();  // 设置setcontentview之前
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        root.setLayoutParams(lp);

        setTopbar();     //初始化顶部菜单栏
        initTopbar();
        setContentLayout();
        this.setContentView(root);

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
        // 无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 4.4以上系统设置沉浸式状态栏
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            // 透明状态栏,还要自定义的NormalTopBar增加相应高度和paddingTop
            paddingTop = ScreenSizeUtils.getInstance().getStatusBarHeight();
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
//			getWindow().addFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void setTopbar() {

        // 获取Activity最外层父容器
        // childView =(ViewGroup)
        // ((ViewGroup)this.findViewById(android.R.id.content)).getChildAt(0);
        topBar = LayoutInflater.from(this)
                .inflate(R.layout.base_topbar, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        topBar.setPadding(0,paddingTop,0,0);

        leftButton = (Button) topBar.findViewById(R.id.top_leftbutton);
        rightButton = (Button) topBar.findViewById(R.id.top_rightbutton);
        tv = (TextView) topBar.findViewById(R.id.top_title);

        leftButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                leftButtonListener();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                rightButtonListener();
            }
        });

        root.addView(topBar);
    }

    public abstract void initTopbar();

    public abstract void setContentLayout();

    public abstract void initView();

    public abstract void initData();

    public void setMyContentView(View v) {

        root.addView(v, lp);
    }

    public void setMyContentView(int view) {

        View v = LayoutInflater.from(this).inflate(view, null);
        root.addView(v, lp);
    }

    public void setTitle(String title) {

        tv.setText(title.toString().trim());
    }

    public void setLeftButtonText(String text) {

        leftButton.setText(text.toString().trim());
    }

    public void setRightButtonText(String text) {

        rightButton.setText(text.toString().trim());
    }

    public void setLeftButtonVisiable(Boolean bool) {

        if (!bool)
            leftButton.setVisibility(View.INVISIBLE);
    }

    public void setRightButtonVisiable(Boolean bool) {

        if (!bool)
            rightButton.setVisibility(View.INVISIBLE);
    }

    public Button getLeftButton() {
        return leftButton;
    }

    public Button getRightButton() {

        return rightButton;
    }

    public TextView getTopTitleView() {

        return tv;
    }

    public void leftButtonListener() {

        finish();

    }

    public void rightButtonListener() {

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((BaseApplication) getApplication()).removeActivity(this);
    }
}
