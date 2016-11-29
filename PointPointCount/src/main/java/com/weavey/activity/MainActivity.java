package com.weavey.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.weavey.activity.view.MainActivityView;
import com.weavey.adapter.ViewPagerAdapter;
import com.weavey.base.BaseActivity;
import com.weavey.base.BaseApplication;
import com.weavey.base.BaseTag;
import com.weavey.bean.CityBean;
import com.weavey.bean.NotesAction;
import com.weavey.fragment.NotesNormalFrgment;
import com.weavey.litepal.NotesType;
import com.weavey.presenter.MainActivityPresenter;
import com.weavey.umeng.SharedUtils;
import com.weavey.utils.LogUtils;
import com.weavey.utils.SharePreferenceUtils;
import com.weavey.utils.TimeUtils;
import com.weavey.utils.ToastUtils;
import com.weavey.weather.Weather2;
import com.zqu.weavey.ppcount.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends BaseActivity implements MainActivityView,
        RippleView.OnRippleCompleteListener {


    @Bind(R.id.main_appbar_layout)
    AppBarLayout appbarLayout;

    @Bind(R.id.main_toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.main_toolbar)
    Toolbar toolbar;

    @Bind(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.main_viewpager)
    ViewPager viewpager;

    @Bind(R.id.main_tablayout)
    TabLayout tablayout;

    @Bind(R.id.main_drawer_address)
    TextView mainDrawerAddress;

    @Bind(R.id.main_drawer_weather)
    TextView mainDrawerWeather;

    @Bind(R.id.main_drawer_date)
    TextView mainDrawerDate;

    @Bind(R.id.main_drawer_dot)
    TextView mainDrawerDot;

    @Bind(R.id.main_fab_edit)
    FloatingActionButton mainFabEdit;

    @Bind(R.id.main_fab_secret)
    FloatingActionButton mainFabSecret;

    @Bind(R.id.main_fab_switch)
    FloatingActionButton mainFabSwitch;

    @Bind(R.id.main_fab_menu)
    FloatingActionsMenu mainFabMenu;

    @Bind(R.id.drawer_share_friend)
    RippleView drawerShareFriend;

    @Bind(R.id.drawer_share_circle)
    RippleView drawerShareCircle;

    @Bind(R.id.drawer_classify)
    RippleView drawerClassify;

    @Bind(R.id.drawer_setting)
    RippleView drawerSetting;

    private ActionBarDrawerToggle toggle;

    private MainActivityPresenter presenter;

    private Context context;

    private List<NotesType> typeList;

    private Intent intent;

    private MaterialDialog dialog;

    private MaterialDialog codebar_dialog;


    @Override
    public void initRootView(Bundle savedInstanceState) {

        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        context = this;
    }

    @Override
    public void initView() {

        initToolBar();
        initDrawerLeft();
        initDialog();

    }

    @Override
    public void presenterDo() {


        presenter = new MainActivityPresenter(this);
        presenter.startBaiduLocating();
        presenter.getNotesType();
        presenter.getWeather();

    }

    private void initToolBar() {

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R
                .string.navigation_drawer_open, R.string
                .navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                if (mainFabMenu.isExpanded()) {
                    mainFabMenu.collapse();
                }
                appbarLayout.setExpanded(false);

            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void initDrawerLeft() {

        mainDrawerDate.setText(TimeUtils.getInstance().getDate());
        drawerShareFriend.setOnRippleCompleteListener(this);
        drawerShareCircle.setOnRippleCompleteListener(this);
        drawerClassify.setOnRippleCompleteListener(this);
        drawerSetting.setOnRippleCompleteListener(this);

    }


    private void initDialog() {

        dialog = new MaterialDialog(context).setTitle("温馨提示").setMessage
                ("您还未设置访问密码，请先设置密码!").setNegativeButton("取消", new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        }).setPositiveButton("前往设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                presenter.startActivity(BaseTag.SettingIntent);
            }
        }).setCanceledOnTouchOutside(true);


        codebar_dialog = new MaterialDialog(context)
                .setCanceledOnTouchOutside(true).
                        setContentView(R.layout.widget_barcode_dialog);

    }

    @OnClick({R.id.main_fab_edit, R.id.main_fab_secret, R.id.main_fab_switch})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.main_fab_edit:

                presenter.startActivity(BaseTag.EditNotesIntent);

                break;

            case R.id.main_fab_secret:

                presenter.startActivity(BaseTag.LockedIntent);
                break;

            case R.id.main_fab_switch:

                presenter.switchView(viewpager.getCurrentItem(), typeList);

                break;
        }
        mainFabMenu.collapse();
    }

    @Override
    public void onComplete(RippleView rippleView) {

        switch (rippleView.getId()) {

            case R.id.drawer_share_friend:

                SharedUtils.getInstance(context).sharedWX();

                break;

            case R.id.drawer_share_circle:

                SharedUtils.getInstance(context).sharedWXCircle();
                break;

            case R.id.drawer_classify:

                break;

            case R.id.drawer_setting:

                presenter.startActivity(BaseTag.SettingIntent);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.action_barcode:

                codebar_dialog.show();
                break;

            case R.id.action_about:

                presenter.startActivity(BaseTag.AboutIntent);

                break;

            case R.id.action_help:

                presenter.startActivity(BaseTag.HelpIntent);
                break;

            case R.id.action_exit:

                ((BaseApplication) getApplication()).closeApplication();

                break;

        }

        if (mainFabMenu.isExpanded()) {
            mainFabMenu.collapse();
        }
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTabLayoutTitle(List<NotesType> list, List<Fragment>
            listFrg) {

        typeList = list;
        viewpager.setAdapter(new ViewPagerAdapter
                (getSupportFragmentManager(), listFrg, list));
        tablayout.setupWithViewPager(viewpager);

    }

    @Override
    public void setCityName(String name) {

        mainDrawerAddress.setText(name);

    }

    @Override
    public void startAct(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showDialog() {
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeCuttentItem(NotesAction action) {

        if (action.getSelectFragment() >= 0)
            viewpager.setCurrentItem(action.getSelectFragment());


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCity(CityBean bean) {

        presenter.setCityName(bean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setMyWeather(Weather2 weather) {

        if (weather.getInfo() != null && !weather.getInfo().equals("")) {
            mainDrawerWeather.setText(weather.getInfo());
            mainDrawerDot.setVisibility(View.VISIBLE);
            SharePreferenceUtils.getInstance().commit(BaseTag.WEATHER,
                    weather.getInfo());
        } else {
            if (!SharePreferenceUtils.getInstance().get(BaseTag.WEATHER,
                    BaseTag.UNKNOWN_WEATHER).equals(BaseTag.UNKNOWN_WEATHER)) {
                mainDrawerWeather.setText(SharePreferenceUtils.getInstance()
                        .get(BaseTag.WEATHER,
                        BaseTag.UNKNOWN_WEATHER));
                mainDrawerDot.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.stopBaiduLocating();
    }

}
