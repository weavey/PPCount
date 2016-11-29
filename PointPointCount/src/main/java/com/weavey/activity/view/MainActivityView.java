package com.weavey.activity.view;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.weavey.bean.CityBean;
import com.weavey.litepal.NotesType;

import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-24
 */
public interface MainActivityView {


    void setTabLayoutTitle(List<NotesType> list, List<Fragment> listFrg);

    void setCityName(String name);

    void startAct(Intent intent);

    void showDialog();

}
