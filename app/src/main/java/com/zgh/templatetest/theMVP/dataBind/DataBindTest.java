package com.zgh.templatetest.theMVP.dataBind;

import com.zgh.appdevtemplate.theMVP.dataBind.DataBinder;
import com.zgh.templatetest.theMVP.delegate.CommonDelegate;
import com.zgh.templatetest.theMVP.model.TestData;

/**
 * Created by ZGH on 2017/6/28.
 */

public class DataBindTest implements DataBinder<CommonDelegate,TestData> {
    @Override
    public void viewBindModel(CommonDelegate viewDelegate, TestData data) {
        viewDelegate.setText(data.getContent());
    }
}
