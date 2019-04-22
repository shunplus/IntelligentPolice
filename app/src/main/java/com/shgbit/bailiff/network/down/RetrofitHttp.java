package com.shgbit.bailiff.network.down;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shgbit.bailiff.config.Constants;
import com.shgbit.bailiff.config.ConstantsApi;
import com.shgbit.bailiff.network.RxRestService;
import com.shgbit.bailiff.util.FileUtil;
import com.shgbit.bailiff.util.SpUtils;

import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by xushun on 2019/4/19.
 * Des:
 * Email:shunplus@163.com
 */

public class RetrofitHttp {

    private static final int DEFAULT_TIMEOUT = 10;
    private static final String TAG = "RetrofitClient";
    private RxRestService apiService;
    private OkHttpClient okHttpClient;
    private Subscription subscription;
    private DownloadInfo info;
    // 清除线程需要用到的
    private Disposable disposable;

    private static RetrofitHttp sIsntance;

    public static RetrofitHttp getInstance() {
        if (sIsntance == null) {
            synchronized (RetrofitHttp.class) {
                if (sIsntance == null) {
                    sIsntance = new RetrofitHttp();
                }
            }
        }
        return sIsntance;
    }

    private RetrofitHttp() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ConstantsApi.HOST)
                .build();
        apiService = retrofit.create(RxRestService.class);
    }

    public void downloadFile(final long range, final String url, final String fileName, final DownloadCallBack downloadCallback) {
        //断点续传时请求的总长度
        File file = new File(Constants.APP_ROOT_PATH + Constants.DOWNLOAD_DIR, fileName);
        String totalLength = "-";
        if (file.exists()) {
            totalLength += file.length();
        }
        dispose();
        apiService.executeDownload("bytes=" + Long.toString(range) + totalLength, url)
                .map(new Function<ResponseBody, DownloadInfo>() {
                    @Override
                    public DownloadInfo apply(ResponseBody responseBody) throws Exception {
                        try {
                            //写入文件
                            FileUtil.writeCache(responseBody, new File(info.getSavePath()), info);
                        } catch (IOException e) {
                            Log.e("异常:", e.toString());
                        }
                        return info;
                    }
                });


        apiService.executeDownload("bytes=" + Long.toString(range) + totalLength, url)
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        RandomAccessFile randomAccessFile = null;
                        InputStream inputStream = null;
                        long total = range;
                        long responseLength = 0;
                        try {
                            byte[] buf = new byte[2048];
                            int len = 0;
                            responseLength = responseBody.contentLength();
                            inputStream = responseBody.byteStream();
                            String filePath = Constants.APP_ROOT_PATH + Constants.DOWNLOAD_DIR;
                            File file = new File(filePath, fileName);
                            File dir = new File(filePath);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            randomAccessFile = new RandomAccessFile(file, "rwd");
                            if (range == 0) {
                                randomAccessFile.setLength(responseLength);
                            }
                            randomAccessFile.seek(range);
                            int progress = 0;
                            int lastProgress = 0;
                            while ((len = inputStream.read(buf)) != -1) {
                                randomAccessFile.write(buf, 0, len);
                                total += len;
                                SpUtils.putLong(url, total);
                                lastProgress = progress;
                                progress = (int) (total * 100 / randomAccessFile.length());
                                if (progress > 0 && progress != lastProgress) {
                                    downloadCallback.onProgress(progress);
                                }
                            }
                            downloadCallback.onCompleted();
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                            downloadCallback.onError(e.getMessage());
                            e.printStackTrace();
                        } finally {
                            try {
                                if (randomAccessFile != null) {
                                    randomAccessFile.close();
                                }

                                if (inputStream != null) {
                                    inputStream.close();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        downloadCallback.onError(e.toString());
                    }

                    @Override
                    public void onComplete() {
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
}