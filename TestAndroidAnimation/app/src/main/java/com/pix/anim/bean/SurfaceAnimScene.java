package com.pix.anim.bean;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

public abstract class SurfaceAnimScene extends SurfaceView implements Callback,
		Runnable {

	/** 用于控制SurfaceView的SurfaceHolder */
	private SurfaceHolder mSfh;
	/** 游戏的画布 */
	private Canvas mCanvas;
	/** 屏幕的宽度 */
	private float mScreenWidth;
	/** 屏幕的高度 */
	private float mScreenHeight;
	// 结束状态
	protected final static int STATE_FINISH = 0xff;
	/**
	 * 默认的间隔时间
	 */
	protected final int DEFAULT_SLEEP_TIME = 30;
	/**
	 * 以宽为基准的比例（如宽为720，opt=720/720=1,宽为360则为opt=720/360=2）
	 */
	protected float mOpt = 1;
	protected boolean mIsRunning = false;
	// 初始状态
	protected int mState = 0;
	/**
	 * 间隔时间
	 */
	public int mSleepTime = DEFAULT_SLEEP_TIME;
	/** 精灵线程 */
	private Thread mSpriteThread;
	/**
	 * 公用绘制画笔
	 */
	protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	/**
	 * 运行状态监听器
	 */
	public RunStateListener mRunstateListener;

	public SurfaceAnimScene(Context context) {
		super(context);
		initOpt();
		// 取得surface holder
		mSfh = getHolder();
		// 添加状态回调函数
		mSfh.addCallback(this);
		this.setZOrderOnTop(true);//设置画布  背景透明
	    this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}
	
	/**
	 * 初始化适配参数，以屏幕宽度为准
	 */
	private void initOpt(){
		// 适配参数
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);

		float width = wm.getDefaultDisplay().getWidth();
		if(width>0) {
			mOpt = width / 720;
		}
		else {
			mOpt = 1 ;
		}
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mScreenWidth = getWidth();
		mScreenHeight = getHeight();
		// 实例化线程
		mSpriteThread = new Thread(this);
		mSpriteThread.start();
		initParams();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mIsRunning = false;
	}

	@Override
	public void run() {
		mIsRunning = true;
		long curTime = 0;
		while (mIsRunning) {
			curTime = System.currentTimeMillis();
			_drawScene();
			move();
			mHandler.sendEmptyMessage(HANDLER_DRAW);
			curTime = System.currentTimeMillis() - curTime;
			try {
				if (curTime < mSleepTime) {
					Thread.sleep(mSleepTime - curTime);
				}
			} catch (InterruptedException e) {
				break;
			}
		}
		
		

	}
	
	

	private void _drawScene() {
		try {
			mCanvas = mSfh.lockCanvas();
			mCanvas.drawColor(Color.BLACK,Mode.CLEAR);
			drawScene(mCanvas);
//			Paint paint =  new Paint();
//			paint.setColor(Color.GREEN);
//			mCanvas.drawRect(new Rect(100,200,300,400), paint);
		} catch (Exception e) {

		} finally {
			if (mCanvas != null) {
				mSfh.unlockCanvasAndPost(mCanvas);
			}
		}

	}

	protected abstract void drawScene(Canvas canvas);
	
	/**
	 * 启动动画
	 */
	public void start() {
		if(mSpriteThread !=null && mIsRunning == false) {
			mSpriteThread.start();
		}
		init();
		initParams();
		if(mRunstateListener !=null) {
			mRunstateListener.onAnimStart();
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
				if(mRunstateListener !=null) {
					mRunstateListener.onAnimFinish();
				}
				// 销毁图片
				onDestroy();
				break;
			}
		};
	};
	
	/**
	 * 设置动画的间隔时间，默认值30ms
	 * @param time 间隔时间，单位ms
	 */
	public void setSleepTime(int time) {
		this.mSleepTime = time;
	}
	
	/**
	 * 取得间隔时间
	 * @return
	 */
	public int getSleepTime() {
		return this.mSleepTime;
	}
	/**
	 * 移动的方法，动画变换一次
	 */
	public abstract void move();
	
	@Override
	protected final void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mIsRunning = false;
	}
	
	protected OnGiftListener mGiftListener;
	
	public interface OnDriverListener {
		void onDriverFinish();
		/**
		 * 由内存不够活内存溢出导致的动画没有播放错误
		 */
		void onError(String errorInfo);
	}
	
	protected void animFinished() {
		mHandler.sendEmptyMessage(HANDLER_LISTENER);
	}
	/**
	 * 设置礼物动画监听
	 * @param listener
	 */
	public void setOnGiftListener(OnGiftListener listener){
		mGiftListener = listener;
	}
	
	
	public interface OnGiftListener{
		void onGiftAnimFinish();
		/**
		 * 由内存不够活内存溢出导致的动画没有播放错误
		 */
		void onError(String errorInfo);
	}
	
	/**
	 * 停止动画
	 */
	public void stop() {
		mState = STATE_FINISH;
		mIsRunning = false;
	}
	
	/**
	 * 销毁时调用的方法
	 */
	public abstract void onDestroy();
	public abstract void initParams();
	
	/**
	 * 初始化方法，主要用来初始化图片等信息
	 */
	public abstract void init();
	
	public void addRunStateListener(RunStateListener listener) {
		this.mRunstateListener = listener;
	}
	/**
	 * 运行状态监听器
	 * @author tpx
	 *
	 */
	public interface RunStateListener {
		public void onAnimStart();
		public void onAnimFinish();
	}
}
