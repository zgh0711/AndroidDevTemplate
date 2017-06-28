package com.zgh.templatetest.theMVP.presenter;

import android.os.Bundle;
import android.view.View;

import com.zgh.appdevtemplate.event.EventCenter;
import com.zgh.appdevtemplate.theMVP.dataBind.DataBindActivity;
import com.zgh.appdevtemplate.theMVP.dataBind.DataBinder;
import com.zgh.templatetest.R;
import com.zgh.templatetest.theMVP.dataBind.DataBindTest;
import com.zgh.templatetest.theMVP.delegate.CommonDelegate;
import com.zgh.templatetest.theMVP.model.TestData;

/**
 * Created by ZGH on 2017/6/28.
 */

public class BindDataActivity extends DataBindActivity<CommonDelegate> {
    TestData mData = new TestData("初始值");

    @Override
    protected Class<CommonDelegate> getDelegateClass() {
        return CommonDelegate.class;
    }

    @Override
    protected void initWidgetEvent() {
        //模拟数据改变(比如也可以写在网络请求成功的时候改变数据)
        viewDelegate.getView(R.id.btn_com).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.setContent("btn clicked");
                //通知数据发生了改变
                notifyModelChanged(mData);
            }
        });
    }

    @Override
    protected DataBinder getDataBinder() {
        return new DataBindTest();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onEventComing(EventCenter event) {

    }
}
