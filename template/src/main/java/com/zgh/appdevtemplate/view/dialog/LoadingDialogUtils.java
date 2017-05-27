package com.zgh.appdevtemplate.view.dialog;

import android.util.Log;

import com.zgh.appdevtemplate.util.ActivityStackManager;


/**
 *   加载数据时显示的对话框
 */
public class LoadingDialogUtils {
    private static final String TAG = "LoadingDialogUtils";

    private static Win10LoadingDialog  win10LoadingDialog;
    private static RotateLoadingDialog rotateLoadingDialog;
    /**
     * 默认载入loading 为 win10 风格
     */
    public static final int WIN10_LOADING  = 0;
    /**
     * 3D 旋转的加载动画
     */
    public static final int ROTATE_LOADING = 1;

    /**
     * 载入loading，使用默认提示词
     */
    public static void showLoadingDialog(int type) {

        showLoadingDialog(type, null, 0);

    }

    /**
     * 载入loading，使用自定义提示词
     */
    public static void showLoadingDialog(int type, String message) {

        showLoadingDialog(type, message, 0);

    }

    /**
     * 载入loading，使用默认提示词，自定义图片
     */
    public static void showLoadingDialog(int type, int drawableId) {

        showLoadingDialog(type, null, drawableId);

    }

    /**
     * 载入loading，使用自定义提示词,自定义图片(只对YzsLoadingDialog有效果)
     */
    public static void showLoadingDialog(int type, String message, int drawableId) {

        switch (type) {
            case WIN10_LOADING:
                showProgressLoading(message);
                break;

            case ROTATE_LOADING:
                showYzsLoading(message, drawableId);
                break;

            default:
                showProgressLoading(message);
                break;
        }
    }


    private static void showProgressLoading() {
        try {
            if (win10LoadingDialog == null) {
                win10LoadingDialog = new Win10LoadingDialog(
                        ActivityStackManager.getInstance().getTopActivity(), "请稍候");
            }
            win10LoadingDialog.setCancelable(false);

            win10LoadingDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "progressDialog启动失败");
        }
    }

    private static void showProgressLoading(String message) {
        try {
            if (win10LoadingDialog == null) {
                win10LoadingDialog = new Win10LoadingDialog(ActivityStackManager.getInstance().getTopActivity(), null == message ? "请稍候" : message);
            }
            win10LoadingDialog.setCancelable(false);

            win10LoadingDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "progressDialog启动失败");
        }
    }

    private static void showYzsLoading() {
        try {
            if (rotateLoadingDialog == null) {
                rotateLoadingDialog = new RotateLoadingDialog(ActivityStackManager.getInstance().getTopActivity(), "请稍候");
            }
            rotateLoadingDialog.setCancelable(false);
            rotateLoadingDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "RotateLoadingDialog启动失败");
        }
    }

    private static void showYzsLoading(String message) {
        try {
            if (rotateLoadingDialog == null) {
                rotateLoadingDialog = new RotateLoadingDialog(ActivityStackManager.getInstance().getTopActivity(), message);
            }
            rotateLoadingDialog.setCancelable(false);
            rotateLoadingDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "RotateLoadingDialog启动失败");
        }
    }

    private static void showYzsLoading(int drawableId) {
        try {
            if (rotateLoadingDialog == null) {
                rotateLoadingDialog = new RotateLoadingDialog(ActivityStackManager.getInstance().getTopActivity(), ActivityStackManager.getInstance().getTopActivity().getResources().getDrawable(drawableId));
                Log.e(TAG, "初始化RotateLoadingDialog");
            }
            rotateLoadingDialog.setCancelable(false);

            rotateLoadingDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "RotateLoadingDialog启动失败");
        }
    }

    private static void showYzsLoading(String message, int drawableId) {
        try {
            if (rotateLoadingDialog == null && drawableId != 0) {
                rotateLoadingDialog = new RotateLoadingDialog(ActivityStackManager.getInstance().getTopActivity(), message, ActivityStackManager.getInstance().getTopActivity().getResources().
                        getDrawable(drawableId));
            } else {
                rotateLoadingDialog = new RotateLoadingDialog(ActivityStackManager.getInstance().getTopActivity(), message);
            }
            rotateLoadingDialog.setCancelable(false);
            rotateLoadingDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "RotateLoadingDialog启动失败");
        }
    }


    /**
     * 默认载入loading
     */
    public static void showLoadingDialog() {
        showLoadingDialog(WIN10_LOADING);
    }


    /**
     * 载入默认loading 自定义message
     *
     * @param message
     */
    public static void showLoadingDialog(String message) {

        showLoadingDialog(WIN10_LOADING, message);
    }

    /**
     * 取消loading
     */
    public static void cancelLoadingDialog() {

        if (win10LoadingDialog != null && win10LoadingDialog.isShowing()) {
            try {
                win10LoadingDialog.dismiss();
            } catch (Exception e) {
                Log.e(TAG, "progressDialog销毁失败");
            }
        }
        win10LoadingDialog = null;

        if (rotateLoadingDialog != null && rotateLoadingDialog.isShowing()) {
            try {
                rotateLoadingDialog.dismiss();
            } catch (Exception e) {
                Log.e(TAG, "RotateLoadingDialog销毁失败");
            }
        }
        rotateLoadingDialog = null;
    }


}
