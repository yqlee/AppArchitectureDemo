package org.yqlee.apparchitecturedemo.ui.activity.recyclerviewpage;

import android.os.Bundle;

import org.yqlee.apparchitecturedemo.R;
import org.yqlee.apparchitecturedemo.ui.activity.base.BasePtrListActivity;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-10 下午 4:05
 * 描述 ：ListView展示数据
 */
public class ListViewPageListview extends BasePtrListActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_list_view_page_listview;
    }

    @Override
    protected void afterView(Bundle savedInstanceState) {

    }

    @Override
    protected void onLoadMore() {

    }

    @Override
    protected void onRefresh(PtrFrameLayout ptrFrameLayout) {

    }
}
