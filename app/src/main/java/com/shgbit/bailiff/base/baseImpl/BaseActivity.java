package com.shgbit.bailiff.base.baseImpl;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.BaseBeanObserver;
import com.shgbit.bailiff.base.BasePresenter;
import com.shgbit.bailiff.base.BaseView;
import com.shgbit.bailiff.bean.BaseBean;
import com.shgbit.bailiff.common.ActivityManager;
import com.shgbit.bailiff.common.ErrorMessage;
import com.shgbit.bailiff.common.LoadDialog;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.network.RetrofitUtils;
import com.shgbit.bailiff.util.NetStateUtil;

import java.util.WeakHashMap;

/**
 * @author:xushun on 2018/7/8
 * description :
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private LoadDialog alertDialog;
    protected P mvpPresenter;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        LawUtils.initSystemBar(false, this);
        ActivityManager.getAppInstance().addActivity(this);//将当前activity添加进入管理栈
        mvpPresenter = initPresenter();
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getAppInstance().removeActivity(this);//将当前activity移除管理栈
        if (mvpPresenter != null) {
            mvpPresenter.detach();//在presenter中解绑释放view
            mvpPresenter = null;
        }
        alertDialog = null;
        super.onDestroy();
    }

    /**
     * 在子类中初始化对应的presenter
     *
     * @return 相应的presenter
     */
    public abstract P initPresenter();

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
            alertDialog = new LoadDialog(this, R.style.MyDialog, R.layout.dialog_loading);
            alertDialog.setCanceledOnTouchOutside(false);
        }
        alertDialog.show();
    }

    @Override
    public void handleError(ErrorMessage error) {
        ErrorMessage.handleError(this, error);
    }

    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(int id) {
        return (T) findViewById(id);
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
            ErrorMessage error = new ErrorMessage(RetrofitUtils.NET_FAIL, LawUtils.getString(R.string.no_net));
            baseBeanObserver.onError(error);
            return;
        }
        RetrofitUtils.getInstance().post(url, params, t, baseBeanObserver);
    }
}
