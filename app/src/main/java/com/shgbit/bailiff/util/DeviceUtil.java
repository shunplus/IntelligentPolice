package com.shgbit.bailiff.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.util.List;


/**
 * 设备信息工具类
 * <p>
 * Created by db on 2018/9/22.
 */
public class DeviceUtil {

    private static DeviceUtil mInstance;

    private DeviceUtil() {
    }

    //双重检查单例模式
    public static DeviceUtil getInstance() {
        if (mInstance == null) {
            synchronized (DeviceUtil.class) {
                if (mInstance == null) {
                    mInstance = new DeviceUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取设备的id
     *
     * @return 返回Imei设为唯一标识值, 如果没有权限则返回null
     */
    @SuppressLint("HardwareIds")
    private String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //如果没有权限则返回null
            return null;
        }
        String deviceId = telephonyManager.getDeviceId();
        if (deviceId == null) {
            //android.provider.Settings;
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }


    /**
     * 获取该应用的versionCode.
     *
     * @param context context
     * @return 本软件的版本号, 异常返回-1
     */
    public int getSelfVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * 获取本软件的versionName.
     *
     * @param context context
     * @return 本软件的版本名字, 异常返回空串
     */
    public String getSelfVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取系统Android版本.
     *
     * @return Android版本号
     */
    public String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取API等级
     *
     * @return API等级
     */
    public int getApiVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备型号.
     *
     * @return 设备型号
     */
    public String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 授权一个应用使用uri.
     * 一个intent发送出去可能有多个应用可以响应,暂时并不知道用户会选择哪一个应用去使用这个uri,所以先查询出
     * 所有可以响应这个intent的应用,然后都授予权限,在不需要的时候可以使用
     * revokeUriPermission(Uri uri, int modeFlags);撤回权限.
     *
     * @param context 上下文
     * @param intent  uri所在的意图
     * @param uri     需要授权的uri
     */
    public void grantUriPermission(Context context, Intent intent, Uri uri) {
        List<ResolveInfo> resolveInfoList = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }
}
