package com.sixteen_night.demo.mvp.presenter;

import com.sixteen_night.demo.bean.TextBean;
import com.sixteen_night.demo.mvp.listener.FirstTestViewInterface;
import com.sixteen_night.demo.mvp.model.BaseModel;
import com.sixteen_night.demo.mvp.model.FirstTestModelImpl;

import java.util.ArrayList;
import java.util.List;

public class FirstTestPresenter {
    //代表了View的接口
    FirstTestViewInterface mFirstTestViewInterface;
    //操作Model的接口
    BaseModel mModel = new FirstTestModelImpl();
    //网络数据请求的实现 同model，暂放
    //----
    private List<TextBean> mTextBeanList = new ArrayList<>();

    public FirstTestPresenter(FirstTestViewInterface firstTestViewInterface) {
        mFirstTestViewInterface = firstTestViewInterface;
    }

    public void fetchData() {
        for (int i = 0; i < 20; i++) {
            int pos = i + 1;
            mTextBeanList.add(new TextBean("测试数据" + pos));
        }
        mModel.saveData(mTextBeanList);
        mFirstTestViewInterface.updateUIForData(mTextBeanList);
    }

    public void loadForCache() {
        mModel.loadDataFromCache(new FirstTestViewInterface() {
            @Override
            public void updateUIForData(List<TextBean> data) {
                mFirstTestViewInterface.updateUIForData(data);
            }
        });
    }
}
