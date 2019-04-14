package com.shgbit.bailiff.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络状态工具类
 * <p>
 * Created by db on 2018/9/22.
 */
public class NetStateUtil {

    public static final int NETWORK_FAIL = 0;       //获取失败
    public static final int NETWORK_WIFI = 1;       //wifi
    public static final int NETWORK_2G = 2;         //2G
    public static final int NETWORK_3G = 3;         //3G
    public static final int NEtWORK_4G = 4;         //4G
    public static final int NETWORK_MOBILE = 5;     //网络类型未知的移动网络
    public static final int NETWORK_UNKNOWN = 6;    //网络类型完全未知,非wifi,非移动网络
    public static final int NETWORK_UNCONNECTED = -1; //网络未连接

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean checkEnable(Context context) {
        int state = getNetworkState(context);
        if (state != NETWORK_FAIL && state != NETWORK_UNCONNECTED) {
            return true;
        }
        return false;
    }

    /**
     * 获取网络状态
     *
     * @param context context
     * @return 网络状态:未连接网络,wifi网络,2G网络,3G网络,4G网络,未知类型的移动网络,未知类型的网络,获取失败
     */
    public static int getNetworkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return NETWORK_FAIL;    //connectivityManager为null,获取失败了
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return NETWORK_UNCONNECTED; //networkInfo为null,或则没有连接,网络未连接
        }
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return NETWORK_WIFI;    //网络类型wifi
        }
        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) { //以下是移动网络
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return NETWORK_MOBILE;  //telephoneManager为null,不能确定移动网络的类型
            }
            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NETWORK_2G;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NETWORK_3G;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NEtWORK_4G;
                default:
                    return NETWORK_MOBILE;  //非2G,非3G,非4G,不知道类型的移动网络
            }
        } else {
            //以下是未知网络,非wifi,非移动网络
            return NETWORK_UNKNOWN;
        }
    }


}
