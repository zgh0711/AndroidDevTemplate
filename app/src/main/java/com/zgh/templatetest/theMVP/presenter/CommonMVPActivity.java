package com.zgh.templatetest.theMVP.presenter;

import android.os.Bundle;
import android.view.View;

import com.zgh.appdevtemplate.event.EventCenter;
import com.zgh.appdevtemplate.theMVP.presenter.ActivityPresenter;
import com.zgh.templatetest.R;
import com.zgh.templatetest.theMVP.delegate.CommonDelegate;

public class CommonMVPActivity extends ActivityPresenter<CommonDelegate> {

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onEventComing(EventCenter event) {

    }

    @Override
    protected Class<CommonDelegate> getDelegateClass() {
        return CommonDelegate.class;
    }

    @Override
    protected void initWidgetEvent() {
        viewDelegate.getView(R.id.btn_com).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDelegate.setText("点击了BUTTON");
            }
        });

        viewDelegate.getView(R.id.tv_common).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDelegate.setText("RESET TEXT");
            }
        });
    }
}
