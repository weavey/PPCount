package com.weavey.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.weavey.activity.AboutActivity;
import com.weavey.activity.EditNotesActivity;
import com.weavey.activity.HelpActivity;
import com.weavey.activity.LockedActivity;
import com.weavey.activity.SettingActivity;
import com.weavey.activity.view.MainActivityView;
import com.weavey.base.BaseTag;
import com.weavey.bean.CityBean;
import com.weavey.fragment.NotesNormalFrgment;
import com.weavey.litepal.NotesType;
import com.weavey.model.BaiduLocationListener;
import com.weavey.model.NotesTypeModel;
import com.weavey.model.WeatherModel;
import com.weavey.utils.LogUtils;
import com.weavey.utils.SharePreferenceUtils;
import com.weavey.utils.ViewUtils;
import com.weavey.weather.Weather2;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-24
 * <p/>
 * view 只能一个 model可以多个
 */
public class MainActivityPresenter {

    private MainActivityView view;

    private LocationClient mLocationClient = null;  //第一个model
    private BDLocationListener myListener;
    private NotesTypeModel mainModel;
    private WeatherModel weatherModel;
    private Intent intent;
    private Context context;

    public MainActivityPresenter(MainActivityView view) {

        this.view = view;
        //百度定位
        mLocationClient = new LocationClient(ViewUtils.getContext(view));
        mainModel = new NotesTypeModel();
        weatherModel  = new WeatherModel();
        this.context = ViewUtils.getContext(view);
    }


    public void startBaiduLocating() {

        myListener = new BaiduLocationListener();
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数

        /******************配置参数*****************************/
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode
                .Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 3600000; //1小时定位一次
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        /******************配置参数*****************************/

        mLocationClient.start(); //开始定位

    }

    public void stopBaiduLocating() {

        mLocationClient.stop();
    }

    public void getNotesType() {

        List<NotesType> list;
        list = mainModel.getNotesType();

        if (list != null) {

            List<Fragment> listFrg = new ArrayList<Fragment>();
            NotesNormalFrgment fragment;
            Bundle bundle;

            for (NotesType type : list) {

                fragment = new NotesNormalFrgment();
                bundle = new Bundle();
                bundle.putParcelable(BaseTag.NOTES_FRG, type);
                fragment.setArguments(bundle);
                listFrg.add(fragment);
            }
            view.setTabLayoutTitle(list, listFrg);
        }

    }

    public void switchView(int currentItem, List<NotesType> typeList) {

        int switchTag = typeList.get(currentItem).getOrderType();
        switchTag = (switchTag + 1) % 3 == 0 ? 3 : (switchTag + 1) % 3;
        typeList.get(currentItem).setOrderType(switchTag);
        //修改数据库中的排序方式
        mainModel.updateNotesTypeOrder(switchTag, typeList.get(currentItem)
                .getId());
        EventBus.getDefault().post(typeList.get(currentItem));
    }

    public void setCityName(CityBean bean) {

        if (bean.getIsLocated()) {

            String c = bean.getCity().trim();
            if (!c.equals("")) {

                c = c.substring(0, c.length() - 1);
                SharePreferenceUtils.getInstance().commit(BaseTag.CITY, c);
            }
            view.setCityName(c);
        } else {

            view.setCityName(SharePreferenceUtils.getInstance().get
                    (BaseTag.CITY, "未知城市"));
        }

    }

    public void setWeather(Weather2 weather){

    }

    public void startActivity(int tag) {

        switch (tag) {


            case BaseTag.EditNotesIntent:
                intent = new Intent(context, EditNotesActivity.class);
                intent.putExtra(BaseTag.Edit_From_Tag,1);
                view.startAct(intent);
                break;

            case BaseTag.LockedIntent:

                if (SharePreferenceUtils.getInstance().get(BaseTag
                        .IS_SET_PWD, false)) {
                    intent = new Intent(context, LockedActivity.class);
                    intent.putExtra(BaseTag.MAIN_ACTIVITY, BaseTag
                            .TO_NOTES_DETAIL);
                    view.startAct(intent);
                } else {

                    view.showDialog();

                }

                break;


            case BaseTag.SettingIntent:

                if (!SharePreferenceUtils.getInstance().get(BaseTag
                        .IS_SET_PWD, false)) {
                    intent = new Intent(context, SettingActivity.class);
                } else {
                    intent = new Intent(context, LockedActivity.class);
                    intent.putExtra(BaseTag.MAIN_ACTIVITY, BaseTag.To_SETTING);
                }

                view.startAct(intent);
                break;

            case BaseTag.HelpIntent:

                intent = new Intent(context, HelpActivity.class);
                view.startAct(intent);

                break;

            case BaseTag.AboutIntent:

                intent = new Intent(context, AboutActivity.class);
                view.startAct(intent);

                break;
            default:
                return;
        }



    }

    public void getWeather(){

   weatherModel.getWeather();

        //

    }
}
