package com.pix.game.base.ui;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * 基本的Surfaceiew，封装了一些基本的操作，如线程，画笔，场景，及通用参数
 * @author Administrator
 *
 */
public abstract class BaseAnimSurfaceView extends SurfaceView implements Callback,Runnable{
	private static final String TAG ="BaseAnimSurfaceView";
	/** 游戏的主线程 */
	protected Thread mainThread;
	/**
	 * 以宽为基准的比例（如宽为720，opt=720/720=1,宽为360则为opt=720/360=2）
	 */
	protected float opt = 1;
	
	protected SurfaceHolder mSurfaceHolder;
	/** 游戏的画布 */
	protected Canvas mCanvas ;
	/** 游戏的画笔 */
	protected Paint mPaint ;
	
	protected OnDriverListener mOnDriverListener;
	/** 屏幕的宽度，在onLayout()中初始化，注意子类要用的化，可以自己初始化，也可以在父类onLayout()被调用后再使用 */
	protected int mScreenWidth ;
	/** 屏幕的高度，在onLayout()中初始化，注意子类要用的化，可以自己初始化，也可以在父类onLayout()被调用后再使用 */
	protected int mScreenHeight;
	/** 这个变量来控制绘制线程的开始与停止 */
	protected boolean isRunning ;
	/** 动画的状态 */
	protected int state ;
	
	public BaseAnimSurfaceView(Context context) {
		super(context);
		init();
	}
	public BaseAnimSurfaceView(Context context, AttributeSet attrs) {
		super(context,attrs);
		init();
	}
	private void init() {
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.RED);
		this.setKeepScreenOn(true);			//屏幕常亮
		initOpt();
		setZOrderOnTop(true);     
		getHolder().setFormat(PixelFormat.TRANSLUCENT); 
	}
	
	@Override
	public abstract void run() ;

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d(TAG, "surfaceChanged(),state:" + state);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated(),state:" + state);
		mainThread = new Thread(this);
		isRunning = true;
		mainThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surfaceDestroyed(),state:" + state);
		isRunning = false;
	}
	
	protected abstract void drawCanvas() ;
	
	/**
	 * 初始化适配参数，以720 * 1280为基准
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
	 * 设置监听器
	 * @param listener
	 */
	public void setOnDriverListener(OnDriverListener listener){
		mOnDriverListener = listener;
	}
	
	public interface OnDriverListener {
		void onDriverFinish();
		/**
		 * 由内存不够活内存溢出导致的动画没有播放错误
		 */
		void onError(String errorInfo);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		mScreenHeight = getHeight();
		mScreenWidth = getWidth();
		super.onLayout(changed, left, top, right, bottom);
	}

}
