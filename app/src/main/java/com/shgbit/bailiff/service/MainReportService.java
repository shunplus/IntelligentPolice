package com.shgbit.bailiff.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.shgbit.bailiff.app.BailiffApplication;
import com.shgbit.bailiff.mvp.location.LocationService;
import com.shgbit.bailiff.util.PLog;

/**
 * Created by xushun on 2019/4/22.
 * Des:上报定位
 * Email:shunplus@163.com
 */

public class MainReportService extends Service {
    private static final String TAG = "MainReportService";
    private LocationService locationService;
    BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null) {
                PLog.i(TAG, bdLocation.getAddress().address);
                // TODO: 2019/4/24 上传定位到服务器
                Toast.makeText(MainReportService.this, "MainReportService" + bdLocation.getAddress().address, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PLog.i(TAG, TAG + " onCreate");
        //适配8.0service 适用于26的 ，高于26会在通知栏有 程序正在运行的提示
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForeground(1, new Notification());
        }
        // -----------location config ------------
        locationService = ((BailiffApplication) getApplication()).locationService;
        LocationClientOption option = locationService.getDefaultLocationClientOption();
        option.setScanSpan(1000 * 60 * 5);//设置上报的时间间隔 5分钟
        locationService.setLocationOption(option);
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        locationService.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PLog.i(TAG, TAG + " onDestroy");
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }
}
