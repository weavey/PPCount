package com.weavey.activity.view;

import android.widget.EditText;

import com.weavey.litepal.NotesType;

import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-30
 */
public interface EditNotesView {

    public String getMyTitle();

    public String getContent();

    public String getTime();

    public String getAddress();

    public String getWeather();

    public String getType();

    public EditText getTitleView();

    public EditText getContentView();

    public void setTitle(String title);

    public void setContent(String content);

    public void setAddress(String address);

    public void setTime(String time);

    public void setMscText(String text);

    public void setMscImg(int flag);

    public void setMscResult(String result);

    public void setWeather(String weather);

    public void initSpinner(List<String>list);

    public int getSpinnerSelectedItemId();

    public void dealResult();

    public void showDialog();

    public void finishActivity();




}
