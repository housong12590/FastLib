package com.hstmpl.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.blankj.utilcode.util.BusUtils;
import com.hstmpl.dialog.LoadingDialog;

public abstract class BaseActivity<DataBinding extends ViewDataBinding> extends AppCompatActivity {

    protected AppCompatActivity mContext;
    protected DataBinding mDataBinding;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusUtils.register(this);
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
        BusUtils.unregister(this);
        mContext = null;
    }
}
