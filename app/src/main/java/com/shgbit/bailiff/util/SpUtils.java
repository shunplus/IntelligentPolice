package com.shgbit.bailiff.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shgbit.bailiff.config.LawUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * SharedPreferences工具类封装
 */
public class SpUtils {
    private static final String SHARE_KEY_UPDATE_DEMO = "share_data";
    private static SharedPreferences sp;

    /**
     * 写入boolean变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值
     */
    public static void putBoolean(String key, boolean value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * 读取boolean标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(String key, boolean defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 写入String变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值
     */
    public static void putString(String key, String value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        sp.edit().putString(key, value).apply();
    }

    /**
     * 读取String标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static String getString(String key, String defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }


    /**
     * 写入int变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值
     */
    public static void putInt(String key, int value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).apply();
    }

    /**
     * 读取long标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static long getLong(String key, long defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }


    /**
     * 写入long变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值
     */
    public static void putLong(String key, long value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).apply();
    }

    /**
     * 读取int标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static int getInt(String key, int defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 从sp中移除指定节点
     *
     * @param key 需要移除节点的名称
     */
    public static void remove(String key) {
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        sp.edit().remove(key).apply();
    }

    /**
     * 保存List
     *
     * @param key
     * @param datalist
     */
    public static <T> void setDataList(String key, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        sp.edit().putString(key, strJson).apply();

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static <T> List<T> getDataList(String tag) {
        if (sp == null) {
            sp = LawUtils.getApplicationContext().getSharedPreferences(SHARE_KEY_UPDATE_DEMO, Context
                    .MODE_PRIVATE);
        }
        List<T> datalist = new ArrayList<>();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;

    }
}
