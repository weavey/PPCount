package com.weavey.base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.weavey.litepal.NotesType;
import com.weavey.utils.LogUtils;
import com.weavey.utils.SharePreferenceUtils;

import org.litepal.LitePalApplication;


public class BaseApplication extends Application {

    private List<AppCompatActivity> activitys = new
            LinkedList<AppCompatActivity>();
    private List<Service> services = new LinkedList<Service>();
    private static BaseApplication application;
    private int mainTid;
    public Map<String, Object> SESSON = new HashMap<String, Object>();


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mainTid = android.os.Process.myTid();
        LogUtils.setOutputLog(true);  //开启log日志输出
        //捕获异常
//		BaseCrashHandler baseCrashHandler = BaseCrashHandler.getInstance();
//		baseCrashHandler.init(application);
        LitePalApplication.initialize(application);
        SpeechUtility.createUtility(application, SpeechConstant.APPID +"=5703b32a");
        createDB();

    }


    public static Context getApplication() {
        return application;
    }

    public int getMainTid() {
        return mainTid;
    }

    //管理activity集合
    public void addActivity(AppCompatActivity activity) {
        activitys.add(activity);
    }

    //移除activity
    public void removeActivity(AppCompatActivity activity) {
        activitys.remove(activity);
    }

    //增加service
    public void addService(Service service) {
        activitys.remove(service);
    }

    //移除service
    public void removeService(Service service) {
        activitys.remove(service);
    }

    //关闭程序：关闭所有activity和service
    public void closeApplication() {
        closeActivity();
        closeService();
    }

    public void closeActivity() {

        ListIterator<AppCompatActivity> listIterator = activitys.listIterator();
        //返回列表中的列表迭代器
        //activitys.listIterator(int index)从指定位置开始返回
        while (listIterator.hasNext()) {
            Activity activity = listIterator.next();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void closeService() {
        ListIterator<Service> listIterator = services.listIterator();
        while (listIterator.hasNext()) {
            Service service = listIterator.next();
            if (service != null) {
                application.stopService(new Intent(application, service
                        .getClass()));
            }

        }
    }

    private void createDB(){

        if(!SharePreferenceUtils.getInstance().get(BaseTag.DB_CREATE,false)){

            LogUtils.log(this,"创建数据库");

            SharePreferenceUtils.getInstance().commit(BaseTag.DB_CREATE,true);

            NotesType type = new NotesType();
            type.setNotesType("默认");
            type.setOrderType(1);
            type.save();

            NotesType type2 = new NotesType();
            type2.setNotesType("工作");
            type2.setOrderType(1);
            type2.save();

            NotesType type3 = new NotesType();
            type3.setNotesType("生活");
            type3.setOrderType(1);
            type3.save();
        }


    }


}
