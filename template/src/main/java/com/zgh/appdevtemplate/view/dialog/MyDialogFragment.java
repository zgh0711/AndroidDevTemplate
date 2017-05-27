package com.zgh.appdevtemplate.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class MyDialogFragment extends DialogFragment {
    private DialogClickListener mDialogClickListener;
    private static float mWidthRadio = 0.75f;
    private static MyDialogFragment mDialog;
    private static View             mView;
    private        String           mTitle;
    private        String           mMessage;

    public static MyDialogFragment getInstance(View view) {
        mView = view;
        if (mDialog == null) {
            mDialog = new MyDialogFragment();
        }
        return mDialog;
    }

    public static MyDialogFragment getInstance(View view, float widthRadio) {
        mView = view;
        if (widthRadio > 0f && widthRadio <= 1.0f) {
            mWidthRadio = widthRadio;
        }
        if (mDialog == null) {
            mDialog = new MyDialogFragment();
        }
        return mDialog;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (TextUtils.isEmpty(mTitle)) {
            // 去标题栏
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        // 背景设置为透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return mView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (!TextUtils.isEmpty(mTitle)) {
            builder.setTitle(mTitle);
        }
        builder.setMessage(mMessage);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mDialogClickListener != null) {
                    mDialogClickListener.onPositiveClicked(dialogInterface, i);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mDialogClickListener != null) {
                    mDialogClickListener.onNegativeClicked(dialogInterface, i);
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置弹出对话框的宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout((int) (mWidthRadio * dm.widthPixels), getDialog().getWindow().getAttributes().height);
    }

    public void setOnDialogClickListener(DialogClickListener dialogClickListener) {
        mDialogClickListener = dialogClickListener;
    }

    /**
     * 回调接口
     */
    public interface DialogClickListener {
        void onPositiveClicked(DialogInterface dialogInterface, int i);

        void onNegativeClicked(DialogInterface dialogInterface, int i);
    }
}
