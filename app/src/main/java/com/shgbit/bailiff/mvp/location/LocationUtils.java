package com.shgbit.bailiff.mvp.location;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.shgbit.bailiff.config.LawUtils;

/**
 * Created by xushun on 2019/4/23.
 * Des:单次获取位置坐标
 * Email:shunplus@163.com
 */

public class LocationUtils {
    private static LocationUtils instace;
    private BDAbstractLocationListener mListener;
    private LocationClient client = null;
    private Object objLock = new Object();

    private LocationUtils() {
        initOption();
        if (instace != null) {
            throw new RuntimeException();
        }
    }

    private void initOption() {
        LocationClientOption option = null;
        if (option == null) {
            option = new LocationClientOption();
        }
//        option.setScanSpan(0);//默认只定位一次
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);//设置是否返回地址信息（默认返回地址信息）
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        synchronized (objLock) {
            if (client == null) {
                client = new LocationClient(LawUtils.getApplicationContext());
                client.setLocOption(option);
            }
        }
    }


    public boolean registerListener(BDAbstractLocationListener mListener) {
        this.mListener = mListener;
        boolean isSuccess = false;
        if (mListener != null) {
            client.registerLocationListener(mListener);
            isSuccess = true;
            start();
        }
        return isSuccess;
    }

    public void unregisterListener() {
        if (mListener != null) {
            client.unRegisterLocationListener(mListener);
            stop();
        }
    }

    public static LocationUtils getInstace() {
        return LocationUtisHolder.instace;
    }

    private static class LocationUtisHolder {
        private static final LocationUtils instace = new LocationUtils();
    }


    private void start() {
        synchronized (objLock) {
            if (client != null && !client.isStarted()) {
                client.start();
            }
        }
    }

    private void stop() {
        synchronized (objLock) {
            if (client != null && client.isStarted()) {
                client.stop();
            }
        }
    }


}
