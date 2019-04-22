package com.shgbit.bailiff.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shgbit.bailiff.R;
import com.shgbit.bailiff.config.LawUtils;

/**
 * Created by xushun on 2019/4/20.
 * Des:
 * Email:shunplus@163.com
 */

public class MaterialDialogUtils {
    /**
     * MaterialDialog 消息提示框显示
     *
     * @param context 上下文
     * @param title   标题
     */
    public static void showUpLoadDialog(Context context, String title, String content,
                                        String neutralText, String negativeText,
                                        String positiveText, boolean setCancelable,
                                        MaterialDialog.SingleButtonCallback singleButtonCallback) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .iconRes(R.mipmap.ic_launcher)
                .maxIconSize(DisplayUtil.dip2px(context, 36))
                .title(title)
                .neutralText(neutralText)
                .negativeText(negativeText)
                .positiveText(positiveText)
                .content(content)
                .onAny(singleButtonCallback).build();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
        if (dialog.getTitleView() != null) {
            dialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_17));
        }
        if (dialog.getContentView() != null) {
            dialog.getContentView().setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_15));
        }
        if (dialog.getActionButton(DialogAction.NEGATIVE) != null) {
            dialog.getActionButton(DialogAction.NEGATIVE).setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_16));
            dialog.getActionButton(DialogAction.NEGATIVE).setTextColor(LawUtils.getColor(R.color.top_color));
        }
        if (dialog.getActionButton(DialogAction.POSITIVE) != null) {
            dialog.getActionButton(DialogAction.POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_16));
            dialog.getActionButton(DialogAction.POSITIVE).setTextColor(LawUtils.getColor(R.color.top_color));
        }
        if (dialog.getActionButton(DialogAction.NEUTRAL) != null) {
            dialog.getActionButton(DialogAction.NEUTRAL).setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_16));
            dialog.getActionButton(DialogAction.NEUTRAL).setTextColor(LawUtils.getColor(R.color.top_color));
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
        dialog.setCancelable(setCancelable);
    }

    /**
     * 显示下载的进度条
     */
    public static MaterialDialog showDownProgress(Context mContext, MaterialDialog.SingleButtonCallback singleButtonCallback) {
        MaterialDialog dialogProgress = null;
        dialogProgress = new MaterialDialog.Builder(mContext)
                .title("正在下载")
                .widgetColor(LawUtils.getColor(R.color.top_color))
                .positiveText("后台下载")// 标题
                .progress(false, 100, true)// 确定进度条
                .onPositive(singleButtonCallback).show();
        dialogProgress.setCancelable(false);
        if (dialogProgress.getActionButton(DialogAction.POSITIVE) != null) {
            dialogProgress.getActionButton(DialogAction.POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.font_16));
            dialogProgress.getActionButton(DialogAction.POSITIVE).setTextColor(LawUtils.getColor(R.color.top_color));
        }
        return dialogProgress;
    }

    /**
     * 显示下载的进度条
     */
    public static void showMesage(Context context, String title, String content,
                                  boolean setCancelable,
                                  MaterialDialog.SingleButtonCallback singleButtonCallback) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(title)
                .neutralText("取消")
                .positiveText("确定")
                .content(content)
                .onAny(singleButtonCallback).build();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
        if (dialog.getTitleView() != null) {
            dialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_17));
        }
        if (dialog.getContentView() != null) {
            dialog.getContentView().setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_15));
        }
        if (dialog.getActionButton(DialogAction.POSITIVE) != null) {
            dialog.getActionButton(DialogAction.POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_16));
            dialog.getActionButton(DialogAction.POSITIVE).setTextColor(LawUtils.getColor(R.color.top_color));
        }
        if (dialog.getActionButton(DialogAction.NEUTRAL) != null) {
            dialog.getActionButton(DialogAction.NEUTRAL).setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_16));
            dialog.getActionButton(DialogAction.NEUTRAL).setTextColor(LawUtils.getColor(R.color.top_color));
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
        dialog.setCancelable(setCancelable);
    }

}
