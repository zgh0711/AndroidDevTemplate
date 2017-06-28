package com.zgh.templatetest.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zgh.appdevtemplate.base.BaseListFragment;
import com.zgh.appdevtemplate.event.EventCenter;
import com.zgh.appdevtemplate.util.LogUtil;
import com.zgh.templatetest.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseListFragment<String> {


    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance() {
        Bundle args = new Bundle();
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    protected void getBundleExtras(Bundle arguments) {

    }

    @Override
    protected void initItemLayout() {
        setItemLayout(R.layout.item_base_recyclerview);
    }

    @Override
    protected void initRecyclerView() {
        setListType(LINEAR_LAYOUT, true);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("" + i);
        }

        mAdapter.addData(data);
        LogUtil.d(mAdapter);
        LogUtil.d(mRecyclerView);
    }

    @Override
    protected void initHeaderView(View headerView) {

    }

    @Override
    protected void initFooterView(View footerView) {

    }

    @Override
    protected void onRefreshListener() {

    }

    @Override
    protected void onLoadMoreLister() {

    }

    @Override
    protected void myHolder(BaseViewHolder baseViewHolder, String s) {

    }

    @Override
    protected void onEventComing(EventCenter event) {

    }

}
