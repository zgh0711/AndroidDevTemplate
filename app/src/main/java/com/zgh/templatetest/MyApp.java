package com.zgh.templatetest;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.Logger;
import com.zgh.appdevtemplate.util.AppUtils;
import com.zgh.appdevtemplate.util.LogUtil;
import com.zgh.appdevtemplate.util.SPUtils;

/**
 * Created by ZGH on 2017/5/27.
 */

public class MyApp extends Application {
    //标志：确保方法只运行一次
    private boolean log_on = true;
    private boolean isDebug = true;

    PendingIntent restartIntent;
    private static Context mContext;
    public static  SPUtils mySP;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        // Logger初始化配置
        Logger.init("MyAPP").methodCount(1).methodOffset(1).hideThreadInfo();
        //Utils库初始化以及初始化 SPUtils 和 SP 文件
        Utils.init(mContext);
        mySP = new SPUtils(AppUtils.getAppName(mContext));

        if (log_on) {
            log_on = false;
            //全局log日志开关
            LogUtil.isDebug = true;
        }

        if (!isDebug) {
            // 以下用来捕获程序崩溃异常
            Intent intent = new Intent();
            // 参数1：包名，参数2：程序入口的activity
            intent.setClassName("com.zgh.templatetest", "com.zgh.templatetest.MainActivity");
            restartIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                                                      intent, Intent.FLAG_ACTIVITY_NEW_TASK);
            Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            LogUtils.e("程序异常崩溃", ex.toString());
            if (!(mContext instanceof MainActivity)) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid()); // 自定义方法，关闭当前打开的所有activity
            } else {
                System.exit(0);
            }
        }
    };
}
