package org.yqlee.apparchitecturedemo.netbusiness;

import org.yqlee.apparchitecturedemo.bean.BaseEntity;

import rx.functions.Func1;

/**
 * 创建者：yqlee
 * 时间 ：2016-08-11 下午 3:38
 * 描述 ：
 */

public class CommonFunc<T> implements Func1<BaseEntity<T>, BaseEntity<T>> {
    @Override
    public BaseEntity<T> call(BaseEntity<T> baseEntity) {
        if (baseEntity.getCount() == 0) {
            throw new RuntimeException("ddd");
        }
        return baseEntity;
    }
}
