package com.shgbit.bailiff.util;

import com.google.gson.Gson;

/**
 * 解析转化json
 */
public class ParseJsonUtils {
    /**
     * Gson
     */
    public static <T> T parseByGson(String json, Class<T> t) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(json, t);
    }
}
