package com.zgh.appdevtemplate.theMVP.presenter;

import android.os.Bundle;

import com.zgh.appdevtemplate.base.BaseListActivity;
import com.zgh.appdevtemplate.theMVP.model.IModel;
import com.zgh.appdevtemplate.theMVP.view.IDelegate;

/**
 * Created by ZGH on 2017/6/21.
 */

public abstract class  ListActivityPresenter<T extends IDelegate,D extends IModel> extends BaseListActivity<D> {
    protected T viewDelegate;//视图层 delegate 对象

    public ListActivityPresenter() {
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
}
