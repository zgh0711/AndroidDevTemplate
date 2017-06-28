package com.zgh.templatetest.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zgh.appdevtemplate.base.BaseListFragment;
import com.zgh.appdevtemplate.event.EventCenter;
import com.zgh.templatetest.R;
import com.zgh.templatetest.theMVP.MVPMainActivity;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseListFragment<String> {


    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    protected void getBundleExtras(Bundle arguments) {

    }

    @Override
    protected void onEventComing(EventCenter event) {

    }

    @Override
    protected void initHeaderView(View headerView) {

    }

    @Override
    protected void initFooterView(View footerView) {
        footerView.findViewById(R.id.btn_goto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MVPMainActivity.class));
//                Intent intent= new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                String url="tmall://tmallclient/?{\"action\":”item:id=36615660686”}\n";
//                Uri uri = Uri.parse(url);
//                intent.setData(uri);
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRefreshListener() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    protected void initItemLayout() {
        setItemLayout(R.layout.item_base_recyclerview);
        setListType(GRID_LAYOUT, true, 3);
    }

    @Override
    protected void initRecyclerView() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            data.add("" + i);
        }

        openRefresh(R.color.md_deep_orange_600, R.color.md_deep_orange_800,
                    R.color.md_deep_orange_A100, R.color.md_deep_orange_A400);

//        setHeaderView(getActivity(), com.zgh.templatetest.R.layout.search_view);
        setFooterView(getActivity(), R.layout.footerview);
        mAdapter.setNewData(data);
    }

    @Override
    protected void onLoadMoreLister() {

    }

    @Override
    protected void myHolder(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_item, s);
    }
}
