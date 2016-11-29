package com.weavey.weather;

/**
 * Created by Administrator on 2016-04-04.
 */
public class Weather {

    private String date;
    private Info2 info;
    private String week;
    private String nongli;


    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }


    public void setInfo(Info2 info) {
        this.info = info;
    }
    public Info2 getInfo() {
        return info;
    }


    public void setWeek(String week) {
        this.week = week;
    }
    public String getWeek() {
        return week;
    }


    public void setNongli(String nongli) {
        this.nongli = nongli;
    }
    public String getNongli() {
        return nongli;
    }
}
