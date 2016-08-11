package org.yqlee.apparchitecturedemo.netbusiness;

import org.yqlee.apparchitecturedemo.bean.BaseEntity;
import org.yqlee.apparchitecturedemo.bean.SubjectEntity;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-10 下午 5:57
 * 描述 ：
 */
public class AppNetFactory {

    private static <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public static void getMovieSubjects(Subscriber<BaseEntity<List<SubjectEntity>>> subscriber,
                                        int start, int count) {
        Observable observable = NetUtil.getInstance().getmAppService().getTopMovie(start, count)
                .map(new CommonFunc<List<SubjectEntity>>());
        toSubscribe(observable, subscriber);
    }
}
