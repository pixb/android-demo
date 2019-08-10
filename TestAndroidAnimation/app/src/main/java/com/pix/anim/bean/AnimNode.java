package com.pix.anim.bean;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 节点类
 * @author Administrator
 *
 */
public abstract class AnimNode {
	/** 绘制的画笔 */
	protected Paint mPaint;
	/** 精灵的透明度 */
	protected int mAlpha;
	/** 精灵的x坐标 */
	protected float mX;
	/** 精灵的Y坐标 */
	protected float mY;
	/** 精灵相对于设置位置的偏移量 */
	protected float mOffsetX;
	/** 精灵相对于设置位置的偏移量 */
	protected float mOffsetY;
	/** x方向缩放值 */
	protected float mScaleX = 1f;
	/** y方向缩放值 */
	protected float mScaleY = 1f;
	/** x方向的缩放坐标点 */
	protected float mScalePX = -10000f;
	/** y方向的缩放坐标点 */
	protected float mScalePY = -10000f;
	
	public abstract void draw(Canvas canvas);
	
	public abstract void destroy();
	
	/**
	 * 设置精灵的Alpha值
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		this.mAlpha = alpha;
	}
	
	/**
	 * 设置精灵x，y的位置，注意这里会把offset值重置为0
	 * @param x
	 * @param y
	 */
	public void setPostion(float x,float y) {
		this.mX = x;
		this.mY = y;
		this.mOffsetX = 0;
		this.mOffsetY = 0;
	}
	
	/**
	 * 设置精灵x坐标的位置，注意这里会把x偏移量置为0
	 * @param x
	 */
	public void setPostionX(float x) {
		this.mX = x;
		this.mOffsetX = 0;
	}
	
	/**
	 * 设置y点坐标的位置，注意这里会把y的偏移量置为0
	 * @param y
	 */
	public void setPostionY(float y) {
		this.mY = y;
		this.mOffsetY = 0;
	}
	
	/**
	 * 设置精灵的偏移量
	 * @param offsetX
	 * @param offsetY
	 */
	public void setOffset(float offsetX,float offsetY) {
		this.mOffsetX = offsetX;
		this.mOffsetY = offsetY;
	}
	
	/** 设置x坐标偏移量 */
	public void setOffsetX(float offsetX) {
		this.mOffsetX = offsetX;
	}
	/**
	 * 设置y坐标偏移量
	 * @param offsetY
	 */
	public void setOffsetY(float offsetY) {
		this.mOffsetY = offsetY;
	}
	/**
	 * 设置x,y的缩放，x = y = scale
	 * @param scale
	 */
	public void setScale(float scale) {
		this.mScaleX = scale;
		this.mScaleY = scale;
	}
	
	/**
	 * 设置x,y的缩放值
	 * @param scaleX
	 * @param scaleY
	 */
	public void setScale(float scaleX,float scaleY) {
		this.mScaleX = scaleX;
		this.mScaleY = scaleY;
	}
	/**
	 * 设置x方向的缩放值
	 * @param scaleX
	 */
	public void setScaleX(float scaleX) {
		this.mScaleX = scaleX;
	}
	/**
	 * 设置y方向的缩放值
	 * @param scaleY
	 */
	public void setScaleY(float scaleY) {
		this.mScaleY = scaleY;
	}
	
	/**
	 * 设置缩放参考点，默认图片中心
	 * @param x
	 * @param y
	 */
	public void setScalePoint(float x,float y) {
		this.mScalePX = x;
		this.mScalePY = y;
	}
}
