package com.hstmpl.fast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class TestAdapter extends BaseQuickAdapter<BgmResult.ListBean, BaseViewHolder> {

    public TestAdapter() {
        super(R.layout.item_test_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, BgmResult.ListBean item) {
        helper.setText(R.id.text, item.getBgm_name());
    }
}
