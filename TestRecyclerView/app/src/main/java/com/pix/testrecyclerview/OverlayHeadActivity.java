package com.pix.testrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 用RecyclerView实现一个叠加的头像效果
 * 参考图:res/1.png
 * 参考地址：http://www.jianshu.com/p/7ddb265f6250
 */
public class OverlayHeadActivity extends AppCompatActivity {
    private static final String TAG = "OverlayHeadActivity";
    private RecyclerView mRCV;
    private LinearLayoutManager mLayoutManager;
    private List<String> imgs;
    private static final String IMG_URL = "http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=f0c5c08030d3d539c16807c70fb7c566/8ad4b31c8701a18bbef9f231982f07082838feba.jpg";

    private OverlayHeadAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay_head);
        mRCV = (RecyclerView) findViewById(R.id.rcv_overlay_head);
        mRCV.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = -30;
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRCV.setLayoutManager(mLayoutManager);
        initData();
    }

    private void initData() {
        imgs = new ArrayList<>();
        for (int i =  0 ;i < 5;i++) {
            imgs.add(IMG_URL);
        }
        mAdapter = new OverlayHeadAdapter(imgs,this);
        mRCV.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    class OverlayHeadAdapter extends RecyclerView.Adapter<OverlayHeadAdapter.OHViewHolder>{
        private List<String> imgList;
        private Context mContext;
        RoundingParams roundingParams;
        public OverlayHeadAdapter(List<String> imgs,Context ctx) {
            imgList = imgs;
            mContext = ctx;
            GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(getResources());
            roundingParams = RoundingParams.fromCornersRadius(7f);
            roundingParams.setRoundAsCircle(true);
            // 或用 fromCornersRadii 以及 asCircle 方法
            builder.setRoundingParams(roundingParams);

        }

        @Override
        public OHViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_overlay_head,parent,false);
            View v = new LinearLayout(parent.getContext());
            return new OHViewHolder(v);
        }

        @Override
        public void onBindViewHolder(OHViewHolder holder, int position) {
            Log.d(TAG,"onBindViewHolder(),position:" + position);
//            holder.layout = (LinearLayout) holder.itemView.findViewById(R.id.layout_head);
            SimpleDraweeView sdv = new SimpleDraweeView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150,150);
            sdv.setLayoutParams(lp);
            sdv.setImageURI(imgList.get(position));
            sdv.getHierarchy().setRoundingParams(roundingParams);
            holder.layout.setOrientation(LinearLayout.HORIZONTAL);
            holder.layout.addView(sdv);
        }

        @Override
        public int getItemCount() {
            return imgs.size();
        }

        class OHViewHolder extends RecyclerView.ViewHolder {
            private View itemView;
            private LinearLayout layout;

            public OHViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                this.layout = (LinearLayout) itemView;
            }
        }
    }
}

