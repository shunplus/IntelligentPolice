package com.shgbit.bailiff.mvp.court;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.baseImpl.BaseActivity;
import com.shgbit.bailiff.mvp.MainActivity;
import com.shgbit.bailiff.mvp.court.adapter.TreeViewAdapter;
import com.shgbit.bailiff.mvp.court.casetree.BranchNode;
import com.shgbit.bailiff.mvp.court.casetree.BranchViewBinder;
import com.shgbit.bailiff.mvp.court.casetree.LeafNode;
import com.shgbit.bailiff.mvp.court.casetree.LeafViewBinder;
import com.shgbit.bailiff.mvp.court.casetree.RootNode;
import com.shgbit.bailiff.mvp.court.casetree.RootViewBinder;
import com.shgbit.bailiff.mvp.court.casetree.bean.LayoutItem;
import com.shgbit.bailiff.mvp.court.casetree.bean.NewCourtBean;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeNode;
import com.shgbit.bailiff.mvp.court.casetree.bean.TreeViewBinder;
import com.shgbit.bailiff.rxbus.RxBus;
import com.shgbit.bailiff.widget.TopViewLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * Created by xushun on  2019/4/12 21:00.
 * 法院列表分层加载
 */

public class SelectCourtActivity extends BaseActivity<SelectCourtPresent> implements CourtContact.OnCoutrView {


    @BindView(R.id.top_view)
    TopViewLayout topView;
    @BindView(R.id.iv_parent_Node)
    ImageView ivParentNode;
    @BindView(R.id.ivCheck)
    TextView ivCheck;
    @BindView(R.id.tv_parent_Name)
    TextView tvParentName;
    @BindView(R.id.llParent)
    RelativeLayout llParent;
    @BindView(R.id.rv_tree)
    RecyclerView rvTree;
    private Unbinder bind;
    private boolean isExpande = false;
    private String id;
    private String courtName;
    private List<TreeNode> list = new ArrayList<>();
    private TreeViewAdapter adapter;

