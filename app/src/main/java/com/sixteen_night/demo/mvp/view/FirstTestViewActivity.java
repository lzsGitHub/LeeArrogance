package com.sixteen_night.demo.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sixteen_night.demo.R;
import com.sixteen_night.demo.TestAdapter;
import com.sixteen_night.demo.bean.TextBean;
import com.sixteen_night.demo.mvp.listener.FirstTestViewInterface;
import com.sixteen_night.demo.mvp.presenter.FirstTestPresenter;

import java.util.List;

import butterknife.ButterKnife;

public class FirstTestViewActivity extends AppCompatActivity implements FirstTestViewInterface {
    private RecyclerView mRc;

    private TestAdapter mTestAdapter;
    private FirstTestPresenter mFirstTestPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_test_view);
        ButterKnife.bind(this);
        initViews();
        mFirstTestPresenter = new FirstTestPresenter(this);
        mFirstTestPresenter.fetchData();
    }

    private void initViews() {
        mRc = findViewById(R.id.rc);
        mRc.setLayoutManager(new LinearLayoutManager(this));
        mTestAdapter = new TestAdapter();
        mTestAdapter.bindToRecyclerView(mRc);
    }

    @Override
    public void updateUIForData(List<TextBean> data) {
        mTestAdapter.addData(data);
    }
}
