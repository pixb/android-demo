package com.pix.anim.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.List;

public class AnimFramesSprite extends AnimNode {
	
	private List<Bitmap> mFrames;

	public AnimFramesSprite() {}
	public AnimFramesSprite(List<Bitmap> frames) {
		init(frames);
	}
	/**
	 * 初始化方法
	 * @param frames
	 * @return
	 */
	public boolean init(List<Bitmap> frames) {
		if (frames == null || frames.size() == 0) {
			return false;
		}
		mFrames = frames;
		mAlpha = 255;
		mX = 0f;
		mY  = 0f;
		mOffsetX = 0f;
		mOffsetY = 0f;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		if(canvas == null) {
			return ;
		}
	}
	
	public void draw(Canvas canvas,int index) {
		if(canvas == null) {
			return ;
		}
		mPaint.setAlpha(mAlpha);
		Matrix mx = new Matrix();
		mx.postTranslate(mX + mOffsetX, mY + mOffsetY);
		if(mFrames != null && mFrames.size() > index && !mFrames.get(index).isRecycled()) {
			canvas.drawBitmap(mFrames.get(index),mx, mPaint);
		}
	}

	@Override
	public void destroy() {
		if(mFrames!=null) {
			for (Bitmap bm : mFrames) {
				if(bm!=null) {
					bm.recycle();
				}
			}
			mFrames.clear();
			mFrames = null;
		}

	}
	/**
	 * 取得精灵的宽度，也就是bitmap的宽度，当bitmap为null，返回0
	 * @return
	 */
	public float getWidth() {
		if(mFrames == null || mFrames.size() == 0) {
			return 0f;
		}
		return mFrames.get(0).getWidth();
	}
	
	/**
	 * 取得精灵的高度，也就是bitmap的高度，当bitmap为null，返回0
	 * @return
	 */
	public float getHeight() {
		if(mFrames == null || mFrames.size() == 0) {
			return 0f;
		}
		return mFrames.get(0).getHeight();
	}
}
