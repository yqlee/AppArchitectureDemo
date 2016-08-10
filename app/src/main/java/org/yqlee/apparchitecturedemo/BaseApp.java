package org.yqlee.apparchitecturedemo;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.squareup.leakcanary.LeakCanary;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-10 下午 3:23
 * 描述 ：Application
 */
public class BaseApp extends Application {

    private static BaseApp sGlobleContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sGlobleContext = this;
        LeakCanary.install(this);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

    public static BaseApp getClobleCtx() {
        return sGlobleContext;
    }

    class AppBlockCanaryContext extends BlockCanaryContext {

        @Override
        public int getConfigBlockThreshold() {
            return 1000;
        }

        @Override
        public boolean isNeedDisplay() {
            return BuildConfig.DEBUG;
        }

        @Override
        public String getLogPath() {
            return super.getLogPath();
        }
    }
}
