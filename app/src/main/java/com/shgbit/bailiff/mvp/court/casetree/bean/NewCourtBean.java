package com.shgbit.bailiff.mvp.court.casetree.bean;

import com.shgbit.bailiff.bean.BaseBean;

import java.util.List;

/**
 * Created by xushun on  2019/2/16 12:55.
 */

public class NewCourtBean extends BaseBean {


    /**
     * data : [{"id":"0","pId":"-9999","online":false,"name":"最高人民法院","open":false}]
     * exception :
     */

    private String exception;
    private List<DataBean> data;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 0
         * pId : -9999
         * online : false
         * name : 最高人民法院
         * open : false
         */

        private String id;
        private String pId;
        private boolean online;
        private String name;
        private boolean open;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public boolean isOnline() {
            return online;
        }

        public void setOnline(boolean online) {
            this.online = online;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }
    }
}
