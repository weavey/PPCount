package com.weavey.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.weavey.base.BaseActivity;
import com.zqu.weavey.ppcount.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity{

    @Bind(R.id.about_toolbar)
    Toolbar toolbar;


    @Override
    public void initRootView(Bundle savedInstanceState) {

        setContentView(R.layout.act_about);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void presenterDo() {

    }

}
