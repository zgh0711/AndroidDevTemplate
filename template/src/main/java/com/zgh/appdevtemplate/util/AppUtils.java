package com.zgh.appdevtemplate.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * 跟App相关的辅助类
 */
public class AppUtils {

    /* cannot be instantiated */
    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取设备唯一id Returns the unique device ID, for example, the IMEI for GSM and
     * the MEID or ESN for CDMA phones. Return null if device ID is not
     * available.
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) (context.getSystemService(Activity.TELEPHONY_SERVICE));
        return tm.getDeviceId();
    }

    /**
     * 手机型号 如ZET-1
     *
     * @return
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机设备名
     */
    public static String getDeviceName(){
        return android.os.Build.USER;
    }
    /**
     * 获取android sdk版本号 如 4.4.4
     *
     * @return
     */
    public static String getSdkVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * A build ID string meant for displaying to the user
     *
     * @return
     */
    public static String getDisplay() {
        return android.os.Build.DISPLAY;
    }

    /**
     * The brand (e.g., carrier) the software is customized for, if any.
     *
     * @return
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用程序版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * whether this process is named with processName 是否该进程是以processName命名的
     *
     * @param context
     * @param processName
     * @return
     */
    public static boolean isNamedProcess(Context context, String processName) {
        if (context == null) {
            return false;
        }
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
        if (processInfoList == null || processInfoList.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo != null && processInfo.pid == pid && processName.equals(processInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * whether application is in background 是否该应用运行在后台
     *
     * @param context
     * @return if application is in background return true, otherwise return
     * false
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
