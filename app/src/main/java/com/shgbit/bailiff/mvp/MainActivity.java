package com.shgbit.bailiff.mvp;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.baseImpl.BaseActivity;
import com.shgbit.bailiff.bean.UpdateMessageBean;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.rxbus.RxBus;
import com.shgbit.bailiff.service.DownloadIntentService;
import com.shgbit.bailiff.util.DisplayUtil;
import com.shgbit.bailiff.util.PermissionsUtils;
import com.shgbit.bailiff.widget.TopViewLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<MainPresent> implements MainContact.OnMianView {
    private static final int DOWNLOADAPK_ID = 10;
    @BindView(R.id.top_view)
    TopViewLayout topView;
    private Unbinder bind;
    private int versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initPermissons();
        LawUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkForUpload();
            }

        }, 500);
    }

    @Override
    public MainPresent initPresenter() {
        return new MainPresent(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().removeAllStickyEvents();
        if (bind != null) {
            bind.unbind();
        }
    }

    @OnClick(R.id.test)
    public void onViewClicked() {

    }

    private void checkForUpload() {
        versionCode = LawUtils.getVersionCode(this);
        mvpPresenter.getUplate(versionCode);
    }

    @Override
    public void setUpdate(UpdateMessageBean.DataBean dataBean) {
        int serVersionCode = Integer.parseInt(dataBean.getValue());
        if (serVersionCode > versionCode) {
            String[] arr = dataBean.getMemo().split("##");
            String isUpdateStr = "";
            String content = "";
            if (null != arr && arr.length == 3) {
                if ("enforcementUpdate=no".equals(arr[1])) {
                    isUpdateStr = "暂不更新";
                }
                content = arr[2];
            }
            //展示更新信息
            showUpdateMessage(isUpdateStr, content, arr[0], serVersionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /**
     * 动态获取权限
     */
    private void initPermissons() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        //创建监听权限的接口对象
        PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
            @Override
            public void passPermissons() {
                Toast.makeText(MainActivity.this, "权限通过，可以做其他事情!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void forbitPermissons() {
//            finish();
                Toast.makeText(MainActivity.this, "权限不通过!", Toast.LENGTH_SHORT).show();
            }
        };
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    /**
     * 展示更新dialog
     */
    private void showUpdateMessage(final String isUpdateStr, String content, final String downUrl, final int serVersionCode) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher)
                .maxIconSize(DisplayUtil.dip2px(this, 36))
                .title("有新的版本，需要更新")
                .neutralText("后台更新")
                .positiveText("立即更新")
                .content(content)
                .negativeText(isUpdateStr)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        switch (which) {
                            //后台更新
                            case NEUTRAL:
                                dialog.dismiss();
                                startUpload(downUrl, serVersionCode, false);
                                break;
                            case NEGATIVE:
                                //暂不更新
                                dialog.dismiss();
                                break;
                            case POSITIVE:
                                //立即更新
                                dialog.dismiss();
                                startUpload(downUrl, serVersionCode, true);
                                break;
                        }
                    }
                }).build();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
        if (dialog.getTitleView() != null) {
            dialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_17));
        }
        if (dialog.getContentView() != null) {
            dialog.getContentView().setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_15));
        }
        if (dialog.getActionButton(DialogAction.NEGATIVE) != null) {
            dialog.getActionButton(DialogAction.NEGATIVE).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_16));
            dialog.getActionButton(DialogAction.NEGATIVE).setTextColor(LawUtils.getColor(R.color.top_color));
        }
        if (dialog.getActionButton(DialogAction.POSITIVE) != null) {
            dialog.getActionButton(DialogAction.POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_16));
            dialog.getActionButton(DialogAction.POSITIVE).setTextColor(LawUtils.getColor(R.color.top_color));
        }
        if (dialog.getActionButton(DialogAction.NEUTRAL) != null) {
            dialog.getActionButton(DialogAction.NEUTRAL).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_16));
            dialog.getActionButton(DialogAction.NEUTRAL).setTextColor(LawUtils.getColor(R.color.top_color));
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
        dialog.setCancelable(false);
    }


    /**
     * 启动下载任务
     *
     * @param downloadUrl
     * @param serVersionCode
     */
    private void startUpload(String downloadUrl, int serVersionCode, boolean isShowProgress) {
        if (LawUtils.isServiceRunning(context, DownloadIntentService.class.getName())) {
            showMessage("正在下载");
            return;
        }
        Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
        Bundle bundle = new Bundle();
        bundle.putString("download_url", downloadUrl);
        bundle.putInt("download_id", serVersionCode);
        bundle.putBoolean("down_show", isShowProgress);
        bundle.putString("download_file", downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1));
        intent.putExtras(bundle);
        startService(intent);
    }

}
