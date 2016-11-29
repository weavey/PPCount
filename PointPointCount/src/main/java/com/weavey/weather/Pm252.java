package com.weavey.weather;

/**
 * Created by Administrator on 2016-04-04.
 */
public class Pm252 {

    private String curpm;
    private String pm25;
    private String pm10;
    private int level;
    private String quality;
    private String des;


    public void setCurpm(String curpm) {
        this.curpm = curpm;
    }
    public String getCurpm() {
        return curpm;
    }


    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }
    public String getPm25() {
        return pm25;
    }


    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }
    public String getPm10() {
        return pm10;
    }


    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }


    public void setQuality(String quality) {
        this.quality = quality;
    }
    public String getQuality() {
        return quality;
    }


    public void setDes(String des) {
        this.des = des;
    }
    public String getDes() {
        return des;
    }
}
