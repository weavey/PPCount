package com.weavey.bean;

/**
 * create by Weavey
 * on date 2016-03-23
 */
public class CityBean {

    private boolean isLocated;

    private String city;

    private String cityCode;

    private String locatedType;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean getIsLocated() {
        return isLocated;
    }

    public void setIsLocated(boolean located) {
        isLocated = located;
    }

    public String getLocatedType() {
        return locatedType;
    }

    public void setLocatedType(String locatedType) {
        this.locatedType = locatedType;
    }
}
