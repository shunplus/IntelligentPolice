package com.shgbit.bailiff.base.baseImpl;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.BaseBeanObserver;
import com.shgbit.bailiff.base.BasePresenter;
import com.shgbit.bailiff.base.BaseView;
import com.shgbit.bailiff.bean.BaseBean;
import com.shgbit.bailiff.common.ErrorMessage;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.network.RetrofitUtils;
import com.shgbit.bailiff.util.NetStateUtil;

import java.util.WeakHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @author:xushun on 2018/7/8
 * description :
 */
public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    public BasePresenterImpl(V view) {
        this.view = view;
        start();
    }

    protected V view;


    @Override
    public void detach() {
        this.view = null;
        unDisposable();
    }

    @Override
    public void start() {

    }


    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    /**
     * 将Disposable添加
     *
     * @param subscription
     */
    @Override
    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 在主线程运行
     *
     * @param r
     */
    public void runOnUIThread(Runnable r) {
        LawUtils.runOnUIThread(r);
    }

    /**
     * post  form 表单 请求
     * 结果返回在main Thread
     *
     * @param url              请求接口地址
     * @param params           参数
     * @param t                Bean
     * @param baseBeanObserver 结果回调
     * @param <T>              泛型类型
     */
    public <T extends BaseBean> void post(final String url, final WeakHashMap<String, Object> params, final Class<T> t, final BaseBeanObserver<T> baseBeanObserver) {
        if (!NetStateUtil.checkEnable(LawUtils.getApplicationContext())) {
            ErrorMessage error = new ErrorMessage(RetrofitUtils.NET_FAIL, LawUtils.getString(R.string.no_net));
            baseBeanObserver.onError(error);
            return;
        }
        RetrofitUtils.getInstance().post(url, params, t, baseBeanObserver);
    }
}
