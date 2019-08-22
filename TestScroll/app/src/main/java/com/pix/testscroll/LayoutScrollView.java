package com.pix.testscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 *  layout()方法实现滑动
 */

public class LayoutScrollView extends View {
    private int mLastX;
    private int mLastY;
    private Paint mPaint;

    public LayoutScrollView(Context context) {
        super(context);
        init();
    }

    public LayoutScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LayoutScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,300,300,mPaint);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(30);
        canvas.drawText("LayoutScrollView",50,200,mPaint);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // 点击的坐标
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        int lastX = 0, lastY = 0;
//
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                // 记录按下的坐标
//                mLastX = x;
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                // 计算滑动偏移量
//                int offsetX = x - mLastX;
//                int offsetY = y - mLastY;
//                // 重新防止位置
//                layout(getLeft() + offsetX, getTop() + offsetY,
//                        getRight() + offsetX, getBottom() + offsetY);
//                break;
//        }
//        return true;
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                layout(getLeft() + offsetX, getTop() + offsetY,
                        getRight() + offsetX, getBottom() + offsetY);
                //重新设置初始坐标
                mLastX = x;
                mLastY = y;
                break;
        }
        return true;
    }
}
