package com.pix.testscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 *  Scroller实现滑动的测试
 */

public class ScrollerView extends View {
    private int mLastX;
    private int mLastY;
    private Scroller mScroller;
    private Paint mPaint;
    public ScrollerView(Context context) {
        super(context);
        init(context);
    }

    public ScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context ctx) {
        mScroller = new Scroller(ctx);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,300,300,mPaint);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(30);
        canvas.drawText("ScrollerView",50,200,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获取点击的位置
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
                //实现View跟随手指移动的效果
                ((View)getParent()).scrollBy(-offsetX, -offsetY);
                //重新设置初始坐标
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时执行滑动过程
                View view = (View) getParent();
                mScroller.startScroll(view.getScrollX(), view.getScrollY(),
                        view.getScrollX(), view.getScrollY(), 5000);
                //调用重绘来间接调用computeScroll()方法
                invalidate();
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断滑动过程是否完成
        if (mScroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通过重绘来不断调用computeScroll()方法
            invalidate();
        }
    }
}
