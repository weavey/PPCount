package com.weavey.activity.view;

/**
 * create by Weavey
 * on date 2016-03-28
 */
public interface LockedView {

    public String getPassward();

    public void setPassward(String pwd);

    public void setRadioChecked(int position,boolean checked);

    public void resetRadioChecked();

    public void setErrorMessage(String message);

    public void startIntent();

}
