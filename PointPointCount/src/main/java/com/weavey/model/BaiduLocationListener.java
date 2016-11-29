package com.weavey.model;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.weavey.bean.CityBean;
import com.weavey.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * create by Weavey
 * on date 2016-03-23
 */
public class BaiduLocationListener implements BDLocationListener {

    CityBean bean;

    @Override
    public void onReceiveLocation(BDLocation location) {

        bean = new CityBean();

        if (location.getLocType() == BDLocation.TypeGpsLocation) {
            // GPS定位结果
            bean.setIsLocated(true);
            bean.setCity(location.getCity());
            bean.setCityCode(location.getCityCode());
            bean.setLocatedType("GPS定位");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
            // 网络定位结果
            bean.setIsLocated(true);
            bean.setCity(location.getCity());
            bean.setCityCode(location.getCityCode());
            bean.setLocatedType("网络定位");

        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
            // 离线定位结果
            bean.setIsLocated(true);
            bean.setCity(location.getCity());
            bean.setCityCode(location.getCityCode());
            bean.setLocatedType("离线定位");

        } else if (location.getLocType() == BDLocation.TypeServerError) {

            bean.setIsLocated(false);
            bean.setLocatedType("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu" +
                    ".com，会有人追查原因");

        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

            bean.setIsLocated(false);
            bean.setLocatedType("网络不同导致定位失败，请检查网络是否通畅");

        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

            bean.setIsLocated(false);
            bean.setLocatedType
                    ("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

        }

        EventBus.getDefault().post(bean);

    }


}
