package com.pix.anim.project.guagua;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;

import com.pix.anim.bean.GuaGuaAnimView;
import com.pix.anim.utils.AnimBitmapLoader;

import java.io.IOException;

/**
 ************************************************************************************
* Module Name: BoomRPView</br>
* File Name: <b>BoomRPView.java</b></br>
* Description:实现超级红包爆掉的特效</br>
* Author: TPX</br>
* 版权 2008-2015，金华长风信息技术有限公司</br>
* 所有版权保护
* 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
**************************************************************************************
 */
public class BoomRPView extends GuaGuaAnimView {
	/**画笔*/
	private Paint paint = new Paint();
	/**精灵线程*/
	private Thread spriteThread;
	/**动画的状态*/
	private int state = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FINISH = 5;
	private static final String TAG = "BoomRPView";
	private static final int SRP_1_MOVE_DIS = 200;
	private static final int SRP_2_MOVE_DIS = 100;
	private static final int SRP_3_MOVE_DIS = 100;
	private static final int SRP_4_MOVE_DIS = 350;
	private static final int SRP_5_MOVE_DIS = 250;
	private static final int SRP_6_MOVE_DIS = 200;
	private static final int SRP_7_MOVE_DIS = 200;
	private static final int SRP_8_MOVE_DIS = 450;
	private static final int SRP_9_MOVE_DIS = 450;
	private static final float SRP_1_Y_POS_OFFSET = -150f;
	private static final float SRP_2_X_POS_OFFSET = -70f;
	private static final float SRP_2_Y_POS_OFFSET = -150f;
	private static final float SRP_3_X_POS_OFFSET = -6f;
	private static final float SRP_3_Y_POS_OFFSET = -150f;
	private static final float SRP_4_X_POS_OFFSET = -80f;
	private static final float SRP_4_Y_POS_OFFSET = -90f;
	private static final float SRP_5_Y_POS_OFFSET = -30f;
	private static final float SRP_6_X_POS_OFFSET = -120f;
	private static final float SRP_6_Y_POS_OFFSET = -100f;
	private static final float SRP_7_X_POS_OFFSET =  10f;
	private static final float SRP_7_Y_POS_OFFSET = -60f;
	private static final float SRP_8_X_POS_OFFSET = -80f;
	private static final float SRP_8_Y_POS_OFFSET = -60f;
	private static final float SRP_9_Y_POS_OFFSET = -50f;
	/** 休息的时间 */
	private static final int SLEEP_TIME = 50;

	/** 旋转光图片*/
	private Bitmap mRotateLightBmp;
	private float mRotateLightWidth = 0f;
	private float mRotateLightHeight = 0f;
	private Bitmap mSRPBmp;
	private float mSRPBmpWidth = 0f;
	private float mSRPBmpHeight = 0f;
	BoomRPEndListener mBoomRPEndListener;

