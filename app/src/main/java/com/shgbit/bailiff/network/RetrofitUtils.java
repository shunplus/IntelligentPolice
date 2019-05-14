package com.shgbit.bailiff.network;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.BaseBeanObserver;
import com.shgbit.bailiff.bean.BaseBean;
import com.shgbit.bailiff.common.ErrorMessage;
import com.shgbit.bailiff.config.ConstantsApi;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.util.NetStateUtil;
import com.shgbit.bailiff.util.ParseJsonUtils;

import org.json.JSONObject;

import java.util.WeakHashMap;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xushun on 2018/9/13.
 */

public class RetrofitUtils {
    public final static int NET_FAIL = 501;//网络无连接
    public final static int NET_OR_SFAIL = 502;//网络或服务器异常
    public final static int SERVE_FAIL = 500;//处理失败
    public final static int NOT_VERIFY = 401;//请求未认证，跳转登录页
    public final static int NOT_COMMITTED = 406;// 请求未授权，跳转未授权提示页
    public final static int SERVE_OK = 200;//响应成功
    public final static int DATA_ERROR = 202;//数据异常
    private static RetrofitUtils instance;

    public static RetrofitUtils getInstance() {
        return RetrofitUtilsHolder.instance;
    }

    private RetrofitUtils() {
        if (instance != null) {
            throw new RuntimeException();
        }
    }

    private static class RetrofitUtilsHolder {
        private final static RetrofitUtils instance = new RetrofitUtils();
    }

    public <T extends BaseBean> void post(final String url, final WeakHashMap<String, Object> params, final Class<T> t, final BaseBeanObserver<T> baseBeanObserver) {
        LawUtils.getConfigurator().isLgion(false);
        RxRestClient.builder().parms(params).url(url).build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        baseBeanObserver.onSubscribe(d);
                    }

