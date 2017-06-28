package com.zgh.appdevtemplate.theMVP.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgh.appdevtemplate.base.BaseFragment;
import com.zgh.appdevtemplate.theMVP.view.IDelegate;

/**
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 *
 * 博客：https://www.kymjs.com/code/2015/11/09/01/
 * Github：https://github.com/kymjs/TheMVP
 */

public abstract class FragmentPresenter<T extends IDelegate> extends BaseFragment {
    public T viewDelegate;//视图层 delegate 对象

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //创建视图层 delegate 对象
            viewDelegate = getDelegateClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在具体实现子类通过这个方法获取具体的 delegete 类名，然后创建对象
     */
    protected abstract Class<T> getDelegateClass();

    /**
     * BaseFragment 的抽象方法，在Fragment的 onCreateView 内调用，初始化布局文件
     */
    @Override
    protected View initContentView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDelegate.create(inflater, container, savedInstanceState);
        return viewDelegate.getRootView();
    }

    /**
     * onViewCreated 是在 onCreateView 执行完毕之后调用的，
     * 可以在这里面对各控件进行初始化
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //通过 delegete 初始化布局中的控件
        viewDelegate.initWidget();
        //绑定各控件的事件
        initWidgetEvent();
    }

    /**
     * 布局中各个控件的事件，通过 delegate 的 getView（）方法来获取控件对象
     */
    protected abstract void initWidgetEvent();

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
    }

    /**
     * 此方法为 BaseFragment 的抽象方法，本来是用来在 Fragment 中初始化控件用的
     * 现在因为是 MVP 模式，视图层为 delegate，所以视图控件的各种操作都需要放在 delegate 中
     * 具体实现子类也就没必要实现此方法，所以在这里写一个空实现，具体之类就不需要再实现这个方法
     */
    @Override
    protected void initView(View view) {

    }
}
