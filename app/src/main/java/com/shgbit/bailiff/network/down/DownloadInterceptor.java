package com.shgbit.bailiff.network.down;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.shgbit.bailiff.config.ConfigKeys;
import com.shgbit.bailiff.config.Constants;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.util.PLog;
import com.shgbit.bailiff.util.SpUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 处理数据
 * Created by zhongjh on 2018/5/18.
 */
public class DownloadInterceptor implements Interceptor {

    private DownloadListener downloadListener;

    public DownloadInterceptor(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(getRequest(chain));
        return response.newBuilder().body(
                new DownloadResponseBody(response.body(), downloadListener))
                .build();
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
