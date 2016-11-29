package com.weavey.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * create by Weavey
 * on date 2016-03-22
 */
public abstract class BaseFragment extends Fragment {

    private boolean isLazyLoad;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup
            container,Bundle savedInstanceState) {


        return setContentView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isLazyLoad()) {

            lazyLoad();
        }

    }

    public abstract View setContentView();

    public abstract boolean isLazyLoad();

    public abstract void initView();

    public abstract void initData();

    public void lazyLoad() {


    }
}
