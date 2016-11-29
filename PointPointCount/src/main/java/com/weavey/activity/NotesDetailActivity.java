package com.weavey.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.iflytek.cloud.SpeechSynthesizer;
import com.weavey.activity.view.NotesDetailView;
import com.weavey.base.BaseActivity;
import com.weavey.base.BaseTag;
import com.weavey.litepal.Notes;
import com.weavey.manager.NetworkManager;
import com.weavey.msc.XunFeiVoiceAction;
import com.weavey.presenter.NotesDetailPresenter;
import com.weavey.utils.LogUtils;
import com.weavey.utils.ToastUtils;
import com.zqu.weavey.ppcount.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;


public class NotesDetailActivity extends BaseActivity implements
        NotesDetailView {


    @Bind(R.id.act_detail_toolbar)
    Toolbar detailToolbar;
    @Bind(R.id.act_detail_collapsing_layout)
    CollapsingToolbarLayout detailCollapsingLayout;
    @Bind(R.id.act_detail_barlayout)
    AppBarLayout detailBarlayout;
    @Bind(R.id.act_detail_time)
    TextView detailTime;
    @Bind(R.id.act_detail_title)
    TextView detailTitle;
    @Bind(R.id.act_detail_address)
    TextView detailAddress;
    @Bind(R.id.act_detail_weather)
    TextView detailWeather;
    @Bind(R.id.act_detail_content)
    TextView detailContent;
    @Bind(R.id.act_detail_fab)
    FloatingActionButton mscFab;

    private Context context;
    private NotesDetailPresenter presenter;
    private MaterialDialog dialog;
    private Notes notes;
    private int frgTag;
    private int fromTag;
    private XunFeiVoiceAction action;
    private SpeechSynthesizer speaker;
    private boolean isReading;

    @Override
    public void initRootView(Bundle savedInstanceState) {

        setContentView(R.layout.act_notes_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setSupportActionBar(detailToolbar);
        this.context = this;
        this.presenter = new NotesDetailPresenter(this);
        action = XunFeiVoiceAction.getInstance();
        action.initXunFeiAction(context);
        speaker = action.getSpeaker();
        isReading = false;
    }


    @Override
    public void initView() {

        detailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        frgTag = getIntent().getIntExtra(BaseTag.NOTES_FRG, -1);
        //从Fragment进来的才需要，修改或删除都要更新主界面的fragment

        notes = getIntent().getParcelableExtra(BaseTag.NOTES_BEAN_KEY);
        //bean  必须有

        fromTag = getIntent().getIntExtra(BaseTag.Edit_From_Tag, -1);
        //从fragment或者秘密记事进来的tag


        dialog = new MaterialDialog(context).setMessage("删除后数据将不可恢复，确定删除？")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (fromTag == 2) {
                            presenter.deleteNotes(notes.getId());
                            presenter.updateFragment(frgTag);
                        } else if (fromTag == 3) {

                            presenter.deleteNotes(notes.getId());
                            presenter.updateSecretAct();

                        }
                        dialog.dismiss();
                        finish();
                    }
                });

    }

    @Override
    public void presenterDo() {

        presenter.initViewData(notes);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.action_detail_delete:
                dialog.show();
                break;
            case R.id.action_detail_modify:

                Intent intent = new Intent(context, EditNotesActivity.class);
                intent.putExtra(BaseTag.NOTES_BEAN_KEY, notes);
                intent.putExtra(BaseTag.Edit_From_Tag, fromTag);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(String s) {

        detailTitle.setText(s);
    }

    @Override
    public void setContent(String s) {

        detailContent.setText(s);
    }

    @Override
    public void setTime(String s) {

        detailTime.setText(s);
    }

    @Override
    public void setAddress(String s) {

        detailAddress.setText(s);
    }

    @Override
    public void setWeather(String s) {

        detailWeather.setText(s);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateView(Notes bean) {

        this.notes = bean;
        presenter.initViewData(bean);
        if (fromTag == 2) {
            presenter.updateFragment(frgTag);
        } else {
            presenter.updateSecretAct();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        speaker.stopSpeaking();
        speaker.destroy();
    }


    @OnClick(R.id.act_detail_fab)
    public void onClick() {
//
//        if (isReading) {
//
//            speaker.stopSpeaking();
//        } else {
        if (NetworkManager.getInstance().isNetworkConnected(context)) {
            ToastUtils.showShort("正在翻译，请稍等···");
            if (!detailContent.getText().equals("")) {
                action.speaking(detailContent.getText().toString());
            }
        } else {
            ToastUtils.showShort("当前无网络连接，请检查您的网络···");
        }


//        }

    }


}
