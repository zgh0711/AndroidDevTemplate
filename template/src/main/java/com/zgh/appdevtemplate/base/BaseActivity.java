package com.zgh.appdevtemplate.base;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.zgh.appdevtemplate.R;
import com.zgh.appdevtemplate.event.EventCenter;
import com.zgh.appdevtemplate.util.ActivityStackManager;
import com.zgh.appdevtemplate.view.TitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by ZGH on 2017/4/18.
 */

public abstract class BaseActivity extends SupportActivity {
    //titleView 定义为 public ，子类就可以直接用
    public TitleView mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //添加Activity到堆栈
        ActivityStackManager.getInstance().addActivity(new WeakReference<Activity>(this));
        setStatusBarColor(getResources().getColor(R.color.colorAccent), 0);
        EventBus.getDefault().register(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getBundleExtras(extras);
        }

        initContentView(savedInstanceState);
        initTitleView();
        initView();
    }

    /**
     * 初始化 titleView，继承 BaseActivity 的 Activity 必须在 XML 中声明 titleView
     * 且 id 为 titleView，如果不需要 titleView，则可以设置为隐藏，
     */
    private void initTitleView() {
        mTitleView = (TitleView) findViewById(R.id.titleView);
        if (mTitleView != null) {
            titleLeftBtnEvent();
        }
    }

    /**
     * titleView 左边按钮逻辑，一般为返回按钮，所以作为公共逻辑，
     * 如果不是回退操作，则重写此方法
     */
    protected void titleLeftBtnEvent() {
        Drawable drawable = mTitleView.getLeft_ibtn().getDrawable();
        if (drawable != null) {
             mTitleView.getLeft_ibtn().setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     finish();
                 }
             });
        }
    }

    /**
     * 在这里使用 setCotentView(R.layout.XXX); 来初始化布局文件
     * 此抽象方法相当于取代了原先的 onCreate 方法。
     */
    protected abstract void initContentView(Bundle savedInstanceState);
    /**
     * 初始化 view
     */
    protected abstract void initView();

    /**
     * 获取传递过来的数据
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * 设置状态栏颜色,默认是带有 112 透明度的，
     * 也可以重写此方法，使用 StatusBarUtil 的其他方式设置状态栏
     */
    protected void setStatusBarColor(@ColorInt int color,int alpha) {
        StatusBarUtil.setColor(this, color,alpha);
    }

    /**
     * 接受 event 消息
     */
    @Subscribe
    public void onEventAsync(EventCenter event) {
        if (event != null) {
            onEventComing(event);
        }
    }

    /**
     * 接受消息后的具体处理逻辑
     */
    protected abstract void onEventComing(EventCenter event);

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ActivityStackManager.getInstance().removeActivity(new WeakReference<Activity>(this));
        super.onDestroy();
    }
}
