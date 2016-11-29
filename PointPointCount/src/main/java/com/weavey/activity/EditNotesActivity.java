package com.weavey.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.weavey.activity.view.EditNotesView;
import com.weavey.base.BaseActivity;
import com.weavey.base.BaseTag;
import com.weavey.litepal.Notes;
import com.weavey.manager.NetworkManager;
import com.weavey.msc.XunFeiVoiceAction;
import com.weavey.presenter.EditNotesPresenter;
import com.weavey.utils.ToastUtils;
import com.zqu.weavey.ppcount.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

public class
EditNotesActivity extends BaseActivity implements EditNotesView {


    @Bind(R.id.editnotes_toolbar)
    Toolbar toolbar;
    @Bind(R.id.notes_edit_time)
    TextView tvTime;
    @Bind(R.id.notes_edit_weather)
    TextView tvWeather;
    @Bind(R.id.notes_edit_address)
    TextView tvAddress;
    @Bind(R.id.notes_edit_title)
    EditText tvTitle;
    @Bind(R.id.notes_edit_content)
    EditText tvContent;
    @Bind(R.id.notes_edit_msc_img)
    ImageView mscImg;
    @Bind(R.id.notes_edit_msc)
    TextView tvMsc;
    @Bind(R.id.notes_edit_btn_msc)
    RelativeLayout btnMsc;
    @Bind(R.id.notes_edit_spinner)
    Spinner spinner;

    private Context context;
    private EditNotesPresenter presenter;
    private MaterialDialog dialog;
    private Notes bean = null;
    private int fromTag;


    @Override
    public void initRootView(Bundle savedInstanceState) {


        setContentView(R.layout.act_edit_notes);
        ButterKnife.bind(this);
        this.context = this;
        setSupportActionBar(toolbar);
        presenter = new EditNotesPresenter(this);

        fromTag = getIntent().getIntExtra(BaseTag.Edit_From_Tag, 1);
        switch (fromTag) {

            case 1:

                break;

            case 2:
                bean = getIntent().getParcelableExtra(BaseTag
                        .NOTES_BEAN_KEY);
                break;

            case 3:

                bean = getIntent().getParcelableExtra(BaseTag
                        .NOTES_BEAN_KEY);

                break;

        }

    }

    @Override
    public void initView() {

        if (fromTag == 1) {
            toolbar.setTitle("添加记事");
        } else if (fromTag == 2) {
            toolbar.setTitle("修改记事");
        } else {
            if (bean == null)
                toolbar.setTitle("添加秘密记事");
            else
                toolbar.setTitle("修改秘密记事");

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showDialog();
            }
        });

        dialog = new MaterialDialog(context).setMessage("您编辑的内容还未保存，确定退出？")
                .setNegativeButton("取消", new View
                        .OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                }).setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        finish();

                    }
                });

    }

    @Override
    public void presenterDo() {

        if (fromTag == 1) {
            presenter.initViewData();
            presenter.setSpinnerList();
        } else if (fromTag == 2) {

            spinner.setVisibility(View.GONE);
            presenter.initViewDataWithBean(bean);

        } else {
            spinner.setVisibility(View.GONE);
            if (bean == null) {
                presenter.initViewData();
            } else {
                presenter.initViewDataWithBean(bean);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_notes_edit, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id) {

            case R.id.action_save:
                if (fromTag == 1) {
                    presenter.saveNotes();
                } else if (fromTag == 2) {
                    presenter.updateNotes(bean.getId());
                } else {
                    if (bean == null) {
                        presenter.saveSecretNotes();
                    } else {
                        presenter.updateNotes(bean.getId());
                    }
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getMyTitle() {

        return tvTitle.getText().toString();
    }

    @Override
    public String getContent() {

        return tvContent.getText().toString();
    }

    @Override
    public String getTime() {

        return tvTime.getText().toString();
    }

    @Override
    public String getAddress() {

        return tvAddress.getText().toString();
    }

    @Override
    public String getWeather() {

        return tvWeather.getText().toString();
    }

    @Override
    public String getType() {

        return (String) spinner.getSelectedItem();
    }

    @Override
    public EditText getTitleView() {
        return tvTitle;
    }

    @Override
    public EditText getContentView() {

        return tvContent;
    }

    @Override
    public void setTitle(String title) {

        tvTitle.setText(title);
    }

    @Override
    public void setContent(String content) {

        tvContent.setText(content);
    }

    @Override
    public void setAddress(String address) {
        tvAddress.setText(address);
    }

    @Override
    public void setTime(String time) {

        tvTime.setText(time);
    }

    @Override
    public void setMscText(String text) {

        tvMsc.setText(text);

    }

    @Override
    public void setMscImg(int flag) {

        if (flag == 1) {

            mscImg.setImageResource(R.drawable.ic_mic);

        } else {
            mscImg.setImageResource(R.drawable.ic_mic_off);
        }


    }

    @Override
    public void setMscResult(String result) {


    }

    @Override
    public void setWeather(String weather) {

        tvWeather.setText(weather);
    }

    @Override
    public void initSpinner(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.item_spinner, list);
//        adapter.setDropDownViewResource(android.R.layout
//                .simple_spinner_dropdown_item);  //设置下拉样式
        spinner.setAdapter(adapter);

    }

    @Override
    public int getSpinnerSelectedItemId() {
        return spinner.getSelectedItemPosition();
    }

    @Override
    public void dealResult() {

        finish();
    }

    @Override
    public void showDialog() {
        dialog.show();
    }

    @Override
    public void finishActivity() {
        finish();
    }


    @OnClick(R.id.notes_edit_btn_msc)
    public void onClick() {
        if (NetworkManager.getInstance().isNetworkConnected(context)) {
            presenter.dealMsc();
        } else {
            ToastUtils.showShort("当前无网络连接，请检查您的网络···");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.destoryMsc();
    }
}
