package com.pix.testscroll;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/2/7.
 */

public class LayoutParamsScrollView extends View {
    private Paint mPaint;
    private int mLastX;
    private int mLastY;

    public LayoutParamsScrollView(Context context) {
        super(context);
        init();
    }

    public LayoutParamsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LayoutParamsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) getLayoutParams();
                mlp.leftMargin = getLeft() + offsetX ;
                mlp.topMargin = getTop() + offsetY ;
                mLastX = x;
                mLastY = y;
                break;
        }
        return true;
    }
}
