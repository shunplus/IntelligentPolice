package com.shgbit.bailiff.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;
import com.shgbit.bailiff.R;
import com.shgbit.bailiff.config.Constants;
import com.shgbit.bailiff.network.down.DownloadCallBack;
import com.shgbit.bailiff.network.down.DownloadInfo;
import com.shgbit.bailiff.network.down.RetrofitHttp;

import com.shgbit.bailiff.util.PLog;
import com.shgbit.bailiff.util.SpUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


/**
 * 创建时间：2018/3/7
 * 编写人：xushun
 * 功能描述 ：
 */

public class DownloadIntentService extends IntentService /*implements DownloadManager.ProgressListener*/ {

    private static final String TAG = "DownloadIntentService";
    private Notification notification;
    private String mDownloadFileName;
    private RemoteViews view;
    private int notificationId;
    private static NotificationManager notificationManager;
    private Notification.Builder builder;
    private int progress;
    private File file;

    public DownloadIntentService() {
        super("InitializeService");
//        downloadManager = DownloadManager.getInstance();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String downloadUrl = intent.getExtras().getString("download_url");
        notificationId = intent.getExtras().getInt("download_id");
        mDownloadFileName = intent.getExtras().getString("download_file");
        boolean isSendProgress = intent.getExtras().getBoolean("down_show", false);
        PLog.d(TAG, "download_url --" + downloadUrl);
        PLog.d(TAG, "download_file --" + mDownloadFileName);
        file = new File(Constants.APP_ROOT_PATH + Constants.DOWNLOAD_DIR + mDownloadFileName);
        long range = 0;
        progress = 0;
        if (file.exists()) {
            range = SpUtils.getLong(downloadUrl, 0);
            progress = (int) (range * 100 / file.length());
            if (range == file.length()) {
                DownloadInfo info = new DownloadInfo();
                info.setSavePath(file.getAbsolutePath());
                info.setInsatall(true);
                //发送安装消息到 MainActivity进行安装
                EventBus.getDefault().post(info);
                return;
            }
        }
        PLog.d(TAG, "range = " + range);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showChannelNotification();
        } else {
            showChannelNotificationBelowO();
        }
        RetrofitHttp.getInstance().downloadFile(range, downloadUrl, mDownloadFileName, new DownloadCallBack() {
            @Override
            public void onProgress(int progress) {
                DownloadInfo downloadInfo = new DownloadInfo();
                downloadInfo.setProgress(progress);
                EventBus.getDefault().post(downloadInfo);
                PLog.d(TAG, "已下载 " + progress + " %");
                builder.setProgress(100, progress, false);
                notification = builder.build();
                notificationManager.notify(notificationId, notification);
            }

            @Override
            public void onCompleted() {
                PLog.d(TAG, "onCompleted");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //关闭通知通道
                    notificationManager.deleteNotificationChannel("1");
                }
                notificationManager.cancel(notificationId);
                DownloadInfo downloadInfo = new DownloadInfo();
                downloadInfo.setSavePath(file.getAbsolutePath());
                downloadInfo.setInsatall(true);
                EventBus.getDefault().post(downloadInfo);
            }

            @Override
            public void onError(String msg) {
                notificationManager.cancel(notificationId);
                PLog.d(TAG, "onError--" + msg);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        PLog.i(TAG, "onDestroy");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showChannelNotification() {
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup("a", "a"));
        @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("1",
                "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.setLightColor(Color.GREEN);
        notificationManager.createNotificationChannel(channel);
        builder = new Notification.Builder(this, "1");
        builder.setContentTitle("正在下载 " + mDownloadFileName)
                .setSmallIcon(R.drawable.logo_main)
                .setProgress(100, progress, false)
                .setOnlyAlertOnce(true);
        notification = builder.build();
        notificationManager.notify(notificationId, notification);
    }

    /**
     * 低于26版本
     */
    private void showChannelNotificationBelowO() {
        builder = new Notification.Builder(this);
        builder.setContentTitle("正在下载 " + mDownloadFileName)
                .setSmallIcon(R.drawable.logo_main)
                .setProgress(100, progress, false);
        notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(notificationId, notification);
    }
}
