package com.zgh.appdevtemplate.theMVP.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 视图层代理的基类
 *
 * 博客：https://www.kymjs.com/code/2015/11/09/01/
 * Github：https://github.com/kymjs/TheMVP
 */

public abstract class BaseDelegate implements IDelegate{
    //android系统建议用SparseArray<E>来代替HashMap<Integer, E>
    private SparseArray<View> mViews = new SparseArray<>();
    //我们的布局页面
    private View rootView;

    /**
     * 在具体子类中通过此方法来获取布局文件的 id，拿到 id 后就可以初始化布局 rootView 了
     */
    protected abstract int getRootLayoutId();

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = getRootLayoutId();
        rootView = inflater.inflate(rootLayoutId, container, false);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    /**
     * 初始化控件，具体子类中通过此方法来获取各控件对象
     */
    public <T extends View> T getView(int id) {
        return bindView(id);
    }

    /**
     * 初始化控件的具体实现
     */
    public  <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    /**
     * 控件的点击事件，可以同时对多个控件设置同一个点击事件,后面id参数可以传多个
     */
    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            getView(id).setOnClickListener(listener);
        }
    }

    public void toast(CharSequence msg) {
        Toast.makeText(rootView.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取 Activity context
     */
    public <T extends Activity> T getActivity() {
        return (T) rootView.getContext();
    }
}
