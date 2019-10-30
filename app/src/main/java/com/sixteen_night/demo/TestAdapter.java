package com.sixteen_night.demo;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sixteen_night.demo.bean.TextBean;

/**
 * Created by Lee on 2019/9/5 10:43.
 */
public class TestAdapter extends BaseQuickAdapter<TextBean, BaseViewHolder> {

    public TestAdapter() {
        super(R.layout.item_susbar);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextBean item) {
        if (!TextUtils.isEmpty(item.getDate()) && item.getDate().length() >= 7) {
            helper.setGone(R.id.sus_view, item.isShowtitle());
            helper.setText(R.id.sus_view, item.getDate().substring(0, 7));
            helper.addOnClickListener(R.id.item);
        }
    }

}
