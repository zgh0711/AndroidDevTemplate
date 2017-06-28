package com.zgh.templatetest.theMVP.model;

import com.zgh.appdevtemplate.theMVP.model.IModel;

/**
 * Created by ZGH on 2017/6/27.
 */

public class TestData implements IModel {
    private String content;

    public TestData(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
