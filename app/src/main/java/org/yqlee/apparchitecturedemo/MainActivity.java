package org.yqlee.apparchitecturedemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import org.yqlee.apparchitecturedemo.bean.BaseEntity;
import org.yqlee.apparchitecturedemo.bean.SubjectEntity;
import org.yqlee.apparchitecturedemo.netbusiness.AppNetFactory;
import org.yqlee.apparchitecturedemo.ui.activity.ListViewPageActivity;
import org.yqlee.apparchitecturedemo.ui.activity.RecyclerViewPageActivity;
import org.yqlee.apparchitecturedemo.ui.activity.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.bt_common_page)
    Button bt_common_page;
    @BindView(R.id.bt_listview_page)
    Button bt_listview_page;
    @BindView(R.id.bt_recyclerview_page)
    Button bt_recyclerview_page;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.bt_common_page)
    void go2CommonPage() {

        AppNetFactory.getMovieSubjects(new Subscriber<BaseEntity<List<SubjectEntity>>>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoading(true);
                Log.d(TAG, "onStart");
                showToast("onStart");
            }

            @Override
            public void onCompleted() {
                showLoading(false);
                Log.d(TAG, "onCompleted");
                showToast("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                showLoading(false);
                Log.d(TAG, "onError");
                showToast("onError");
            }

            @Override
            public void onNext(BaseEntity<List<SubjectEntity>> listBaseEntity) {
                Log.d(TAG, listBaseEntity.toString());
                showToast("获取数据成功");
                showLoading(false);
            }
        }, 0, 10);
    }

    @OnClick(R.id.bt_listview_page)
    void go2ListViewPage() {
        ListViewPageActivity.startActivity(this);
    }

    @OnClick(R.id.bt_recyclerview_page)
    void go2RecyclerViewPage() {

        RecyclerViewPageActivity.startActivity(this);

//        showToast("RecyclerView页面，待开发");
    }

}
