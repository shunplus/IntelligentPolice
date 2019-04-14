package com.shgbit.bailiff.mvp.login;


import com.shgbit.bailiff.base.baseImpl.BaseFragment;

/**
 * Created by xushun on 2018/9/13.
 */

public class LoginFragment extends BaseFragment<FragmentPresent> implements FragmentContact.framenView {


    @Override
    public void set() {

    }


    @Override
    public FragmentPresent initPresenter() {
        return new FragmentPresent(this);
    }

    @Override
    public int setContentView() {
        return 0;
    }

    @Override
    protected void firstLoad() {
        super.firstLoad();
        presenter.get();
    }
}
