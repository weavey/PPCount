package com.weavey.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weavey.activity.NotesDetailActivity;
import com.weavey.adapter.GridNotesAdapter;
import com.weavey.adapter.LinearNotesAdapter;
import com.weavey.adapter.StaggerNotesAdapter;
import com.weavey.base.BaseFragment;
import com.weavey.base.BaseTag;
import com.weavey.bean.NotesAction;
import com.weavey.fragment.view.FragmentNoteView;
import com.weavey.fragment.view.OnNotesItemClickListener;
import com.weavey.litepal.Notes;
import com.weavey.litepal.NotesType;
import com.weavey.presenter.FragmentPresenter;
import com.weavey.utils.ViewUtils;
import com.zqu.weavey.ppcount.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * create by Weavey
 * on date 2016-03-22
 */
public class NotesNormalFrgment extends BaseFragment implements
        FragmentNoteView {


    @Bind(R.id.frg_recycleview)
    RecyclerView recycleView;
    @Bind(R.id.empty)
    TextView empty;

    private LinearLayoutManager linearManager;
    private LinearNotesAdapter linearAdapter;
    private GridLayoutManager gridManager;
    private GridNotesAdapter gridAdapter;
    private StaggeredGridLayoutManager staggerManager;
    private StaggerNotesAdapter staggerAdapter;
    private View root;
    private NotesType type; //记事的类型bean
    private int TAG;  //当前是哪个fragment的标志
    private Intent intent;
    private Context context;
    private FragmentPresenter presenter;

    @Override
    public View setContentView() {

        root = ViewUtils.inflate(R.layout.frg_notes_view);
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getParcelable(BaseTag.NOTES_FRG);
            TAG = type.getId();
        }
        this.context = getActivity();

        return root;
    }

    @Override
    public boolean isLazyLoad() {
        return false;
    }

    @Override
    public void initView() {

        ButterKnife.bind(this, root);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        linearManager = new LinearLayoutManager(getActivity());
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridManager = new GridLayoutManager(getActivity(), 2);
        staggerManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);

        linearAdapter = new LinearNotesAdapter(getActivity());
        gridAdapter = new GridNotesAdapter(getActivity());
        staggerAdapter = new StaggerNotesAdapter(getActivity());
        exchangeView(type);

        linearAdapter.setOnLinearItemListener(new OnNotesItemClickListener() {
            @Override
            public void onClick(Notes notes, int position) {
                startIntent(notes);
            }
        });

        gridAdapter.setOnGridItemListener(new OnNotesItemClickListener() {
            @Override
            public void onClick(Notes notes, int position) {
                startIntent(notes);
            }

        });

        staggerAdapter.setOnStaggerItemListener(new OnNotesItemClickListener() {
            @Override
            public void onClick(Notes notes, int position) {
                startIntent(notes);
            }
        });

    }


    @Override
    public void initData() {
        presenter = new FragmentPresenter(this);
        intent = new Intent(context, NotesDetailActivity.class);
        presenter.setViewData(type.getNotesType());

    }


    private void startIntent(Notes notes) {

        intent.putExtra(BaseTag.NOTES_BEAN_KEY, notes);//记事的bean
        intent.putExtra(BaseTag.Edit_From_Tag, 2); //从界面的item点击进入的tag
        intent.putExtra(BaseTag.NOTES_FRG, TAG);  //返回时更新与TAG对应的Fragment
        startActivity(intent);
    }

    @Override
    public void updateAdapter(List<Notes> list) {

        linearAdapter.updateData(list);
        gridAdapter.updateData(list);
        staggerAdapter.updateData(list);
        if (list.size() > 0) {

            empty.setVisibility(View.GONE);

        } else {
            empty.setVisibility(View.VISIBLE);
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exchangeView(NotesType type) {

        if (type.getId() == TAG) {


            switch (type.getOrderType()) {

                case 1:

                    recycleView.setLayoutManager(linearManager);
                    recycleView.setAdapter(linearAdapter);
                    break;

                case 2:

                    recycleView.setLayoutManager(gridManager);
                    recycleView.setAdapter(gridAdapter);
                    break;

                case 3:

                    recycleView.setLayoutManager(staggerManager);
                    recycleView.setAdapter(staggerAdapter);
                    break;


            }
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateRecycleviewData(NotesAction action) {

        if (action.getTag() == TAG) {

            presenter.setViewData(type.getNotesType());

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

}
