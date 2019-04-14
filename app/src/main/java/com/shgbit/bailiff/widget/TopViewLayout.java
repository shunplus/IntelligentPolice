package com.shgbit.bailiff.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.util.DisplayUtil;


/**
 * 顶部view
 * Created by wangjinxue on 16/11/11.
 */

public class TopViewLayout extends RelativeLayout {

    private Context mContext;
    public ImageView mBackImage;
    private TextView mTitleView;
    private TextView mRightMenu;

    LayoutParams mBackParams;
    LayoutParams mTitleParams;
    LayoutParams mMenuParams;
    public final static int WARP_CONTENT = LayoutParams.WRAP_CONTENT;
    public final static int MATCH_PARENT = LayoutParams.MATCH_PARENT;

    public TopViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopViewLayout(Context context) {
        this(context, null);
    }

    String mTitle;
    int mLeftImage;
    String rightText;
    int dp8;

    public TopViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (isInEditMode())
            return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopViewLayout, defStyleAttr, 0);
        for (int i = 0; i < array.length(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.TopViewLayout_topview_left:
                    mLeftImage = array.getResourceId(attr, -1);
                    break;
                case R.styleable.TopViewLayout_topview_title:
                    mTitle = array.getString(attr);
                    break;
                case R.styleable.TopViewLayout_topview_right:
                    rightText = array.getString(attr);
                    break;
                default:
                    break;
            }
        }
        dp8 = DisplayUtil.dip2px(mContext, 8);
        int dp45 = DisplayUtil.dip2px(mContext, 45);
        int dp12 = DisplayUtil.dip2px(mContext, 12);
        mBackImage = new ImageView(mContext);
        mTitleView = new TextView(mContext);
        mRightMenu = new TextView(mContext);

        mBackParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mTitleParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mMenuParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);

        mTitleParams.addRule(CENTER_IN_PARENT);
        mMenuParams.addRule(ALIGN_PARENT_RIGHT);
        mMenuParams.addRule(CENTER_VERTICAL);
        mRightMenu.setPadding(dp8, 0, dp12, 0);
        mBackParams.addRule(CENTER_VERTICAL);
        mBackImage.setPadding(0, 0, dp12, 0);

        addView(mBackImage, mBackParams);
        addView(mTitleView, mTitleParams);
        addView(mRightMenu, mMenuParams);
        mTitleView.setText(mTitle);
        mTitleView.setMaxLines(1);
        mTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mTitleView.setPadding(5 * dp8, 0, 5 * dp8, 0);

        mRightMenu.setText(rightText);
        mRightMenu.setMaxLines(1);
        mRightMenu.setPadding(5 * dp8, 0, 1 * dp8, 0);

        if (mLeftImage > 0) {
            mBackImage.setImageResource(mLeftImage);
        }
        setBackgroundResource(R.color.top_color);
        mTitleView.setTextColor(ContextCompat.getColor(context, R.color.white));
        int dimen = getResources().getDimensionPixelSize(R.dimen.font_16);
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen);

        mRightMenu.setTextColor(ContextCompat.getColor(context, R.color.white));
        int dimen2 = getResources().getDimensionPixelSize(R.dimen.font_13);
        mRightMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen2);

        array.recycle();
    }


    public void setTitle(String text) {
        mTitleView.setText(text);
    }

    public void setRightText(String text) {
        this.mRightMenu.setText(text);
    }

    public void setRightTextSize(int textSize) {
        int dimen = getResources().getDimensionPixelSize(textSize);
        mRightMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen);
    }

    public void setBackImageRes(int resid) {
        this.mBackImage.setImageResource(resid);
    }

    public void setRightMenuListener(OnClickListener mClick) {
        if (mClick != null) {
            mRightMenu.setOnClickListener(mClick);
        }
    }

    public void setBackOnClickListener(OnClickListener mL) {
        if (mL != null) {
            mBackImage.setOnClickListener(mL);
        }
    }

    public void setmRightMenuVisable(boolean visable) {
        if (visable) {
            mRightMenu.setVisibility(VISIBLE);
        } else {
            mRightMenu.setVisibility(GONE);
        }
    }

    /**
     * 设置返回按钮的便利方法
     *
     * @param mActivity
     */
    public void setFinishActivity(final Activity mActivity) {
//       if(mBackImage.)
        mBackImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }
}
