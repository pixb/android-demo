package com.pix.anim.project.guagua;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;

import com.pix.anim.bean.GuaGuaAnimView;
import com.pix.anim.utils.AnimBitmapLoader;

public class SingleGiftView extends GuaGuaAnimView {

	private Thread spriteThread;
	private long sleepTime = 30;

	private long tranTime = 600;
	private long tranTimeStep = 0;
	private float x = 0;
	private float y = 0;
	private float tranY = 0;
	private float scale = 1f;
	private float scaleStep = 0;
	private float scaleX = 1;
	private float scaleY = 1;
	private float bombX = 0;
	private float bombY = 0;

	private Paint paint = new Paint();
	private int alpha = 255;
	private Bitmap gift;
	private Bitmap[] bit_bombs;

	private int state = 0;
	private final int START = 1;
	private final int SCALE = 2;
	private final int ALPHA = 3;
	private final int FINISH = 4;

	public SingleGiftView(Context context, Bitmap bitmap) {
		super(context);
		gift = bitmap;
	}

	private void init() {
		bit_bombs = new Bitmap[6];
		for (int i = 0; i < bit_bombs.length; i++) {
			bit_bombs[i] = AnimBitmapLoader.getInstance().getBitmap(
					getContext(), "gift/star_frame" + (i + 1) + ".png", opt);
			if (bit_bombs[i] == null) {
				if (mGiftListener != null) {
					mGiftListener.onError("礼物动画");
				}
				state = FINISH;// 解决内存不足无法解析图片而导致动画无法运行
				break;
			}
		}
	}

	public void start() {
		running = false;
		spriteThread = new Thread(run);
		spriteThread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
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
		canvas.drawBitmap(bit_bombs[scaleCount], bombX, bombY, null);
	}

	private Runnable run = new Runnable() {
		public void run() {
			running = true;
			tranTimeStep = 0;
			long curTime = 0;
			while (running && state != FINISH) {
				curTime = System.currentTimeMillis();
				move();
				mHandler.sendEmptyMessage(HANDLER_DRAW);
				curTime = System.currentTimeMillis() - curTime;
				try {
					if (curTime < sleepTime) {
						Thread.sleep(sleepTime - curTime);
					}
				} catch (InterruptedException e) {
					break;
				}
			}
			// 添加动画结束回调
			destoryBitmap();
			mHandler.sendEmptyMessage(HANDLER_LISTENER);
		}
	};

	private void move() {
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
		scale += scaleStep;
		if (scale > 1.5) {
			scaleStep *= -1;
		}
		x = (getWidth() - gift.getWidth() * scale) / 2;
		scaleX = x / scale;
		scaleY = y / scale;
		tranTimeStep += sleepTime;
		if (tranTimeStep > tranTime) {
			state = SCALE;
			scaleStep = scaleStep * 2;
			bombX = (getWidth() - bit_bombs[0].getWidth()) / 2;
			bombY = y - (bit_bombs[0].getHeight() - gift.getHeight()) / 2;
		}
	}

	private int scaleCount = 0;

	private void moveScale() {
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
		x = (getWidth() - gift.getWidth() * scale) / 2;
		scaleX = x / scale;
		scaleY = (y + fixH) / scale;

		bombX = (getWidth() - bit_bombs[0].getWidth()) / 2;
		bombY = y - (bit_bombs[0].getHeight() - gift.getHeight()) / 2;
	}

	private void moveAlpha() {
		x = (getWidth() - gift.getWidth()) / 2;
		alpha -= 20;
		paint.setAlpha(alpha);
		if (alpha <= 0) {
			state = FINISH;
			running = false;
		}
	}

	private final int HANDLER_DRAW = 1;
	private final int HANDLER_LISTENER = 2;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HANDLER_DRAW:
				invalidate();
				break;
			case HANDLER_LISTENER:
				if (mGiftListener != null) {
					mGiftListener.onGiftAnimFinish();
				}
				break;
			}
		};
	};

	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (state != 0) {
			return;
		}
		init();
		if (state == FINISH) {
			return;
		}
		int step = (int) (tranTime / sleepTime);
		// Y起始位置为屏幕的2/3处，终点为1/4处
		x = (getWidth() - gift.getWidth() * scale) / 2;
		y = (getHeight() - gift.getHeight()) * 2 / 3;
		// 位移总距离，需要分割
		float totalY = getHeight() / 4 - y - gift.getHeight() / 2;
		tranY = totalY / step;

		scaleStep = 1f / step;
		scaleX = x / scale;
		scaleY = y / scale;
		state = START;
	};

	private void destoryBitmap() {
//		for (int i = 0; i < bit_bombs.length; i++) {
//			bit_bombs[i].recycle();
//			bit_bombs[i] = null;
//		}
	}
}