    @Override
    public SelectCourtPresent initPresenter() {
        return new SelectCourtPresent(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_court);
        bind = ButterKnife.bind(this);
        topView.setFinishActivity(this);
        initAdapter();
        //获取最高院
        mvpPresenter.getNodeCourList("0", null, 0);
        RxBus.getInstance().toObservable(this, String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                showMessage(s);
            }
        });
    }

    private void initAdapter() {
        adapter = new TreeViewAdapter(list, Arrays.asList(new RootViewBinder(), new BranchViewBinder(), new LeafViewBinder())) {
            @Override
            public void toggleClick(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                if (isOpen) {
                    addNewNode(treeNode);
                } else {
                    adapter.lastToggleClickToggle();
                    adapter.notifyData(list);//合拢时重叠
                }
            }

            @Override
            public void toggled(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                viewHolder.findViewById(R.id.ivNode).setRotation(isOpen ? 90 : 0);
            }

            @Override
            public void checked(TreeViewBinder.ViewHolder viewHolder, View view, boolean checked, TreeNode treeNode) {
                String name = null;
                String courtId = null;
                LayoutItem item = treeNode.getValue();
                if (item instanceof RootNode) {
                    name = ((RootNode) item).getName();
                    courtId = ((RootNode) item).getCourtId();
                } else if (item instanceof BranchNode) {

                    name = ((BranchNode) item).getName();
                    courtId = ((BranchNode) item).getCourtId();
                } else if (item instanceof LeafNode) {
                    name = ((LeafNode) item).getName();
                    courtId = ((LeafNode) item).getCourtId();
                }
                Intent intent = new Intent(SelectCourtActivity.this, MainActivity.class);
                intent.putExtra("courtId", courtId);
                intent.putExtra("courtName", name);
                startActivity(intent);
                RxBus.getInstance().postSticky(name);
//                setResult(RESULT_OK, intent);
//                finish();
            }

            @Override
            public void itemClick(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                if (isOpen) {
                    addNewNode(treeNode);
                } else {
                    adapter.lastToggleClickToggle();
                    adapter.notifyData(list);//合拢时重叠
                }
            }
        };
        rvTree.setLayoutManager(new LinearLayoutManager(this));
        rvTree.setAdapter(adapter);
    }

    private void addNewNode(final TreeNode treeNode) {
        String courtId = "";
        String courtGrade = "";
        LayoutItem item = treeNode.getValue();
        List<TreeNode> list = treeNode.getChildNodes();
        boolean hasLeaf = false;
        if (item instanceof RootNode) {
            courtId = ((RootNode) item).getCourtId();
            courtGrade = ((RootNode) item).getCourtGrade();
            for (TreeNode child : list) {
                if (child.getValue() instanceof BranchNode) {
                    hasLeaf = true;
                }
            }

        } else if (item instanceof BranchNode) {
            courtId = ((BranchNode) item).getCourtId();
            courtGrade = ((BranchNode) item).getCourtGrade();
            for (TreeNode child : list) {
                if (child.getValue() instanceof LeafNode) {
                    hasLeaf = true;
                }
            }

        } else if (item instanceof LeafNode) {
            courtId = ((LeafNode) item).getCourtId();
            courtGrade = ((LeafNode) item).getCourtGrade();
            for (TreeNode child : list) {
                if (child.getValue() instanceof LeafNode) {
                    hasLeaf = true;
                }
            }
        }
        if (!hasLeaf) {
            switch (courtGrade) {
                case "1":
                    //点击高院列表后重新获取
                    mvpPresenter.getNodeCourList(courtId, treeNode, 1);
                    break;
                case "2":
                    //点击中院列表后重新获取
                    mvpPresenter.getNodeCourList(courtId, treeNode, 2);
                    break;
                case "3":
                    break;
            }
        } else {
            adapter.lastToggleClickToggle();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }

    /**
     * 点击最高人民法院
     */
    @OnClick({R.id.iv_parent_Node, R.id.tv_parent_Name})
    public void onViewClicked() {
        if (TextUtils.isEmpty(courtName)) {
            showMessage("网络或服务器异常");
            return;
        }
        if (isExpande) {
            isExpande = false;
            ivParentNode.setRotation(0);
            list.clear();
            adapter.notifyData(list);
        } else {
            ivParentNode.setRotation(90);
            isExpande = true;
            mvpPresenter.getNodeCourList("0", null, 0);
        }
    }

    @Override
    public void setCourtList(NewCourtBean.EntityBean entityBean, TreeNode treeNode, int type) {
        switch (type) {
            case 0:
                if (entityBean != null) {
                    courtName = entityBean.getName();
                    tvParentName.setText(courtName);
                    id = entityBean.getId();
                    if (entityBean.getChildren() != null && entityBean.getChildren().size() > 0) {
                        ivParentNode.setRotation(90);
                        isExpande = true;
                        list.clear();
                        for (NewCourtBean.EntityBean.ChildrenBean bean : entityBean.getChildren()) {
                            RootNode rootNode = new RootNode(bean.getName());
                            rootNode.setCourtGrade(bean.getGrade());
                            rootNode.setCourtId(bean.getId());
                            TreeNode<RootNode> rootNodeTreeNode = new TreeNode<>(rootNode);
                            list.add(rootNodeTreeNode);
                            adapter.notifyData(list);
                        }
                    }
                }
                break;
            case 1:
                for (NewCourtBean.EntityBean.ChildrenBean beans : entityBean.getChildren()) {
                    BranchNode branchNode = new BranchNode(beans.getName());
                    branchNode.setCourtGrade(beans.getGrade());
                    branchNode.setCourtId(beans.getId());
                    TreeNode<BranchNode> branchNodeTreeNode = new TreeNode<>(branchNode);
                    treeNode.addChild(branchNodeTreeNode);
                }
                adapter.lastToggleClickToggle();
                break;
            case 2:
                for (NewCourtBean.EntityBean.ChildrenBean beans : entityBean.getChildren()) {
                    LeafNode leafNode = new LeafNode(beans.getName());
                    leafNode.setCourtGrade(beans.getGrade());
                    leafNode.setCourtId(beans.getId());
                    TreeNode<LeafNode> leafNodeTreeNode = new TreeNode<>(leafNode);
                    treeNode.addChild(leafNodeTreeNode);
                }
                adapter.lastToggleClickToggle();
                break;
        }
    }


}
