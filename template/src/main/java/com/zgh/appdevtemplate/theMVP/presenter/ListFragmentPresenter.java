package com.zgh.appdevtemplate.theMVP.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgh.appdevtemplate.base.BaseListFragment;
import com.zgh.appdevtemplate.theMVP.model.IModel;
import com.zgh.appdevtemplate.theMVP.view.IDelegate;

/**
 * Created by ZGH on 2017/6/21.
 */

public abstract class ListFragmentPresenter<T extends IDelegate,D extends IModel> extends BaseListFragment<D> {
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
//        initWidgetEvent();
    }

    /**
     * 布局中各个控件的事件，通过 delegate 的 getView（）方法来获取控件对象
     *
     * 这里因为是继承的 BaseListActivity，而列表页面除了列表外，额外的控件一般都是在 headerView中
     * headerView 的事件在 BaseListActivity 中一个专门的抽象方法，具体子类实现那个方法就可以了。
     * 而 item 的点击事件等，都可以写在 BaseListActivity 的 initRecyclerView 这个抽象方法中
     */
//    protected abstract void initWidgetEvent();

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
}
