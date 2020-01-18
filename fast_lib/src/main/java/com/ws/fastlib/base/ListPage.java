package com.ws.fastlib.base;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ws.fastlib.common.LoadStatus;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;

public interface ListPage<T> {

    BaseQuickAdapter<T, BaseViewHolder> getAdapter();

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.ItemDecoration getItemDecoration();

    Single<List<T>> requestApi(LoadStatus status, String currPage);

    boolean isLoadMoreEnable();

    RecyclerView.OnItemTouchListener onItemTouchListener();

    void setTotalPage(int totalPage);

    View setupEmptyView();

    String setEmptyContent();

    int setEmptyIcon();
}
