package com.weavey.weather;

/**
 * Created by Administrator on 2016-04-04.
 */
public class Pm25 {

    private String key;
    private int showDesc;
    private Pm252 pm25;
    private String datetime;
    private String cityname;


    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }


    public void setShowDesc(int showDesc) {
        this.showDesc = showDesc;
    }
    public int getShowDesc() {
        return showDesc;
    }


    public void setPm25(Pm252 pm25) {
        this.pm25 = pm25;
    }
    public Pm252 getPm25() {
        return pm25;
    }


    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public String getDatetime() {
        return datetime;
    }


    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
    public String getCityname() {
        return cityname;
    }
}
