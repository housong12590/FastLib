package com.ws.fastlib.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ws.fastlib.R;


public class LoadingStateView extends FrameLayout {

    private View mLoadingView;
    private View mErrorView;
    private View mContentView;
    private View mEmptyView;
    public static int LOAD_LAYOUT_ID = R.layout.loading_layout;
    public static int ERROR_LAYOUT_ID = R.layout.loading_error_layout;
    public static int EMPTY_LAYOUT_ID = R.layout.loading_empyt_layout;

    public LoadingStateView(View view) {
        super(view.getContext());
        mContentView = view;
        ViewGroup parent = (ViewGroup) view.getParent();
        int childCount = parent.getChildCount();
        int index = 0;
        for (int i = 0; i < childCount; i++) { // 找到contentView在父容器所在的位置
            if (parent.getChildAt(i) == view) {
                index = i;
            }
        }
        parent.removeView(view); // 把原先contentView移除
        ViewGroup.LayoutParams params = view.getLayoutParams();
        parent.addView(this, index, params); // 再把自己创建的view添加到原来的位置

        addView(view);
        view.setVisibility(GONE);
        //错误布局
        if (mErrorView == null) {
            mErrorView = inflate(ERROR_LAYOUT_ID);
        }
        if (mErrorView == null) {
            throw new NullPointerException("error view is null");
        }
        addView(mErrorView);
        mErrorView.setVisibility(GONE);
        //空布局
        if (mEmptyView == null) {
            mEmptyView = inflate(EMPTY_LAYOUT_ID);
        }
        if (mEmptyView == null) {
            throw new NullPointerException("empty view is null");
        }
        addView(mEmptyView);
        mEmptyView.setVisibility(GONE);
        //loading界面
        if (mLoadingView == null) {
            mLoadingView = inflate(LOAD_LAYOUT_ID);
        }
        if (mLoadingView == null) {
            throw new NullPointerException("loading view is null");
        }
        addView(mLoadingView);
        mLoadingView.setVisibility(GONE);
    }

    public LoadingStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void showView(View view) {
        if (view == null) {
            return;
        }
        mLoadingView.setVisibility(view == mLoadingView ? VISIBLE : GONE);
        mErrorView.setVisibility(view == mErrorView ? VISIBLE : GONE);
        mContentView.setVisibility(view == mContentView ? VISIBLE : GONE);
        mEmptyView.setVisibility(view == mEmptyView ? VISIBLE : GONE);
    }

    public void showErrorView() {
        showView(mErrorView);
    }

    public void showContentView() {
        showView(mContentView);
    }

    public void showEmptyView() {
        showView(mEmptyView);
    }

    public void showLoadingView() {
        showView(mLoadingView);
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View getErrorView() {
        return mErrorView;
    }

    public View getContentView() {
        return mContentView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setLoadingView(View mLoadingView) {
        removeView(this.mLoadingView);
        addView(mLoadingView);
        mLoadingView.setVisibility(GONE);
        this.mLoadingView = mLoadingView;
    }

    public void setEmptyView(View mEmptyView) {
        removeView(this.mEmptyView);
        addView(mEmptyView);
        mEmptyView.setVisibility(GONE);
        this.mEmptyView = mEmptyView;
    }

    public void setContentView(View mContentView) {
        removeView(this.mContentView);
        addView(mContentView);
        mContentView.setVisibility(GONE);
        this.mContentView = mContentView;
    }

    public void setErrorView(View mErrorView) {
        removeView(this.mErrorView);
        addView(mErrorView);
        mErrorView.setVisibility(GONE);
        this.mErrorView = mErrorView;
    }

    public void setLoadingView(int layoutId) {
        setLoadingView(inflate(layoutId));
    }

    public void setErrorView(int layoutId) {
        setErrorView(inflate(layoutId));
    }

    public void EmptyView(int layoutId) {
        setEmptyView(inflate(layoutId));
    }

    private View inflate(int layoutId) {
        return inflate(getContext(), layoutId, null);
    }

    public void setOnErrorClickListener(int id, OnClickListener listener) {
        getErrorView().findViewById(id).setOnClickListener(listener);
    }
}
