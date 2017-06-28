package com.zgh.appdevtemplate.theMVP.dataBind;

import com.zgh.appdevtemplate.theMVP.model.IModel;
import com.zgh.appdevtemplate.theMVP.view.IDelegate;

/**
 * ViewModel实现
 *
 * 博客：https://www.kymjs.com/code/2015/11/09/01/
 * Github：https://github.com/kymjs/TheMVP
 */

public interface DataBinder<T extends IDelegate, D extends IModel> {
    /**
     * 将数据与View绑定，这样当数据改变的时候，框架就知道这个数据是和哪个View绑定在一起的，就可以自动改变ui
     * 当数据改变的时候，会回调本方法。
     *
     * @param viewDelegate 视图层代理
     * @param data         数据模型对象
     */
    void viewBindModel(T viewDelegate, D data);
}
