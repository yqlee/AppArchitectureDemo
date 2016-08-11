package org.yqlee.apparchitecturedemo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.yqlee.apparchitecturedemo.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-11 下午 4:43
 * 描述 ：统一界面管理、状态：showContent、Loading、empty、Error、NoNet
 */
public class BaseContentLayout extends FrameLayout {

    private Context context;
    private ViewMode currentView = ViewMode.CONTENT;
    private Map<ViewMode, View> viewMap = new HashMap<ViewMode, View>();

    private OnRetryCallback mOnRetryCallback;

    public static enum ViewMode {
        CONTENT, LOADING, ERROR, EMPTY, NONET
    }

    public BaseContentLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public BaseContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BaseContentLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public void init() {
        addLayerView(ViewMode.NONET,
                inflate(context, R.layout.layout_nonet_view, null));
        addLayerView(ViewMode.ERROR,
                inflate(context, R.layout.layout_error_view, null));
        addLayerView(ViewMode.EMPTY,
                inflate(context, R.layout.layout_empty_view, null));
        addLayerView(ViewMode.LOADING,
                inflate(context, R.layout.layout_loading_view, null));
        viewMap.get(ViewMode.ERROR).setOnClickListener(defultClick);
        viewMap.get(ViewMode.NONET).setOnClickListener(defultClick);
        viewMap.get(ViewMode.EMPTY).setOnClickListener(defultClick);
    }

    public BaseContentLayout addLayerView(ViewMode key, View view) {
        viewMap.put(key, view);
        return this;
    }

    public void setOnRetryCallback(OnRetryCallback mOnRetryCallback) {
        this.mOnRetryCallback = mOnRetryCallback;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int c = getChildCount();
        if (c > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ViewGroup) {
                viewMap.put(ViewMode.CONTENT, childView);
            } else {
            }
        } else {

        }

    }

    public void showContent() {
        show(ViewMode.CONTENT);
    }

    public void showNonet() {
        show(ViewMode.NONET);
    }

    public void showError() {
        show(ViewMode.ERROR);
    }

    public void showError(String tip) {
        setTextByViewMode(ViewMode.ERROR, tip);
        show(ViewMode.ERROR);
    }

    public void showError(View v) {
        FrameLayout mFrameLayout = (FrameLayout) viewMap.get(ViewMode.ERROR);
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(v);
        show(ViewMode.ERROR);
    }

    public void showEmpty() {
        show(ViewMode.EMPTY);
    }

    public void showEmpty(String tip) {
        setTextByViewMode(ViewMode.EMPTY, tip);
        show(ViewMode.EMPTY);
    }

    public void showEmpty(View v) {
        FrameLayout mFrameLayout = (FrameLayout) viewMap.get(ViewMode.EMPTY);
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(v);
        show(ViewMode.EMPTY);
    }

    public void showLoading() {
        show(ViewMode.LOADING);
    }

    public void showViewWithText(ViewMode m, String tip) {
        show(m);
        setTextByViewMode(m, tip);
    }

    public void showViewByMode(ViewMode key) {
        show(key);
    }

    private void setTextByViewMode(ViewMode m, String tip) {
        TextView tv_tip = (TextView) viewMap.get(m).findViewById(R.id.tip);
        if (tip != null)
            tv_tip.setText(tip);
    }

    public void showContentWithLoading() {
        View view = viewMap.get(ViewMode.LOADING);
        View contentView = viewMap.get(ViewMode.CONTENT);

        if (!isAdded(view)) {
            addView(view);
        }
        hideView(currentView);
        contentView.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        currentView = ViewMode.LOADING;
    }

    public void hideView(ViewMode key) {
        View v_hide = viewMap.get(key);
        v_hide.setVisibility(View.GONE);
    }

    public void showView(ViewMode key) {
        View v_shown = viewMap.get(key);
        v_shown.setVisibility(View.VISIBLE);
    }

    private void show(ViewMode key) {
        View view = viewMap.get(key);
        if (!isAdded(view)) {
            addView(view);
        }
        Set<Map.Entry<ViewMode, View>> entries = viewMap.entrySet();
        for (Map.Entry mEntry : entries) {
            View v = (View) mEntry.getValue();
            v.setVisibility(mEntry.getKey().toString().equals(key.name()) ? VISIBLE : GONE);
        }
        currentView = key;
    }

    public boolean isAdded(View view) {
        boolean isAdded = false;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child == view) {
                isAdded = true;
                break;
            } else {
            }
        }
        return isAdded;
    }

    public ViewMode getShownViewMode() {
        return currentView;
    }

    public View.OnClickListener defultClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOnRetryCallback != null) {
                showLoading();
                mOnRetryCallback.OnRetry();
            }
        }
    };

    public interface OnRetryCallback {
        void OnRetry();
    }

}
