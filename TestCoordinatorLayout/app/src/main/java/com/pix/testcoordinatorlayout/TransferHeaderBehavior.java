package com.pix.testcoordinatorlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;


public class TransferHeaderBehavior extends CoordinatorLayout.Behavior<TextView> {
    private static final String TAG = "TransferHeaderBehavior ";

    /**
     * 处于中心时候原始X轴
     */
    private int mOriginalHeaderX = 0;
    /**
     * 处于中心时候原始Y轴
     */
    private int mOriginalHeaderY = 0;


    public TransferHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof CollapsingToolbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
//        LogUtils.d(TAG,"child:" + child + ",dependency:" + dependency);

        Log.d(TAG,"dependency,width:" + dependency.getWidth() + ",dependency.height:" + dependency.getHeight());
        Log.d(TAG,"denpendency.x:" + dependency.getX() + ",denpendency.y:" + dependency.getY() );
        Log.d(TAG,"child.width:" + child.getWidth() + ",child.height" + child.getHeight());
        Log.d(TAG,"child.x:" + child.getX() + ",child.y:" + child.getY());


        if(mOriginalHeaderX == 0) {

        }
        return true;
    }
}