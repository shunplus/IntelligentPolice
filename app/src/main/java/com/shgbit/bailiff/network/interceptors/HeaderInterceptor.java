package com.shgbit.bailiff.network.interceptors;

import android.text.TextUtils;

import com.shgbit.bailiff.config.ConfigKeys;
import com.shgbit.bailiff.config.Constants;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.util.PLog;
import com.shgbit.bailiff.util.SpUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author:xushun on 2018/7/7
 * description : 添加请求头
 */

public class HeaderInterceptor extends BaseInterceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(getRequest(chain));
        List<String> cookis = response.headers("Set-Cookie");
        if (cookis != null && cookis.size() > 0) {
            String s1 = cookis.get(0);
            if (!TextUtils.isEmpty(s1)) {
                String cookie = s1.split(";")[0];
                SpUtils.putString(Constants.COOKIE, cookie);//保存cookie
            }
        }
        return response;
    }

    private Request getRequest(Interceptor.Chain chain) {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .header("app", "true");
        if (LawUtils.getConfiguration(ConfigKeys.IS_LPGIN)) {
            PLog.d("IS_LPGIN=true");

        } else {
            String cookie = SpUtils.getString(Constants.COOKIE, "");
            if (!TextUtils.isEmpty(cookie)) {
                requestBuilder.header("Cookie", cookie);
            }
        }

        return requestBuilder.build();

    }
}
