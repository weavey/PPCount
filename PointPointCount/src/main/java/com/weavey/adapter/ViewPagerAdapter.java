package com.weavey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weavey.litepal.NotesType;

import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-23
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private List<NotesType>title;


    public ViewPagerAdapter(FragmentManager fm, List<Fragment>list, List<NotesType>title) {
        super(fm);
        this.list= list;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {


        return title.get(position).getNotesType();
    }
}
