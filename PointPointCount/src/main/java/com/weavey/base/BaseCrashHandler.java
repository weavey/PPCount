package com.weavey.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * create by Weavey
 * on date 2016-03-21
 */
public class BaseCrashHandler implements UncaughtExceptionHandler {

    private static BaseCrashHandler instance = new BaseCrashHandler();
    private Context mContext;

    //系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    //使用Properties来保存设备的信息和错误堆栈信息
    private Properties mDeviceCrashInfo = new Properties();
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    //错误保存文件夹路径
    private String path = Environment.getExternalStorageDirectory().toString
            () + "/CR/crash/";
    private SimpleDateFormat formatter = new SimpleDateFormat
            ("yyyy-MM-dd-HH-mm-ss");


    private BaseCrashHandler() {}

    public static BaseCrashHandler getInstance() {

        return instance;
    }

    /**
     * 初始化,注册Context对象,
     * 获取系统默认的UncaughtException处理器,
     * 设置该CrashHandler为程序的默认处理器
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //当UncaughtException发生时会转入该函数来处理
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        Log.i("mytag","uncaught");

        //如果用户没有处理则让系统默认的异常处理器来处理
        if (!handleException(ex) && mDefaultHandler != null) {

            mDefaultHandler.uncaughtException(thread, ex);
        } else {

            //使用Toast来显示异常信息
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext, "程序出错，即将退出...", Toast
                            .LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);

//            Intent i = mContext.getPackageManager()
//                    .getLaunchIntentForPackage(mContext.getPackageName());
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(i);

//            ClientApp.exitAllActivity(mContext);
        }
    }

    /**
     * 自定义错误处理,收集错误信息
     * 发送错误报告等操作均在此完成.
     * 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @return true:处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {

            return true;
        }
        final String msg = ex.getLocalizedMessage();
        if (msg == null) {
            return false;
        }

//        collectCrashDeviceInfo();
//        saveCrashInfoToFile(ex);
//        sendCrashReportsToServer();
        return true;
    }


    //收集程序崩溃的设备信息
    public void collectCrashDeviceInfo() {

        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi
                        .versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
//                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
            }
        }
    }

    //保存错误信息到文件中
    private String saveCrashInfoToFile(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment
                    .MEDIA_MOUNTED)) {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
        }
        return null;
    }

    //把错误报告发送给服务器,包含新产生的和以前没发送的.
    private void sendCrashReportsToServer() {

        ArrayList<String> filePaths = getCrashReportFiles();
        for (String s : filePaths) {

            File file = new File(s);

            postReport(file);

        }
    }

    //获取错误报告文件名
    private ArrayList<String> getCrashReportFiles() {

        ArrayList<String> filePaths = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {

            File[] files = file.listFiles();
            for (File f : files) {

                filePaths.add(f.getAbsolutePath());
            }
        }
        return filePaths;
    }

    //发送错误报告到服务器
    private void postReport(File file) {

        Log.i("mytag", "file name:" + file.getAbsolutePath());
    }
}
