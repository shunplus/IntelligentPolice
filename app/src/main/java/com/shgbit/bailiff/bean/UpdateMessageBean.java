package com.shgbit.bailiff.bean;

/**
 * Created by xushun on 2019/4/18.
 * Des:
 * Email:shunplus@163.com
 */

public class UpdateMessageBean extends BaseBean {


    /**
     * data : {"id":"21770515151554463","isNewRecord":false,"code":"android.exjudge.version","name":"验证-执行法官app版本信息","value":"114","memo":"http://119.23.50.215:9056/app/ydzx_ceshi.apk##enforcementUpdate=no##\r\n本次更新内容：\r\n     1、任务流转、委托修改BUG"}
     * exception :
     */

    private DataBean data;
    private String exception;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public static class DataBean {
        /**
         * id : 21770515151554463
         * isNewRecord : false
         * code : android.exjudge.version
         * name : 验证-执行法官app版本信息
         * value : 114
         * memo : http://119.23.50.215:9056/app/ydzx_ceshi.apk##enforcementUpdate=no##
         * 本次更新内容：
         * 1、任务流转、委托修改BUG
         */

        private String id;
        private boolean isNewRecord;
        private String code;
        private String name;
        private String value;
        private String memo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
}