	/** 移动的时间*/
	private float rotateLightWidth = 0f;
	private float rotateLightHeight = 0f;
	private int moveTwoCount = 0;
	/** 旋转光的x坐标 */
	private float rotateLightX = 0;
	/** 旋转光的y坐标 */
	private float rotateLightY = 0;
	/** srp 1 x coordinate */
	private float srp1X;
	/** srp 1 y coordinate */
	private float srp1Y;
	/** srp 2 x coordinate */
	private float srp2X;
	/** srp 2 y coordinate */
	private float srp2Y;
	/** srp 3 x coordinate */
	private float srp3X;
	/** srp 3 y coordinate */
	private float srp3Y;
	/** srp 4 x coordinate */
	private float srp4X;
	/** srp 4 y coordinate */
	private float srp4Y;
	/** srp 5 x coordinate */
	private float srp5X;
	/** srp 5 y coordinate */
	private float srp5Y;
	/** srp 6 x coordinate */
	private float srp6X;
	/** srp 6 y coordinate */
	private float srp6Y;
	/** srp 7 x coordinate */
	private float srp7X;
	/** srp 7 y coordinate */
	private float srp7Y;
	/** srp 1 x coordinate */
	private float srp8X;
	/** srp 1 x coordinate */
	private float srp8Y;
	/** srp 1 x coordinate */
	private float srp9X;
	/** srp 1 x coordinate */
	private float srp9Y;
	/** srp 1 scale rate*/
	private float scaleSrp1 = 0.5f * opt;
	private float scaleSrp2 = 0.5f * opt;
	private float scaleSrp3 = 0.5f * opt;
	private float scaleSrp4 = 0.3f * opt;
	private float scaleSrp5 = 0.4f * opt;
	private float scaleSrp6 = 0.4f * opt;
	private float scaleSrp7 = 0.4f * opt;
	private float scaleSrp8 = 0.3f * opt;
	private float scaleSrp9 = 0.3f * opt;
	//srp scale coordinate
	private float srp1ScaleX = 0f;
	private float srp1ScaleY = 0f;
	private float srp2ScaleX = 0f;
	private float srp2ScaleY = 0f;
	private float srp3ScaleX = 0f;
	private float srp3ScaleY = 0f;
	private float srp4ScaleX = 0f;
	private float srp4ScaleY = 0f;
	private float srp5ScaleX = 0f;
	private float srp5ScaleY = 0f;
	private float srp6ScaleX = 0f;
	private float srp6ScaleY = 0f;
	private float srp7ScaleX = 0f;
	private float srp7ScaleY = 0f;
	private float srp8ScaleX = 0f;
	private float srp8ScaleY = 0f;
	private float srp9ScaleX = 0f;
	private float srp9ScaleY = 0f;
	//srp move distance
	private float srp1MoveDistance = 0;
	private float srp2MoveDistance = 0;
	private float srp3MoveDistance = 0;
	private float srp4MoveDistance = 0;
	private float srp5MoveDistance = 0;
	private float srp6MoveDistance = 0;
	private float srp7MoveDistance = 0;
	private float srp8MoveDistance = 0;
	private float srp9MoveDistance = 0;

	private float srp1MoveStep = SRP_1_MOVE_DIS  / 10;
	private float srp2MoveStep = SRP_2_MOVE_DIS  / 10;
	private float srp3MoveStep = SRP_3_MOVE_DIS  / 10;
	private float srp4MoveStep = SRP_4_MOVE_DIS  / 10;
	private float srp5MoveStep = SRP_5_MOVE_DIS  / 10;
	private float srp6MoveStep = SRP_6_MOVE_DIS  / 10;
	private float srp7MoveStep = SRP_7_MOVE_DIS  / 10;
	private float srp8MoveStep = SRP_8_MOVE_DIS  / 10;
	private float srp9MoveStep = SRP_9_MOVE_DIS  / 10;

