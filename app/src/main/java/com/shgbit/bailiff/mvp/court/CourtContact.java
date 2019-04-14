package com.shgbit.bailiff.mvp.court;

import com.shgbit.bailiff.base.BasePresenter;
import com.shgbit.bailiff.base.BaseView;
import com.shgbit.bailiff.mvp.court.casetree.bean.NewCourtBean;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeNode;

import java.util.List;

/**
 * Created by xushun on 2019/4/12.
 * Des:
 */

public interface CourtContact {

    interface OnCoutrView extends BaseView {
        void setCourtList(List<NewCourtBean.DataBean> dataBeans, TreeNode treeNode, int type);
    }

    interface OnCourtPresent extends BasePresenter {
        void getNodeCourList(String node, TreeNode treeNode, int type);
    }
}
