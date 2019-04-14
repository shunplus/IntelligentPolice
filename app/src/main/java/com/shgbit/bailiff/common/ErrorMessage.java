package com.shgbit.bailiff.common;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by xushun on 2018/7/10.
 */

public class ErrorMessage {
    public final static int SEESION_OUT = 501;//session过期
    public final static int NET_FAIL = 502;//网络或服务器异常
    public final static int SERVE_FAIL = 50;//服务器异常
    public int errorCode;
    public String errorMessage;

    public ErrorMessage() {
        this.errorCode = 0;
        this.errorMessage = "";
    }

    public ErrorMessage(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorMessage(int errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = "";
    }

    public static void handleError(Context context, ErrorMessage error) {
        switch (error.errorCode) {
            case ErrorMessage.NET_FAIL:
                Toast.makeText(context.getApplicationContext(), error.errorMessage, Toast.LENGTH_SHORT).show();
                break;
            case ErrorMessage.SERVE_FAIL:
                Toast.makeText(context.getApplicationContext(), error.errorMessage, Toast.LENGTH_SHORT).show();
                break;
            case ErrorMessage.SEESION_OUT:
                break;
        }
    }
}
