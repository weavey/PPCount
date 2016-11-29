package com.weavey.msc;

/**
 * create by Weavey
 * on date 2016-01-22
 * TODO
 */
public class Pk_fea {

    private String last_service;

    private int used_hist_depth;

    private String gram_type;

    public void setLast_service(String last_service) {
        this.last_service = last_service;
    }

    public String getLast_service() {
        return this.last_service;
    }

    public void setUsed_hist_depth(int used_hist_depth) {
        this.used_hist_depth = used_hist_depth;
    }

    public int getUsed_hist_depth() {
        return this.used_hist_depth;
    }

    public void setGram_type(String gram_type) {
        this.gram_type = gram_type;
    }

    public String getGram_type() {
        return this.gram_type;
    }
}
