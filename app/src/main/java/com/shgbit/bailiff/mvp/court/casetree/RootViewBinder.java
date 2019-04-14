package com.shgbit.bailiff.mvp.court.casetree;

import android.view.View;
import android.widget.TextView;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeNode;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeViewBinder;


/**
 * RootViewBinder
 *
 * @author xushun
 * @date 2018/12/7 10:28.
 */

public class RootViewBinder extends TreeViewBinder<RootViewBinder.ViewHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_root;
    }

    @Override
    public int getToggleId() {
        return R.id.ivNode;
    }

    @Override
    public int getCheckedId() {
        return R.id.ivCheck;
    }

    @Override
    public int getClickId() {
        return R.id.tvName;
    }

    @Override
    public ViewHolder creatViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position, TreeNode treeNode) {
        ((TextView) holder.findViewById(R.id.tvName)).setText(((RootNode) treeNode.getValue()).getName());
        holder.findViewById(R.id.ivNode).setRotation(treeNode.isExpanded() ? 90 : 0);
//        ((ImageView) holder.findViewById(R.id.ivCheck)).setImageResource(treeNode.isChecked() ? R.drawable.ic_checked : R.drawable.ic_uncheck);
//        holder.findViewById(R.id.llParent).setBackgroundColor(holder.itemView.getContext().getResources().getColor(treeNode.isChecked() ? R.color.gray : R.color.white));
//        ((TextView) holder.findViewById(R.id.ivCheck)).setText(((RootNode) treeNode.getValue()).getCount() + "件");
    }

    class ViewHolder extends TreeViewBinder.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
