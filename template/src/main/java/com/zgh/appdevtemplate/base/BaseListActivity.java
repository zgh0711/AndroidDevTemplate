package com.zgh.appdevtemplate.base;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.zgh.appdevtemplate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通list的 BaseActivity,封装一些公共逻辑，子类只要实现部分抽象方法就可以完成页面
 */

public abstract class BaseListActivity<T> extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    protected static final int     LINEAR_LAYOUT         = 0;//普通list布局
    protected static final int     GRID_LAYOUT           = 1;//grid布局
    protected static final int     STAGGERED_GRID_LAYOUT = 2;//瀑布流布局
    private                int     itemLayoutResId       = -1;//子布局id

    private   View                    mHeaderView;
    private   LoadMoreView            mLoadMoreView;
    protected RecyclerView            mRecyclerView;
    protected SwipeRefreshLayout      mRefreshLayout;
    protected BaseRecyclerViewAdapter mAdapter;

    @Override
    protected void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        initItemLayout();
        if (-1 == itemLayoutResId) {
            throw new RuntimeException("itemLayoutResId is null!");
        }

        mAdapter = new BaseRecyclerViewAdapter(itemLayoutResId, new ArrayList<T>());
        initRecyclerView();
        mAdapter.setLoadMoreView(getLoadMoreView());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化子布局，子类需在这里调用 setItemLayout 和 setListType
     * 传入 itemLayout 和 布局类型等参数
     */
    protected abstract void initItemLayout();

    /**
     * 设置子布局layout，子类必须在 initItemLayout 里面调用此方法
     */
    public void setItemLayout(@LayoutRes int layoutResId) {
        this.itemLayoutResId = layoutResId;
    }

    /**
     * 设置布局类型和方向，子类必须在 initItemLayout 里面调用此方法
     */
    protected void setListType(int type, boolean isVertical) {
        chooseListType(type, isVertical, 1);
    }

    protected void setListType(int type, boolean isVertical, int columns) {
        chooseListType(type, isVertical, columns);
    }

    /**
     * 初始化 recyclerView 各种状态处理，在这个方法里处理的是 recyclerView 的所有的初始化，
     * 包括对他的展示形式，是list或grid或瀑布流，是否开启上啦加载，是否自定义加载动画，
     * 开启下拉刷新，给下拉刷新设置颜色，添加 headerView，初始化 adapter 数据等
     */
    protected abstract void initRecyclerView();

    /**
     * 添加 headerView
     */
    protected void setHeaderView(@NonNull Context context, @LayoutRes int headerView) {
        if (mRecyclerView != null && mAdapter != null) {
            mHeaderView = LayoutInflater.from(context).inflate(headerView, mRecyclerView, false);
            mAdapter.setHeaderView(mHeaderView);
            initHeaderView(mHeaderView);
        }
    }

    /**
     * 初始化 headerView 中的控件及相关逻辑
     */
    protected abstract void initHeaderView(View headerView);

    /**
     * 开启下拉刷新并设置刷新颜色
     */
    protected void openRefresh(@ColorRes int... colorResIds) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setColorSchemeResources(colorResIds);
            mRefreshLayout.setOnRefreshListener(this);
        }
    }

    /**
     * SwipeRefresh 的刷新回调
     */
    @Override
    public void onRefresh() {
        onRefreshListener();
    }

    /**
     * 下拉刷新监听
     */
    protected abstract void onRefreshListener();

    /**
     * 调用此方法则开启上拉加载
     */
    protected void openLoadMore() {
        if (mRecyclerView != null) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        }
    }

    /**
     * 设置上拉加载时要显示的 View，需要传入一个自定义的 LoadMoreView，
     * 写法参照 CustomLoadMoreView，也可以不调用这个方法，
     * 不调用此方法的话就是使用 BaseRecyclerViewAdapterHelper 自带的默认加载动画
     */
    protected void setLoadMordView(LoadMoreView loadMoreView) {
        this.mLoadMoreView = loadMoreView;
    }

    /**
     * 获取上啦加载动画，有自定义的就使用自定义动画，没有则用默认动画
     */
    public LoadMoreView getLoadMoreView() {
        return mLoadMoreView == null ? new LoadMoreView() {
            @Override
            public int getLayoutId() {
                return R.layout.quick_view_load_more;
            }

            @Override
            protected int getLoadingViewId() {
                return R.id.load_more_loading_view;
            }

            @Override
            protected int getLoadFailViewId() {
                return R.id.load_more_load_fail_view;
            }

            @Override
            protected int getLoadEndViewId() {
                return R.id.load_more_load_end_view;
            }
        } : mLoadMoreView;
    }

    /**
     * BaseRecyclerViewAdapterHelper 库的上拉加载回调
     */
    @Override
    public void onLoadMoreRequested() {
        onLoadMoreLister();
    }

    /**
     * adapter的上拉加载监听
     */
    protected abstract void onLoadMoreLister();

    /**
     * 选择布局种类
     */
    private void chooseListType(int listType, boolean isVertical, int columns) {
        if (mRecyclerView != null) {
            switch (listType) {
                case LINEAR_LAYOUT:
                    //设置布局管理器
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(
                            isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    break;
                case GRID_LAYOUT:
                    GridLayoutManager gridLayoutManager =
                            new GridLayoutManager(this, columns);
                    gridLayoutManager.setOrientation(
                            isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
                    mRecyclerView.setLayoutManager(gridLayoutManager);
                    break;
                case STAGGERED_GRID_LAYOUT:
                    //设置布局管理器
                    StaggeredGridLayoutManager staggeredGridLayoutManager =
                            new StaggeredGridLayoutManager(columns,
                                                           isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
                    mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                    break;
                default:
                    //设置布局管理器
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(
                            isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
                    mRecyclerView.setLayoutManager(layoutManager);
                    break;
            }
        }
    }

    /**
     * adapter内的处理
     */
    protected abstract void MyHolder(BaseViewHolder baseViewHolder, T t);

    public class BaseRecyclerViewAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        public BaseRecyclerViewAdapter(int layoutResId, List<T> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, T t) {
            MyHolder(baseViewHolder, t);
        }
    }
}
