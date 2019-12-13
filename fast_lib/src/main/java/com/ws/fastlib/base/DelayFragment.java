package com.ws.fastlib.base;

import android.os.Bundle;
import android.view.View;

import com.ws.fastlib.LoadStatus;

public abstract class DelayFragment extends BaseFragment {

    protected boolean isCreated;
    protected boolean isVisibleToUser;
    protected boolean hasLoaded;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        isCreated = true;
        delayLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
        delayLoad();
    }

    protected void delayLoad() {
        if (isVisibleToUser && isCreated && !hasLoaded) {
            initData();
            requestData(LoadStatus.LOADING);
            hasLoaded = true;
        }
    }
}
