package com.shgbit.bailiff.app;

import android.app.Application;

import com.shgbit.bailiff.config.ConstantsApi;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.network.interceptors.HeaderInterceptor;


/**
 * @author:xushun on 2018/7/5
 * description :
 */

public class BailiffApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LawUtils.init(this)
                .withApiHost(ConstantsApi.HOST)
                .withInterceptor(new HeaderInterceptor())
                .configure();
        //初始化消息事件消息总线
//        LiveEventBus.get()
//                .config()
//                .supportBroadcast(this)//支持广播
//                .lifecycleObserverAlwaysActive(true);//配置LifecycleObserver（如Activity）
        // 接收消息的模式：true：整个生命周期（从onCreate到onDestroy）都可以实时收到消息
        // false：激活状态（Started）可以实时收到消息，非激活状态（Stoped）无法实时收到消息，需等到Activity重新变成激活状态，方可收到消息
    }
}
