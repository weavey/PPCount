package com.weavey.weather;

/**
 * Created by Administrator on 2016-04-04.
 */
public class Wind {

    private String windspeed;
    private String direct;
    private String power;
    private String offset;


    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }
    public String getWindspeed() {
        return windspeed;
    }


    public void setDirect(String direct) {
        this.direct = direct;
    }
    public String getDirect() {
        return direct;
    }


    public void setPower(String power) {
        this.power = power;
    }
    public String getPower() {
        return power;
    }


    public void setOffset(String offset) {
        this.offset = offset;
    }
    public String getOffset() {
        return offset;
    }
}
