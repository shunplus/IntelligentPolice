package com.shgbit.bailiff.mvp.login;

import android.os.Bundle;
import android.text.TextUtils;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.baseImpl.BaseActivity;
import com.shgbit.bailiff.common.ErrorMessage;
import com.shgbit.bailiff.common.LoadDialog;
import com.shgbit.bailiff.config.Constants;
import com.shgbit.bailiff.util.SpUtils;

import java.util.WeakHashMap;

/**
 * Created by xushun on 2018/9/18.
 * 启动页面
 */

public class SplashActivity extends BaseActivity<LoginPresenter> implements LoginContact.OnLoginView {
    private LoadDialog mLoadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mLoadDialog == null) {
            mLoadDialog = new LoadDialog(this, R.style.MyDialog, R.layout.dialog_loding_splash);
            mLoadDialog.setCanceledOnTouchOutside(false);
        }
        String userCode = SpUtils.getString(Constants.USER_CODE, "");
        String crop = SpUtils.getString(Constants.CROP, "");
        String device = SpUtils.getString(Constants.DEVICE, "");
        String upw = SpUtils.getString(Constants.UPW, "");
        if (!TextUtils.isEmpty(userCode) && !TextUtils.isEmpty(crop) &&
                !TextUtils.isEmpty(device) && !TextUtils.isEmpty(upw)) {
            final WeakHashMap<String, Object> params = new WeakHashMap<>();
            params.put("userCode", "ceshi");
            params.put("crop", "TT01");
            params.put("device", "861243043273741");
            params.put("upw", "1");
            mLoadDialog.show();
            mvpPresenter.getData(params);
        } else {
            mvpPresenter.startActivity(this, LoginActivity.class);
        }
    }

    @Override
    public void onSucess(String data) {
        if (mLoadDialog != null) {
            mLoadDialog.dismiss();
        }
        showMessage(data);
        //TODO: start mainActivity
    }

    @Override
    public void onPasswordError(ErrorMessage errorMessage) {
        if (mLoadDialog != null) {
            mLoadDialog.dismiss();
        }
        handleError(errorMessage);
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }


}
