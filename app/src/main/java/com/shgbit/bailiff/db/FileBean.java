package com.shgbit.bailiff.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by xushun on 2019/4/24.
 * Des:
 * Email:shunplus@163.com
 */

@Entity
public class FileBean {
    @Id(autoincrement = true)
    private Long id;
    private String name;

    @Generated(hash = 1361053293)
    public FileBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1910776192)
    public FileBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
