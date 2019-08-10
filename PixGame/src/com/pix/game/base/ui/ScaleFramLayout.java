package com.pix.game.base.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * 带标尺的FrameLayout
 * @author pix
 *
 */
public class ScaleFramLayout extends FrameLayout {
	private static final String TAG = "ScaleFramLayout";
	Paint mPaint;
	public ScaleFramLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setStrokeWidth(1);
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.d(TAG, "ScaleFramLayout");
		drawScale(canvas);
	}
	
	private void drawScale(Canvas canvas) {
		canvas.save();
		//画横向的线
		for(int i = 0 ;i <= getHeight() / 100;i++) {
			canvas.drawLine(0, i * 100, getWidth(), i * 100, mPaint);
		}
		//画纵向的线
		for(int i = 0 ;i <= getWidth() / 100;i++) {
			canvas.drawLine(i * 100, 0,i * 100,getHeight(),mPaint);
		}
		canvas.restore();
	}
}
