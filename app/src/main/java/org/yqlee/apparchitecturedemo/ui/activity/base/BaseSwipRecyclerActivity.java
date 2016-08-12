package org.yqlee.apparchitecturedemo.ui.activity.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import org.yqlee.apparchitecturedemo.R;
import org.yqlee.apparchitecturedemo.ui.adapter.common.CommonRecyclerAdapter;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-12 上午 10:49
 * 描述 ：SwipeRefreshLayout+RecyclerView的组合
 */
public abstract class BaseSwipRecyclerActivity<T> extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommonRecyclerAdapter<T> mAdapter;

    protected void initRecyclerView(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                                    RecyclerView.LayoutManager layoutManager,
                                    CommonRecyclerAdapter<T> adapter) {
        mSwipeRefreshLayout = swipeRefreshLayout;
        mRecyclerView = recyclerView;
        mLayoutManager = layoutManager;
        mAdapter = adapter;

        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.colorAccent, R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = getLastVisiblePosition(mLayoutManager);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    && mAdapter.isShowFooter()) {
                onLoadMore();
            }
        }

        /**
         * 获取最后一条展示的位置
         */
        private int getLastVisiblePosition(RecyclerView.LayoutManager layoutManager) {
            int position;
            if (layoutManager instanceof LinearLayoutManager) {
                position = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                position = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {

                int[] lastPositions =
                        ((StaggeredGridLayoutManager) layoutManager).
                                findLastVisibleItemPositions(new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()]);
                position = getMaxPosition(lastPositions);
            } else {
                position = layoutManager.getItemCount() - 1;
            }
            return position;
        }

        /**
         * 获得最大的位置
         */
        private int getMaxPosition(int[] positions) {
            int size = positions.length;
            int maxPosition = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                maxPosition = Math.max(maxPosition, positions[i]);
            }
            return maxPosition;
        }
    };

    protected abstract void onLoadMore();

}
