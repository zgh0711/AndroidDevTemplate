package com.zgh.templatetest;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

/**
 * Created by ZGH on 2017/5/27.
 */

public class MyApp extends Application {
    //标志：确保方法只运行一次
    private boolean log_on = true;
    private boolean isDebug = true;

    private static MyApp   myApp;

    @Override
    public void onCreate() {
        super.onCreate();

        myApp = this;

        Utils.init(myApp);

    }

    private MyApp getMyApp() {
        return myApp;
    }

    public static Context getContext() {
        return myApp.getApplicationContext();
    }

}
