package com.zgh.appdevtemplate.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgh.appdevtemplate.R;


/**
 *   3D转动图片loading的dialog（全屏透明）
 */
public class RotateLoadingDialog extends Dialog {
    private static final String TAG = "RotateLoadingDialog";
    private TextView  tv_message;
    private ImageView iv_Loading;
    private Context   context;
    /**
     * 下方显示message
     */
    private String    message;

    private Drawable drawable;

    public RotateLoadingDialog(Context context) {
        super(context, R.style.RotateLoadingDialog);
        this.context = context;
    }

    public RotateLoadingDialog(Context context, String message) {
        super(context, R.style.RotateLoadingDialog);
        this.context = context;
        this.message = message;
    }

    public RotateLoadingDialog(Context context, String message, Drawable drawable) {
        super(context, R.style.RotateLoadingDialog);
        this.context = context;
        this.message = message;
        this.drawable = drawable;
    }

    public RotateLoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public RotateLoadingDialog(Context context, Drawable drawable) {
        super(context, R.style.RotateLoadingDialog);
        this.drawable = drawable;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.rotate_loading_dialog);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        iv_Loading = (ImageView) findViewById(R.id.iv_rotate_loading_dialog);
        tv_message = (TextView) findViewById(R.id.tv_rotate_loading_dialog);
        if (message != null) {
            tv_message.setText(message);
        }
        if (null == drawable) {
            iv_Loading.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher_round));
        } else {
            iv_Loading.setImageDrawable(drawable);
        }
        Rotate3d rotate3d = new Rotate3d();
        rotate3d.setDuration(2000);
        rotate3d.setRepeatCount(Integer.MAX_VALUE);
        rotate3d.setRepeatMode(2);
        iv_Loading.startAnimation(rotate3d);
    }

    public void show(String message) {
        this.message = message;
        if (tv_message != null && message != null) {
            tv_message.setText(message);
        }
        super.show();
    }

    public void show(int msgResId) {
        show(getContext().getString(msgResId));
    }


    public RotateLoadingDialog setYzsMessage(String message) {
        this.message = message;
        if (tv_message != null && message != null) {
            tv_message.setText(message);
        }
        return this;
    }

    public RotateLoadingDialog setYzsMessage(int resId) {
        this.message = getContext().getString(resId);
        if (tv_message != null && message != null) {
            tv_message.setText(message);
        }
        return this;
    }

    @Override
    public void hide() {
        if (null != iv_Loading) {
            iv_Loading.clearAnimation();
        }
        super.hide();
    }

    @Override
    public void dismiss() {
        if (null != iv_Loading) {
            iv_Loading.clearAnimation();
        }
        super.dismiss();
    }
}
