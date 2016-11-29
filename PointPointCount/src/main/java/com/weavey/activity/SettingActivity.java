package com.weavey.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weavey.activity.view.SettingView;
import com.weavey.base.BaseActivity;
import com.weavey.presenter.SettingActicityPresenter;
import com.zqu.weavey.ppcount.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

public class SettingActivity extends BaseActivity implements SettingView {

    @Bind(R.id.setting_toolbar)
    Toolbar toolbar;
    @Bind(R.id.setting_message)
    TextView message;
    @Bind(R.id.setting_first_pwd)
    EditText firstPwd;
    @Bind(R.id.setting_second_pwd)
    EditText secondPwd;

    private SettingActicityPresenter presenter;
    private MaterialDialog dialog;
    private Context context;

    @Override
    public void initRootView(Bundle savedInstanceState) {

        setContentView(R.layout.act_setting);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        this.context = this;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    @Override
    public void presenterDo() {

        presenter = new SettingActicityPresenter(this);

    }

    @Override
    public void initView() {


    }


    @Override
    public String getFirstPassward() {

        return firstPwd.getText().toString().trim();
    }

    @Override
    public String getSecondPassward() {

        return secondPwd.getText().toString().trim();
    }

    @Override
    public void showErrorMessage(String message) {

        this.message.setText(message);

    }

    @Override
    public void showSuccessDialog(String message, String right) {

        dialog = new MaterialDialog(context).setCanceledOnTouchOutside(true)
                .setMessage(message).setPositiveButton(right, new View
                        .OnClickListener() {


            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();

    }


    @Override
    public void clearPassward() {

        firstPwd.setText("");
        secondPwd.setText("");

    }


    @OnClick(R.id.setting_confirm)
    public void onClick() {

        presenter.savePassward();
    }
}
