package org.yqlee.apparchitecturedemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.yqlee.apparchitecturedemo.R;
import org.yqlee.apparchitecturedemo.bean.BaseEntity;
import org.yqlee.apparchitecturedemo.bean.SubjectEntity;
import org.yqlee.apparchitecturedemo.netbusiness.AppNetFactory;
import org.yqlee.apparchitecturedemo.ui.activity.base.BaseActivity;
import org.yqlee.apparchitecturedemo.ui.activity.base.BaseSwipRecyclerActivity;
import org.yqlee.apparchitecturedemo.ui.adapter.RecyclerViewAdapter;
import org.yqlee.apparchitecturedemo.ui.widget.BaseContentLayout;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-12 上午 9:15
 * 描述 ：RecyclerView的页面展示
 */
public class RecyclerViewPageActivity extends BaseSwipRecyclerActivity<SubjectEntity> implements BaseContentLayout.OnRetryCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.layout_content)
    BaseContentLayout layout_content;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;
    @BindView(R.id.view_recycler)
    RecyclerView view_recycler;

    private RecyclerViewAdapter mAdapter;
    private int startNum = 0;

    public static void startActivity(BaseActivity mActivity) {
        Intent intent = new Intent(mActivity, RecyclerViewPageActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_recycler_view_page;
    }

    @Override
    protected void afterView(Bundle savedInstanceState) {

        initToolbar(toolbar, tv_title, "RecyclerView展示数据", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAdapter = new RecyclerViewAdapter(this);
        initRecyclerView(layout_refresh, view_recycler, new LinearLayoutManager(this),
                mAdapter);
        requestData(startNum);
    }

    private void requestData(int startNum) {

        AppNetFactory.getMovieSubjects(new Subscriber<BaseEntity<List<SubjectEntity>>>() {
            @Override
            public void onCompleted() {
                layout_refresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
            layout_refresh.setRefreshing(false);
            }

            @Override
            public void onNext(BaseEntity<List<SubjectEntity>> listBaseEntity) {
                handleData(listBaseEntity);
                layout_refresh.setRefreshing(false);
            }

        }, startNum, 20);
    }

    private void handleData(BaseEntity<List<SubjectEntity>> listBaseEntity) {
        if (startNum == 0) {
            mAdapter.setDatas(listBaseEntity.getSubjects());
        } else {
            mAdapter.addDatas(listBaseEntity.getSubjects());
        }
    }

    @Override
    protected void onLoadMore() {
        startNum += 20;
        requestData(startNum);
    }

    @Override
    public void onRefresh() {
        startNum = 0;
        requestData(startNum);
    }

    @Override
    public void OnRetry() {
        reload();
    }
}
