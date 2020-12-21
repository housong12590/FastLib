package com.hstmpl.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.hstmpl.dialog.LoadingDialog;
import com.hstmpl.util.ActivityUtils;

import gorden.rxbus2.RxBus;

public abstract class BaseActivity<DataBinding extends ViewDataBinding> extends AppCompatActivity {

    protected AppCompatActivity mContext;
    protected DataBinding mDataBinding;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
        ActivityUtils.addActivity(this);
        mContext = this;
        mDataBinding = DataBindingUtil.setContentView(mContext, getLayoutId());
        mDataBinding.setLifecycleOwner(this);
        parseIntent(getIntent());
        init();
    }

    private void init() {
        initView(mDataBinding);
        initData();
    }

    public void parseIntent(Intent intent) {

    }

    protected abstract int getLayoutId();

    protected abstract void initView(DataBinding dataBinding);

    public abstract void initData();

    public DataBinding getBinding() {
        return mDataBinding;
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
        mContext = null;
    }
}
