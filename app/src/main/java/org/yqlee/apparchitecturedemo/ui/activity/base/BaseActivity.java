package org.yqlee.apparchitecturedemo.ui.activity.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.yqlee.apparchitecturedemo.R;
import org.yqlee.apparchitecturedemo.common.manager.AppManager;
import org.yqlee.apparchitecturedemo.common.utils.ToastUtil;

import butterknife.ButterKnife;

/**
 * 创建者：yqlee
 * 时间 ：2016/8/9  下午 5:46
 * 描述 ：Activity的抽取，第一层
 */
public abstract class BaseActivity extends AppCompatActivity {

    private boolean isDestroy;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        isDestroy = false;
        if (getLayoutResID() > 0) {
            setContentView(getLayoutResID());
            ButterKnife.bind(this);
        }
        afterView(savedInstanceState);
    }

    /**
     * 获取LayoutId
     *
     * @return 返回值必须大于0
     */
    protected abstract int getLayoutResID();

    protected abstract void afterView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
        AppManager.getAppManager().removeActivity(this);
    }

    public final void showToast(String msg) {
        ToastUtil.showCustomToast(this, msg);
    }

    public final void showToast(int strId) {
        ToastUtil.showCustomToast(this, strId);
    }

    public final void showLoading(Boolean flag) {
        showLoading(flag, getString(R.string.txt_loading));
    }

    public final void showLoading(Boolean flag, String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(msg);
        }
        if (flag) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 重载本页面
     */
    public final void reload() {
        reload(getIntent());
    }

    public final void reload(Intent intent) {
        if (intent == null)
            return;
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}
