package org.yqlee.apparchitecturedemo.netbusiness;

import org.yqlee.apparchitecturedemo.bean.BaseEntity;
import org.yqlee.apparchitecturedemo.bean.SubjectEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-10 下午 5:10
 * 描述 ：
 */
public interface AppService {

    @GET("top250")
    Observable<BaseEntity<List<SubjectEntity>>> getTopMovie(@Query("start") int start, @Query("count") int count);

}
