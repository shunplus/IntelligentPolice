package com.shgbit.bailiff.mvp.court;

import com.shgbit.bailiff.base.BaseBeanObserver;
import com.shgbit.bailiff.base.baseImpl.BasePresenterImpl;
import com.shgbit.bailiff.common.ErrorMessage;
import com.shgbit.bailiff.config.ConstantsApi;
import com.shgbit.bailiff.mvp.court.casetree.bean.NewCourtBean;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeNode;

import java.util.WeakHashMap;

import io.reactivex.disposables.Disposable;

/**
 * Created by xushun on 2019/4/12.
 * Des:
 */

public class SelectCourtPresent extends BasePresenterImpl<CourtContact.OnCoutrView> implements CourtContact.OnCourtPresent {
    public SelectCourtPresent(CourtContact.OnCoutrView view) {
        super(view);
    }

    @Override
    public void getNodeCourList(String node, final TreeNode treeNode, final int type) {
        if (view != null) {
            view.showLoadingDialog();
        }
        WeakHashMap<String, Object> parms = new WeakHashMap<>();
        parms.put("id", node);
        post(ConstantsApi.GET_COURTLIST, parms, NewCourtBean.class, new BaseBeanObserver<NewCourtBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(NewCourtBean newCourtBean) {
                if (view != null) {
                    view.dismissLoadingDialog();
                    view.setCourtList(newCourtBean.getEntity(), treeNode, type);
                }
            }

            @Override
            public void onError(ErrorMessage error) {
                if (view != null) {
                    view.dismissLoadingDialog();
                    view.handleError(error);
                }
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
