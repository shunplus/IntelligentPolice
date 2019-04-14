package com.shgbit.bailiff.mvp.login;


import com.shgbit.bailiff.base.BasePresenter;
import com.shgbit.bailiff.base.BaseView;

/**
 * Created by xushun on 2018/9/13.
 */

public interface FragmentContact {
    interface framenView extends BaseView {
        void set();
    }

    interface framenPresent extends BasePresenter {
        void get();
    }
}
