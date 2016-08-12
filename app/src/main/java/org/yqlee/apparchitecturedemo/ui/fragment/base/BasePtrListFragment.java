package org.yqlee.apparchitecturedemo.ui.fragment.base;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.yqlee.apparchitecturedemo.R;
import org.yqlee.apparchitecturedemo.ui.adapter.common.CommonAbsLVAdapter;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-10 下午 3:01
 * 描述 ：Fragment的抽取，第二层，封装ultra-ptr下拉刷新
 */
public abstract class BasePtrListFragment<T> extends BaseFragment implements AbsListView.OnScrollListener, PtrHandler {

    protected int mPageSize = 0;//页数，默认20，可以设置
    public final static int PAGE_SIZE = 20;//ListView一页加载的数量

    private boolean mLastItemVisible;
    private boolean mFirstItemVisible;
    private FrameLayout mFooterView;
    private TextView mFooterText;
    protected ListView mListView;
    protected CommonAbsLVAdapter<T> mAdapter;


    private int mMinSizeWhenShowText = 5;
    protected boolean isRemoveFooterWhenEnd;
    private View mProgressWheel;

    @Override
    protected void afterView(View view, Bundle savedInstanceState) {
        mPageSize = PAGE_SIZE;
        afterView(view);
    }

    public void initListView(PtrClassicFrameLayout mPtrFrameLayout, ListView lv, CommonAbsLVAdapter<T> adapter) {
        mListView = lv;
        mAdapter = adapter;
        mListView.setOnScrollListener(this);
        initFooterView();
        mListView.setAdapter(adapter);
        if (mPtrFrameLayout != null) {
            MaterialHeader header = getMaterialHeader();
            mPtrFrameLayout.setHeaderView(header);
            mPtrFrameLayout.addPtrUIHandler(header);
            mPtrFrameLayout.setPtrHandler(this);
        }
    }

    private MaterialHeader getMaterialHeader() {
        MaterialHeader header = new MaterialHeader(mActivity);
        header.setColorSchemeColors(getResources().getIntArray(R.array.google_colors));
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, 30, 0, 20);
        return header;
    }

    public void initFooterView() {
        mFooterView = (FrameLayout) View.inflate(getActivity(), R.layout.footer_loading_view, null);
        mFooterView.setVisibility(View.GONE);
        mFooterText = (TextView) mFooterView.findViewById(R.id.footer_text);
        mProgressWheel = mFooterView.findViewById(R.id.pull_to_refresh_progress);
        mListView.addFooterView(mFooterView);
    }

    public boolean isHasFooterView() {
        if (mListView != null && mListView.getFooterViewsCount() > 0) return true;
        return false;
    }

    public void updateFooterTipText(String text) {
        if (mFooterView.getVisibility() != View.VISIBLE) mFooterView.setVisibility(View.VISIBLE);
        if (mFooterText != null && text != null) {
            mFooterText.setText(text);
        }
    }

    public void updateFooterTextAndhideLoad(String text) {
        if (mFooterView.getVisibility() != View.VISIBLE) mFooterView.setVisibility(View.VISIBLE);
        if (mFooterText != null && text != null) {
            mFooterText.setText(text);
            mProgressWheel.setVisibility(View.GONE);
        }
    }

    public boolean checkIsEnd() {
        int count = mAdapter.getCount();
        if (count < mPageSize) {
            return true;
        } else {
            if (count % mPageSize != 0) {
                return true;
            }
        }
        return false;
    }

    protected void removeFootView() {
        if (mListView != null && mListView.getFooterViewsCount() > 0) {
            mListView.removeFooterView(mFooterView);
        }
    }

    public void removeFooterViewWhenEnd() {
        if (isRemoveFooterWhenEnd && isHasFooterView()) {
            if (mListView != null)
                mListView.removeFooterView(mFooterView);
        } else {
            if (mAdapter.getCount() >= mMinSizeWhenShowText) {
                mFooterView.setVisibility(View.GONE);
                updateFooterTextAndhideLoad(getString(R.string.txt_has_no_more));
            }
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible) {
            mFooterView.setVisibility(View.GONE);
            if (!checkIsEnd()) {
                updateFooterTipText(getString(R.string.txt_loading));
                onLoadMore();
            } else {
                removeFooterViewWhenEnd();
            }
        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mFirstItemVisible) {
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 2);
        mFirstItemVisible = (totalItemCount > 0) && (firstVisibleItem <= 1);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        onRefresh(frame);
    }

    protected abstract void onRefresh(PtrFrameLayout ptrFrameLayout);

    protected abstract void afterView(View view);

    protected abstract void onLoadMore();
}
