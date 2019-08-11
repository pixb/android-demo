package com.pix.testrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mContentRV;
    private MainAdapter mContentListAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // 1、取得RecyclerView的对象
        mContentRV = (RecyclerView) findViewById(R.id.rv_content);
        // 2、设置布局管理器
        mContentRV.setLayoutManager(new LinearLayoutManager(this));
        // 3、设置adapter
        mContentListAdapter = new MainAdapter();
        mContentRV.setAdapter(mContentListAdapter);
        // 4、设置Item增加移除动画
        mContentRV.setItemAnimator(new DefaultItemAnimator());

        initData();

    }


    protected void initData()
    {
        mDatas = new ArrayList<String>();
        mDatas.add("DecorationDemoOneActivity");
        mDatas.add("叠加效果");
        if(mContentListAdapter != null) {
            mContentListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * RecyclerView的数据适配器
     */
    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> implements View.OnClickListener{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.item_layout_main_list, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            if(tv.getText().equals("DecorationDemoOneActivity")) {
                startActivity(new Intent(MainActivity.this,DecorationDemoOneActivity.class));
            }
            if(tv.getText().equals("叠加效果")) {
                startActivity(new Intent(MainActivity.this,OverlayHeadActivity.class));
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
                tv.setOnClickListener(MainAdapter.this);
            }
        }
    }
}
