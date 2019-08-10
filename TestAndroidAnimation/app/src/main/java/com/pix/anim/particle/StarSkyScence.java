package com.pix.anim.particle;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;


/**
 * ***********************************************************************************
* Module Name: 星空的粒子场景View类</br>
* File Name: <b>StarSkyAnimation.java</b></br>
* Description: 本类是一个用来绘制一个星空夜景的View本类继承</br>
* Author: TPX</br>
* 版权 2008-2015，金华长风信息技术有限公司</br>
* 所有版权保护
* 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
**************************************************************************************
 */
public class StarSkyScence extends ParticleScence {
	
	public StarSkyScence(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public StarSkyScence(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StarSkyScence(Context context) {
		super(context,100);
	}
	
	@Override
	protected ParticleSet initScence(int itemNum) {
		int width = getWidth();
		int height = getHeight();
		
		if(width <= 0){
			width = 800;
		}
		if(height <= 0){
			height = 1000;
		}
		return new StarSkySet(width, height,0,0, itemNum);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawRGB(0, 0, 0);
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
	
}
