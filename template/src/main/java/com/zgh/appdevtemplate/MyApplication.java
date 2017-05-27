package com.zgh.appdevtemplate;

import android.app.Application;
import android.content.Context;

/**
 * Created by ZGH on 2017/4/17.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();


    }

    public static Context getContext() {
        return mContext;
    }


}
