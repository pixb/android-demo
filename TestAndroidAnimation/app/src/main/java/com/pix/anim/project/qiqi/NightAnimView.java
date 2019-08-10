package com.pix.anim.project.qiqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;

import com.pix.anim.bean.GuaGuaAnimView;
import com.pix.anim.utils.AnimBitmapLoader;

public class NightAnimView extends GuaGuaAnimView {
	
	private static final String TAG = "CloudAnimView";
	
	private static final int FINISH = 1;
	
	private static final int MOVE = 2;
	
	private static final int MOVE_STOP = 3;
	/** -- 画笔 --*/
	private Paint paint = new Paint();
	/** --精灵线程-- */
	private Thread spriteThread;
	/**
	 * 休息的时间
	 */
	private int sleepTime = 50;
	/**
	 * 移动的时间
	 */
	private int tranTime = 3000;
	
	private float nightX = 0;
	private float nightY = 0;
	
	private float nightX2 = 0;
	private float nightY2 = 0;
	
	private float tranY = 0;
	
	
	
	/** -- 状态 -- */
	private int state = 0;
	
	private Bitmap nightBitmap ;
	private Bitmap nightBitmap2;
	
	private float cloudScaleX = 1f;
	private float cloudScaleY = 1f;
	
	private final int HANDLER_DRAW = 1;
	private final int HANDLER_LISTENER = 2;
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
			}
		};
	};

	public NightAnimView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	


	//放置View的位置,初始化参数
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, TAG + " onLayout Func run");
		// TODO Auto-generated method stub
		if(state != 0){
			return;
		}
		init();
		if(state == FINISH){
			return;
		}
		//移动的时间/休息的时间
		float tranStep = tranTime / sleepTime;
		
		nightX = 0;
		nightY = 0;
		nightX2 = 0;
		nightY2 = getHeight();
		tranY = getHeight() / tranStep ;

	}
	
	/**
	 * 初始化的方法
	 * 将资源中图片加载，编程Bitmap
	 */
	private void init() {
		// TODO Auto-generated method stub
		try {
//			nightBitmap = BitmapFactory.decodeStream(getContext().getAssets().open("fly/night.jpg"));
			nightBitmap = AnimBitmapLoader.getInstance().
					getBitmap(getContext(),"fly/night.jpg", getWidth() * 1.0f, getHeight() * 1.0f);
			nightBitmap2 = nightBitmap;
			
			state = MOVE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			state = FINISH;
		} catch(OutOfMemoryError e)
		{
			if(mDriverlistener != null){
				mDriverlistener.onError("飞行动画");
			}
			state = FINISH;//如果内存不够导致解析图片没有，则不会播放动画
			System.gc();
		}
	}
	
	/**
	 * 启动动画
	 */
	public void start() {
		running = false;
		spriteThread = new Thread(run);
		spriteThread.start();
	}
	
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
	
	protected void onDraw(Canvas canvas) {
		switch (state) {
		case MOVE:
			drawNight(canvas);
			break;
		
		default:
		}
	};
	
	
	private void move() {
		Log.d(TAG, TAG + " move Func run");
		switch (state) {
		case MOVE:
			nightMove();
			break;
		case MOVE_STOP:
			running = false;
			state = FINISH;
			break;
		}
	}



	
	private void nightMove() {
		if(nightBitmap == null)
		{
			return ;
		}
		// TODO Auto-generated method stub
		nightY -= tranY;
		if(nightY <= -getHeight())
		{
			nightY = 0;
		}
		nightY2 -= tranY;
		
		if(nightY2 <= 0)
		{
			nightY2 = getHeight();
		}
	}
	
	private void destoryBitmap(){
		if(nightBitmap != null){
			nightBitmap.recycle();
			nightBitmap = null;
		}
//		for(int i = 0; i < bit_frames.length; i ++){
//			bit_frames[i].recycle();
//		}
	}
	
	
	void drawNight(Canvas canvas)
	{
		if(nightBitmap == null || nightBitmap2 == null)
		{
			return ;
		}
		float fixX = nightX / cloudScaleX;
		float fixY = nightY / cloudScaleY;
		
		float fixX2 = nightX2 / cloudScaleX;
		float fixY2 = nightY2 / cloudScaleY;
		canvas.save();
		canvas.scale(cloudScaleX, cloudScaleY);
		Log.d(TAG, TAG + " drawNight : fixX :" + fixX + ",fixY:" + fixY);
		canvas.drawBitmap(nightBitmap, fixX, fixY, null);
		canvas.drawBitmap(nightBitmap2, fixX2, fixY2, null);
		
		canvas.restore();
	}

}
