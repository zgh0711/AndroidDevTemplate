package com.zgh.appdevtemplate.theMVP.dataBind;

import android.os.Bundle;

import com.zgh.appdevtemplate.theMVP.model.IModel;
import com.zgh.appdevtemplate.theMVP.presenter.ActivityPresenter;
import com.zgh.appdevtemplate.theMVP.view.IDelegate;

/**
 * 集成数据-视图绑定的Activity基类(Presenter层)
 *
 * @param <T> View层代理类
 *
 * 博客：https://www.kymjs.com/code/2015/11/09/01/
 * Github：https://github.com/kymjs/TheMVP
 */

public abstract class DataBindActivity<T extends IDelegate> extends ActivityPresenter<T> {
    protected DataBinder mBinder;

    /**
     * 对应于 Activity 的 onCreate 方法，通过抽象方法在具体子类中获得 DataBinder 对象
     */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        super.initContentView(savedInstanceState);
        mBinder = getDataBinder();
    }

    /**
     * 子类实现此方法获取 DataBinder 对象，
     * 首先需要新建一个类实现 DataBinder 接口，传入具体的 viewDelegate 和 model，
     * 然后在此方法中直接 new 一个 DataBinder，return 即可。
     */
    protected abstract DataBinder getDataBinder();

    /**
     * 当数据发生改变时，在具体子类中调用此方法，传入改变后的数据
     */
    protected  <D extends IModel> void notifyModelChanged(D data) {
        if (mBinder != null) {
            mBinder.viewBindModel(viewDelegate, data);
        }
    }
}
