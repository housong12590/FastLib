package com.ws.fastlib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ws.fastlib.common.LoadStatus;

import gorden.rxbus2.RxBus;

public abstract class BaseFragment extends Fragment {

    protected View mContentView;
    protected int PAGE_SIZE = 15;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        RxBus.get().register(this);
        if (arguments != null) {
            parseIntent(arguments);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), null);
        }
        ViewGroup parent = (ViewGroup) mContentView.getParent();
        if (parent != null) {
            parent.removeView(mContentView);
        }
        initView();
        return mContentView;
    }

    public void parseIntent(Bundle bundle) {

    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public void requestData(LoadStatus status) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        requestData(LoadStatus.LOADING);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

}
