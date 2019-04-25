package com.shgbit.bailiff.config;

import android.os.Environment;

/**
 * Created by xushun on 2018/9/14.
 * 配置静态常量
 */

public class Constants {

    /**
     * session key
     */
    public final static String COOKIE = "cookie";
    /**
     * 登录用户，法院id，设备号，密码
     */
    public final static String USER_CODE = "userCode";
    public final static String CROP = "crop";
    public final static String DEVICE = "device";
    public final static String UPW = "upw";
    /**
     * 选择法院code
     */
    public final static int COURT_SELSET_CODE = 10;

    /**
     * 保存目录
     */
    public final static String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + LawUtils.getApplicationContext().getPackageName();
    public final static String DOWNLOAD_DIR = "/downlaod/";
    public final static String DB_NAME = "baliliff.db";

}
