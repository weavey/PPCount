package com.weavey.activity.view;

/**
 * create by Weavey
 * on date 2016-03-27
 */
public interface SettingView {

    public String getFirstPassward();

    public String getSecondPassward();

    public void showErrorMessage(String message);

    public void showSuccessDialog(String message,String right);

    public void clearPassward();


}
