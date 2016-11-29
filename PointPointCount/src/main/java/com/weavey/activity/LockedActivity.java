package com.weavey.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.weavey.activity.view.LockedView;
import com.weavey.base.BaseActivity;
import com.weavey.base.BaseTag;
import com.weavey.presenter.LockedPresenter;
import com.weavey.utils.LogUtils;
import com.zqu.weavey.ppcount.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LockedActivity extends BaseActivity implements LockedView{

    @Bind(R.id.act_locked_toolbar)
    Toolbar toolbar;
    @Bind(R.id.locked_radio1)
    RadioButton lockedRadio1;
    @Bind(R.id.locked_radio2)
    RadioButton lockedRadio2;
    @Bind(R.id.locked_radio3)
    RadioButton lockedRadio3;
    @Bind(R.id.locked_radio4)
    RadioButton lockedRadio4;
    @Bind(R.id.locked_num1)
    Button lockedNum1;
    @Bind(R.id.locked_num2)
    Button lockedNum2;
    @Bind(R.id.locked_num3)
    Button lockedNum3;
    @Bind(R.id.locked_num4)
    Button lockedNum4;
    @Bind(R.id.locked_num5)
    Button lockedNum5;
    @Bind(R.id.locked_num6)
    Button lockedNum6;
    @Bind(R.id.locked_num7)
    Button lockedNum7;
    @Bind(R.id.locked_num8)
    Button lockedNum8;
    @Bind(R.id.locked_num9)
    Button lockedNum9;
    @Bind(R.id.locked_num0)
    Button lockedNum0;
    @Bind(R.id.locked_delete)
    LinearLayout lockedDelete;
    @Bind(R.id.act_locked_input_text)
    TextView lockedInputText;

    private Context context;
    private Intent intent;
    private ArrayList<RadioButton> radioList;
    public String pwd = "";
    private LockedPresenter presenter;
    private String fromTag;

    @Override
    public void initRootView(Bundle savedInstanceState) {

        setContentView(R.layout.act_locked);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        context = this;
        presenter = new LockedPresenter(this);
        fromTag = getIntent().getStringExtra(BaseTag.MAIN_ACTIVITY);

    }

    @Override
    public void initView() {

        radioList = new ArrayList<>();
        radioList.add(lockedRadio1);
        radioList.add(lockedRadio2);
        radioList.add(lockedRadio3);
        radioList.add(lockedRadio4);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void presenterDo() {


    }

    @OnClick({R.id.locked_num1, R.id.locked_num2, R.id.locked_num3, R.id
            .locked_num4, R.id.locked_num5, R.id.locked_num6, R.id
            .locked_num7, R.id.locked_num8, R.id.locked_num9, R.id
            .locked_num0, R.id.locked_delete})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.locked_num1:
                pwd += "1";
                break;
            case R.id.locked_num2:
                pwd += "2";
                break;
            case R.id.locked_num3:
                pwd += "3";
                break;
            case R.id.locked_num4:
                pwd += "4";
                break;
            case R.id.locked_num5:
                pwd += "5";
                break;
            case R.id.locked_num6:
                pwd += "6";
                break;
            case R.id.locked_num7:
                pwd += "7";
                break;
            case R.id.locked_num8:
                pwd += "8";
                break;
            case R.id.locked_num9:
                pwd += "9";
                break;
            case R.id.locked_num0:
                pwd += "0";
                break;
            case R.id.locked_delete:
                presenter.changePwd();
                return;
        }
        presenter.checkPwd();
    }


    @Override
    public String getPassward() {

        return pwd;
    }

    public void setPassward(String pwd){

        this.pwd = pwd;
    }

    @Override
    public void setRadioChecked(int position,boolean checked) {

        radioList.get(position).setChecked(checked);
    }

    @Override
    public void resetRadioChecked() {

        this.pwd = "";
        for (RadioButton button : radioList) {
            button.setChecked(false);
        }
    }

    @Override
    public void setErrorMessage(String message) {

        lockedInputText.setText(message);

    }


    @Override
    public void startIntent() {

        if(fromTag!=null){

            if(fromTag.equals(BaseTag.TO_NOTES_DETAIL)){
                intent = new Intent(context, SecretNotesActivity.class);
            }
            else{
                intent = new Intent(context, SettingActivity.class);
            }

        }
        startActivity(intent);
        finish();

    }
}
