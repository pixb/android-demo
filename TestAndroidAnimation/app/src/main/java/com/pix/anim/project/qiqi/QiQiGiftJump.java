package com.pix.anim.project.qiqi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * 单个礼物动画（从指定位置到目标位置的位移，缩放和爆炸效果）参考{@link#RepeatGiftView}
 * @author lixianfeng
 *
 */
public class QiQiGiftJump {
	/**
	 * 显示区域的宽度
	 */
	protected int width;
	/**
	 * 显示区域的高度
	 */
	protected int height;

	private int step = 0;
	private long tranTimeStep = 0;
	private float x = 0;
	private float y = 0;
	private float tranY = 0;
	private float tranX = 0;
	private float scale = 1.2f;
	private float scaleStep = 0;
	private float scaleX = 1;
	private float scaleY = 1;
	private float bombX = 0;
	private float bombY = 0;
	private float mfStartX = 0.0f;
	private float mfStartY = 0.0f ;
	private float mfEndX = 500f ;
	private float mfEndY = 500f ;

	private Paint paint = new Paint();
	private int alpha = 255;
	private Bitmap gift;
	private Bitmap[] bit_bombs;

	private int state = 0;
	private final int START = 1;
	private final int SCALE = 2;
	private final int ALPHA = 3;
	private final int FINISH = 4;
	


	private boolean showNum = false;//连击是判读增加数量
	
	public QiQiGiftJump(int width, int height, int step, Bitmap gift, Bitmap[] stars,float startX,float startY
			,float endX,float endY) {
		this.width = width;
		this.height = height;
		this.step = step;
		this.gift = gift;
		this.bit_bombs = stars;
		mfStartX = startX ;
		mfStartY = startY ;
		mfEndX = endX ;
		mfEndY = endY ;
		reset();
	}

	public void draw(Canvas canvas) {
		switch (state) {
		case START:
			drawStart(canvas);
			break;
		case SCALE:
			drawScale(canvas);
			drawBomb(canvas);
			break;
		case ALPHA:
			canvas.drawBitmap(gift, x, y, paint);
			break;
		}
	}

	private void drawStart(Canvas canvas) {
		canvas.save();
		canvas.scale(scale, scale);
		canvas.drawBitmap(gift, scaleX, scaleY, null);
		canvas.restore();
	}

	private void drawScale(Canvas canvas) {
		canvas.save();
		canvas.scale(scale, scale);
		canvas.drawBitmap(gift, scaleX, scaleY, null);
		canvas.restore();
	}

	private void drawBomb(Canvas canvas) {
		Log.d("", " moveTran() bombX :  drawBomb" + bombX +", bombY :" + bombY);
		canvas.drawBitmap(bit_bombs[scaleCount], bombX, bombY, null);
	}

	public void move() {
		switch (state) {
		case START:
			moveTran();
			break;
		case SCALE:
			moveScale();
			break;
		case ALPHA:
			moveAlpha();
			break;
		}
	}

	private void moveTran() {
		y += tranY;
		x += tranX;
		scale += scaleStep;
		if (scale > 1.5) {
			scaleStep *= -1;
		}
//		x = (width - gift.getWidth() * scale) / 2;
		scaleX = x / scale;
		scaleY = y / scale;
		tranTimeStep ++;
		if (tranTimeStep > step) {
			state = SCALE;
			scaleStep = scaleStep * 2;
//			bombX = (width - bit_bombs[0].getWidth()) / 2;
			bombX = x - (bit_bombs[0].getWidth()-gift.getWidth()) / 2;
			bombY = y - (bit_bombs[0].getHeight() - gift.getHeight()) / 2;
			Log.d("", " moveTran() bombX :" + bombX +", bombY :" + bombY);
			
			showNum = true;
		}
	}

	private int scaleCount = 0;

	private void moveScale() {
		showNum = false;
		scale += scaleStep;
		if (scale > 1.2 && scaleCount < 5) {
			scaleStep *= -1;
			scaleCount++;
		}
		if (scale < 0.8 && scaleCount < 5) {
			scaleStep *= -1;
			scaleCount++;
		}
		if (scaleCount > 4 && Math.abs(scale - 1) < 0.1) {
			scale = 1;
			state = ALPHA;
		}
		float fixH = (gift.getHeight() - gift.getHeight() * scale) / 2;
//		x = (width - gift.getWidth() * scale) / 2;
		scaleX = x / scale;
		scaleY = (y + fixH) / scale;

//		bombX = (width - bit_bombs[0].getWidth()) / 2;
		bombY = x - (bit_bombs[0].getWidth()- gift.getWidth()) /2 ;
		bombY = y - (bit_bombs[0].getHeight() - gift.getHeight()) / 2;
	}

	private void moveAlpha() {
//		x = (width - gift.getWidth()) / 2;
		alpha -= 20;
		paint.setAlpha(alpha);
		if (alpha <= 0) {
			state = FINISH;
		}
	}

	public boolean isShowNum(){
		return showNum;
	}
	
	public boolean isStop(){
		return state == FINISH;
	}
	
	private void reset() {
		initPosition();
		// 位移总距离，需要分割
//		float totalY = height / 4 - y - gift.getHeight() / 2;
//		float totalX = (width - gift.getWidth() * scale) / 2 - x;
		
		float totalY = mfEndY - y - gift.getHeight() / 2;
		float totalX = mfEndX - x -gift.getWidth() / 2 ; 
		
		tranY = totalY / step;
		tranX = totalX / step;

		scaleStep = 1f / step;
		scaleX = x / scale;
		scaleY = y / scale;
		state = START;
	}

	private void initPosition(){
//		Random rand = new Random();
//		//是否从底部出现
//		int pos = rand.nextInt(3);
//		if(pos == 0){
//			// Y起始位置为屏幕的2/3处，终点为1/4处
//			x = (width - gift.getWidth() * scale) / 2;
//			y = (height - gift.getHeight()) * 2 / 3;
//		}else{
//			if(pos == 1){
//				x = 0;
//			}else{
//				x = width - gift.getWidth() * scale;
//			}
//			y = height / 4 + rand.nextInt(((height - gift.getHeight()) * 2 / 3));
//		}
		x = mfStartX - gift.getWidth()* scale / 2 ;
		y = mfStartY - gift.getHeight() * scale /2 ;
	}
	public void setStartLocation(float fStartX,float fStartY)
	{
		mfStartX = fStartX ;
		mfStartY = fStartY;
	}
	
	public void setEndLocation(float fEndX,float fEndY)
	{
		mfEndX = fEndX ;
		mfEndY = fEndY ;
	}
	
}
