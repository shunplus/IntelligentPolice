package com.shgbit.bailiff.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.baseImpl.BaseActivity;
import com.shgbit.bailiff.common.ErrorMessage;
import com.shgbit.bailiff.mvp.court.SelectCourtActivity;
import com.shgbit.bailiff.util.PLog;

import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author:xushun on 2018/7/8
 * description :登录
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContact.OnLoginView {
    private String userCode, crop, device, upw;
    private final static String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.login_btn)
    TextView loginBtn;
    private Unbinder bind;
//    private Observer<String> observer = new Observer<String>() {
//        @Override
//        public void onChanged(String s) {
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        bind = ButterKnife.bind(this);
        PLog.i("onCreate");
//        LiveEventBus.get().with("login", String.class)
//                .observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(String s) {
//
//                    }
//                });
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }


    /**
     * 点击登录
     */
    @OnClick(R.id.login_btn)
    public void onLogin() {
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        userCode = "ceshi";
        crop = "TT01";
        device = "861243043273741";
        upw = "1";
        params.put("userCode", userCode);
        params.put("crop", crop);
        params.put("device", device);
        params.put("upw", upw);
        mvpPresenter.getData(params);
    }

    @Override
    public void onSucess(String data) {
        //TODO: save passWord； start  MainActivity
        showMessage(data);
//        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
//        LawUtils.getPreference().saveString(Constants.USER_CODE, userCode);
//        LawUtils.getPreference().saveString(Constants.CROP, crop);
//        LawUtils.getPreference().saveString(Constants.DEVICE, device);
//        LawUtils.getPreference().saveString(Constants.UPW, upw);
    }

    @Override
    public void onPasswordError(ErrorMessage error) {
        handleError(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
//        LiveEventBus.get().with("login", String.class).removeObserver(observer);
    }

    @OnClick(R.id.get_fayuan)
    public void getFayuan() {
        Intent intent = new Intent(this, SelectCourtActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
