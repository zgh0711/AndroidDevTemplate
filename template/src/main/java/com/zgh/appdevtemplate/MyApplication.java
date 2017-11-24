package com.zgh.appdevtemplate;

import android.app.Application;
import android.content.Context;

/**
 * Created by ZGH on 2017/4/17.
 */

public class MyApplication extends Application {

    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    private MyApplication getApplication() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }


}
