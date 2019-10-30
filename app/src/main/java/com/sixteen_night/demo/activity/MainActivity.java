package com.sixteen_night.demo.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.sixteen_night.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rc)
    RecyclerView rc;
    private String[] indexStr = {"recycleView+悬浮栏", "弹性滑动布局", "AsyncTask实践"};
    private Class[] indexClass = {SusActivity.class, TestActivity.class, TaskActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

}
