package com.zgh.appdevtemplate.model;

import java.io.Serializable;

/**
 * 网络请求返回的数据模型，此模型为最外层模型，具体数据在 data 节点内
 */
public class ResponseInfo implements Serializable {
    public int    code;
    public Object data;
    public String text;
    public long   time;
}
