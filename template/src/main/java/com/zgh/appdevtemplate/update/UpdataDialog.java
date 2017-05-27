package com.zgh.appdevtemplate.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class UpdataDialog extends DialogFragment {
    private OnDialogClickListener mOnDialogClickListener;


    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        mOnDialogClickListener = onDialogClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("升级提示");
        builder.setMessage("发现新版本,建议立即更新使用！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mOnDialogClickListener != null) {
                    mOnDialogClickListener.onDialogPositiveClick();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mOnDialogClickListener != null) {
                    mOnDialogClickListener.onDialogNegativeClick();
                }
            }
        });
        return builder.create();
    }


    public interface OnDialogClickListener {
        void onDialogPositiveClick();

        void onDialogNegativeClick();
    }
}
