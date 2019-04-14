package com.shgbit.bailiff.util;

import android.content.Context;

/**
 * 单位转换工具类
 * <p>
 * Created by db on 2018/9/22.
 */
public class DisplayUtil {

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float dipValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dipValue * fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}