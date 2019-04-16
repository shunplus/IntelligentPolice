package com.shgbit.bailiff.mvp.login;

import android.content.Context;
import android.content.Intent;

import com.shgbit.bailiff.base.baseImpl.BasePresenterImpl;

import java.util.WeakHashMap;

/**
 * @author:xushun on 2018/7/9
 * description :
 */

public class LoginPresenter extends BasePresenterImpl<LoginContact.OnLoginView> implements LoginContact.OnLoginPresenter {
    public LoginPresenter(LoginContact.OnLoginView view) {
        super(view);
    }


    @Override
    public void getData(WeakHashMap<String, Object> params) {
        if (view != null) {
            view.showLoadingDialog();
        }
//        RetrofitUtils.getInstance().postLogin(params, new BaseBeanObserver<BaseBean>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                addDisposable(d);
//            }
//
//            @Override
//            public void onNext(BaseBean baseBean) {
//                view.dismissLoadingDialog();
//                view.showMessage(baseBean.getMessage());
//                view.onSucess("123444");
//            }
//
//            @Override
//            public void onError(ErrorMessage error) {
//                view.dismissLoadingDialog();
//                view.onPasswordError(error);
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

    public void startActivity(Context context, Class mClass) {
        Intent intent = new Intent(context, mClass);
        context.startActivity(intent);
    }
}
