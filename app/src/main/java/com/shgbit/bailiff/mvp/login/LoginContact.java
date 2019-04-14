package com.shgbit.bailiff.mvp.login;

import com.shgbit.bailiff.base.BasePresenter;
import com.shgbit.bailiff.base.BaseView;
import com.shgbit.bailiff.common.ErrorMessage;

import java.util.WeakHashMap;

/**
 * @author:xushun on 2018/7/9
 * description :
 */

public interface LoginContact {

    interface OnLoginView extends BaseView {
        /**
         * 设置数据
         */
        void onSucess(String data);

        void onPasswordError(ErrorMessage error);
    }

    interface OnLoginPresenter extends BasePresenter {
        /**
         * 获取数据
         */
        void getData(WeakHashMap<String, Object> params);
    }
}
