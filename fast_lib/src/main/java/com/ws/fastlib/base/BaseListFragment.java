package com.ws.fastlib.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ws.fastlib.R;
import com.ws.fastlib.common.LoadStatus;
import com.ws.fastlib.databinding.FragmentBaseListBinding;
import com.ws.fastlib.network.observer.DefaultObserver;
import com.ws.fastlib.utils.ColorUtils;
import com.ws.fastlib.widget.CustomLoadMoreView;
import com.ws.fastlib.widget.MultipleStatusLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

// 如果需要实现懒加载 在FragmentPagerAdapter在使用两个参数的构造方法, super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
public abstract class BaseListFragment<T> extends BaseFragment<FragmentBaseListBinding> implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    private MultipleStatusLayout mMultipleStatusLayout;
    private Disposable mDisposable;
    private int mCurrentPage = 1;
    private boolean isLoadData = false;
    private int mTotalPage = Integer.MAX_VALUE;
    private int PAGE_SIZE = 20;
    private List<T> mData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void initView(FragmentBaseListBinding binding) {
        mRecyclerView = binding.recyclerView;
        mSwipeRefreshLayout = binding.swipeRefreshLayout;

        mMultipleStatusLayout = new MultipleStatusLayout(mSwipeRefreshLayout);
        mMultipleStatusLayout.setOnErrorClickListener(R.id.bt_retry, v -> requestData(LoadStatus.REFRESH));
        mSwipeRefreshLayout.setColorSchemeColors(ColorUtils.getRandColors(3));
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }

        RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
        if (itemDecoration != null) {
            mRecyclerView.addItemDecoration(itemDecoration);
        }

        RecyclerView.OnItemTouchListener onItemTouchListener = onItemTouchListener();
        if (onItemTouchListener != null) {
            mRecyclerView.addOnItemTouchListener(onItemTouchListener);
        }
        mAdapter = getAdapter();
        if (mAdapter == null) {
            throw new NullPointerException("Please set the adapter first");
        }
        mAdapter.setEmptyView(setupEmptyView());
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        mAdapter.setPreLoadNumber(3);
        mAdapter.setNewData(mData);
        mRecyclerView.setAdapter(mAdapter);
        if (isLoadMoreEnable()) {
            mAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void requestData(LoadStatus status) {
        this.mCurrentPage = status == LoadStatus.LOAD_MORE ? ++mCurrentPage : 1;
        if (status == LoadStatus.LOADING) {
            mMultipleStatusLayout.showLoadingView();
        }
        requestApi(status, mCurrentPage).subscribe(new DefaultObserver<List<T>>() {

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                mDisposable = d;
            }

            @Override
            public void onSuccess(List<T> ts) {
                isLoadData = true;
                if (status == LoadStatus.LOADING) {
                    mData.clear();
                    mMultipleStatusLayout.showContentView();
                    mAdapter.addData(ts);
                    mAdapter.disableLoadMoreIfNotFullPage();
                } else if (status == LoadStatus.REFRESH) {
                    mData.clear();
                    mAdapter.addData(ts);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mMultipleStatusLayout.showContentView();
                    if (isLoadMoreEnable()) {
                        mAdapter.setEnableLoadMore(true);
//                        mAdapter.loadMoreComplete();
                    }
                } else if (status == LoadStatus.LOAD_MORE) {
                    mAdapter.addData(ts);
                    if (ts.isEmpty() || ts.size() < PAGE_SIZE || mCurrentPage >= mTotalPage) {
                        mAdapter.loadMoreEnd(mAdapter.getData().size() < PAGE_SIZE);
                    } else {
                        mAdapter.loadMoreComplete();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (status == LoadStatus.LOADING) {
                    mMultipleStatusLayout.showErrorView();//失败重试
                } else if (status == LoadStatus.REFRESH) {
                    mSwipeRefreshLayout.setRefreshing(false); //下拉刷新完成
                } else if (status == LoadStatus.LOAD_MORE) {
                    mAdapter.loadMoreFail(); // 加载更多失败
                }
            }
        });
    }

    public abstract BaseQuickAdapter<T, BaseViewHolder> getAdapter();

    public abstract Single<List<T>> requestApi(LoadStatus status, int currPage);

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    public boolean isLoadMoreEnable() {
        return false;
    }

    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return null;
    }

    public void setTotalPage(int totalPage) {
        this.mTotalPage = totalPage;
    }

    public View setupEmptyView() {
        View emptyView = View.inflate(getActivity(), R.layout.loading_empyt_layout, null);
        ((TextView) emptyView.findViewById(R.id.tv_content)).setText(setEmptyContent());
        ImageView image = emptyView.findViewById(R.id.iv_image);
        image.setImageResource(setEmptyIcon());
        return emptyView;
    }

    public String setEmptyContent() {
        return "当前无数据哦...";
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public int setEmptyIcon() {
        return R.drawable.ic_placeholder;
    }

    @Override
    public void onRefresh() {
        requestData(LoadStatus.REFRESH);
    }

    @Override
    public void onLoadMoreRequested() {
        requestData(LoadStatus.LOAD_MORE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoadData) {
            requestData(LoadStatus.LOADING);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
