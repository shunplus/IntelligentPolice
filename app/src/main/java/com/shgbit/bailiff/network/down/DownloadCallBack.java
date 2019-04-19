package com.shgbit.bailiff.network.down;

/**
 * @author:xushun on 2018/6/30
 * description :下载回调接口
 */

public interface DownloadCallBack {

    void onProgress(int progress);

    void onCompleted();

    void onError(String msg);

}
