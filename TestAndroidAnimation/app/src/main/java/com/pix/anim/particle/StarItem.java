package com.pix.anim.particle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;


/**
 ************************************************************************************
* Module Name: 星星粒子的一个元素描述</br>
* File Name: <b>StarItem.java</b></br>
* Description: 描述一个星星粒子</br>
* Author: TPX</br>
* 版权 2008-2015，金华长风信息技术有限公司</br>
* 所有版权保护
* 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
**************************************************************************************
 */
public class StarItem extends ParticleItem {

	private final int NORMAL = 0;
	private final int LIGHT = 1;
	private final int METEOR = 2;
	private int state = NORMAL;

	private Paint paint = new Paint();
	private final int size = 10; // 长度在0-size像素
	private int radius;
	private Point point; // 星星

	private int light = 100;// 闪烁
	private int meteor = 10000;// 流星
	
	//星星闪烁类型
	private final int LIGHT_FULL = 0;
	private final int LIGHT_HALF = 1;
	private final int LIGHT_HALF_ALPHA = 2;
	private int lightState = 0;
	private int lightAlpha = 80;
	
	//流星移动值
	private int meteorSpeedX;
	private int meteorSpeedY;
	private int meteorState = 0;
	private int meteorAlpha = 255;
	private int meteorStep;
	

	public StarItem(float width, float height,float offsetX,float offsetY) {
		super(width, height,offsetX,offsetY);
		point = new Point();
		paint.setColor(0xffffffff);
		reset();
	}

	public void draw(Canvas canvas) {
		// 变长小于等于８绘制圆形
		switch (state) {
		case NORMAL:
			canvas.drawCircle(point.x + m_fOffsetX, point.y + m_fOffsetY, radius / 2, paint);
			break;
		case LIGHT:
			canvas.drawCircle(point.x + m_fOffsetX, point.y + m_fOffsetY, radius / 2, paint);
			drawLightStar(canvas);
			break;
		case METEOR:
			drawMeteor(canvas);
			break;
		}
	}

	public void move() {

		switch (state) {
		case NORMAL:
			while (point.x < 0 || point.x > m_fWidth || point.y > m_fHeight) {
				reset();
			}
			int mod = rand.nextInt(light + 1) % light;
			if (mod == 0) {
				// 闪烁
				state = LIGHT;
				lightState = rand.nextInt(10) % 3;
				return;
			}
			mod = rand.nextInt(meteor + 1) % meteor;
			if (mod == 0) {
				// 流星
				state = METEOR;
				meteorSpeedY = 1 + rand.nextInt((int)m_fHeight / 20);
				meteorSpeedX = rand.nextInt((int)m_fWidth / 20);
				meteorSpeedX *= rand.nextBoolean() ? 1 : -1;
				meteorStep = 1;
				return;
			}
			break;
		case LIGHT:
			lightAlpha -= 20;
			if(lightAlpha < 0){
				state = NORMAL;
				lightAlpha = 80;
			}
			break;
		case METEOR:
			meteorAlpha -= 20;
			if(meteorAlpha < 0){
				state = NORMAL;
				meteorAlpha = 255;
				meteorStep = 1;
				return;
			}
			meteorState = rand.nextInt(10) % 3;
			meteorStep ++;
			break;
		}

	}

	private void reset() {
		point.x = rand.nextInt((int)m_fWidth);
		point.y = rand.nextInt((int)m_fHeight / 2);
		radius = rand.nextInt(size);
	}

	private void drawLightStar(Canvas canvas) {
		
		switch(lightState){
		case LIGHT_HALF:
			//左右交叉
			canvas.drawLine(point.x + m_fOffsetX - radius, point.y  + m_fOffsetY- radius, point.x + radius + m_fOffsetX, point.y + radius + m_fOffsetY, paint);
			canvas.drawLine(point.x - radius + m_fOffsetX, point.y + radius + m_fOffsetY, point.x + radius + m_fOffsetX, point.y - radius + m_fOffsetY, paint);
			break;
		case LIGHT_FULL:
			paint.setAlpha(255 - lightAlpha);
			//绘制横竖向
			canvas.drawLine(point.x - 2 * radius + m_fOffsetX, point.y + m_fOffsetY, point.x + 2 * radius + m_fOffsetX, point.y + m_fOffsetY, paint);
			canvas.drawLine(point.x + m_fOffsetX, point.y - 2 * radius + m_fOffsetY, point.x + m_fOffsetX, point.y + 2 * radius + m_fOffsetY, paint);
		case LIGHT_HALF_ALPHA:
			paint.setAlpha(lightAlpha);
			//左右交叉
			canvas.drawLine(point.x - radius + m_fOffsetX, point.y - radius + m_fOffsetY, point.x + radius + m_fOffsetX, point.y + radius + m_fOffsetY, paint);
			canvas.drawLine(point.x - radius + m_fOffsetX, point.y + radius + m_fOffsetY, point.x + radius + m_fOffsetX, point.y - radius + m_fOffsetY, paint);
			paint.setAlpha(255);
			break;
		}
		
	}
	
	private void drawMeteor(Canvas canvas){
		if(true) return ;
		
		int trimX = meteorStep * meteorSpeedX;
		int trimY = meteorStep * meteorSpeedY;
		paint.setAlpha(lightAlpha);
		//绘制流行轨迹
		canvas.drawLine(point.x + m_fOffsetX, point.y + m_fOffsetY, trimX + point.x + m_fOffsetX, trimY + point.y + m_fOffsetY, paint);
		paint.setAlpha(255);
		canvas.drawCircle(trimX + point.x + m_fOffsetX, trimY + point.y + m_fOffsetY, radius / 2, paint);
		switch(meteorState){
		case LIGHT_HALF:
			//左右交叉
			canvas.drawLine(trimX + point.x - radius + m_fOffsetX, trimY + point.y - radius + m_fOffsetX, trimX + point.x + radius + m_fOffsetX, trimY + point.y + radius + m_fOffsetY,  paint);
			canvas.drawLine(trimX + point.x - radius + m_fOffsetX, trimY + point.y + radius + m_fOffsetY, trimX + point.x + radius + m_fOffsetX, trimY + point.y - radius + m_fOffsetY, paint);
			break;
		case LIGHT_FULL:
			paint.setAlpha(255 - lightAlpha);
			//绘制横竖向
			canvas.drawLine(trimX + point.x - 2 * radius, trimY + point.y, trimX + point.x + 2 * radius, trimY + point.y, paint);
			canvas.drawLine(trimX + point.x, trimY + point.y - 2 * radius, trimX + point.x, trimY + point.y + 2 * radius, paint);
		case LIGHT_HALF_ALPHA:
			paint.setAlpha(lightAlpha);
			//左右交叉
			canvas.drawLine(trimX + point.x - radius + m_fOffsetX, trimY + point.y - radius + m_fOffsetY, trimX + point.x + radius + m_fOffsetX, trimY + point.y + radius + m_fOffsetY, paint);
			canvas.drawLine(trimX + point.x - radius + m_fOffsetX, trimY + point.y + radius + m_fOffsetY, trimX + point.x + radius + m_fOffsetX, trimY + point.y - radius + m_fOffsetY, paint);
			paint.setAlpha(255);
			break;
		}
	}

}
