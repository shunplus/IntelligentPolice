package com.shgbit.bailiff.app;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.shgbit.bailiff.config.ConstantsApi;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.mvp.location.LocationService;
import com.shgbit.bailiff.network.interceptors.HeaderInterceptor;


/**
 * @author:xushun on 2018/7/5
 * description :
 */

public class BailiffApplication extends Application {
    public LocationService locationService;
    @Override
    public void onCreate() {
        super.onCreate();
        LawUtils.init(this)
                .withApiHost(ConstantsApi.HOST)
                .withInterceptor(new HeaderInterceptor())
                .configure();
        locationService = new LocationService(getApplicationContext());
        // baidu sdk init
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
