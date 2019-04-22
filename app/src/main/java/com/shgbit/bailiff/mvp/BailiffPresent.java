package com.shgbit.bailiff.mvp;

import com.shgbit.bailiff.base.BaseBeanObserver;
import com.shgbit.bailiff.base.baseImpl.BasePresenterImpl;
import com.shgbit.bailiff.bean.UpdateMessageBean;
import com.shgbit.bailiff.common.ErrorMessage;
import com.shgbit.bailiff.config.ConstantsApi;

import java.util.WeakHashMap;

import io.reactivex.disposables.Disposable;

/**
 * Created by xushun on 2019/4/18.
 * Des:
 * Email:shunplus@163.com
 */

public class BailiffPresent extends BasePresenterImpl<BailiffContact.OnMianView> implements BailiffContact.OnMianPresent {
    public BailiffPresent(BailiffContact.OnMianView view) {
        super(view);
    }


    @Override
    public void getUplate(int versionCode) {
        if (view != null) {
            view.showLoadingDialog();
        }
        WeakHashMap<String, Object> parms = new WeakHashMap<>();
        parms.put("versionCode", versionCode);
        parms.put("code", "android.exjudge.version");
        post(ConstantsApi.APP_CHECK_UPDATE, parms, UpdateMessageBean.class, new BaseBeanObserver<UpdateMessageBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(UpdateMessageBean baseBean) {
                if (view != null) {
                    view.dismissLoadingDialog();
                    if (baseBean.getData() != null) {
                        view.setUpdate(baseBean.getData());
                    }
                }
            }

            @Override
            public void onError(ErrorMessage error) {
                if (view != null) {
                    view.dismissLoadingDialog();
                    view.handleError(error);
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
