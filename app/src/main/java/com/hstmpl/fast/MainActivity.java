package com.hstmpl.fast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hstmpl.fast.databinding.ActivityMainBinding;
import com.hstmpl.base.BaseActivity;
import com.hstmpl.base.BaseFragment;
import com.hstmpl.base.BaseListFragment;
import com.hstmpl.net.LoadStatus;
import com.hstmpl.net.HttpManager;
import com.hstmpl.net.Transformer;

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
