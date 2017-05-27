package com.zgh.appdevtemplate.update;

import android.os.Environment;

import com.zgh.appdevtemplate.MyApplication;
import com.zgh.appdevtemplate.util.AppUtils;

import java.io.File;

public class UpdataConstant {
    public static final String SDCARD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String APK_FOLDER  = AppUtils.getAppName(MyApplication.getContext());
    public static final String APK_NAME    = APK_FOLDER + "_update.apk";
    public static final String APK_PATH    =
            SDCARD_PATH + File.separator + APK_FOLDER + File.separator + APK_NAME;


}
