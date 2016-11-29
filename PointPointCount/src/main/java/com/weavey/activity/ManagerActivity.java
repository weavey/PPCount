package com.weavey.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.weavey.base.BaseActivity;
import com.weavey.msc.XunFeiVoiceAction;
import com.zqu.weavey.ppcount.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ManagerActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.manager_recycleview)
    RecyclerView recycleview;

    private Context context;



    @Override
    public void initRootView(Bundle savedInstanceState) {

        setContentView(R.layout.act_manager);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        context = this;

    }

    @Override
    public void initView() {



    }

    @Override
    public void presenterDo() {

    }


}
