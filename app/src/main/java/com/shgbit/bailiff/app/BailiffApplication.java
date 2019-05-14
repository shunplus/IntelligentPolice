package com.shgbit.bailiff.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.shgbit.bailiff.config.Constants;
import com.shgbit.bailiff.config.ConstantsApi;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.db.DaoMaster;
import com.shgbit.bailiff.db.DaoSession;
import com.shgbit.bailiff.mvp.location.LocationService;
import com.shgbit.bailiff.network.interceptors.HeaderInterceptor;
import com.tencent.mmkv.MMKV;


/**
 * @author:xushun on 2018/7/5
 * description :
 */

public class BailiffApplication extends Application {
    public LocationService locationService;
    private static BailiffApplication mApp;
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        LawUtils.init(mApp)
                .withApiHost(ConstantsApi.HOST)
                .withInterceptor(new HeaderInterceptor())
                .configure();
        //初始化MMKV 数据存储
        MMKV.initialize(this);
        locationService = new LocationService(getApplicationContext());
        // baidu sdk init
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        initGreenDao();
    }

    private void initGreenDao() {
        //创建数据库mydb.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mApp, Constants.DB_NAME);
        //获取可写数据库
        SQLiteDatabase database = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(database);
        //获取Dao对象管理者
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }
}
