package com.shgbit.bailiff.common;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by xushun on 2018/7/10.
 */

public class ErrorMessage {
    public int errorCode;
    public String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

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
        if (error.errorCode == 501 || error.errorCode == 502) {
            Toast.makeText(context.getApplicationContext(), error.errorMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context.getApplicationContext(), error.errorCode + "," + error.errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
