package com.pix.testscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/2/7.
 */

public class OffsetScrollView extends View {
    private int mLastX;
    private int mLastY;
    private Paint mPaint;
    public OffsetScrollView(Context context) {
        super(context);
        init();
    }

    public OffsetScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OffsetScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,300,300,mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(30);
        canvas.drawText("OffsetScrollView",50,200,mPaint);
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
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);
                layout(getLeft() + offsetX,getTop() + offsetY ,getRight() + offsetX,getBottom() + offsetY);
                // 重新设置上次值
                mLastX = x;
                mLastY = y;
                break;
        }
        return true;
    }
}
