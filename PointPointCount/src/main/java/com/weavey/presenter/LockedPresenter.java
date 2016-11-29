package com.weavey.presenter;

import com.weavey.activity.view.LockedView;
import com.weavey.litepal.Passward;
import com.weavey.model.PasswardModel;

/**
 * create by Weavey
 * on date 2016-03-28
 */
public class LockedPresenter {

    private LockedView view;
    private PasswardModel model;

    public  LockedPresenter(LockedView view){

        this.view = view;
        model = new PasswardModel();
    }

    public void checkPwd(){

        String pwd = view.getPassward();
        Passward bean = model.getPassward();
        view.setRadioChecked(pwd.length()-1,true);
        if (pwd.trim().length() == 4) {

            if (pwd.equals(bean.getPwd())) {

                view.startIntent();
            } else {
                view.setErrorMessage("密码错误，请重新输入...");
                view.resetRadioChecked();
            }
        }
    }

    public void changePwd(){

        String s = view.getPassward();

        if (!s.equals("")) {
            s = s.substring(0, s.length() - 1);
            view.setRadioChecked(s.length(),false);
            view.setPassward(s);
        }

    }

}
