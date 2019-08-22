package com.pix.testmyview.viewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 *  垂直添加控件的ViewGroup
 *  参考地址：http://www.cnblogs.com/xyhuangjinfu/p/5435201.html
 */

public class VerticalOffsetLayout extends ViewGroup {
    private static final int OFFSET = 100;
    private Paint mPaint;

    public VerticalOffsetLayout(Context context) {
        super(context);
        init();
    }

    public VerticalOffsetLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalOffsetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(125);
    }

    /**
     * 测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 取出MeasureSpec的模式和值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        // 测量子View
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            // 根据父布局的MeasureSpec和padding值，还有LayoutParams值获取子View的MeasureSpec
            int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            //测量子View
            child.measure(childWidthSpec, childHeightSpec);
        }

        // 根据宽度模式，计算出适合的宽度
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    int widthAddOffset = i * OFFSET + child.getMeasuredWidth();
                    width = Math.max(width, widthAddOffset);
                }
                break;
            default:
                break;

        }

        // 根据高度模式，计算出适合的高度
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    height = height + child.getMeasuredHeight();
                }
                break;
            default:
                break;

        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 左边是算OFFSET的倍数
            left = i * OFFSET;
            // 右边是左边加上子View的宽度
            right = left + child.getMeasuredWidth();
            // 下面是上面加上子View的高度
            bottom = top + child.getMeasuredHeight();
            //放置子View
            child.layout(left, top, right, bottom);
            // 上面增长放置完的View的高度
            top += child.getMeasuredHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth()/2;
        int y = getHeight()/2;
        canvas.drawCircle(x, y, Math.min(x, y), mPaint);
    }
}
