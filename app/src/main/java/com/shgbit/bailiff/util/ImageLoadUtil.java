package com.shgbit.bailiff.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shgbit.bailiff.R;

/**
 * Created by db on 2018/9/22.
 */
public class ImageLoadUtil {

    /**
     * 无参数的网络图片加载方法
     *
     * @param imv 加载到哪个ImageView
     * @param url 图片地址
     */
    public static void loadNetImage(ImageView imv, String url) {
        RequestOptions options = new RequestOptions()
                .dontAnimate()//取消加载动画
                .error(R.drawable.ic_launcher_background)//加在出错后显示的图片
                .placeholder(R.drawable.ic_launcher_background);//加载中显示的图片
        Glide.with(imv.getContext())
                .load(url)
                .apply(options)
                .into(imv);
    }

    /**
     * 自带参数的网络图片加载方法
     *
     * @param imv 加载到哪个ImageView
     * @param url 图片地址
     */
    public static void loadNetImage(ImageView imv, String url, RequestOptions options) {
        Glide.with(imv.getContext())
                .load(url)
                .apply(options)
                .into(imv);
    }
}
