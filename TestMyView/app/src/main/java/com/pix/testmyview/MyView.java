package com.pix.testmyview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.pix.testmyview.utils.AnimBitmapLoader;

/**
 * 自定义View
 * 目的是测试自定义View的相关属性，特性
 * 1、测试坐标，因为在写动画的时候遇到当图片缩放后，坐标混乱的问题，所以单独写个项目研究坐标问题
 * @author pix
 *
 */
public class MyView extends View {
	private static final String TAG = "MyView";
	/** 绘制的Bitmap */
	private Bitmap mImgBmp;
	/** 适配的参数 */
	private float opt ;
	/** 是否开始 */
	private boolean isRunning = false;
	/** 动画的状态 */
	private byte state = 0;
	/** 动画移动状态 */
	private static final byte STATE_MOVE = 1;
	/** 动画结束状态 */
	private static final byte STATE_FINISH = 5;
	/** 精灵线程 */
	private Thread mSpriteThread ;
	/** 绘制的消息 */
	private static final int HANDLER_DRAW = 1000;
	private final int HANDLER_LISTENER = 2;
	/** 绘制时间倍数 */
	private final int timeControl = 1;
	/** 重绘间隔时间 */
	private long sleepTime = 30 * timeControl;
	/** 图片的缩放 */
	private float scale = 0.1f;
	/** 图片的x坐标 */
	private float imgX;
	/** 图片的y坐标 */
	private float imgY;
	/** 图片缩放后的x坐标 */
	private float imgScaleX;
	/** 图片缩放后的y坐标 */
	private float imgScaleY;
	/** 图片移动的速度 */
	private float moveSpeed;
	/** 图片移动的距离 */
	private float img_move_x_distance  = 100 ;
	private float img_move_y_distance = 100 ;
	/** 图片的缩放最终值 */
	private float img_scale_x_end = 1f;
	private float img_scale_y_end = 1f;
	//每步的缩放值
	private float xStepScale;
	private float yStepScale;
	/** 图片移动的时间 */
	private static final float IMG_MOVE_TIME = 1000;
	/** 图片移动的步数 */
	private int moveStep;
	//每步的移动距离
	private float xStepDistance;
	private float yStepDistance;
	//移动步数计数器
	private int moveCount;
	

	public MyView(Context context) {
		super(context);
		initOpt();
		init();
	}

	/**
	 * 初始化适配的参数
	 */
	private void initOpt(){
		// 适配参数
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);

		float width = wm.getDefaultDisplay().getWidth();
		if(width>0) {
			opt = width / 720;
		}
		else {
			opt = 1 ;
		}
		
	}
	/**
	 * 初始化动画
	 */
	private void init() {
		//加载图片
		mImgBmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				"img.png", opt);
	}
	
	/**
	 * 开始动画
	 */
	public void start() {
		isRunning = false;
		mSpriteThread = new Thread(run);
		mSpriteThread.start();
	}
	
	/**
	 * 停止动画
	 */
	public void stop() {
		state = STATE_FINISH;
		isRunning = false;
	}

	
	/**
	 * 精灵线程
	 */
	private Runnable run = new Runnable() {
		public void run() {
			if (state != STATE_FINISH) {
				isRunning = true;
			}
			long curTime = 0;
			while (isRunning && state != STATE_FINISH) {
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
			// 销毁图片
			destoryBitmap();
			mHandler.sendEmptyMessage(HANDLER_LISTENER);
		}
	};
	
	/**
	 * 动画的Handler
	 */
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
	
	
	private void move() {
		Log.d(TAG, "move(),state:" + state);
		switch (state) {
		case STATE_MOVE:
			moveImg();
			break;
		case STATE_FINISH:
			stop();
			break;
		}
	}
	/**
	 * 移动图片
	 */
	private void moveImg() {
		Log.d(TAG, "moveImg() run...");
		
		if(moveCount>=moveStep) {  //补数超过则不再增加
//			state = STATE_FINISH;
			return;
		}
		//增加移动距离
		imgScaleX += xStepDistance;
		imgScaleY += yStepDistance;
		scale += xStepScale;
		moveCount++;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch(state) {
			case STATE_MOVE:
				drawMove(canvas);
				break;
			case STATE_FINISH:
				break;
		}
	}
	/**
	 * 绘制动画移动
	 */
	private void drawMove(Canvas canvas) {
		Log.d(TAG, "drawMove(),imgScaleX:" + imgScaleX + ",imgScaleY:" + imgScaleY);
		canvas.save();
		canvas.scale(scale, scale);
		canvas.drawBitmap(mImgBmp, imgScaleX, imgScaleY, null);
		canvas.restore();
		
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if(state != 0) { //动画未结束，不计算
			return ;
		}
		if(mImgBmp == null) {
			initOpt();
			init();
		}
		imgX = 0;
		imgY = 0;
		imgScaleX = 0;
		imgScaleY = 0;
		Log.d(TAG, "onLayoput(),img width:" + mImgBmp.getWidth() + ",img height:" + mImgBmp.getHeight());
		//算出步数，总时间 / 每步时间
		moveStep = (int)(IMG_MOVE_TIME / sleepTime);
		Log.d(TAG,"onLayout(),总步数:" + moveStep);
		//算出每步走的距离  总距离 /总步数
		xStepDistance = img_move_x_distance / moveStep;
		yStepDistance = img_move_y_distance / moveStep;
		//算出每步缩放值
		xStepScale = (img_scale_x_end - scale) / moveStep;
		yStepScale = (img_scale_y_end - scale) / moveStep;
		state = STATE_MOVE;
	}
	private void destoryBitmap() {
	}
	
	protected OnDriverListener mDriverlistener;
	protected OnGiftListener mGiftListener;
	/**
	 * 设置座驾动画监听
	 * @param listener
	 */
	public void setOnDriverListener(OnDriverListener listener){
		mDriverlistener = listener;
	}
	/**
	 * 设置礼物动画监听
	 * @param listener
	 */
	public void setOnGiftListener(OnGiftListener listener){
		mGiftListener = listener;
	}
	
	public interface OnDriverListener {
		void onDriverFinish();
		/**
		 * 由内存不够活内存溢出导致的动画没有播放错误
		 */
		void onError(String errorInfo);
	}
	public interface OnGiftListener{
		void onGiftAnimFinish();
		/**
		 * 由内存不够活内存溢出导致的动画没有播放错误
		 */
		void onError(String errorInfo);
	}

}
