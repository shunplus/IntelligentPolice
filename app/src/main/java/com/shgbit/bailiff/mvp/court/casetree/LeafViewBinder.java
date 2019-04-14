package com.shgbit.bailiff.mvp.court.casetree;

import android.view.View;
import android.widget.TextView;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeNode;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeViewBinder;


/**
 * LeafViewBinder
 *
 * @author xushun
 * @date 2018/12/7 10:28.
 */

public class LeafViewBinder extends TreeViewBinder<LeafViewBinder.ViewHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_leaf;
    }

    @Override
    public int getToggleId() {
        return 0;
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
        ((TextView) holder.findViewById(R.id.tvName)).setText(((LeafNode) treeNode.getValue()).getName());
        holder.findViewById(R.id.ivNode).setRotation(treeNode.isExpanded() ? 90 : 0);
//        ((ImageView) holder.findViewById(R.id.ivNode)).setImageResource(treeNode.isExpanded() ? R.drawable.more_down_sh : R.drawable.more_sh_tools);
//        ((TextView) holder.findViewById(R.id.ivCheck)).setText(((LeafNode) treeNode.getValue()).getCount() + "件");
    }

    class ViewHolder extends TreeViewBinder.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
