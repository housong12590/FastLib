package com.hstmpl.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.BusUtils;
import com.hstmpl.net.LoadStatus;

import org.jetbrains.annotations.NotNull;

public abstract class BaseFragment<DataBinding extends ViewDataBinding> extends Fragment {

    private DataBinding mDataBinding;

    public static void attachToContainer(AppCompatActivity activity, int container, Fragment fragment) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container, fragment);
        fragment.setUserVisibleHint(true);
        transaction.commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        BusUtils.register(this);
        if (arguments != null) {
            parseIntent(arguments);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        View mContentView = mDataBinding.getRoot();
        ViewGroup parent = (ViewGroup) mContentView.getParent();
        if (parent != null) {
            parent.removeView(mContentView);
        }
        initView(mDataBinding);
        return mContentView;
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        requestData(LoadStatus.LOADING);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(DataBinding binding);

    protected abstract void initData();

    protected DataBinding getBinding() {
        return mDataBinding;
    }

    public void parseIntent(Bundle bundle) {

    }

    public void requestData(LoadStatus status) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
    }


}
