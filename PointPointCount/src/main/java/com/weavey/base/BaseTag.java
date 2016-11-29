package com.weavey.base;

/**
 * create by Weavey
 * on date 2016-03-21
 */
public class BaseTag {

    public static String DB_CREATE = "db_create"; //创建数据库的key

    public static String CITY = "city";   //城市的key

    public static String UNKNOWN_CITY = "未知城市"; //城市的默认value

    public static String WEATHER = "weather"; //天气的key

    public static String UNKNOWN_WEATHER = ""; //天气的默认value

    public static String NOTES_FRG = "notes_frg"; //创建每个fragment的key

    public static String IS_SET_PWD = "is_set_pwd"; //是否设置过密码的key

    public static String To_SETTING = "to_setting_pwd";  //

    public static String TO_NOTES_DETAIL = "to_notes_detail";

    public static String MAIN_ACTIVITY = "main_activity";

    //添加记事界面的key  value：1主界面进入   2普通记事详情进入修改  3秘密记事详情修改
    public static String Edit_From_Tag = "edit_from_tag";

    public static String NOTES_BEAN_KEY = "notes_tag"; //传递记事记录的bean的key

//    public static String NOTES_DETAIL = "notes_detail"; //记事详情页面标志

//    public static String NOTES_DETAIL_BEAN="notes_detail_bean";

    public final static int EditNotesIntent = 0;
    public final static int LockedIntent = 1;
    public final static int SettingIntent = 4;
    public final static int HelpIntent = 5;
    public final static int AboutIntent = 6;

}
