package org.yqlee.apparchitecturedemo.netbusiness;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-10 下午 4:57
 * 描述 ：网络工具类
 */
public class NetUtil {

    private static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 10;
    private Retrofit mRetrofit;
    AppService mAppService;

    private NetUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mAppService = mRetrofit.create(AppService.class);
    }

    public AppService getmAppService() {
        return mAppService;
    }

    private static class SingletonHolder {
        private static final NetUtil INSTANCE = new NetUtil();
    }

    public static NetUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
