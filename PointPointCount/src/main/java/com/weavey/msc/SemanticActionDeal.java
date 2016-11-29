package com.weavey.msc;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.weavey.base.BaseApplication;

/**
 * create by Weavey
 * on date 2016-01-22
 * TODO  语义分析结果 跳转acticity的中转类
 */
public class SemanticActionDeal {

    private static SemanticActionDeal instance = null;
    private static Context context = null;
    private Intent intent;

    private String REGISTER = "REGISTER";  //挂号
    private String EXIT0 = "退出程序";
    private String EXIT1 = "退出应用";
    private String EXIT2 = "关闭应用";

    public static SemanticActionDeal getInstance(Context mcontext) {

        context = mcontext;
        if (instance == null) {

            synchronized (SemanticActionDeal.class) {

                if (instance == null) {

                    instance = new SemanticActionDeal();

                }
            }

        }

        return instance;
    }

    public void standardAction(String action) {

        if (TextUtils.equals(action, REGISTER)) {

//            intent = new Intent(context, AppointmentBookActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public boolean defineAction(String action) {

        boolean isAction = false;
        if (TextUtils.equals(action, EXIT0) || TextUtils.equals(action, EXIT1) || TextUtils.equals(action, EXIT2)) {
            {

                XunFeiVoiceAction.getInstance().speaking("正在退出");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        BaseApplication.e;
                    }
                },1000);

                isAction = true;
            }
        }
        return isAction;
    }


}


