package com.pix.anim.particle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;


public class SnowScence extends ParticleScence {

	public SnowScence(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SnowScence(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SnowScence(Context context) {
		super(context,100);
	}

	@Override
	protected ParticleSet initScence(int itemNum) {
		int width = getWidth();
		int height = getHeight();
		
		if(width == 0){
			width = 800;
		}
		if(height == 0){
			height = 1000;
		}
		return new SnowSet(width, height,0,0,itemNum);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawRGB(0, 0, 0);
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
}