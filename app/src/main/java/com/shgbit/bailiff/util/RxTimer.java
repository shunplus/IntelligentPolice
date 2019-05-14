package com.shgbit.bailiff.util;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by xushun on  2019/5/9 21:51.
 * Email：shunplus@163.com
 * Des：利用RxJava的操作符
 * 指定时间后执行一次，指定时间轮训task
 */
public class RxTimer {

    private Disposable mDisposable;

    /**
     *milliseconds毫秒后执行next操作
     * @param milliSeconds
     * @param iRxNext
     */
    public void timer(long milliSeconds ,final IRxNext iRxNext){
        Observable.timer(milliSeconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (iRxNext != null) {
                            iRxNext.doNext(aLong);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 每隔milliseconds毫秒后执行next操作
     * @param millliseconds  间隔时间
     * @param iRxNext  需要处理的操作
     */
    public void interval(long millliseconds,final IRxNext iRxNext){
        Observable.interval(millliseconds,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (iRxNext!=null){
                        iRxNext.doNext(aLong); }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface IRxNext{
        void doNext(long number);
    }

    /**
     * 取消订阅
     */
    public void  cancel(){
        if (mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }
}
