package com.zgh.appdevtemplate.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.zgh.appdevtemplate.constant.Urls;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.util.TextUtils;

public class HttpUtil {
    private static Context mContext;
    private static HttpUtil ourInstance = new HttpUtil();
    private static AsyncHttpClient mClient = new AsyncHttpClient();

    static {
        //设置网络超时时间
        mClient.setTimeout(10000);
    }

    public static HttpUtil getInstance(Context context) {
        mContext = context;
        return ourInstance;
    }

    private HttpUtil() {
    }

    private void addHeader(Context context, String url) {
        // 本地保存cookie
        PersistentCookieStore mCookieStore = new PersistentCookieStore(context);
        mClient.setCookieStore(mCookieStore);
        //        LogUtils.d("HttpUtil.url" + url);
        // header,根据接口文档上要求传入相应的参数作为请求的 header
        String OS = "android";
        String osVer = AppUtils.getSdkVersion();
        String PKG = context.getPackageName();
        String uuid = AppUtils.getDeviceId(context);
        int versionCode = AppUtils.getAppVersionCode(context);
        long time = System.currentTimeMillis();
        String hash = MD5Utils.getMD5String(OS + osVer + PKG + versionCode + MD5Utils.getMD5String(uuid + url + time));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("os", OS);
        params.put("ver", osVer);
        params.put("pkg", PKG);
        params.put("num", versionCode);
        params.put("uuid", uuid);
        params.put("path", url);
        params.put("time", time);
        params.put("sign", hash);
        String p = null;
        try {
            p = JsonTools.buildJsonStr(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mClient.addHeader("client", p);
        //添加调试模式的 header
        //        if (!TextUtils.isEmpty(MyConfig.DEBUG)) {
        //            mClient.addHeader("debug", MyConfig.DEBUG);
        //        }
        //        if (!TextUtils.isEmpty(MyConfig.PASSPORT)) {
        //            mClient.addHeader("passport", MyConfig.PASSPORT);
        //        }
        //        mClient.addHeader("timestamp", String.valueOf(time));
        //        mClient.addHeader("token", MD5Utils.getMD5String(time + "|" + uri));
        //        mClient.addHeader("user-agent", PKG + OS);
    }

    /**
     * get 请求
     */
    public void get(@NonNull String url, RequestParams params, boolean show, HttpHandler handler) {
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (!url.contains(Urls.BASE_URL)) {//完成拼接
                url = Urls.BASE_URL + url;
            }
            addHeader(mContext, url);
            //弹出加载进度条
            showDialog(url, show, handler);
            mClient.get(url, params, handler);
        } else {
            ToastUtils.showShort(mContext, "网络连接不可用,请设置网络");
        }
    }

    /**
     * get 请求
     */
    public void get(@NonNull String url, RequestParams params, String msg, HttpHandler handler) {
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (!url.contains(Urls.BASE_URL)) {//完成拼接
                url = Urls.BASE_URL + url;
            }
            addHeader(mContext, url);
            //弹出加载进度条
            showDialog(url, true, msg, handler);
            mClient.get(url, params, handler);
        } else {
            ToastUtils.showShort(mContext, "网络连接不可用,请设置网络");
        }
    }

    /**
     * post 请求
     */
    public void post(@NonNull String url, RequestParams params, boolean show, HttpHandler handler) {
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (!url.contains(Urls.BASE_URL)) {//完成拼接
                url = Urls.BASE_URL + url;
            }
            addHeader(mContext, url);
            //弹出加载进度条
            showDialog(url, show, handler);
            mClient.post(url, params, handler);
        } else {
            ToastUtils.showShort(mContext, "网络连接不可用,请设置网络");
        }
    }

    /**
     * post 请求
     */
    public void post(@NonNull String url, RequestParams params, String msg, HttpHandler handler) {
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (!url.contains(Urls.BASE_URL)) {//完成拼接
                url = Urls.BASE_URL + url;
            }
            addHeader(mContext, url);
            //弹出加载进度条
            showDialog(url, true, msg, handler);
            mClient.post(url, params, handler);
        } else {
            ToastUtils.showShort(mContext, "网络连接不可用,请设置网络");
        }
    }

    /**
     * delete 请求
     */
    public void delete(@NonNull String url, RequestParams params, boolean show, HttpHandler handler) {
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (!url.contains(Urls.BASE_URL)) {//完成拼接
                url = Urls.BASE_URL + url;
            }
            addHeader(mContext, url);
            //弹出加载进度条
            showDialog(url, show, handler);
            mClient.delete(url, params, handler);
        } else {
            ToastUtils.showShort(mContext, "网络连接不可用,请设置网络");
        }
    }

    /**
     * delete 请求
     */
    public void delete(@NonNull String url, RequestParams params, String msg, HttpHandler handler) {
        if (NetworkUtils.isNetworkAvailable(mContext)) {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (!url.contains(Urls.BASE_URL)) {//完成拼接
                url = Urls.BASE_URL + url;
            }
            addHeader(mContext, url);
            //弹出加载进度条
            showDialog(url, true, msg, handler);
            mClient.delete(url, params, handler);
        } else {
            ToastUtils.showShort(mContext, "网络连接不可用,请设置网络");
        }
    }

    //=======以下为辅助=============================================================================

    /**
     * 根据show判断显示对话框
     */
    private void showDialog(@NonNull String url, boolean show, HttpHandler handler) {
        if (show) {
            ProgressDialog dialog = new ProgressDialog(mContext);
            dialog.setMessage("数据加载中，请稍后...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            handler.set(url, mContext, dialog);
        } else {
            handler.set(url, mContext);
        }
    }

    /**
     * 根据show判断显示对话框
     */
    private void showDialog(@NonNull String url, boolean show, @NonNull String msg, HttpHandler handler) {
        if (show) {
            ProgressDialog dialog = new ProgressDialog(mContext);
            dialog.setMessage(msg);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            handler.set(url, mContext, dialog);
        } else {
            handler.set(url, mContext);
        }
    }
}
