package com.shgbit.bailiff.mvp.court.casetree;


import com.shgbit.bailiff.R;
import com.shgbit.bailiff.mvp.court.casetree.bean.LayoutItem;

/**
 * BranchNode
 *
 * @author xushun
 * @date 2018/12/7 10:28.
 */

public class BranchNode implements LayoutItem {
    private String name;
    private String courtId;
    private String count;
    private String courtGrade;
    private String abbreviate;//简称

    public String getAbbreviate() {
        return abbreviate;
    }

    public void setAbbreviate(String abbreviate) {
        this.abbreviate = abbreviate;
    }

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCourtGrade() {
        return courtGrade;
    }

    public void setCourtGrade(String courtGrade) {
        this.courtGrade = courtGrade;
    }


    public BranchNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
