package com.shgbit.bailiff.mvp;


import android.os.Bundle;

import com.shgbit.bailiff.R;
import com.shgbit.bailiff.base.BasePresenter;
import com.shgbit.bailiff.base.baseImpl.BaseActivity;
import com.shgbit.bailiff.rxbus.RxBus;
import com.shgbit.bailiff.widget.TopViewLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.top_view)
    TopViewLayout topView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        LiveEventBus.get().with("login", String.class).observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//
//            }
//        });

        RxBus.getInstance().toObservableSticky(this, String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                showMessage(s);
            }
        });
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().removeAllStickyEvents();
    }

    @OnClick(R.id.test)
    public void onViewClicked() {
        RxBus.getInstance().post("12345677890");
    }
}
