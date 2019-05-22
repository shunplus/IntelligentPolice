package com.shgbit.bailiff.config;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.common.ThreadPoolManager;
import com.shgbit.bailiff.util.PLog;
import com.tencent.mmkv.MMKV;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author:xushun on 2018/7/5
 * description : 通用工具类
 */

public final class LawUtils {
    public static Configurator init(Context context) {
        long time = System.currentTimeMillis();
        try {
            InputStream is = context.getAssets().open("config.properties");
            Properties prop = new Properties();
            prop.load(is);
            ConstantsApi.HOST = prop.getProperty("host");
//            Constants.initHost();
            PLog.e("共耗时：" + (System.currentTimeMillis() - time));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configurator.getInstance()
                .getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    /**
     * 获取全局ApplicationContext
     *
     * @return
     */
    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    /**
     * 获取全局handler
     *
     * @return
     */
    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    /**
     * 获取主线程id
     *
     * @return
     */
    public static int getMainThreadId() {
        return getConfiguration(ConfigKeys.MAIN_THREADID);
    }

    /**
     * 运行在主线程
     *
     * @param r 运行的Runnable对象
     */
    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程, 直接运行
            r.run();
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            getHandler().post(r);
        }
    }

    /**
     * 判断是否运行在主线程
     *
     * @return true：当前线程运行在主线程
     * fasle：当前线程没有运行在主线程
     */
    public static boolean isRunOnUIThread() {
        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }

    /**
     * 获取strings.xml资源文件字符串
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串
     */
    public static String getString(int id) {
        return getApplicationContext().getResources().getString(id);
    }

    /**
     * 获取strings.xml资源文件字符串数组
     *
     * @param id 资源文件id
     * @return 资源文件对应字符串数组
     */
    public static String[] getStringArray(int id) {
        return getApplicationContext().getResources().getStringArray(id);
    }

    /**
     * 获取drawable资源文件图片
     *
     * @param id 资源文件id
     * @return 资源文件对应图片
     */
    public static Drawable getDrawable(@DrawableRes int id) {
        return getApplicationContext().getResources().getDrawable(id);
    }

    /**
     * 获取drawable资源文件图片bitmap
     *
     * @param id 资源文件id
     * @return 资源文件对应图片bitmap
     */
    public static Bitmap getBitmap(@DrawableRes int id) {
        return BitmapFactory.decodeResource(getApplicationContext().getResources(), id);
    }

    /**
     * 获取colors.xml资源文件颜色
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色值
     */
    public static int getColor(@ColorRes int id) {
        return getApplicationContext().getResources().getColor(id);
    }

    /**
     * 获取颜色的状态选择器
     *
     * @param id 资源文件id
     * @return 资源文件对应颜色状态
     */
    public static ColorStateList getColorStateList(int id) {
        return getApplicationContext().getResources().getColorStateList(id);
    }

    /**
     * 获取dimens资源文件中具体像素值
     *
     * @param id 资源文件id
     * @return 资源文件对应像素值
     */
    public static int getDimen(int id) {
        return getApplicationContext().getResources().getDimensionPixelSize(id);// 返回具体像素值
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param isLight
     */
    public static void initSystemBar(Boolean isLight, Activity mActivity) {
        if (Build.VERSION.SDK_INT >= 21) {
            //LAYOUT_FULLSCREEN 、LAYOUT_STABLE：让应用的主体内容占用系统状态栏的空间；
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
            Window window = mActivity.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            if (isLight) {
                window.setStatusBarColor(getColor(R.color.white));
            } else {
                window.setStatusBarColor(getColor(R.color.top_color));
            }
            //状态栏颜色接近于白色，文字图标变成黑色
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (isLight) {
                //light --> a|=b的意思就是把a和b按位或然后赋值给a,   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                //dark  --> &是位运算里面，与运算,  a&=b相当于 a = a&b,  ~非运算符
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }
    }

    /**
     * 加载布局文件
     *
     * @param id 布局文件id
     * @return 布局view
     */
    public static View c(int id) {
        return View.inflate(getApplicationContext(), id, null);
    }

    /**
     * 返回线程
     *
     * @return
     */
    public static ThreadPoolManager getThreadPoolManager() {
        return getConfiguration(ConfigKeys.THREAD);
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context context, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 获取当前的versioncode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();//context为当前Activity上下文
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi.versionCode;
    }

    /**
     * 获取全局MMKV实例
     * @return
     */
    public static MMKV getMMKV(){
        return getConfiguration(ConfigKeys.MKKV);
    }
}
