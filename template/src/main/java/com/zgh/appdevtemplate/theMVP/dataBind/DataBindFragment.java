package com.zgh.appdevtemplate.theMVP.dataBind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zgh.appdevtemplate.theMVP.model.IModel;
import com.zgh.appdevtemplate.theMVP.presenter.FragmentPresenter;
import com.zgh.appdevtemplate.theMVP.view.IDelegate;

/**
 * 集成数据-视图绑定的Fragment基类(Presenter层)
 *
 * @param <T> View层代理类
 *
 * 博客：https://www.kymjs.com/code/2015/11/09/01/
 * Github：https://github.com/kymjs/TheMVP
 */

public abstract class DataBindFragment<T extends IDelegate> extends FragmentPresenter {
    protected DataBinder mBinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    protected <D extends IModel> void notifyDataChanged(D data) {
        if (mBinder != null) {
            mBinder.viewBindModel(viewDelegate, data);
        }
    }
}
