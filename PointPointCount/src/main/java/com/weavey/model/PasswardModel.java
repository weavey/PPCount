package com.weavey.model;

import com.weavey.litepal.Passward;
import com.weavey.model.interfac.PasswardInfc;

import org.litepal.crud.DataSupport;

/**
 * create by Weavey
 * on date 2016-03-27
 */
public class PasswardModel implements PasswardInfc {

    @Override
    public void wirtePassward(String pwd) {

        Passward bean = new Passward();
        bean.setPwd(pwd);
        bean.save();
    }



    @Override
    public Passward getPassward() {

        Passward bean;
        bean = DataSupport.findFirst(Passward.class);
        return bean;
    }

    @Override
    public void updatePassward(int id,String pwd) {

        Passward bean = new Passward();
        bean.setPwd(pwd);
        bean.update(id);

    }


}
