package com.hstmpl.base;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.hstmpl.net.LoadStatus;

import org.jetbrains.annotations.NotNull;

public abstract class DelayFragment<DataBinding extends ViewDataBinding> extends BaseFragment<DataBinding> {

    private boolean isCreated;
    private boolean isVisibleToUser;
    private boolean isLoad;

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.isCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad() {
        if (isVisibleToUser && isCreated && !isLoad) {
            requestData(LoadStatus.LOADING);
            isLoad = true;
        }
    }

}
