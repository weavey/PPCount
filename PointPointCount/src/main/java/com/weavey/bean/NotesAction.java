package com.weavey.bean;

/**
 * Created by Administrator on 2016-04-03.
 */
public class NotesAction {

    private int tag; //哪个Fragment更新 对数据增删改后 重新读取数据库 更新页面

    private int selectFragment; // 记事保存后自动定位到当前的Fragment

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getSelectFragment() {
        return selectFragment;
    }

    public void setSelectFragment(int selectFragment) {
        this.selectFragment = selectFragment;
    }
}
