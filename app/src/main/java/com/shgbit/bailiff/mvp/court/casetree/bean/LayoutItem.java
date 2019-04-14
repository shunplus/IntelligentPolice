package com.shgbit.bailiff.mvp.court.casetree.bean;

/**
 * 节点布局
 *
 * @author xushun
 * @date 2018/12/7 10:28.
 */
public interface LayoutItem {
    /**
     * 返回布局id
     *
     * @return
     */
    int getLayoutId();

    /**
     * 返回展开收拢事件触发id
     *
     * @return
     */
    int getToggleId();

    /**
     * 返回选中事件触发id
     *
     * @return
     */
    int getCheckedId();

    /**
     * 返回单项点击事件触发id
     *
     * @return
     */
    int getClickId();
}
