package com.shgbit.bailiff.bean;

/**
 * Created by xushun on 2018/9/13.
 */

public class BaseBean {
    //    private String webSessionId;
    private String errorCode;
    private boolean iserror;
    private String message;

//    public String getWebSessionId() {
//        return webSessionId;
//    }

//    public void setWebSessionId(String webSessionId) {
//        this.webSessionId = webSessionId;
//    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isIserror() {
        return iserror;
    }

    public void setIserror(boolean iserror) {
        this.iserror = iserror;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
