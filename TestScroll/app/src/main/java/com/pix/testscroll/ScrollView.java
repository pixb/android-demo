package com.pix.testscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 使用scrollTo()或scrollBy()实现滑动，移动的是View的内容。
 */

public class ScrollView extends View {
    private Paint mPaint;
    private int mLastX;
    private int mLastY;
    public ScrollView(Context context) {
        super(context);
        init();
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0,0,300,300,mPaint);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(30);
        canvas.drawText("ScrollView",50,200,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                // 进行移动
                scrollBy(-offsetX,-offsetY);
                mLastX = x ;
                mLastY = y;
                break;
        }
        return true;
    }
}
