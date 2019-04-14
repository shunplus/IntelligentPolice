package com.shgbit.bailiff.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by xushun on 2018/9/13.
 */

public class DateTimeUtils {

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DFYYYYMMDD = "yyyyMMdd";

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDfYyyyMmDdHhMmSs() {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    /**
     * 将日期以yyyy-MM-dd格式化
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDfYyyyMmDd() {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    /**
     * 将日期以yyyyMMdd格式化
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDfyyyymmdd() {
        SimpleDateFormat sdf = new SimpleDateFormat(DFYYYYMMDD);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }
}
