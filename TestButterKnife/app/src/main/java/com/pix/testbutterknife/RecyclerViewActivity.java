package com.pix.testbutterknife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.rcv_bind)
    RecyclerView mBindRCV;


    private RecyclerView.LayoutManager mLayoutManager;
    private BindListAdapter mAdapter;
    private List<String> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this);
        mBindRCV.setLayoutManager(mLayoutManager);
        mAdapter = new BindListAdapter(mData);
        mBindRCV.setAdapter(mAdapter);
        initData();
    }

    public void initData() {
        for (int i = 0 ; i < 10 ;i++) {
            mData.add("第> " +i + "<个ITEM");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }
}
