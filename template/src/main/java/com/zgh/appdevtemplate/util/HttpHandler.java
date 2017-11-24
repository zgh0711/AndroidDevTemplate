package com.zgh.appdevtemplate.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zgh.appdevtemplate.constant.MyConfig;
import com.zgh.appdevtemplate.model.ResponseInfo;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public abstract class HttpHandler extends TextHttpResponseHandler {
    private Context mContext;
    private ProgressDialog mDialog;
    private String mUrl;

    public void set(String url, Context context) {
        mContext = context;
        mUrl = url;
    }

    public void set(String url, Context context, ProgressDialog dialog) {
        mContext = context;
        mDialog = dialog;
        mUrl = url;
    }

    public abstract void onSuccess(int statusCode, int requestCode, String json);

    public abstract void onFailure(int statusCode, int requestCode, String msg, Throwable e);


    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        dismissDialog();
        if (!TextUtils.isEmpty(responseString)) {
            String json = null;
            //先对返回数据解析，拿到data节点的数据
            ResponseInfo info = JsonTools.fromJson(responseString, ResponseInfo.class);
            Object object = null;
            int requestCode = 0;
            if (info != null) {

                if (info.data != null) {
                    object = info.data;
                }
                if (info.code != 0) {
                    requestCode = info.code;
                }

                //是debug模式，则不用解密，正式环境需要先对数据进行解密
                if (!TextUtils.isEmpty(MyConfig.DEBUG)) {
                    //因为解析出来 data 是一个实体对象，要先将它转为json，再传递出去使用
                    try {
                        json = JsonTools.buildJsonStr(object != null ? object : "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    onSuccess(statusCode, requestCode, json);
                    log("成功", statusCode, requestCode, json);
                } else {
                    //如果是正式环境，data是加密过的，是一个字符串，先强转为字符串，再解密，再传出去使用
                    if (object != null) {
                        String data = (String) object;
                        json = MyBase64.decode(data);
                        onSuccess(statusCode, requestCode, json);
                        log("成功", statusCode, requestCode, json);
                    }else {
                        onSuccess(statusCode, requestCode, "");
                    }
                }
            } else {
                log("成功", statusCode, requestCode, responseString);
            }
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
        dismissDialog();
        if (!TextUtils.isEmpty(responseString)) {
            ResponseInfo responseInfo = JsonTools.fromJson(responseString, ResponseInfo.class);
            String msg = null;
            int requestCode = 0;
            if (responseInfo != null) {
                if (responseInfo.text != null) {
                    msg = responseInfo.text;
                }
                if (responseInfo.code != 0) {
                    requestCode = responseInfo.code;
                }
                //如果请求失败，则只需要返回失败信息即可
                //是debug模式，则不用解密，正式环境需要先对数据进行解密
                if (!TextUtils.isEmpty(MyConfig.DEBUG)) {
                    onFailure(statusCode, requestCode, msg, e);
                    log("失败", statusCode, requestCode, msg);
                    log("失败", statusCode, requestCode, e.toString());
                } else {
                    onFailure(statusCode, requestCode, msg, e);
                    log("失败", statusCode, requestCode, msg);
                    log("失败", statusCode, requestCode, e.toString());
                }
            } else {
                log("失败", statusCode, requestCode, responseString);
            }
        }
    }

    private void log(String type, int statusCode, int requestCode, String responseString) {
        if (responseString != null) {
            if (type.equals("成功")) {
                LogUtils.d(type, "mUrl --> " + mUrl + "\nstatusCode --> " + statusCode +
                                 "\nrequestCode --> " + requestCode);
                LogUtils.json(responseString);
            } else {
                LogUtils.d(type, "mUrl --> " + mUrl + "\nstatusCode --> " + statusCode +
                                 "\nrequestCode --> " + requestCode + "\nFailureMsg --> " +
                                 responseString);
            }
        }
    }

    protected void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
