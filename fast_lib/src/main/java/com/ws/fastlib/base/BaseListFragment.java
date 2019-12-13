package com.ws.fastlib.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ws.fastlib.LoadStatus;
import com.ws.fastlib.R;
import com.ws.fastlib.network.observer.DefaultObserver;
import com.ws.fastlib.utils.ColorUtils;
import com.ws.fastlib.widget.CustomLoadMoreView;
import com.ws.fastlib.widget.LoadingStateView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.disposables.Disposable;

public class BaseListFragment<T> extends DelayFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    private LoadingStateView mLoadingStateView;
    private Disposable mDisposable;
    private int mCurrentPage = 1;
    private int mTotalPage = Integer.MAX_VALUE;
    private List<T> mData = new ArrayList<>();
    private ListPage<T> mView;

    public static <T> BaseFragment newInstance(ListPage<T> view) {
        return new BaseListFragment<>(view);
    }

    private BaseListFragment(ListPage<T> view) {
        this.mView = view;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_list_layout;
    }

    @Override
    public void initView() {
        mRecyclerView = mContentView.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = mContentView.findViewById(R.id.swipeRefreshLayout);

        mLoadingStateView = new LoadingStateView(mSwipeRefreshLayout);
        mLoadingStateView.setOnErrorClickListener(R.id.bt_retry, v -> requestData(LoadStatus.REFRESH));
        mSwipeRefreshLayout.setColorSchemeColors(ColorUtils.getRandColors(3));
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager = mView.getLayoutManager();
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }

        RecyclerView.ItemDecoration itemDecoration = mView.getItemDecoration();
        if (itemDecoration != null) {
            mRecyclerView.addItemDecoration(itemDecoration);
        }

        RecyclerView.OnItemTouchListener onItemTouchListener = mView.onItemTouchListener();
        if (onItemTouchListener != null) {
            mRecyclerView.addOnItemTouchListener(onItemTouchListener);
        }
        mAdapter = mView.getAdapter();
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
        if (mView.isLoadMoreEnable()) {
            mAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void requestData(LoadStatus status) {
        this.mCurrentPage = status == LoadStatus.MORE ? ++mCurrentPage : 1;
        if (status == LoadStatus.LOADING) {
            mLoadingStateView.showLoadingView();
        }
        mView.requestApi(status, String.valueOf(mCurrentPage)).subscribe(new DefaultObserver<List<T>>() {

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                mDisposable = d;
            }

            @Override
            public void onSuccess(List<T> ts) {
                if (status == LoadStatus.LOADING) {
                    mData.clear();
                    mLoadingStateView.showContentView();
                    mAdapter.addData(ts);
                    mAdapter.disableLoadMoreIfNotFullPage();
                } else if (status == LoadStatus.REFRESH) {
                    mData.clear();
                    mAdapter.addData(ts);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mLoadingStateView.showContentView();
                    if (mView.isLoadMoreEnable()) {
                        mAdapter.loadMoreComplete();
                    }
                } else if (status == LoadStatus.MORE) {
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
                    mLoadingStateView.showErrorView();//失败重试
                } else if (status == LoadStatus.REFRESH) {
                    mSwipeRefreshLayout.setRefreshing(false); //下拉刷新完成
                } else if (status == LoadStatus.MORE) {
                    mAdapter.loadMoreFail(); // 加载更多失败
                }
            }
        });
    }

    @Override
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

    public int setEmptyIcon() {
        return R.drawable.ic_placeholder;
    }

    @Override
    public void onRefresh() {
        requestData(LoadStatus.REFRESH);
    }

    @Override
    public void onLoadMoreRequested() {
        requestData(LoadStatus.MORE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
