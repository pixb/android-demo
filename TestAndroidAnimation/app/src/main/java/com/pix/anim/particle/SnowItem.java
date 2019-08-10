package com.pix.anim.particle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;


public class SnowItem extends ParticleItem {

	private Paint paint = new Paint();
	private final int size = 36; // 长度在0-50像素
	/** 雪点 */
	private Rect point; // 雪点
	private Point speed; // 雪点x,y方向速度

	public SnowItem(float width, float height,float offsetX,float offsetY) {
		super(width, height,offsetX,offsetY);
		point = new Rect();
		speed = new Point();
		paint.setColor(0xffffffff);
		reset();
	}

	public void draw(Canvas canvas) {
		canvas.drawColor(0x000);
		//变长小于等于８绘制圆形
		if(point.width() <= 8){
			canvas.drawCircle(point.left + m_fOffsetX, point.top + m_fOffsetY, point.width() / 2, paint);
		}
		else{
			//绘制雪花形状
			drawSknow(canvas);
		}
	}

	int count = 0;

	public void move() {
		point.left += speed.x;
		point.top += speed.y;
		point.right = point.right + speed.x;
		point.bottom = point.bottom + speed.y;
		count++;
		if (count > 5) {
			count = 0;
			speed.x = rand.nextInt(size);
			speed.x = speed.x > speed.y ? speed.y : speed.x;
			speed.x = rand.nextBoolean() ? -speed.x : speed.x;
			speed.y += rand.nextBoolean() ? 1 : 0;
		}

		if (point.left < 0 || point.bottom > m_fHeight) {
			reset();
		}
	}
	
	/**
	 * 随机生成一个雪点
	 * TODO
	 * @return: void
	 */
	private void reset() {
		int x = rand.nextInt((int)m_fWidth);
		int y = rand.nextInt((int)m_fHeight);
		int w = rand.nextInt(size) % 9;
		int h = rand.nextInt(size);
		
		if(w > 8){
			//勾３股４弦５（宽是４的倍数，高是３的倍数）
			int mod = w % 4;
			w += mod;
			int mul = w / 4;//倍数
			h = 3 * mul;
			point.left = x;
			point.top = y;
			point.right = x + w;
			point.bottom = y + h;
			
		}
		else{
			point.left = x;
			point.top = y;
			point.right = x + w;
			point.bottom = y + w;
		}

		int speedX = rand.nextInt(size);
		int speedY = rand.nextInt(size);
		/*
		 * int speedX = w; int speedY = h;
		 */

		speedX = speedX == 0 ? 1 : speedX;
		speedY = speedY == 0 ? 1 : speedY;
		speedX = speedX > speedY ? speedY : speedX;

		speed.x = speedX;
		speed.y = speedY;

	}

	private void drawSknow(Canvas canvas){
		int w = point.width();
		int h = point.height();
		int mul = w / 4;//倍数
		float xie = 5 * mul / 2;
		float centerY = point.top + h / 2;
		float centerX =point.left + w / 2;
		
		canvas.drawLine(point.left, point.top, point.right, point.bottom, paint);
		canvas.drawLine(point.left, point.bottom, point.right, point.top, paint);
		canvas.drawLine(centerX, centerY - xie, centerX, centerY + xie, paint);
		
	}
	
	public void printPosition() {
		Log.d("SknowPoint", "x : " + point.left + " y : " + point.top + " r : "
				+ point.right + " b : " + point.bottom);
	}

}
