package com.zgh.templatetest.theMVP.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zgh.appdevtemplate.theMVP.view.BaseDelegate;
import com.zgh.templatetest.R;

/**
 * Created by ZGH on 2017/6/27.
 */

public class ListDelegate extends BaseDelegate {
    private RecyclerView mRecyclerView;
    @Override
    public void initWidget() {
        mRecyclerView = getView(R.id.recyclerView);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getRootLayoutId() {
        return R.layout.activity_list_mvp;
    }
}
