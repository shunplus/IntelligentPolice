package com.shgbit.bailiff.mvp.court.casetree;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeNode;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeViewBinder;


/**
 * BranchViewBinder
 *
 * @author xushun
 * @date 2018/12/7 10:28.
 */

public class BranchViewBinder extends TreeViewBinder<BranchViewBinder.ViewHolder> {

    @Override
    public int getLayoutId() {
        return R.layout.item_branch;
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
        holder.setIsRecyclable(false);//强制关闭复用
        String courtName = ((BranchNode) treeNode.getValue()).getName();
        String courtId = ((BranchNode) treeNode.getValue()).getCourtId();
        holder.textView.setText(courtName);
        holder.findViewById(R.id.ivNode).setRotation(treeNode.isExpanded() ? 90 : 0);
        if (isHaveLeaf(courtId)) {
            holder.findViewById(R.id.ivNode).setVisibility(View.VISIBLE);
        } else {
            holder.findViewById(R.id.ivNode).setVisibility(View.INVISIBLE);
        }
    }

    private boolean isHaveLeaf(String courtName) {
        String court[] = {"1006", "1125", "1127", "1128", "1130", "1402", "1693", "1737"
                , "2010", "230000", "2367", "24", "260000", "2697", "2698", "2699", "270000"
                , "2701", "280000", "2878", "2925", "2998", "3625", "3861", "3862", "72", "732", "829", "975"};

        for (int i = 0; i < court.length; i++) {
            if (courtName.equals(court[i])) {
                return false;
            }
        }

        return true;
    }

    class ViewHolder extends TreeViewBinder.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivNode);
            textView = itemView.findViewById(R.id.tvName);
        }
    }
}
