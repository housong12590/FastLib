package com.ws.fast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ws.fastlib.LoadStatus;
import com.ws.fastlib.base.BaseListActivity;
import com.ws.fastlib.network.HttpManager;
import com.ws.fastlib.network.Transformer;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;

public class MainActivity extends BaseListActivity<DraftResult.ListBean> {

    @Override
    public BaseQuickAdapter<DraftResult.ListBean, BaseViewHolder> getAdapter() {
        return new TestAdapter();
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public Single<List<DraftResult.ListBean>> requestApi(LoadStatus status, String currNum) {
        return HttpManager.getService(Api.class).getDraftList("2000", currNum, "15")
                .compose(Transformer.resultFunc())
                .doOnSuccess(draftResult -> setTotalPage(draftResult.getTotal_page()))
                .map(DraftResult::getList);
    }

    @Override
    public boolean isLoadMoreEnable() {
        return true;
    }

    @Override
    public RecyclerView.OnItemTouchListener onItemTouchListener() {
        return null;
    }
}
