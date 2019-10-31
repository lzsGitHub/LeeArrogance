package com.sixteen_night.demo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sixteen_night.demo.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    RecyclerView rc;
    private String[] indexStr = {"recycleView+悬浮栏", "弹性滑动布局", "AsyncTask实践", "OpenCv抠图"};
    private Class[] indexClass = {SusActivity.class, TestActivity.class, TaskActivity.class, OpenCVTestActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        rc = findViewById(R.id.rc);
        rc.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(rc);
        mAdapter.setNewData(Arrays.asList(indexStr));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(MainActivity.this, indexClass[position]));
            }
        });
    }

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_item_index) {
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.text, item);
        }
    };
}
