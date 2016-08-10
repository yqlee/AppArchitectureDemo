package org.yqlee.apparchitecturedemo.ui.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yqlee.apparchitecturedemo.ui.activity.base.BaseActivity;


public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;
    protected Context mContext;
    protected boolean isViewPrepared;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            mActivity = (BaseActivity) activity;
        }
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = initRootView(inflater);
        isViewPrepared = true;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterView(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInvisible();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    protected void onInvisible() {
    }

    protected void onVisible() {
    }

    protected abstract void afterView(View view, Bundle savedInstanceState);

    protected abstract View initRootView(LayoutInflater inflater);

}
