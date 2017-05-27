package com.zgh.appdevtemplate.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zgh.appdevtemplate.R;


/**
 * win10 风格加载对话框
 */
public class Win10LoadingDialog extends Dialog {
    final static String TAG = "Win10LoadingDialog";
    private TextView         tvMessage;
    private String           message;
    private long             number;
    private Win10ProgressBar progressBar;
    Context context;
    public Win10LoadingDialog(Context context) {
        super(context, R.style.BMProgressDialog);
        this.context = context;
    }

    public Win10LoadingDialog(Context context, String message) {
        super(context, R.style.BMProgressDialog);
        this.context = context;
        this.message = message;
    }

    public Win10LoadingDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.win10_loading_dialog);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        progressBar = (Win10ProgressBar) findViewById(R.id.win10_progressbar);
        tvMessage = (TextView)findViewById(R.id.message);
        if(message != null){
            tvMessage.setText(message);
        }
        Log.e(TAG, "onCreate");
    }

    public void show(String message) {
        if(progressBar!= null){
            progressBar.start();
        }
        super.show();
        this.message = message;
        if(tvMessage != null && message != null){
            tvMessage.setText(message);
        }
    }
    
    public void show(int msgResId){
        show(getContext().getString(msgResId));
    }

    @Override
    public void show() {
        if(progressBar!= null){
            progressBar.start();
        }
        super.show();
    }

    public Win10LoadingDialog setMesage(String message){
        this.message = message;
        if(tvMessage != null && message != null){
            tvMessage.setText(message);
        }
        return this;
    }

    public Win10LoadingDialog setMesage(int resId){
        this.message = getContext().getString(resId);
        if(tvMessage != null && message != null){
            tvMessage.setText(message);
        }
        return this;
    }

    public void setNumber(long number) {
        this.number = number;
    }
    public long getNumber() {
        return number;
    }

    @Override
    public void hide() {
        if(progressBar!= null){
            progressBar.stop();
        }
        super.hide();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}