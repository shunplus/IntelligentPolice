package com.shgbit.bailiff.network.down;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shgbit.bailiff.config.ConstantsApi;
import com.shgbit.bailiff.network.RxRestService;
import com.shgbit.bailiff.util.FileUtil;
import com.shgbit.bailiff.util.PLog;
import com.shgbit.bailiff.util.SpUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * 下载管理
 * Created by ${R.js} on 2018/3/22.
 */

public class DownloadManager implements DownloadProgressListener {
    private static final String TAG = "DownloadManager";
    private DownloadInfo info;
    private ProgressListener progressObserver;
    private RxRestService service;
    // 清除线程需要用到的
    private Disposable disposable;

    private DownloadManager() {
        info = new DownloadInfo();
    }

    public static DownloadManager getInstance() {
        return Holder.manager;
    }


    public static class Holder {
        private static DownloadManager manager = new DownloadManager();
    }

    @Override
    public void progress(long read, final long contentLength, final boolean done) {
        PLog.i(TAG, "read = " + read + "contentLength = " + contentLength);
        // 该方法仍然是在子线程，如果想要调用进度回调，需要切换到主线程，否则的话，会在子线程更新UI，直接错误
        // 如果断电续传，重新请求的文件大小是从断点处到最后的大小，不是整个文件的大小，info中的存储的总长度是
        // 整个文件的大小，所以某一时刻总文件的大小可能会大于从某个断点处请求的文件的总大小。此时read的大小为
        // 之前读取的加上现在读取的
        if (info.getContentLength() > contentLength) {
            read = read + (info.getContentLength() - contentLength);
        } else {
            info.setContentLength(contentLength);
        }
        SpUtils.putLong(info.getSavePath(), read);
        info.setReadLength(read);
        Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                if (progressObserver != null) {
                    progressObserver.progressChanged(info.getReadLength(), info.getContentLength(), done);
                }
            }
        });
    }

    /**
     * 开始下载
     *
     * @param url
     */
    public void start(Long range, String url, String filePath) {
        info.setUrl(url);
        info.setReadLength(range);
        info.setSavePath(filePath);
        final DownloadInterceptor interceptor = new DownloadInterceptor(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ConstantsApi.HOST)
                .build();
        if (service == null) {
            service = retrofit.create(RxRestService.class);
            info.setService(service);
        } else {
            service = info.getService();
        }
        downLoad();
    }

    /**
     * 开始下载
     */
    private void downLoad() {
        PLog.i(TAG, info.toString());
        /*指定线程*//* 读取下载写入文件，并把ResponseBody转成DownInfo *///写入文件
        service.executeDownload("bytes=" + info.getReadLength() + "-", info.getUrl())
                  /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                  /* 读取下载写入文件，并把ResponseBody转成DownInfo */
                .map(new Function<ResponseBody, DownloadInfo>() {
                    @Override
                    public DownloadInfo apply(ResponseBody responseBody) throws Exception {
                        try {
                            //写入文件
                            FileUtil.writeCache(responseBody, new File(info.getSavePath()), info);
                        } catch (IOException e) {
                            PLog.e(TAG, e.toString());
                        }
                        return info;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressObserver.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        progressObserver.onComplete();
                    }
                });
    }

    /**
     * 销毁
     */
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 暂停下载
     */
//    public void pause() {
//        if (subscribe != null)
//            subscribe.cancel();
//    }

    /**
     * 继续下载
     */
//    public void reStart() {
//        downLoad();
//    }

    /**
     * 进度监听
     */
    public interface ProgressListener {
        void progressChanged(long read, long contentLength, boolean done);

        void onError(String errorMessage);

        void onComplete();
    }

    public void setProgressListener(ProgressListener progressObserver) {
        this.progressObserver = progressObserver;
    }
}
