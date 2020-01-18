package com.ws.fast;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ws.fastlib.common.LoadStatus;
import com.ws.fastlib.base.BaseActivity;
import com.ws.fastlib.base.BaseFragment;
import com.ws.fastlib.base.BaseListFragment;
import com.ws.fastlib.base.ListPage;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;

public class TestActivity extends BaseActivity  implements ListPage<Object> {


    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        BaseFragment fragment = BaseListFragment.newInstance(this);


    }

    @Override
    public void initData() {

    }

    @Override
    public BaseQuickAdapter<Object, BaseViewHolder> getAdapter() {
        return null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public Single<List<Object>> requestApi(LoadStatus status, String currPage) {
        return null;
    }

    @Override
    public boolean isLoadMoreEnable() {
        return false;
    }

    @Override
    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return null;
    }

    @Override
    public void setTotalPage(int totalPage) {

    }

    @Override
    public View setupEmptyView() {
        return null;
    }

    @Override
    public String setEmptyContent() {
        return null;
    }

    @Override
    public int setEmptyIcon() {
        return 0;
    }
}
