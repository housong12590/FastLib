package com.ws.fast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class TestAdapter extends BaseQuickAdapter<DraftResult.ListBean, BaseViewHolder> {

    public TestAdapter() {
        super(R.layout.item_test_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DraftResult.ListBean item) {
        helper.setText(R.id.text, item.getTitle());
    }
}
