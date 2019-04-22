package com.shgbit.bailiff.network.down;

import com.shgbit.bailiff.network.RxRestService;

/**
 * Created by xushun on 2019/4/19.
 * Des:
 * Email:shunplus@163.com
 */

public class DownloadInfo {
    /* 存储位置 */
    private String savePath;
    /* 文件总长度 */
    private long contentLength;
    /* 下载长度 */
    private long readLength;
    /* 下载该文件的url */
    private String url;

    public boolean isInsatall() {
        return isInsatall;
    }

    public void setInsatall(boolean insatall) {
        isInsatall = insatall;
    }

    private boolean isInsatall;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private int progress;
    private RxRestService service;

    public RxRestService getService() {
        return service;
    }

    public void setService(RxRestService service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "savePath='" + savePath + '\'' +
                ", contentLength=" + contentLength +
                ", readLength=" + readLength +
                ", url='" + url + '\'' +
                '}';
    }
}
