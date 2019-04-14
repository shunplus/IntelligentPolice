package com.shgbit.bailiff.mvp.login;


import com.shgbit.bailiff.base.baseImpl.BasePresenterImpl;

/**
 * Created by xushun on 2018/9/13.
 */

public class FragmentPresent extends BasePresenterImpl<FragmentContact.framenView> implements FragmentContact.framenPresent {
    public FragmentPresent(FragmentContact.framenView view) {
        super(view);
    }

    @Override
    public void get() {
        view.set();
    }
}
