package com.hstmpl.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.hstmpl.fastlib.R;
import com.hstmpl.net.LoadStatus;
import com.hstmpl.fastlib.databinding.FragmentBaseListBinding;
import com.hstmpl.net.observer.DefaultObserver;
import com.hstmpl.util.ColorUtils;
import com.hstmpl.widget.CustomLoadMoreView;
import com.hstmpl.widget.MultipleStatusLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

// 如果需要实现懒加载 在FragmentPagerAdapter在使用两个参数的构造方法, super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
public abstract class BaseListFragment<T> extends DelayFragment<FragmentBaseListBinding> implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    private MultipleStatusLayout mMultipleStatusLayout;
    private Disposable mDisposable;
    private int mCurrentPage = 1;
    private int mTotalPage = Integer.MAX_VALUE;
    private static final int PAGE_SIZE = 20;
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
        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setEmptyView(setupEmptyView());
        mAdapter.setNewData(mData);
        mRecyclerView.setAdapter(mAdapter);
        // 启用加载更多
        if (isLoadMoreEnable()) {
            mAdapter.setOnLoadMoreListener(this, mRecyclerView);
            mAdapter.disableLoadMoreIfNotFullPage();
            mAdapter.setPreLoadNumber(3);
            mAdapter.setLoadMoreView(getLoadMoreView());
        }
        mAdapter.setEnableLoadMore(isLoadMoreEnable());
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
                if (status == LoadStatus.LOADING) {
                    mData = ts;
                    mMultipleStatusLayout.showContentView();
                    mAdapter.setNewData(mData);
                } else if (status == LoadStatus.REFRESH) {
                    mData = ts;
                    mAdapter.setNewData(mData);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mMultipleStatusLayout.showContentView();
                } else if (status == LoadStatus.LOAD_MORE) {
                    mAdapter.addData(ts);
                    if (ts.isEmpty() || ts.size() < getPageSize() || mCurrentPage >= mTotalPage) {
                        mAdapter.loadMoreEnd(mAdapter.getData().size() < getPageSize());
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

    public List<T> getData() {
        return mData;
    }

    public boolean isLoadMoreEnable() {
        return true;
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

    public int getPageSize() {
        return PAGE_SIZE;
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

    public LoadMoreView getLoadMoreView() {
        return new CustomLoadMoreView();
    }

    @Override
    public void onLoadMoreRequested() {
        requestData(LoadStatus.LOAD_MORE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
