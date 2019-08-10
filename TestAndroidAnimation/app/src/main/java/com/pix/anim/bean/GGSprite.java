package com.pix.anim.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * ***********************************************************************************
* Module Name: GG动画精灵类</br>
* File Name: <b>GGSprite.java</b></br>
* Description: 用来抽象在动画中的精灵</br>
* Author: TPX</br>
* 版权 2008-2015，金华长风信息技术有限公司</br>
* 所有版权保护
* 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
**************************************************************************************
 */
public class GGSprite {

	private Bitmap mBitmap = null;
	private Vec2 mPos = new Vec2(0, 0);
	private Vec2 mScale= new Vec2(1,1);

	public GGSprite() {
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void setPosition(Vec2 vec) {
		this.mPos = vec;
	}

	public Vec2 getPostion() {
		return mPos;
	}
	
	public void setScale(Vec2 scale) {
		this.mScale = scale ;
	}
	
	public Vec2 getScale() {
		return mScale ;
	}
	
	public float getWidth(){
		if(mBitmap == null){
			return -1;
		}
		return mBitmap.getWidth();
	}
	public float getHeight(){
		if(mBitmap == null){
			return -1;
		}
		return mBitmap.getHeight();
	}
	
	public void draw(Canvas canvas) {
		if(mBitmap == null)	{
			return ;
		}
		if(mPos == null) {
			return ;
		}
		float fixX = mPos.x / mScale.x;
		float fixY = mPos.y / mScale.y;
		
		canvas.save();
		canvas.scale(mScale.x, mScale.y);
		canvas.drawBitmap(mBitmap, fixX, fixY, null);
		canvas.restore();
	}
}
