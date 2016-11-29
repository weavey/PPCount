package com.weavey.litepal;

import org.litepal.crud.DataSupport;

/**
 * create by Weavey
 * on date 2016-03-28
 */
public class Passward extends DataSupport{

    private int id;

    private String pwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
