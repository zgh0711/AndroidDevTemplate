package com.zgh.templatetest.theMVP.presenter;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zgh.appdevtemplate.event.EventCenter;
import com.zgh.appdevtemplate.theMVP.presenter.ListActivityPresenter;
import com.zgh.templatetest.R;
import com.zgh.templatetest.theMVP.delegate.ListDelegate;
import com.zgh.templatetest.theMVP.model.TestData;

public class ListMVPActivity extends ListActivityPresenter<ListDelegate,TestData> {

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onEventComing(EventCenter event) {

    }

    @Override
    protected Class<ListDelegate> getDelegateClass() {
        return ListDelegate.class;
    }

    @Override
    protected void initItemLayout() {
        setItemLayout(R.layout.item_base_recyclerview);
        setListType(LINEAR_LAYOUT, true);
    }

    @Override
    protected void initRecyclerView() {
        for (int i = 0; i < 8; i++) {
            datas.add(new TestData(i + ""));
        }
        mAdapter.setNewData(datas);

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
    protected void myHolder(BaseViewHolder baseViewHolder, TestData testData) {
        baseViewHolder.setText(R.id.tv_item, testData.getContent());
    }
}
