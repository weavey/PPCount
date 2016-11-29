package com.weavey.manager;


import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.weavey.base.BaseTag;
import com.weavey.utils.SharePreferenceUtils;
import com.weavey.weather.Data;
import com.weavey.weather.Realtime;
import com.weavey.weather.Result;
import com.weavey.weather.ResultFromJson;
import com.weavey.weather.Weather2;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * create by Weavey
 * on date 2016-03-24
 */
public class OkhttpManager {

    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private String url = "http://op.juhe.cn/onebox/weather/query?cityname=";
    private String key = "&key=b72b1e78f6ef1c18aaba06778aea2097";
    private String city;
    private String result;

    public void getWeather() {

        city = SharePreferenceUtils.getInstance().get(BaseTag.CITY, BaseTag
                .UNKNOWN_CITY);
        url = url + city + key;


        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(final Response response) throws IOException {

                result = response.body().string();
                Gson gson = new Gson();
                ResultFromJson bean = gson.fromJson(result,ResultFromJson.class);
                Result result = bean.getResult();
                if(result!=null) {
                    Data data = result.getData();
                    Realtime real = data.getRealtime();
                    Weather2 weather = real.getWeather();
                    EventBus.getDefault().post(weather);
                }else
                {
                    EventBus.getDefault().post(new Weather2());
                }
            }
        });

    }


}
