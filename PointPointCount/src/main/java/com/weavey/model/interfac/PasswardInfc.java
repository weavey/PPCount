package com.weavey.model.interfac;

import com.weavey.litepal.Passward;

/**
 * create by Weavey
 * on date 2016-03-27
 */
public interface PasswardInfc {

    public void wirtePassward(String pwd);

    public Passward getPassward();

    public void updatePassward(int id,String pwd);
}
