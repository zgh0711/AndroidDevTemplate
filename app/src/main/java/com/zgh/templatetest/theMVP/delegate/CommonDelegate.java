package com.zgh.templatetest.theMVP.delegate;

import android.widget.TextView;

import com.zgh.appdevtemplate.theMVP.view.BaseDelegate;
import com.zgh.templatetest.R;

/**
 * Created by ZGH on 2017/6/27.
 */

public class CommonDelegate extends BaseDelegate {
    private TextView mTextView;
    @Override
    public void initWidget() {
        mTextView = getView(R.id.tv_common);
        mTextView.setText("初始文本");
    }

    public void setText(String s) {
        mTextView.setText(s);
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.activity_common_mvp;
    }
}
