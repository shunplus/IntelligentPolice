package com.shgbit.bailiff.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.shgbit.bailiff.R;


/**
 * @author:xushun on 2018/7/5
 * description : 加载。。。
 */

public class LoadDialog extends Dialog {

    private int buju;
    private ImageView linearLayout;
    private Animation animation;

    public LoadDialog(Context context, int theme, int buju) {
        super(context, theme);
        this.buju = buju;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(buju);
        linearLayout = (ImageView) findViewById(R.id.main);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.load_anim);
    }

    @Override
    public void show() {
        super.show();
//        animation(1000);
        linearLayout.startAnimation(animation);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        animation.cancel();
    }
//
}
