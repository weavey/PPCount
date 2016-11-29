package com.weavey.presenter;

import com.weavey.activity.view.SettingView;
import com.weavey.base.BaseTag;
import com.weavey.model.PasswardModel;
import com.weavey.utils.SharePreferenceUtils;

/**
 * create by Weavey
 * on date 2016-03-27
 */
public class SettingActicityPresenter {

    private SettingView view;
    private PasswardModel model;

    public SettingActicityPresenter(SettingView view) {

        this.view = view;
        model = new PasswardModel();

    }

    public void savePassward() {

        if (view.getFirstPassward().equals(view.getSecondPassward())) {

            if (SharePreferenceUtils.getInstance().get(BaseTag.IS_SET_PWD,
                    false)) {

                model.updatePassward(model.getPassward().getId(), view
                        .getFirstPassward());
                view.showSuccessDialog("修改密码成功！", "确定");

            } else {
                model.wirtePassward(view.getFirstPassward());
                view.showSuccessDialog("创建密码成功！", "确定");
                SharePreferenceUtils.getInstance().commit(BaseTag.IS_SET_PWD,
                        true);
            }

        } else {
            view.clearPassward();
            view.showErrorMessage("两次密码输入不一致！请重新输入···");
        }

    }
}
