package com.ws.fast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ws.fast.databinding.ActivityMainBinding;
import com.ws.fastlib.base.BaseActivity;
import com.ws.fastlib.base.BaseFragment;
import com.ws.fastlib.base.BaseListFragment;
import com.ws.fastlib.common.LoadStatus;
import com.ws.fastlib.network.HttpManager;
import com.ws.fastlib.network.Transformer;

import java.util.List;

import io.reactivex.Single;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(ActivityMainBinding binding) {
        BaseFragment.attachToContainer(mContext, R.id.contentLayout, new DraftFragment());
    }

    @Override
    public void initData() {

    }

    public static class DraftFragment extends BaseListFragment<BgmResult.ListBean> {

        @Override
        public BaseQuickAdapter<BgmResult.ListBean, BaseViewHolder> getAdapter() {
            return new TestAdapter();
        }

        @Override
        public Single<List<BgmResult.ListBean>> requestApi(LoadStatus status, int currPage) {
            return HttpManager.getService(Api.class).getBgmList(currPage, 20)
                    .compose(Transformer.resultFunc())
                    .doOnSuccess(bgmResult -> setTotalPage(bgmResult.getTotal_page()))
                    .map(BgmResult::getList);
        }

        @Override
        public boolean isLoadMoreEnable() {
            return true;
        }
    }

}
