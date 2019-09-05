package com.sixteen_night.demo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rc;
    TextView susView;
    private String curType;
    private List<TextBean> mCurReqList = new ArrayList<>();
    private List<TextBean> mDataList = new ArrayList<>();
    private TestAdapter mAdapter;
    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews(savedInstanceState);
    }

    private void initViews(Bundle savedInstanceState) {
        rc = findViewById(R.id.rc);
        susView = findViewById(R.id.sus_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        if (rc != null) {
            rc.setLayoutManager(linearLayoutManager);
            mAdapter = new TestAdapter();
            mAdapter.bindToRecyclerView(rc);
            mAdapter.isFirstOnly(true);
            rc.addOnScrollListener(new RecyclerView.OnScrollListener() {
                //悬浮View高
                int mSuspensionHeight;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    mSuspensionHeight = susView.getHeight();
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (mDataList.size() == 0) return;
                    //获取最顶部的item
                    View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                    //获取到的view顶部坐标小于悬浮条最底部的y轴坐标就是要重叠了
                    //datas.get(mCurrentPosition+1).isTo 表示下个item存在悬浮条
                    if (view != null && view.getTop() <= mSuspensionHeight && mDataList.get(mCurrentPosition + 1).isShowtitle()) {
                        susView.setY(-(mSuspensionHeight - view.getTop()));
                    } else {
                        susView.setY(0);
                    }
                    //mCurrentPosition 不是 当前显示最顶部的position
                    if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                        mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                        susView.setText(mDataList.get(mCurrentPosition).getDate().substring(0, 7));
                        susView.setY(0);
                    }
                }
            });
            initData();
        }
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mDataList.add(new TextBean("2019-09"));
        }
        for (int i = 0; i < 20; i++) {
            mDataList.add(new TextBean("2019-08"));
        }
        for (int i = 0; i < 20; i++) {
            mDataList.add(new TextBean("2019-07"));
        }
        for (int i = 0; i < 20; i++) {
            mDataList.add(new TextBean("2019-06"));
        }
        susView.setText(mDataList.get(0).getDate());
        mAdapter.setNewData(reData(mDataList));
    }

    /**
     * 递归筛选List
     * *&&……%……&……%&%&……%
     */
    private List<TextBean> reData(List<TextBean> cw) {
        List<TextBean> otherList = new ArrayList<>();
        boolean isFirst = true;
        for (int i = 0; i < cw.size(); i++) {
            if (!cw.get(i).getDate().substring(0, 7).equals(curType) && isFirst) {
                curType = cw.get(i).getDate().substring(0, 7);
                cw.get(i).setShowtitle(true);
                isFirst = false;
            }
            if (cw.get(i).getDate().substring(0, 7).equals(curType) && !isFirst) {
                mCurReqList.add(cw.get(i));
            } else {
                otherList.add(cw.get(i));
            }
        }
        if (mCurReqList.size() != mDataList.size()) {
            return reData(otherList);
        } else {
            return mCurReqList;
        }
    }
}
