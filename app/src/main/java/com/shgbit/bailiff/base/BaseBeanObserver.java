package com.shgbit.bailiff.base;


import com.shgbit.bailiff.common.ErrorMessage;

import io.reactivex.disposables.Disposable;

/**
 * Created by xushun on 2018/9/13.
 * 网络请求获取数据后回调
 */

public interface BaseBeanObserver<T> {
    void onSubscribe(Disposable d);

    void onNext(T t);

    void onError(ErrorMessage error);

    void onComplete();
}
