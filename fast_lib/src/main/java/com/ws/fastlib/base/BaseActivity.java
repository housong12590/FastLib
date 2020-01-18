package com.ws.fastlib.base;

import android.content.Intent;
import android.os.Bundle;

import com.ws.fastlib.common.LoadStatus;
import com.ws.fastlib.dialog.LoadingDialog;
import com.ws.fastlib.utils.ActivityUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gorden.rxbus2.RxBus;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mBind;
    public AppCompatActivity mContext;
    private LoadingDialog mLoadingDialog;
    protected int PAGE_SIZE = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
        ActivityUtils.addActivity(this);
        this.mContext = this;
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);
        parseIntent(getIntent());
        init();
    }

    protected void init() {
        initView();
        initData();
        requestData(LoadStatus.LOADING);
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public void requestData(LoadStatus status) {
    }

    public void parseIntent(Intent intent) {

    }

    public void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mContext);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void dismissDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
        ActivityUtils.removeActivity(this);
        mBind.unbind();
        mContext = null;
    }
}
