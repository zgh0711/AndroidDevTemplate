package com.zgh.templatetest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgh.appdevtemplate.R;
import com.zgh.appdevtemplate.base.BaseFragment;
import com.zgh.appdevtemplate.event.EventCenter;
import com.zgh.appdevtemplate.view.BottomBar;
import com.zgh.appdevtemplate.view.BottomBarTab;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    private SupportFragment[] mFragments = new SupportFragment[4];
    public static final int               FIRST      = 0;
    public static final int               SECOND     = 1;
    public static final int               THIRD      = 2;
    public static final int               FOUR       = 3;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //初始化四个 fragment 对应底部的四个 tab
        if (savedInstanceState == null) {
            mFragments[FIRST] = BlankFragment.newInstance();
            mFragments[SECOND] = ListFragment.newInstance();
            mFragments[THIRD] = BlankFragment.newInstance();
            mFragments[FOUR] = ListFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_mainFragment, FIRST, mFragments[FIRST], mFragments[SECOND],
                                     mFragments[THIRD], mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findChildFragment(BlankFragment.class);
            mFragments[SECOND] = findChildFragment(ListFragment.class);
            mFragments[THIRD] = findChildFragment(BlankFragment.class);
            mFragments[FOUR] = findChildFragment(ListFragment.class);
        }

        initBottomNav(view);
        return view;
    }

    @Override
    protected void getBundleExtras(Bundle arguments) {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void onEventComing(EventCenter event) {

    }

    //底部导航栏及其事件
    private void initBottomNav(View view) {
        BottomBar bottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
        bottomBar.addItem(new BottomBarTab(_mActivity, R.mipmap.ic_launcher, "首页"));
        bottomBar.addItem(new BottomBarTab(_mActivity, R.mipmap.ic_launcher, "首页"));
        bottomBar.addItem(new BottomBarTab(_mActivity, R.mipmap.ic_launcher, "首页"));
        bottomBar.addItem(new BottomBarTab(_mActivity, R.mipmap.ic_launcher, "首页"));

        //        bottomBar.getItem(1).setUnreadCount(8);// 模拟未读消息
        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // TODO: 2017/1/5 当重新选择tab时需要进行的操作，没有可不写
            }
        });
    }
}
