package com.shgbit.bailiff.rxbus.event;

/**
 * @author DeMon
 * @date 2018/9/7
 * @description
 */
public class MsgEvent {
    private String msg;

    public MsgEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
