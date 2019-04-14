package com.shgbit.bailiff.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 屏幕测量工具
 * <p>
 * Created by db on 2018/9/23.
 */
public class ScreenUtil {

    /**
     * 获取屏幕宽度.
     *
     * @param context context
     * @return 屏幕宽度, 单位px
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度.
     *
     * @param context context
     * @return 屏幕高度, 单位px
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
