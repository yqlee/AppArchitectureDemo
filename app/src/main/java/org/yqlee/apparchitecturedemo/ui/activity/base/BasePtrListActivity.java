package org.yqlee.apparchitecturedemo.ui.activity.base;

import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.yqlee.apparchitecturedemo.R;
import org.yqlee.apparchitecturedemo.ui.adapter.common.CommonAdapter;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-10 下午 2:33
 * 描述 ：Activity的抽取，第二层，封装ultra-ptr下拉刷新
 */
public abstract class BasePtrListActivity<T> extends BaseActivity implements AbsListView.OnScrollListener, PtrHandler {

    private ListView mListView;
    private CommonAdapter<T> mAdapter;
    protected FrameLayout mFooterView;
    private TextView mFooterText;
    private View mProgressWheel;

    private boolean mLastItemVisible;
    private boolean mFirstItemVisible;
    public static int PAGE_SIZE = 20;//ListView一页加载的数量

    protected void initListView(PtrClassicFrameLayout ptrFrameLayout, ListView listView, CommonAdapter<T> adapter) {
        mListView = listView;
        mAdapter = adapter;
        mListView.setOnScrollListener(this);
        initFooterView();
        mListView.setAdapter(adapter);
        if (ptrFrameLayout != null) {
            MaterialHeader header = getMaterialHeader();
            ptrFrameLayout.setHeaderView(header);
            ptrFrameLayout.addPtrUIHandler(header);
            ptrFrameLayout.setPtrHandler(this);
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible) {
            if (!checkIsEnd()) {
                updateFooterTipText(getString(R.string.txt_loading));
                onLoadMore();
            } else {
                removeFooterViewWhenEnd();
            }
        } else if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mFirstItemVisible) {
            removeFooterViewWhenEnd();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 2);
        mFirstItemVisible = (totalItemCount > 0) && (firstVisibleItem <= 1);
    }

    private boolean checkIsEnd() {
        int count = mAdapter.getCount();
        //count<PAGE_SIZE
        if (count < PAGE_SIZE) {
            return true;
        }//count>=PAGE_SIZE
        else {
            //模不等于0，说明最后一次加载的数据不是不是20，那么久说明数据已经加载完了
            if (count % PAGE_SIZE != 0) {
                return true;
            }
        }
        return false;
    }

    private void updateFooterTipText(String text) {
        if (mFooterView.getVisibility() != View.VISIBLE) mFooterView.setVisibility(View.VISIBLE);
        if (mFooterText != null && text != null) {
            mFooterText.setText(text);
        }
    }

    private void removeFooterViewWhenEnd() {
        if (mListView != null && mListView.getFooterViewsCount() > 0)
            mListView.removeFooterView(mFooterView);
    }


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (mListView.getFooterViewsCount()==0){
            mListView.addFooterView(mFooterView);
        }
        onRefresh(frame);
    }


    private MaterialHeader getMaterialHeader() {
        MaterialHeader header = new MaterialHeader(this);
        header.setColorSchemeColors(getResources().getIntArray(R.array.google_colors));
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, 30, 0, 20);
        return header;
    }

    protected void initFooterView() {
        mFooterView = (FrameLayout) View.inflate(this, R.layout.footer_loading_view, null);
        mFooterView.setVisibility(View.GONE);
        mFooterText = (TextView) mFooterView.findViewById(R.id.footer_text);
        mProgressWheel = mFooterView.findViewById(R.id.pull_to_refresh_progress);
        mListView.addFooterView(mFooterView, null, false);
    }

    protected abstract void onLoadMore();

    protected abstract void onRefresh(PtrFrameLayout ptrFrameLayout);
}
