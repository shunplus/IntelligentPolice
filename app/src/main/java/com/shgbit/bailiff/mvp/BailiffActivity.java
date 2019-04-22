package com.shgbit.bailiff.mvp;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.baseImpl.BaseActivity;
import com.shgbit.bailiff.bean.UpdateMessageBean;
import com.shgbit.bailiff.config.LawUtils;
import com.shgbit.bailiff.mvp.login.LoginActivity;
import com.shgbit.bailiff.network.down.DownloadInfo;
import com.shgbit.bailiff.rxbus.RxBus;
import com.shgbit.bailiff.service.DownloadIntentService;
import com.shgbit.bailiff.util.MaterialDialogUtils;
import com.shgbit.bailiff.util.PLog;
import com.shgbit.bailiff.util.PermissionsUtils;
import com.shgbit.bailiff.widget.TopViewLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public class BailiffActivity extends BaseActivity<BailiffPresent> implements BailiffContact.OnMianView {
    @BindView(R.id.top_view)
    TopViewLayout topView;
    private Unbinder bind;
    private int versionCode;
    private MaterialDialog dialogProgress;
    private final static int INSTALL_PERMISS_CODE = 1008;
    private DownloadInfo info;
    private static final String TAG = "BailiffActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        initPermissons();
        LawUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                versionCode = LawUtils.getVersionCode(mContext);
                mvpPresenter.getUplate(versionCode);
            }

        }, 500);
        RxBus.getInstance().toObservable(this, DownloadInfo.class).subscribe(new Consumer<DownloadInfo>() {
            @Override
            public void accept(DownloadInfo downloadInfo) throws Exception {
                if (dialogProgress != null && !downloadInfo.isInsatall()) {
                    dialogProgress.setProgress(downloadInfo.getProgress());
                }
                if (downloadInfo.isInsatall()) {
                    PLog.i(TAG, "--install apk");
                    info = downloadInfo;
                    setInstallPermission();
                    dialogProgress.dismiss();
                }

            }
        });
    }

    @Override
    public BailiffPresent initPresenter() {
        return new BailiffPresent(this);
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


    @Override
    public void setUpdate(UpdateMessageBean.DataBean dataBean) {
        final int serVersionCode = Integer.parseInt(dataBean.getValue());
        if (serVersionCode > versionCode) {
            final String[] arr = dataBean.getMemo().split("##");
            String isUpdateStr = "";
            String content = "";
            if (null != arr && arr.length == 3) {
                if ("enforcementUpdate=no".equals(arr[1])) {
                    isUpdateStr = "暂不更新";
                }
                content = arr[2];
            }
            //展示更新信息
            MaterialDialogUtils.showUpLoadDialog(mContext, "有新的版本，需要更新", content, "后台更新"
                    , isUpdateStr, "立即更新", false, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            switch (which) {
                                //后台更新
                                case NEUTRAL:
                                    dialog.dismiss();
                                    startUpload(arr[0], serVersionCode, false);
                                    break;
                                case NEGATIVE:
                                    //暂不更新
                                    dialog.dismiss();
                                    break;
                                case POSITIVE:
                                    //立即更新
                                    dialog.dismiss();
                                    startUpload(arr[0], serVersionCode, true);
                                    dialogProgress = MaterialDialogUtils.showDownProgress(mContext, new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialogProgress.dismiss();
                                        }
                                    });
                                    break;
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == INSTALL_PERMISS_CODE) {
            if (info != null) {
                File file = new File(info.getSavePath());
                installApk(file);
            }
        }
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

            }

            @Override
            public void forbitPermissons() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        };
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    /**
     * 启动下载任务
     *
     * @param downloadUrl
     * @param serVersionCode
     */
    private void startUpload(String downloadUrl, int serVersionCode, boolean isShowProgress) {
        if (LawUtils.isServiceRunning(mContext, DownloadIntentService.class.getName())) {
            showMessage("正在下载");
            return;
        }
        Intent intent = new Intent(BailiffActivity.this, DownloadIntentService.class);
        Bundle bundle = new Bundle();
        bundle.putString("download_url", downloadUrl);
        bundle.putInt("download_id", serVersionCode);
        bundle.putBoolean("down_show", isShowProgress);
        bundle.putString("download_file", downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1));
        intent.putExtras(bundle);
        startService(intent);
    }

    /**
     * 8.0以上系统设置安装未知来源权限
     */
    public void setInstallPermission() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先判断是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
//            installApk(new File(info.getSavePath()));
            if (!haveInstallPermission) {
                //弹框提示用户手动打开
                MaterialDialogUtils.showMesage(mContext, "安装权限",
                        "安装应用需要打开未知来源权限，请去设置中开启权限",
                        false,
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    startInstallPermissionSettingActivity();
                                }
                            }
                        });
                return;
            }
        }
        if (info != null) {
            File file = new File(info.getSavePath());
            installApk(file);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, INSTALL_PERMISS_CODE);
    }

    //安装应用
    private void installApk(File apk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        } else {//Android7.0之后获取uri要用contentProvider
            Uri uri = FileProvider.getUriForFile(mContext, getPackageName() + ".fileprovider", apk);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
