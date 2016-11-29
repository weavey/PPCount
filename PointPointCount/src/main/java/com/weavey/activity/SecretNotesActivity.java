package com.weavey.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.weavey.activity.EditNotesActivity;
import com.weavey.activity.view.SecretNotesView;
import com.weavey.adapter.LinearNotesAdapter;
import com.weavey.base.BaseActivity;
import com.weavey.base.BaseTag;
import com.weavey.bean.NotesAction;
import com.weavey.fragment.view.OnNotesItemClickListener;
import com.weavey.litepal.Notes;
import com.weavey.presenter.SecretNotesPresenter;
import com.zqu.weavey.ppcount.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecretNotesActivity extends BaseActivity implements SecretNotesView{


    @Bind(R.id.act_secret_toolbar)
    Toolbar toolbar;
    @Bind(R.id.act_secret_collapsing_layout)
    CollapsingToolbarLayout collapsingLayout;
    @Bind(R.id.act_secret_barlayout)
    AppBarLayout barlayout;
    @Bind(R.id.act_secret_recycleview)
    RecyclerView recycleView;
    @Bind(R.id.act_secret_float)
    FloatingActionButton actSecretFloat;

    private Context context;
    private Intent intent;
    private LinearLayoutManager linearManager;
    private LinearNotesAdapter linearAdapter;
    private SecretNotesPresenter presenter;

    @Override
    public void initRootView(Bundle savedInstanceState) {

        setContentView(R.layout.act_secret_notes);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setSupportActionBar(toolbar);
        presenter = new SecretNotesPresenter(this);
        this.context = this;
    }

    @Override
    public void initView() {


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recycleView.setItemAnimator(new DefaultItemAnimator());
        linearManager = new LinearLayoutManager(context);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearManager);
        linearAdapter = new LinearNotesAdapter(context);
        linearAdapter.setOnLinearItemListener(new OnNotesItemClickListener() {
            @Override
            public void onClick(Notes notes, int position) {

                intent = new Intent(context, NotesDetailActivity.class);
                intent.putExtra(BaseTag.Edit_From_Tag,3);
                intent.putExtra(BaseTag.NOTES_BEAN_KEY,notes);
                startActivity(intent);

            }
        });
        recycleView.setAdapter(linearAdapter);

    }

    @Override
    public void presenterDo() {

        presenter.getListData();

    }


    @OnClick({R.id.act_secret_float})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.act_secret_float:

                intent = new Intent(context, EditNotesActivity.class);
                intent.putExtra(BaseTag.Edit_From_Tag,3);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setRecycleViewData(List<Notes> list) {

        linearAdapter.updateData(list);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateRecycleviewData(NotesAction action){

        if (action.getTag()== -100) {

            presenter.getListData();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
