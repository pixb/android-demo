package com.pix.anim.bean;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.pix.anim.utils.AnimBitmapLoader;

/**
 * 动画精灵对象
 * 
 * @author tpx
 * 
 */
public class AnimSprite extends AnimNode{

	private Bitmap mBitmap;
	
	public  AnimSprite() {
	}
	
	/**
	 * 初始化方法
	 * @param path
	 * @param ctx
	 * @param opt
	 * @return
	 */
	public boolean init(String path, Context ctx, float opt) {
		if (path == null) {
			return false;
		}
		mBitmap = AnimBitmapLoader.getInstance().getBitmap(ctx, path, opt);
		
		if(mBitmap == null) {
			return false;
		}
		mAlpha = 255;
		mX = 0f;
		mY  = 0f;
		mOffsetX = 0f;
		mOffsetY = 0f;
		mScaleX = 1f;
		mScaleY = 1f;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		return true;
	}
	
	/**
	 * 初始化方法
	 * @param bmp
	 * @return
	 */
	public boolean init(Bitmap bmp) {
		if (bmp == null) {
			return false;
		}
		mBitmap = bmp;
		mAlpha = 255;
		mX = 0f;
		mY  = 0f;
		mOffsetX = 0f;
		mOffsetY = 0f;
		mScaleX = 1f;
		mScaleY = 1f;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		return true;
	}

	/**
	 * 绘制的函数，用来绘制精灵
	 * 
	 * @param canvas
	 */
	@Override
	public void draw(Canvas canvas) {
		if(canvas == null) {
			return ;
		}
		mPaint.setAlpha(mAlpha);
		canvas.save();
		if(mScalePX == -10000f) {
			mScalePX = mX + mBitmap.getWidth() / 2;
		}
		if(mScalePY == -10000f) {
			mScalePY = mY  + mBitmap.getHeight() / 2;
		}
		canvas.scale(mScaleX, mScaleY, mScalePX, mScalePY);
		canvas.translate(mX + mOffsetX, mY + mOffsetY);
		if(mBitmap != null && !mBitmap.isRecycled()) {
			canvas.drawBitmap(mBitmap, 0, 0, mPaint);
		}
		canvas.restore();
	}
	
	/**
	 * 使用这个方法请注意，构建这个精灵时的Bitmap是可回收的
	 */
	@Override
	public void destroy() {
		if(mBitmap!=null) {
			mBitmap.recycle();
			mBitmap = null;
		}
	}
	/**
	 * 取得精灵的宽度，也就是bitmap的宽度，当bitmap为null，返回0
	 * @return
	 */
	public float getWidth() {
		if(mBitmap == null) {
			return 0f;
		}
		return mBitmap.getWidth();
	}
	
	/**
	 * 取得精灵的高度，也就是bitmap的高度，当bitmap为null，返回0
	 * @return
	 */
	public float getHeight() {
		if(mBitmap == null) {
			return 0f;
		}
		return mBitmap.getHeight();
	}
	
	
	
	
}
