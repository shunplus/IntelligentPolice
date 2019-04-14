package com.shgbit.bailiff.base.baseImpl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.BaseBeanObserver;
import com.shgbit.bailiff.base.BasePresenter;
import com.shgbit.bailiff.base.BaseView;
import com.shgbit.bailiff.bean.BaseBean;
import com.shgbit.bailiff.common.ErrorMessage;
import com.shgbit.bailiff.common.LoadDialog;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.network.RetrofitUtils;
import com.shgbit.bailiff.util.NetStateUtil;

import java.util.WeakHashMap;

/**
 * @author:xushun on 2018/7/8
 * description :实现Fragment的懒加载，性能优化
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    private LoadDialog alertDialog;
    protected P presenter;
    /**
     * view是否创建
     */
    private boolean isViewCreate = false;
    /**
     * view是否可见
     */
    private boolean isViewVisible = false;
    public Context context;
    /**
     * 是否第一次加载
     */
    private boolean isFirst = true;

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreate = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisible = isVisibleToUser;
        if (isVisibleToUser && isViewCreate) {
            visibleToUser();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isViewVisible) {
            visibleToUser();
        }
    }

    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(int id) {
        return (T) getContentView().findViewById(id);
    }

    /**
     * 设置Fragment要显示的布局
     *
     * @return 布局的layoutId
     */
    public abstract int setContentView();

    /**
     * 获取设置的布局
     *
     * @return
     */
    public View getContentView() {
        return view;
    }

    /**
     * 懒加载
     * 让用户可见
     * 第一次加载
     */
    protected void firstLoad() {

    }

    /**
     * 懒加载
     * 让用户可见
     */
    protected void visibleToUser() {
        if (isFirst) {
            firstLoad();
            isFirst = false;
        }
    }


    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detach();
        }
        isViewCreate = false;
        super.onDestroyView();
    }

    public abstract P initPresenter();

    @Override
    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void showLoadingDialog() {
        if (alertDialog == null) {
            alertDialog = new LoadDialog(context, R.style.MyDialog, R.layout.dialog_loading);
            alertDialog.setCanceledOnTouchOutside(false);
        }
        alertDialog.show();
    }

    @Override
    public void handleError(ErrorMessage error) {
        ErrorMessage.handleError(context, error);
    }


    /**
     * 在主线程运行
     *
     * @param r
     */
    public void runOnUIThread(Runnable r) {
        LawUtils.runOnUIThread(r);
    }


    /**
     * post  form 表单 请求
     * 结果返回在main Thread
     *
     * @param url              请求接口地址
     * @param params           参数
     * @param t                Bean
     * @param baseBeanObserver 结果回调
     * @param <T>              泛型类型
     */
    public <T extends BaseBean> void post(final String url, final WeakHashMap<String, Object> params, final Class<T> t, final BaseBeanObserver<T> baseBeanObserver) {
        if (!NetStateUtil.checkEnable(LawUtils.getApplicationContext())) {
            ErrorMessage error = new ErrorMessage(ErrorMessage.NET_FAIL, LawUtils.getString(R.string.no_net));
            baseBeanObserver.onError(error);
            return;
        }
        RetrofitUtils.getInstance().post(url, params, t, baseBeanObserver);
    }

}
