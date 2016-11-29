package com.weavey.weather;

import java.util.List;

/**
 * Created by Administrator on 2016-04-04.
 */
public class Data {

    private Realtime realtime;
    private Life life;
    private List<Weather> weather;
    private Pm25 pm25;
    private String date;
    private int isforeign;


    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }
    public Realtime getRealtime() {
        return realtime;
    }


    public void setLife(Life life) {
        this.life = life;
    }
    public Life getLife() {
        return life;
    }


    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
    public List<Weather> getWeather() {
        return weather;
    }


    public void setPm25(Pm25 pm25) {
        this.pm25 = pm25;
    }
    public Pm25 getPm25() {
        return pm25;
    }


    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }


    public void setIsforeign(int isforeign) {
        this.isforeign = isforeign;
    }
    public int getIsforeign() {
        return isforeign;
    }
}
