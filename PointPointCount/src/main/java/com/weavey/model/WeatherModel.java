package com.weavey.model;

import com.weavey.manager.OkhttpManager;

/**
 * Created by Administrator on 2016-04-04.
 */
public class WeatherModel {

    private OkhttpManager manager;

    public void getWeather(){

        manager = new OkhttpManager();
        manager.getWeather();
    }

}
