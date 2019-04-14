package com.shgbit.bailiff.network;

import com.google.gson.Gson;
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

    private static RetrofitUtils instance;
    private static Gson gson;

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
                        try {
                            if (gson == null)
                                gson = new Gson();
                            T bean = ParseJsonUtils.parseByGson(s, t);
                            /**
                             * 此处先判断是否是登录超时，如超时再次登录获取session
                             */
                            if (bean != null && bean.iserror && bean.errorCode.equals("timeout")) {
                                //TODO:
                                postLogin(params, new BaseBeanObserver<BaseBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        baseBeanObserver.onSubscribe(d);
                                    }

                                    @Override
                                    public void onNext(BaseBean baseBean) {
                                        post(url, params, t, baseBeanObserver);
                                    }

                                    @Override
                                    public void onError(ErrorMessage error) {
                                        baseBeanObserver.onError(error);
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                                return;
                            }
                            if (bean == null || bean.iserror) {
                                ErrorMessage error = new ErrorMessage(ErrorMessage.SERVE_FAIL, LawUtils.getString(R.string.net_error));
                                baseBeanObserver.onError(error);
                            } else {
                                baseBeanObserver.onNext(bean);
                            }

                        } catch (Exception e) {
                            try {
                                BaseBean baseBean = ParseJsonUtils.parseByGson(s, BaseBean.class);
                                ErrorMessage error = new ErrorMessage(ErrorMessage.SERVE_FAIL, baseBean.message);
                                baseBeanObserver.onError(error);
                            } catch (Exception e1) {
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorMessage error = new ErrorMessage(ErrorMessage.NET_FAIL, LawUtils.getString(R.string.net_error));
                        baseBeanObserver.onError(error);
                    }

                    @Override
                    public void onComplete() {
                        baseBeanObserver.onComplete();
                    }
                });
    }

    public void postLogin(WeakHashMap<String, Object> params, final BaseBeanObserver<BaseBean> baseBeanObserver) {
        if (!NetStateUtil.checkEnable(LawUtils.getApplicationContext())) {
            ErrorMessage error = new ErrorMessage(ErrorMessage.NET_FAIL, LawUtils.getString(R.string.no_net));
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
                        boolean iserror = false;
                        String message = null;
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            iserror = jsonObject.getBoolean("iserror");
                            message = jsonObject.getString("message");
                            if (iserror) {
                                ErrorMessage error = new ErrorMessage(ErrorMessage.SERVE_FAIL, message);
                                baseBeanObserver.onError(error);
                            } else {
                                if (gson == null)
                                    gson = new Gson();
                                try {
                                    BaseBean bean = ParseJsonUtils.parseByGson(s, BaseBean.class);
                                    if (bean == null || bean.iserror) {
                                        ErrorMessage error = new ErrorMessage(ErrorMessage.SERVE_FAIL, bean.message);
                                        baseBeanObserver.onError(error);
                                    } else {
                                        baseBeanObserver.onNext(bean);
                                    }
                                } catch (Exception e) {
                                    ErrorMessage error = new ErrorMessage(ErrorMessage.NET_FAIL, LawUtils.getString(R.string.net_error));
                                    baseBeanObserver.onError(error);
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
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
                            ErrorMessage error = new ErrorMessage(ErrorMessage.NET_FAIL, LawUtils.getString(R.string.net_error));
                            baseBeanObserver.onError(error);
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
