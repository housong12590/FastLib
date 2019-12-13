package com.ws.fastlib.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.ws.fastlib.R;

public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.load_more_layout;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.tv_load_end;
    }
}
