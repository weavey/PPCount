package com.weavey.weather;

/**
 * Created by Administrator on 2016-04-04.
 */
public class Realtime {
    private Wind wind;
    private String time;
    private Weather2 weather;
    private int datauptime;
    private String date;
    private String cityCode;
    private String cityName;
    private int week;
    private String moon;


    public void setWind(Wind wind) {
        this.wind = wind;
    }
    public Wind getWind() {
        return wind;
    }


    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }


    public void setWeather(Weather2 weather) {
        this.weather = weather;
    }
    public Weather2 getWeather() {
        return weather;
    }


    public void setDatauptime(int datauptime) {
        this.datauptime = datauptime;
    }
    public int getDatauptime() {
        return datauptime;
    }


    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }


    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getCityCode() {
        return cityCode;
    }


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCityName() {
        return cityName;
    }


    public void setWeek(int week) {
        this.week = week;
    }
    public int getWeek() {
        return week;
    }


    public void setMoon(String moon) {
        this.moon = moon;
    }
    public String getMoon() {
        return moon;
    }
}
