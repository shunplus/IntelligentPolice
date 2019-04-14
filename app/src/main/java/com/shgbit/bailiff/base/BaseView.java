package com.shgbit.bailiff.base;


import com.shgbit.bailiff.common.ErrorMessage;

/**
 * @author:xushun on 2018/7/8
 * description :
 */
public interface BaseView {
    /**
     * 展示提示信息
     *
     * @param message 要提示的信息
     */
    void showMessage(String message);

    /**
     * 网络请求数据阻塞
     * 进度框dialog
     */
    void showLoadingDialog();

    /**
     * 销毁dialog
     */
    void dismissLoadingDialog();

    /**
     * 处理异常error
     */
    void handleError(ErrorMessage error);
}
