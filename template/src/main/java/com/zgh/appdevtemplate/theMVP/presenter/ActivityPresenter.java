package com.zgh.appdevtemplate.theMVP.presenter;

import android.os.Bundle;

import com.zgh.appdevtemplate.base.BaseActivity;
import com.zgh.appdevtemplate.theMVP.view.IDelegate;

/**
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 *
 * 博客：https://www.kymjs.com/code/2015/11/09/01/
 * Github：https://github.com/kymjs/TheMVP
 */

public abstract class ActivityPresenter<T extends IDelegate> extends BaseActivity {
    protected T viewDelegate;//视图层 delegate 对象

    public ActivityPresenter() {
        try {
            //创建视图层 delegate 对象
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
    }

    /**
     * 在具体实现子类通过这个方法获取具体的 delegete 类名，然后创建对象
     */
    protected abstract Class<T> getDelegateClass();

    /**
     * BaseActivity 的抽象方法，Activity onCreate 内调用，初始化布局文件
     */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(viewDelegate.getRootView());
        viewDelegate.initWidget();
        initWidgetEvent();
    }

    /**
     * 布局中各个控件的事件，通过 delegate 的 getView（）方法来获取控件对象
     */
    protected abstract void initWidgetEvent();

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IDelegate error");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
    }

    /**
     * 此方法为 BaseActivity 的抽象方法，本来是用来在 Activity 中初始化控件用的
     * 现在因为是 MVP 模式，视图层为 delegate，所以视图控件的各种操作都需要放在 delegate 中
     * 具体实现子类也就没必要实现此方法，所以在这里写一个空实现，具体之类就不需要再实现这个方法
     */
    @Override
    protected void initView() {

    }
}
