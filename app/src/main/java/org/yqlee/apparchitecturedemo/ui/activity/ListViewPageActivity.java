package org.yqlee.apparchitecturedemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.yqlee.apparchitecturedemo.R;
import org.yqlee.apparchitecturedemo.bean.BaseEntity;
import org.yqlee.apparchitecturedemo.bean.SubjectEntity;
import org.yqlee.apparchitecturedemo.netbusiness.AppNetFactory;
import org.yqlee.apparchitecturedemo.ui.activity.base.BaseActivity;
import org.yqlee.apparchitecturedemo.ui.activity.base.BasePtrListActivity;
import org.yqlee.apparchitecturedemo.ui.adapter.ListViewAdapter;
import org.yqlee.apparchitecturedemo.ui.widget.BaseContentLayout;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Subscriber;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-12 上午 9:14
 * 描述 ：ListView页面的展示
 */
public class ListViewPageActivity extends BasePtrListActivity<SubjectEntity> implements BaseContentLayout.OnRetryCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.layout_content)
    BaseContentLayout layout_content;
    @BindView(R.id.ptr_fl)
    PtrClassicFrameLayout ptr_fl;
    @BindView(R.id.lv_content)
    ListView lv_content;
    private ListViewAdapter mAdapter;
    private int startNum = 0;

    public static void startActivity(BaseActivity mActivity) {
        Intent intent = new Intent(mActivity, ListViewPageActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_list_view_page;
    }

    @Override
    protected void afterView(Bundle savedInstanceState) {

        initToolbar(toolbar, tv_title, "ListView展示页", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_content.showLoading();
        layout_content.setOnRetryCallback(this);
        mAdapter = new ListViewAdapter(this);
        lv_content.setAdapter(mAdapter);
        initListView(ptr_fl, lv_content, mAdapter);
        requestData(startNum);
    }

    private void requestData(int startNum) {

        AppNetFactory.getMovieSubjects(new Subscriber<BaseEntity<List<SubjectEntity>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                layout_content.showError();
                ptr_fl.refreshComplete();
            }

            @Override
            public void onNext(BaseEntity<List<SubjectEntity>> listBaseEntity) {
                handleData(listBaseEntity);
                ptr_fl.refreshComplete();
            }


        }, startNum, 20);

    }

    private void handleData(BaseEntity<List<SubjectEntity>> listBaseEntity) {
        if (startNum == 0) {
            layout_content.showContent();
            mAdapter.clearData();
        }
        List<SubjectEntity> subjects = listBaseEntity.getSubjects();
        mAdapter.addData(subjects);
    }

    @Override
    protected void onLoadMore() {
        startNum += 20;
        requestData(startNum);
    }

    @Override
    protected void onRefresh(PtrFrameLayout ptrFrameLayout) {
        startNum = 0;
        requestData(startNum);
    }

    @Override
    public void OnRetry() {
        reload();
    }
}