	/** 旋转光缩放的x坐标 */
	private float rotateLightScaleX = 0;
	/** 旋转光缩放的y坐标 */
	private float rotateLightScaleY = 0;
	private float bitWidth;
	private float bitHeight;
	private float bitScaleX = 0;
	private float bitScaleY = 0;
	private float bitScale = 1.1f;
	/** 爆炸的x坐标*/
	private float bombX = 0f;
	/** 爆炸的y坐标 */
	private float bombY = 0f;
	/** 爆炸循环数*/
	private int bombCount = 0;
	private int bombNum = 0;
	private int bombStep = 10;
	/** 旋转光的起始大小 */
	private float scaleRotateLight = 1.2f * opt;
	/** 缩放步长 */
	private float scaleStep = 0.02f;
	/** 旋转角度 */
	private float rotateRotateLightAngle = 0;
	private float rotateAngleStep = 5;
	/** 绘制的消息 */
	private final int HANDLER_DRAW = 1;
	/** 监听消息 */
	private final int HANDLER_LISTENER = 2;
	/** 运行第二步 */
	private final int RUN_TWO = 3;
	private Matrix mMatrix;
	/** 爆炸序列帧 */
	private Bitmap[] bit_bombs;
	/** Handler消息 */
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case HANDLER_DRAW:
					invalidate();
					break;
				case HANDLER_LISTENER:
					if (mDriverlistener != null) {
						mDriverlistener.onDriverFinish();
					}
					break;
			}
		};
	};

	public BoomRPView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	//启动动画
	@Override
	public void start() {
		running = false;
		spriteThread = new Thread(run);
		spriteThread.start();
	}

	/**
	 * 动画异步运行线程
	 */
	private Runnable run = new Runnable() {
		public void run() {
			running = true;
			while (running && state != FINISH) {
				move();
				mHandler.sendEmptyMessage(HANDLER_DRAW);
				try {
					Thread.sleep(SLEEP_TIME);
				}
				catch (InterruptedException e) {
					break;
				}
			}
			//销毁图片
			destoryBitmap();
			mHandler.sendEmptyMessage(HANDLER_LISTENER);
		}
	};

	/**
	 * 动画执行，状态选择
	 * TODO
	 * @return: void
	 */
	private void move() {
		switch (state) {
			case ONE:
				moveOne();
				moveBomb();
				break;
			case TWO:
				moveTwo();
				break;
			case FINISH:
				break;
			default:
				break;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		switch (state) {
			case ONE:
				//drawOne(canvas);
				drawGaryBG(canvas);
				drawBomb(canvas);
				drawOne(canvas);
				break;
			case TWO:
				drawGaryBG(canvas);
				drawTwo(canvas);
				drawBomb(canvas);
				drawOne(canvas);
				break;
			case FINISH:
				drawAlphaBG(canvas);
				destoryBitmap();
				break;
			default:
				break;
		}
	}

	private void moveOne() {
	}

	/**
	 * 第二步移动
	 * @return: void
	 */
	private void moveTwo() {
		//缩放增一步
		scaleRotateLight += scaleStep;
		if (scaleRotateLight > 1.2f * opt) {
			scaleRotateLight = 1.2f * opt;
		}
		rotateRotateLightAngle += rotateAngleStep;
		if (rotateRotateLightAngle == 360) {
			rotateRotateLightAngle = 0;
		}
		//缩放补充高度
		float fixH = (mRotateLightBmp.getHeight() - mRotateLightBmp.getHeight() * scaleRotateLight) / 2;
		float fixW = (mRotateLightBmp.getWidth() - mRotateLightBmp.getWidth() * scaleRotateLight) / 2;
		rotateLightScaleX = (rotateLightX + fixW) / scaleRotateLight;
		rotateLightScaleY = (rotateLightY + fixH) / scaleRotateLight;

		if(opt >= 1) {
			srp1MoveDistance += srp1MoveStep;
			if (srp1MoveDistance >= SRP_1_MOVE_DIS ) {
				srp1MoveDistance = SRP_1_MOVE_DIS;
			}
			srp2MoveDistance += srp2MoveStep;
			if (srp2MoveDistance >= SRP_2_MOVE_DIS ) {
				srp2MoveDistance = SRP_2_MOVE_DIS ;
			}
			srp3MoveDistance += srp3MoveStep;
			if (srp3MoveDistance >= SRP_3_MOVE_DIS ) {
				srp3MoveDistance = SRP_3_MOVE_DIS ;
			}
			srp4MoveDistance += srp4MoveStep;
			if (srp4MoveDistance >= SRP_4_MOVE_DIS ) {
				srp4MoveDistance = SRP_4_MOVE_DIS ;
			}

			srp5MoveDistance += srp5MoveStep;
			if (srp5MoveDistance >= SRP_5_MOVE_DIS ) {
				srp5MoveDistance = SRP_5_MOVE_DIS ;
			}

			srp6MoveDistance += srp6MoveStep;
			if (srp6MoveDistance >= SRP_6_MOVE_DIS ) {
				srp6MoveDistance = SRP_6_MOVE_DIS ;
			}

			srp7MoveDistance += srp7MoveStep;
			if (srp7MoveDistance >= SRP_7_MOVE_DIS ) {
				srp7MoveDistance = SRP_7_MOVE_DIS ;
			}

			srp8MoveDistance += srp8MoveStep;
			if (srp8MoveDistance >= SRP_8_MOVE_DIS ) {
				srp8MoveDistance = SRP_8_MOVE_DIS ;
			}

			srp9MoveDistance += srp9MoveStep;
			if (srp9MoveDistance >= SRP_9_MOVE_DIS) {
				srp9MoveDistance = SRP_9_MOVE_DIS ;
			}
		} else {
			srp1MoveDistance += srp1MoveStep;
			if (srp1MoveDistance >= SRP_1_MOVE_DIS * opt) {
				srp1MoveDistance = SRP_1_MOVE_DIS * opt;
			}
			srp2MoveDistance += srp2MoveStep;
			if (srp2MoveDistance >= SRP_2_MOVE_DIS * opt) {
				srp2MoveDistance = SRP_2_MOVE_DIS * opt;
			}
			srp3MoveDistance += srp3MoveStep;
			if (srp3MoveDistance >= SRP_3_MOVE_DIS * opt) {
				srp3MoveDistance = SRP_3_MOVE_DIS * opt;
			}
			srp4MoveDistance += srp4MoveStep;
			if (srp4MoveDistance >= SRP_4_MOVE_DIS * opt) {
				srp4MoveDistance = SRP_4_MOVE_DIS * opt;
			}

			srp5MoveDistance += srp5MoveStep;
			if (srp5MoveDistance >= SRP_5_MOVE_DIS * opt) {
				srp5MoveDistance = SRP_5_MOVE_DIS * opt;
			}

			srp6MoveDistance += srp6MoveStep;
			if (srp6MoveDistance >= SRP_6_MOVE_DIS * opt) {
				srp6MoveDistance = SRP_6_MOVE_DIS * opt;
			}

			srp7MoveDistance += srp7MoveStep;
			if (srp7MoveDistance >= SRP_7_MOVE_DIS * opt) {
				srp7MoveDistance = SRP_7_MOVE_DIS * opt;
			}

			srp8MoveDistance += srp8MoveStep;
			if (srp8MoveDistance >= SRP_8_MOVE_DIS * opt) {
				srp8MoveDistance = SRP_8_MOVE_DIS * opt;
			}

			srp9MoveDistance += srp9MoveStep;
			if (srp9MoveDistance >= SRP_9_MOVE_DIS * opt) {
				srp9MoveDistance = SRP_9_MOVE_DIS * opt;
			}
		}
		

		if (moveTwoCount >= 0) {
			moveTwoCount++;
		}

		if (moveTwoCount == 50) {
			if (mBoomRPEndListener != null) {
				mBoomRPEndListener.end();
			}

			moveTwoCount = -1;
			state = FINISH;
		}

	}

	private void moveBomb() {
		bombNum += bombStep;
		if (bombNum % 10 == 0) {
			bombCount++;
		}
		if (bombCount >= 42) {
			bombCount = 42;
			state = TWO;
		}
		if (bombNum >= 100) {
			bombNum = 0;
		}
	}

	/**
	 * 绘制第一步
	 * TODO
	 * @param canvas
	 * @return: void
	 */
	private void drawOne(Canvas canvas) {
	}

	private void drawTwo(Canvas canvas) {
		canvas.save();
		canvas.scale(scaleRotateLight, scaleRotateLight);

//		canvas.drawBitmap(mRotateLightBmp, rotateLightScaleX, rotateLightScaleY, null);
		mMatrix.setTranslate(rotateLightScaleX, rotateLightScaleY); //设置图片的旋转中心，即绕（X,Y）这点进行中心旋转
		mMatrix.preRotate(rotateRotateLightAngle, mRotateLightBmp.getWidth() / 2, mRotateLightBmp.getHeight() / 2); //要旋转的角度
		canvas.drawBitmap(mRotateLightBmp, mMatrix, null);
		canvas.restore();
		drawSRP(canvas);
	}

	private void drawBomb(Canvas canvas) {
		canvas.save();
		canvas.scale(bitScale, bitScale);
		canvas.drawBitmap(bit_bombs[bombCount], bitScaleX, bitScaleY, null);
		canvas.restore();
	}

	private void drawGaryBG(Canvas canvas) {
		//绘制灰色背景
		canvas.save();
		canvas.drawARGB(160, 0, 0, 0);
		canvas.restore();
	}

	private void drawAlphaBG(Canvas canvas) {
		//绘制透明色背景
		canvas.save();
		canvas.drawARGB(0, 0, 0, 0);
		canvas.restore();
	}

	private void drawSRP(Canvas canvas) {
		//1
		canvas.save();
		canvas.scale(scaleSrp1, scaleSrp1);
		canvas.drawBitmap(mSRPBmp, srp1ScaleX, srp1ScaleY - srp1MoveDistance, null);
		canvas.restore();
		//2
		canvas.save();
		canvas.scale(scaleSrp2, scaleSrp2);
		mMatrix.setTranslate(srp2ScaleX - srp2MoveDistance, srp2ScaleY - srp2MoveDistance);
		mMatrix.preRotate(-45, mSRPBmp.getWidth() / 2, mSRPBmp.getHeight() / 2); //要旋转的角度
		canvas.drawBitmap(mSRPBmp, mMatrix, null);
		canvas.restore();
		//3
		canvas.save();
		canvas.scale(scaleSrp3, scaleSrp3);
		mMatrix.setTranslate(srp3ScaleX + srp3MoveDistance, srp3ScaleY - srp3MoveDistance);
		mMatrix.preRotate(45, mSRPBmp.getWidth() / 2, mSRPBmp.getHeight() / 2); //要旋转的角度
		canvas.drawBitmap(mSRPBmp, mMatrix, null);
		canvas.restore();
		//4
		canvas.save();
		canvas.scale(scaleSrp4, scaleSrp4);
		mMatrix.setTranslate(srp4ScaleX - (float) (srp4MoveDistance / 0.70f), srp4ScaleY - srp4MoveDistance);
		mMatrix.preRotate(-75, mSRPBmp.getWidth() / 2, mSRPBmp.getHeight() / 2); //要旋转的角度
		canvas.drawBitmap(mSRPBmp, mMatrix, null);
		canvas.restore();
		//5
		canvas.save();
		canvas.scale(scaleSrp5, scaleSrp5);
		mMatrix.setTranslate(srp5ScaleX + (float) (srp5MoveDistance / 0.70f), srp5ScaleY - srp5MoveDistance);
		mMatrix.preRotate(75, mSRPBmp.getWidth() / 2, mSRPBmp.getHeight() / 2); //要旋转的角度
		canvas.drawBitmap(mSRPBmp, mMatrix, null);
		canvas.restore();

		//6
		canvas.save();
		canvas.scale(scaleSrp6, scaleSrp6);
		mMatrix.setTranslate(srp6ScaleX - srp6MoveDistance, srp6ScaleY);
		mMatrix.preRotate(-80, mSRPBmp.getWidth() / 2, mSRPBmp.getHeight() / 2); //要旋转的角度
		canvas.drawBitmap(mSRPBmp, mMatrix, null);
		canvas.restore();

		//7
		canvas.save();
		canvas.scale(scaleSrp7, scaleSrp7);
		mMatrix.setTranslate(srp7ScaleX + srp7MoveDistance, srp7ScaleY);
		mMatrix.preRotate(95, mSRPBmp.getWidth() / 2, mSRPBmp.getHeight() / 2); //要旋转的角度
		canvas.drawBitmap(mSRPBmp, mMatrix, null);
		canvas.restore();

		//8
		canvas.save();
		canvas.scale(scaleSrp8, scaleSrp8);
		mMatrix.setTranslate(srp8ScaleX - srp8MoveDistance, srp8ScaleY + srp8MoveDistance * 0.4f);
		mMatrix.preRotate(-100, mSRPBmp.getWidth() / 2, mSRPBmp.getHeight() / 2); //要旋转的角度
		canvas.drawBitmap(mSRPBmp, mMatrix, null);
		canvas.restore();
//		
//		//9
		canvas.save();
		canvas.scale(scaleSrp9, scaleSrp9);
		mMatrix.setTranslate(srp9ScaleX + srp8MoveDistance, srp9ScaleY + srp8MoveDistance * 0.4f);
		mMatrix.preRotate(110, mSRPBmp.getWidth() / 2, mSRPBmp.getHeight() / 2); //要旋转的角度
		canvas.drawBitmap(mSRPBmp, mMatrix, null);
		canvas.restore();

	}

	/**
	 * 初始化
	 */
	public void init() {
		try {
			//加载旋转的图片
			mRotateLightBmp = BitmapFactory.decodeStream(getContext().getAssets().open("srp/rotate_light.png"));
			//加载红包的图片
			mSRPBmp = BitmapFactory.decodeStream(getContext().getAssets().open("srp/srp.png"));
			bit_bombs = new Bitmap[43];
			for (int i = 0; i < bit_bombs.length; i++) {
				bit_bombs[i] = AnimBitmapLoader.getInstance().getBitmap(getContext(), "srp/monkey/monkey_" + (i + 1) + ".png", opt);
				if (bit_bombs[i] == null) {
					if (mGiftListener != null) {
						mGiftListener.onError("礼物动画");
					}
					if (mBoomRPEndListener != null) {
						mBoomRPEndListener.end();
					}
					state = FINISH;// 解决内存不足无法解析图片而导致动画无法运行
					break;
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (OutOfMemoryError oe) {
			if (mDriverlistener != null) {
				mDriverlistener.onError("SRP Boom");
			}
			state = FINISH;//如果内存不够导致解析图片没有，则不会播放动画
			System.gc();
			if (mBoomRPEndListener != null) {
				mBoomRPEndListener.end();
			}
		}
		mMatrix = new Matrix();
	}

	private void destoryBitmap() {
		if (mRotateLightBmp != null) {
			mRotateLightBmp.recycle();
			mRotateLightBmp = null;
		}
		if (mSRPBmp != null) {
			mSRPBmp.recycle();
			mSRPBmp = null;
		}

		if (bit_bombs != null && bit_bombs.length > 0) {
			for (int i = 0; i < bit_bombs.length; i++) {
				if (bit_bombs[i] != null) {
					bit_bombs[i].recycle();
					bit_bombs[i] = null;
				}
			}
		}

	}

	/**
	 * 绘制布局
	 * @see android.view.View#onLayout(boolean, int, int, int, int)
	 * TODO
	 */
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		if (state != 0) {
			return;
		}
		init();
		if (state == FINISH) {
			return;
		}

		if (mSRPBmp == null || bit_bombs == null || bit_bombs.length <= 0) {
			state = FINISH;
			return;
		}
		mSRPBmpWidth = mSRPBmp.getWidth() * opt;
		mSRPBmpHeight = mSRPBmp.getHeight() * opt;
		rotateLightWidth = mRotateLightBmp.getWidth() * opt;
		rotateLightHeight = mRotateLightBmp.getHeight() * opt;
		bitWidth = bit_bombs[0].getWidth() * opt;
		bitHeight = bit_bombs[0].getHeight() * opt;

		//x初始化屏宽
		rotateLightX = (getWidth() - mRotateLightBmp.getWidth()) / 2;
		//y屏幕的1/2
		rotateLightY = (getHeight() - mRotateLightBmp.getHeight()) / 2;

		//缩放补充高度
		float fixH = (mRotateLightBmp.getHeight() - mRotateLightBmp.getHeight() * scaleRotateLight) / 2;
		float fixW = (mRotateLightBmp.getWidth() - mRotateLightBmp.getWidth() * scaleRotateLight) / 2;
		rotateLightScaleX = (rotateLightX + fixW) / scaleRotateLight;
		rotateLightScaleY = (rotateLightY + fixH) / scaleRotateLight;

		//Bomb coordinate init
		bombX = (getWidth() - bit_bombs[0].getWidth()) / 2;
		bombY = (getHeight()) / 2 - 250 * opt;
		float bitFixH = (bit_bombs[0].getWidth() - bit_bombs[0].getWidth() * bitScale) / 2;
		float bitFixW = (bit_bombs[0].getWidth() - bit_bombs[0].getWidth() * bitScale) / 2;
		bitScaleX = (bombX + bitFixW) / bitScale;
		if(opt <= 1) {
			bitScaleY = (bombY + bitFixH) / bitScale;
		} else if (opt > 1) {
			bitScaleY = rotateLightScaleY + 115;
		} 

		//srp coordinate init
		//1
		srp1X = getWidth() / 2 - mSRPBmp.getWidth() / 2;
		if(opt >= 1) {
			srp1Y = getHeight() / 2 + (SRP_1_Y_POS_OFFSET * opt);
		}
		else {
			srp1Y = getHeight() / 2 - 100 ;
		}
		//srp1 scale adjust
		float srp1FixH = (mSRPBmp.getHeight() - mSRPBmp.getHeight() * scaleSrp1) / 2;
		float srp1FixW = (mSRPBmp.getWidth() - mSRPBmp.getWidth() * scaleSrp1) / 2;
		srp1ScaleX = (srp1X + srp1FixW) / scaleSrp1;
		srp1ScaleY = (srp1Y + srp1FixH) / scaleSrp1;
		//2
	
		if(opt >= 1) {
			srp2X = getWidth() / 2 + (SRP_2_X_POS_OFFSET * opt);
			srp2Y = getHeight() / 2 + (SRP_2_Y_POS_OFFSET * opt);
		} else {
			srp2X = getWidth() / 2 - 80;
			srp2Y = getHeight() / 2 - 100;
		}
		
		//srp1 scale adjust
		float srp2FixH = (mSRPBmp.getHeight() - mSRPBmp.getHeight() * scaleSrp2) / 2;
		float srp2FixW = (mSRPBmp.getWidth() - mSRPBmp.getWidth() * scaleSrp2) / 2;
		srp2ScaleX = (srp2X + srp2FixW) / scaleSrp2;
		srp2ScaleY = (srp2Y + srp2FixH) / scaleSrp2;
		//3
		if(opt >= 1) {
			srp3X = getWidth() / 2 + (SRP_3_X_POS_OFFSET * opt);
			srp3Y = getHeight() / 2 + (SRP_3_Y_POS_OFFSET * opt);
		}
		else {
			srp3X = getWidth() / 2 + SRP_3_X_POS_OFFSET ;
			srp3Y = getHeight() / 2 - 100 ;
		}
		
		float srp3FixH = (mSRPBmp.getHeight() - mSRPBmp.getHeight() * scaleSrp3) / 2;
		float srp3FixW = (mSRPBmp.getWidth() - mSRPBmp.getWidth() * scaleSrp3) / 2;
		srp3ScaleX = (srp3X + srp3FixW) / scaleSrp3;
		srp3ScaleY = (srp3Y + srp3FixH) / scaleSrp3;
		//4
		if(opt >= 1) {
			srp4X = getWidth() / 2 + (SRP_4_X_POS_OFFSET * opt);
			srp4Y = getHeight() / 2 + (SRP_4_Y_POS_OFFSET * opt);
		}
		else {
			srp4X = getWidth() / 2  - 136;
			srp4Y = getHeight() / 2 - 70  ;
		}
		
		float srp4FixH = (mSRPBmp.getHeight() - mSRPBmp.getHeight() * scaleSrp4) / 2;
		float srp4FixW = (mSRPBmp.getWidth() - mSRPBmp.getWidth() * scaleSrp4) / 2;
		srp4ScaleX = (srp4X + srp4FixW) / scaleSrp4;
		srp4ScaleY = (srp4Y + srp4FixH) / scaleSrp4;
		//5
		if(opt >= 1) {
			srp5X = getWidth() / 2;
			srp5Y = getHeight() / 2 + (SRP_5_Y_POS_OFFSET * opt);
		} else {
			srp5X = getWidth() / 2 + 30 ;
			srp5Y = getHeight() / 2 - 50;
		}
		
		float srp5FixH = (mSRPBmp.getHeight() - mSRPBmp.getHeight() * scaleSrp5) / 2;
		float srp5FixW = (mSRPBmp.getWidth()  - mSRPBmp.getWidth()  * scaleSrp5) / 2;
		srp5ScaleX = (srp5X + srp5FixW) / scaleSrp5;
		srp5ScaleY = (srp5Y + srp5FixH) / scaleSrp5;
		//6
		if(opt >= 1) {
			srp6X = getWidth() / 2 + (SRP_6_X_POS_OFFSET * opt);
			srp6Y = getHeight() / 2 + (SRP_6_Y_POS_OFFSET * opt);
		}
		else {
			srp6X = getWidth() / 2 + (SRP_6_X_POS_OFFSET );
			srp6Y = getHeight() / 2 - 50 ;
		}
		
		float srp6FixH = (mSRPBmp.getHeight() - mSRPBmp.getHeight() * scaleSrp6) / 2;
		float srp6FixW = (mSRPBmp.getWidth() - mSRPBmp.getWidth() * scaleSrp6) / 2;
		srp6ScaleX = (srp6X + srp6FixW) / scaleSrp6;
		srp6ScaleY = (srp6Y + srp6FixH) / scaleSrp6;
		//7
		if(opt >=1) {
			srp7X = getWidth() / 2 + (SRP_7_X_POS_OFFSET * opt);
			srp7Y = getHeight() / 2 + (SRP_7_Y_POS_OFFSET * opt);
		}
		else {
			srp7X = getWidth() / 2 + (SRP_7_X_POS_OFFSET );
			srp7Y = getHeight() / 2 -20;
		}
		
		float srp7FixH = (mSRPBmp.getHeight() - mSRPBmp.getHeight() * scaleSrp7) / 2;
		float srp7FixW = (mSRPBmp.getWidth() - mSRPBmp.getWidth() * scaleSrp7) / 2;
		srp7ScaleX = (srp7X + srp7FixW) / scaleSrp7;
		srp7ScaleY = (srp7Y + srp7FixH) / scaleSrp7;
		//8
		if(opt >= 1) {
			srp8X = getWidth() / 2 + (SRP_8_X_POS_OFFSET * opt);
			srp8Y = (getHeight() / 2 + (SRP_8_Y_POS_OFFSET * opt));
		}
		else {
			srp8X = getWidth() / 2 - 90;
			srp8Y = (getHeight() / 2 );
		}
		
		float srp8FixH = (mSRPBmp.getHeight() - mSRPBmp.getHeight() * scaleSrp8) / 2;
		float srp8FixW = (mSRPBmp.getWidth() - mSRPBmp.getWidth() * scaleSrp8) / 2;
		srp8ScaleX = (srp8X + srp8FixW) / scaleSrp8;
		srp8ScaleY = (srp8Y + srp8FixH) / scaleSrp8;
		//9
		if(opt >= 1) {
			srp9X = getWidth() / 2;
			srp9Y = getHeight() / 2 + (SRP_9_Y_POS_OFFSET * opt);
		}
		else {
			srp9X = getWidth() / 2 + 10 ;
			srp9Y = getHeight() / 2 ;
		}
		
		float srp9FixH = (mSRPBmp.getHeight() - mSRPBmp.getHeight() * scaleSrp9) / 2;
		float srp9FixW = (mSRPBmp.getWidth() - mSRPBmp.getWidth() * scaleSrp9) / 2;
		srp9ScaleX = (srp9X + srp9FixW) / scaleSrp9;
		srp9ScaleY = (srp9Y + srp9FixH) / scaleSrp9;
		initLayer();
		state = ONE;
	};

	/** 初始化图层 */
	private void initLayer() {

	}

	public void setBoomRPEndListener(BoomRPEndListener listener) {
		mBoomRPEndListener = listener;
	}

	public interface BoomRPEndListener {
		public void end();
	}
}
