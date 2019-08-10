package com.pix.anim.project.guagua;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * 单个下落动画，下落方向，速度，透明度随机，参考
 * 
 * @author lixianfeng
 * 
 */
public class GiftDown {
	protected int width;
	protected int height;
	protected Random rand;
	private Paint paint = new Paint();

	private int speedNum = 20;
	private Point speed; // 玫瑰x,y方向速度
	private Point position; // 位置
	private Bitmap gift;
	private static int giftWidth;
	private static int giftHeight;

	// 动画的维度，缩放大小，选择角度，翻转角度，透明度
	private Matrix matrix;
	private int alpha = 0;
	private float scale = 1;// 缩放比例，
	private float tranX = 0;
	private float tranY = 0;

	private boolean isStop = false;

	public GiftDown(int width, int height, Bitmap gift) {
		this.width = width;
		this.height = height;
		rand = new Random();
		position = new Point();
		speed = new Point();
		matrix = new Matrix();
		this.gift = gift;
		giftWidth = gift.getWidth();
		giftHeight = gift.getHeight();
		reset();
	}

	public void draw(Canvas canvas) {

		if (gift != null) {
			paint.setAlpha(alpha);
			matrix.setTranslate(tranX, tranY);
			matrix.postScale(scale, scale);
			canvas.drawBitmap(gift, matrix, paint);
		}

	}

	private int count = 0;

	public void move() {

		if (position.y > height) {
			// reset();
			isStop = true;
			return;
		}

		position.x += speed.x;
		position.y += speed.y;
		count++;
		if (count > 10) {
			count = 0;
			speed.x = rand.nextInt(speedNum);
			speed.x = speed.x > speed.y ? speed.y : speed.x;
			speed.x = rand.nextBoolean() ? -speed.x : speed.x;
//			speed.y += rand.nextBoolean() ? 1 : 0;
			// alpha = rand.nextInt(255);
		}

		tranX = (float) position.x / scale;
		tranY = (float) position.y / scale;

		if (position.x < 0) {
			speed.x *= -1;
			position.x = 0;
		}
		if (position.x > width - gift.getWidth()) {
			speed.x *= -1;
			position.x = width - gift.getWidth();
		}

	}

	private void reset() {
		position.x = rand.nextInt(width - giftWidth);
		position.y = -giftHeight;
		speed.x = rand.nextInt(speedNum);
		speed.y = 10 + rand.nextInt(speedNum - 10);
		scale = (float) (30 + rand.nextInt(11)) / 40;
		scale = scale == 0 ? 0.5f : scale;
		matrix = new Matrix();
		tranX = (float) position.x / scale;
		tranY = (float) position.y / scale;
		alpha = 100 + rand.nextInt(156);
	}

	public boolean isStop(){
		return isStop;
	}
	
}