                    @Override
                    public void onNext(String s) {
                        String post = ConstantsApi.HOST + url;//同于打断点时确定接口
                        WeakHashMap<String, Object> parms = params;
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int code = SERVE_OK /*jsonObject.getInt("code")*/;
                            boolean iserror = jsonObject.getBoolean("iserror");
                            String message = jsonObject.getString("message");
                            ErrorMessage error = new ErrorMessage();
                            switch (code) {
                                case SERVE_OK:
                                    if (iserror) {
                                        error.errorCode = code;
                                        error.errorMessage = TextUtils.isEmpty(message) ? LawUtils.getString(R.string.data_error) : message;
                                    } else {
                                        T bean = ParseJsonUtils.getInstance().parseByGson(s, t);
                                        if (bean != null) {
                                            baseBeanObserver.onNext(bean);
                                        } else {
                                            error.errorMessage = TextUtils.isEmpty(message) ? LawUtils.getString(R.string.data_error) : message;
                                            error.errorCode = code;
                                            baseBeanObserver.onError(error);
                                        }
                                    }
                                    break;
                                case NOT_VERIFY:
                                    error.errorCode = code;
//                                    error.errorMessage = LawUtils.getString(R.string.not_verify);
                                    error.errorMessage = jsonObject.getString("entity");
                                    baseBeanObserver.onError(error);
                                    break;
                                case NOT_COMMITTED:
                                    error.errorCode = code;
//                                    error.errorMessage = LawUtils.getString(R.string.not_committed);
                                    error.errorMessage = jsonObject.getString("entity");
                                    baseBeanObserver.onError(error);
                                    break;
                                case SERVE_FAIL:
                                    error.errorCode = code;
                                    error.errorMessage = LawUtils.getString(R.string.net_error);
                                    baseBeanObserver.onError(error);
                                    break;
                            }

                        } catch (Exception e) {
                            try {
                                BaseBean baseBean = ParseJsonUtils.getInstance().parseByGson(s, BaseBean.class);
                                ErrorMessage error = new ErrorMessage(DATA_ERROR, LawUtils.getString(R.string.data_error));
                                baseBeanObserver.onError(error);
                            } catch (Exception e1) {
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String postUrl = ConstantsApi.HOST + url;
                        WeakHashMap<String, Object> parms = params;
                        if (e instanceof HttpException) {
                            //获取对应statusCode和Message
                            HttpException exception = (HttpException) e;
                            String message = exception.response().message();
                            int code = exception.response().code();
                            ErrorMessage error = new ErrorMessage(code, message);
                            baseBeanObserver.onError(error);

                        } else if (e instanceof SSLHandshakeException) {
                            //接下来就是各种异常类型判断...
                        } else {
                            ErrorMessage error = new ErrorMessage(NET_OR_SFAIL, LawUtils.getString(R.string.net_error));
                            baseBeanObserver.onError(error);
                        }
                    }

                    @Override
                    public void onComplete() {
//                        baseBeanObserver.onComplete();
                    }
                });
    }

    public <T extends BaseBean> void postLogin(final WeakHashMap<String, Object> params, final Class<T> t, final BaseBeanObserver<T> baseBeanObserver) {
        if (!NetStateUtil.checkEnable(LawUtils.getApplicationContext())) {
            ErrorMessage error = new ErrorMessage(NET_FAIL, LawUtils.getString(R.string.no_net));
            baseBeanObserver.onError(error);
            return;
        }
        LawUtils.getConfigurator().isLgion(true);
        RxRestClient.builder().parms(params).url(ConstantsApi.LOGIN_USER).build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        baseBeanObserver.onSubscribe(d);
                    }

                    @Override
                    public void onNext(String s) {
                        String postUrl = ConstantsApi.HOST + ConstantsApi.LOGIN_USER;
                        WeakHashMap<String, Object> parms = params;
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int code = jsonObject.getInt("code");
                            boolean iserror = jsonObject.getBoolean("iserror");
                            String message = jsonObject.getString("message");
                            ErrorMessage error = new ErrorMessage();
                            switch (code) {
                                case SERVE_OK:
                                    if (iserror) {
                                        error.errorCode = code;
                                        error.errorMessage = jsonObject.getString("entity");
                                    } else {
                                        T bean = ParseJsonUtils.getInstance().parseByGson(s, t);
                                        if (bean != null) {
                                            baseBeanObserver.onNext(bean);
                                        } else {
                                            error.errorMessage = TextUtils.isEmpty(message) ? LawUtils.getString(R.string.data_error) : message;
                                            error.errorCode = code;
                                            baseBeanObserver.onError(error);
                                        }
                                    }
                                    break;
                                case NOT_VERIFY:
                                    error.errorCode = code;
                                    error.errorMessage = jsonObject.getString("entity");
                                    baseBeanObserver.onError(error);
                                    break;
                                case NOT_COMMITTED:
                                    error.errorCode = code;
//                                    error.errorMessage = LawUtils.getString(R.string.not_committed);
                                    error.errorMessage = jsonObject.getString("entity");
                                    baseBeanObserver.onError(error);
                                    break;
                                case SERVE_FAIL:
                                    error.errorCode = code;
                                    error.errorMessage = LawUtils.getString(R.string.net_error);
                                    baseBeanObserver.onError(error);
                                    break;
                            }

                        } catch (Exception e) {
                            try {
                                BaseBean baseBean = ParseJsonUtils.getInstance().parseByGson(s, BaseBean.class);
                                ErrorMessage error = new ErrorMessage(DATA_ERROR, LawUtils.getString(R.string.data_error));
                                baseBeanObserver.onError(error);
                            } catch (Exception e1) {
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String postUrl = ConstantsApi.HOST + ConstantsApi.LOGIN_USER;
                        WeakHashMap<String, Object> parms = params;
                        if (e instanceof HttpException) {
                            //获取对应statusCode和Message
                            HttpException exception = (HttpException) e;
                            String message = exception.response().message();
                            int code = exception.response().code();
                            ErrorMessage error = new ErrorMessage(code, message);
                            baseBeanObserver.onError(error);

                        } else if (e instanceof SSLHandshakeException) {
                            //接下来就是各种异常类型判断...
                        } else {
                            ErrorMessage error = new ErrorMessage(NET_OR_SFAIL, LawUtils.getString(R.string.net_error));
                            baseBeanObserver.onError(error);
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
