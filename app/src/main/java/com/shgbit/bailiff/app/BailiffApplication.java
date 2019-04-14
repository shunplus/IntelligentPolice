package com.shgbit.bailiff.app;

import android.app.Application;

import com.shgbit.bailiff.config.ConstantsApi;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.network.interceptors.HeaderInterceptor;


/**
 * @author:xushun on 2018/7/5
 * description :
 */

public class BailiffApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LawUtils.init(this)
                .withApiHost(ConstantsApi.HOST)
                .withInterceptor(new HeaderInterceptor())
                .configure();
    }
}
