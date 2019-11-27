package com.sixteen_night.demo.mvp.model;

import com.sixteen_night.demo.bean.TextBean;
import com.sixteen_night.demo.mvp.listener.BaseListener;

import java.util.ArrayList;
import java.util.List;

public class FirstTestModelImpl implements BaseModel<TextBean, BaseListener<List<TextBean>>> {
    private List<TextBean> mLocalData = new ArrayList<>();

    @Override
    public void saveData(List<TextBean> data) {
        mLocalData.addAll(data);
    }

    @Override
    public void loadDataFromCache(BaseListener<List<TextBean>> listBaseListener) {
        listBaseListener.onComplete(mLocalData);
    }

}
