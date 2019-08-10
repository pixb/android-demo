package com.pix.anim.project.guagua;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;


import com.pix.anim.bean.GuaGuaAnimView;

import java.io.IOException;


public class NoviceDialogView extends GuaGuaAnimView {
    /**画笔*/
	private Paint paint = new Paint();
	/**精灵线程*/
	private Thread spriteThread;
	/**动画的状态*/
	private int state = 0;
	private static final int ONE = 1 ;
	private static final int TWO = 2 ;
	private static final int THREE = 3 ;
	private static final int  FOUR = 4 ;
	private static final int FINISH = 5;
	private static final String TAG = "NoviceDialogView";
	
	/** 白色光图片 */
	private Bitmap mWhiteLightBmp ;
	/** 旋转光图片*/
	private Bitmap mRotateLightBmp ;
	/** 锁背景的图片 */
	private Bitmap mLockBgBmp ;
	private Bitmap mLockBearBmp ;
	private Bitmap mLockPrBmp ;
	private Bitmap mLockHeadBmp ;
	/** 休息的时间 */
	private int sleepTime = 50;
	/** 移动的时间*/
	private int tranTime = 300;
	/** 白光的x坐标 */
	private float whiteLightX = 0;
	/** 白光的y坐标 */
	private float whiteLightY = 0;
	/** 旋转光的x坐标 */
	private float rotateLightX = 0;
	/** 旋转光的y坐标 */
	private float rotateLightY = 0;
	/** 旋转光缩放的x坐标 */
	private float rotateLightScaleX = 0;
	/** 旋转光缩放的y坐标 */
	private float rotateLightScaleY = 0;
	/** 锁前图的x坐标 */
	private float lockPreX = 0f ;
	/** 锁前图的y坐标 */
	private float lockPreY = 0f ;
	/** 锁小熊图的x坐标 */
	private float lockBearX = 0f ;
	/** 锁小熊图的y坐标 */
	private float lockBearY = 0f ;
	/** 锁背景图的x坐标 */
	private float lockBgX = 0f ;
	/** 锁背景图的y坐标 */
	private float lockBgY = 0f ;
	/** 锁头图的x坐标 */
	private float lockHeadX = 0f ;
	/** 锁头图的x坐标 */
	private float lockHeadY = 0f ;
	/** 旋转光的起始大小 */
	private float scaleRotateLight = 0.1f;
	/** 缩放步长 */
	private float scaleStep = 0.02f;
	/** 旋转角度 */
	private float rotateRotateLightAngle = 0;
	/** 选择的步长*/
	private float rotateAngleStep = 2 ;
	/** 绘制的消息 */
	private final int HANDLER_DRAW = 1;
	/** 监听消息 */
	private final int HANDLER_LISTENER = 2;
	/** 运行第二步 */
	private final int RUN_TWO = 3;
	private Matrix mMatrix ;
	/** Handler消息 */
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case HANDLER_DRAW:
				invalidate();
				break;
			case HANDLER_LISTENER:
				if(mDriverlistener != null){
					mDriverlistener.onDriverFinish();
				}
				break;
			case RUN_TWO: //运行第二步
				state = TWO ;
				break ;
			}
		};
	};
	
	public NoviceDialogView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	//启动动画
	@Override
	public void start() {
		running = false;
		spriteThread = new Thread(run);
		spriteThread.start();
		//开始2秒后，切换到2
		mHandler.sendEmptyMessageDelayed(RUN_TWO, 2000);
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
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
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
			break;
		case TWO:
			moveTwo();
			break;
//		case THREE:
//			moveTree();
//			break;
//		case THREE_STOP:
//			moveStop();
//			break;
//		case THREE_ROTATE:
//			moveRotate();
//			break;
//		case THREE_OUT:
//			moveOut();
//			break;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		switch (state) {
		case ONE:
			drawOne(canvas);
			break;
		case TWO:
			drawOne(canvas);
			drawTwo(canvas);
			drawLock(canvas);
			break;
//		case THREE:
//			drawTree(canvas);
//			break;
//		case THREE_STOP:
//			drawTreeStop(canvas);
//			break;
//		case THREE_ROTATE:
//			drawTreeRotate(canvas);
//			break;
//		case THREE_OUT:
//			drawTreeRotate(canvas);
//			break;
//		default:
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
		if (scaleRotateLight > 2) {
			scaleRotateLight= 2;
		}
		rotateRotateLightAngle += rotateAngleStep;
		if(rotateRotateLightAngle == 360) {
			rotateRotateLightAngle = 0;
		}
		//缩放补充高度
		float fixH = (mRotateLightBmp.getHeight() - mRotateLightBmp.getHeight() * scaleRotateLight) / 2;
		float fixW = (mRotateLightBmp.getWidth() - mRotateLightBmp.getWidth() * scaleRotateLight) / 2;
		rotateLightScaleX = (rotateLightX + fixW) / scaleRotateLight;
		rotateLightScaleY = (rotateLightY + fixH) / scaleRotateLight;
		
	}
	/**
	 * 绘制第一步
	 * TODO
	 * @param canvas
	 * @return: void
	 */
	private void drawOne(Canvas canvas) {
		canvas.save();
		canvas.drawBitmap(mWhiteLightBmp, whiteLightX, whiteLightY, null);
		Log.d(TAG, "x-->One:" + whiteLightX +",y-->" + whiteLightY);
		canvas.restore();
	}
	private void drawTwo(Canvas canvas) {
		canvas.save();
		canvas.scale(scaleRotateLight, scaleRotateLight);
		
//		canvas.drawBitmap(mRotateLightBmp, rotateLightScaleX, rotateLightScaleY, null);
		mMatrix.setTranslate(rotateLightScaleX, rotateLightScaleY);     //设置图片的旋转中心，即绕（X,Y）这点进行中心旋转
		mMatrix.preRotate(rotateRotateLightAngle,mRotateLightBmp.getWidth()/ 2,mRotateLightBmp.getHeight() / 2);  //要旋转的角度
		canvas.drawBitmap(mRotateLightBmp, mMatrix, null);
		canvas.restore();
	}
	
	private void drawLock(Canvas canvas) {
		canvas.save();
//		canvas.scale(scaleRotateLight, scaleRotateLight);
		canvas.drawBitmap(mLockHeadBmp, lockHeadX, lockHeadY, null);
		canvas.drawBitmap(mLockBgBmp, lockBgX, lockBgY, null);
		canvas.drawBitmap(mLockBearBmp, lockBearX, lockBearY, null);
		canvas.drawBitmap(mLockPrBmp, lockPreX,lockPreY,null);
		canvas.restore();
		
	}
	/**
	 * 初始化
	 */
	public void init() {
		try {
			//加载白光图片
			mWhiteLightBmp = BitmapFactory.decodeStream(getContext().getAssets().open(
					"white_light.png"));
			//加载旋转的图片
			mRotateLightBmp = BitmapFactory.decodeStream(getContext().getAssets().open(
					"rotate_light.png"));
			//加载锁相关的图片
			mLockPrBmp = BitmapFactory.decodeStream(getContext().getAssets().open(
					"lock_preview.png"));
			mLockBearBmp = BitmapFactory.decodeStream(getContext().getAssets().open(
					"lock_bear.png"));
			mLockBgBmp = BitmapFactory.decodeStream(getContext().getAssets().open(
					"lock_background.png"));
			mLockHeadBmp = BitmapFactory.decodeStream(getContext().getAssets().open(
					"lock_head.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}catch(OutOfMemoryError oe){
			if(mDriverlistener != null){
				mDriverlistener.onError("飞行动画");
			}
			state = FINISH;//如果内存不够导致解析图片没有，则不会播放动画
			System.gc();
		}
		mMatrix = new Matrix();
	}
	
	private void destoryBitmap(){
		if(mWhiteLightBmp != null){
			mWhiteLightBmp.recycle();
			mWhiteLightBmp = null;
		}
		if(mRotateLightBmp != null){
			mRotateLightBmp.recycle();
			mRotateLightBmp = null;
		}
		
	}
	
	/**
	 * 绘制布局
	 * @see android.view.View#onLayout(boolean, int, int, int, int)
	 * TODO
	 */
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if(state != 0){
			return;
		}
		init();
		if(state == FINISH){
			return;
		}
		//移动的时间/休息的时间
		float tranStep = tranTime / sleepTime;
		//x初始化屏宽/2
		whiteLightX = getWidth()/2 - mWhiteLightBmp.getWidth() / 2;
		//y屏幕的1/2
		whiteLightY = (getHeight() - mWhiteLightBmp.getHeight()) /2;
		//x初始化屏宽
		rotateLightX = (getWidth()-mRotateLightBmp.getWidth()) / 2 ; 
		//y屏幕的1/2
		rotateLightY = (getHeight() - mRotateLightBmp.getHeight())/ 2 ;
		
		lockPreX = (getWidth() - mLockPrBmp.getWidth()) / 2 ;
		
		lockPreY = (getHeight() - mLockPrBmp.getHeight()) / 2 ;
		
		lockBearX = (getWidth() - mLockBearBmp.getWidth()) / 2 ;
		
		lockBearY = (getHeight() - mLockBearBmp.getHeight()) / 2 ;
		
		lockBgX = (getWidth() - mLockBgBmp.getWidth()) / 2 ;
		
		lockBgY = (getHeight() - mLockBgBmp.getHeight()) / 2 ;
		
		lockHeadX = (getWidth() - mLockHeadBmp.getWidth()) / 2 ;
		
		lockHeadY = (getHeight() ) / 2 - mLockHeadBmp.getHeight() - 10 ;
		//缩放补充高度
		float fixH = (mRotateLightBmp.getHeight() - mRotateLightBmp.getHeight() * scaleRotateLight) / 2;
		float fixW = (mRotateLightBmp.getWidth() - mRotateLightBmp.getWidth() * scaleRotateLight) / 2;
		rotateLightScaleX = (rotateLightX + fixW) / scaleRotateLight;
		rotateLightScaleY = (rotateLightY + fixH) / scaleRotateLight;
		Log.d(TAG, "FUNC onLayout(),rotateLightScaleX-->:" + rotateLightScaleX +",rotateLightScaleY-->" + rotateLightScaleY);
		initLayer();
		state = ONE;
	};
	
	/** 初始化图层 */
	private void initLayer() {
		
	}

}
