package com.shgbit.bailiff.mvp.court.casetree.bean;

import com.shgbit.bailiff.bean.BaseBean;

import java.util.List;

/**
 * Created by xushun on  2019/2/16 12:55.
 */

public class NewCourtBean extends BaseBean {


    /**
     * code : 200
     * entity : {"id":"0","pId":"0","grade":"0","online":false,"name":"最高人民法院","open":false,"children":[{"id":"1","grade":"1","online":false,"name":"北京市高级人民法院","open":false},{"id":"100","grade":"1","online":false,"name":"河北省高级人民法院","open":false},{"id":"1150","grade":"1","online":false,"name":"江苏省高级人民法院","open":false},{"id":"1300","grade":"1","online":false,"name":"浙江省高级人民法院","open":false},{"id":"1451","grade":"1","online":false,"name":"安徽省高级人民法院","open":false},{"id":"1600","grade":"1","online":false,"name":"福建省高级人民法院","open":false},{"id":"1700","grade":"1","online":false,"name":"江西省高级人民法院","open":false},{"id":"1850","grade":"1","online":false,"name":"山东省高级人民法院","open":false},{"id":"200000","grade":"1","online":false,"name":"上海市高级人民法院","open":false},{"id":"2050","grade":"1","online":false,"name":"河南省高级人民法院","open":false},{"id":"2250","grade":"1","online":false,"name":"湖北省高级人民法院","open":false},{"id":"2400","grade":"1","online":false,"name":"湖南省高级人民法院","open":false},{"id":"2550","grade":"1","online":false,"name":"广东省高级人民法院","open":false},{"id":"2750","grade":"1","online":false,"name":"广西壮族自治区高级人民法院","open":false},{"id":"2900","grade":"1","online":false,"name":"海南省高级人民法院","open":false},{"id":"2950","grade":"1","online":false,"name":"重庆市高级人民法院","open":false},{"id":"300","grade":"1","online":false,"name":"山西省高级人民法院","open":false},{"id":"3000","grade":"1","online":false,"name":"四川省高级人民法院","open":false},{"id":"3250","grade":"1","online":false,"name":"贵州省高级人民法院","open":false},{"id":"3350","grade":"1","online":false,"name":"云南省高级人民法院","open":false},{"id":"3500","grade":"1","online":false,"name":"西藏自治区高级人民法院","open":false},{"id":"3600","grade":"1","online":false,"name":"陕西省高级人民法院","open":false},{"id":"3750","grade":"1","online":false,"name":"甘肃省高级人民法院","open":false},{"id":"3900","grade":"1","online":false,"name":"青海省高级人民法院","open":false},{"id":"4000","grade":"1","online":false,"name":"宁夏高级人民法院","open":false},{"id":"4050","grade":"1","online":false,"name":"新疆维吾尔自治区高级人民法院","open":false},{"id":"4166","grade":"1","online":false,"name":"新疆高级法院生产建设兵团分院","open":false},{"id":"451","grade":"1","online":false,"name":"内蒙古自治区高级人民法院","open":false},{"id":"51","grade":"1","online":false,"name":"天津市高级人民法院","open":false},{"id":"600","grade":"1","online":false,"name":"辽宁省高级人民法院","open":false},{"id":"750","grade":"1","online":false,"name":"吉林省高级人民法院","open":false},{"id":"850","grade":"1","online":false,"name":"黑龙江省高级人民法院","open":false},{"id":"TT00","grade":"1","online":false,"name":"测试省高级人民法院","open":false}]}
     */

    private int code;
    private EntityBean entity;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
    }

    public static class EntityBean {
        /**
         * id : 0
         * pId : 0
         * grade : 0
         * online : false
         * name : 最高人民法院
         * open : false
         * children : [{"id":"1","grade":"1","online":false,"name":"北京市高级人民法院","open":false},{"id":"100","grade":"1","online":false,"name":"河北省高级人民法院","open":false},{"id":"1150","grade":"1","online":false,"name":"江苏省高级人民法院","open":false},{"id":"1300","grade":"1","online":false,"name":"浙江省高级人民法院","open":false},{"id":"1451","grade":"1","online":false,"name":"安徽省高级人民法院","open":false},{"id":"1600","grade":"1","online":false,"name":"福建省高级人民法院","open":false},{"id":"1700","grade":"1","online":false,"name":"江西省高级人民法院","open":false},{"id":"1850","grade":"1","online":false,"name":"山东省高级人民法院","open":false},{"id":"200000","grade":"1","online":false,"name":"上海市高级人民法院","open":false},{"id":"2050","grade":"1","online":false,"name":"河南省高级人民法院","open":false},{"id":"2250","grade":"1","online":false,"name":"湖北省高级人民法院","open":false},{"id":"2400","grade":"1","online":false,"name":"湖南省高级人民法院","open":false},{"id":"2550","grade":"1","online":false,"name":"广东省高级人民法院","open":false},{"id":"2750","grade":"1","online":false,"name":"广西壮族自治区高级人民法院","open":false},{"id":"2900","grade":"1","online":false,"name":"海南省高级人民法院","open":false},{"id":"2950","grade":"1","online":false,"name":"重庆市高级人民法院","open":false},{"id":"300","grade":"1","online":false,"name":"山西省高级人民法院","open":false},{"id":"3000","grade":"1","online":false,"name":"四川省高级人民法院","open":false},{"id":"3250","grade":"1","online":false,"name":"贵州省高级人民法院","open":false},{"id":"3350","grade":"1","online":false,"name":"云南省高级人民法院","open":false},{"id":"3500","grade":"1","online":false,"name":"西藏自治区高级人民法院","open":false},{"id":"3600","grade":"1","online":false,"name":"陕西省高级人民法院","open":false},{"id":"3750","grade":"1","online":false,"name":"甘肃省高级人民法院","open":false},{"id":"3900","grade":"1","online":false,"name":"青海省高级人民法院","open":false},{"id":"4000","grade":"1","online":false,"name":"宁夏高级人民法院","open":false},{"id":"4050","grade":"1","online":false,"name":"新疆维吾尔自治区高级人民法院","open":false},{"id":"4166","grade":"1","online":false,"name":"新疆高级法院生产建设兵团分院","open":false},{"id":"451","grade":"1","online":false,"name":"内蒙古自治区高级人民法院","open":false},{"id":"51","grade":"1","online":false,"name":"天津市高级人民法院","open":false},{"id":"600","grade":"1","online":false,"name":"辽宁省高级人民法院","open":false},{"id":"750","grade":"1","online":false,"name":"吉林省高级人民法院","open":false},{"id":"850","grade":"1","online":false,"name":"黑龙江省高级人民法院","open":false},{"id":"TT00","grade":"1","online":false,"name":"测试省高级人民法院","open":false}]
         */

        private String id;
        private String pId;
        private String grade;
        private boolean online;
        private String name;
        private boolean open;
        private List<ChildrenBean> children;

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

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
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

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 1
             * grade : 1
             * online : false
             * name : 北京市高级人民法院
             * open : false
             */

            private String id;
            private String grade;
            private boolean online;
            private String name;
            private boolean open;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
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
}
