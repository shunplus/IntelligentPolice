package com.shgbit.bailiff.mvp;

import com.shgbit.bailiff.base.BasePresenter;
import com.shgbit.bailiff.base.BaseView;
import com.shgbit.bailiff.bean.UpdateMessageBean;

/**
 * Created by xushun on 2019/4/18.
 * Des:
 * Email:shunplus@163.com
 */

public interface MainContact {

    interface OnMianView extends BaseView {
        void setUpdate(UpdateMessageBean.DataBean dataBean);
    }

    interface OnMianPresent extends BasePresenter {
        void getUplate(int versionCode);
    }
}
