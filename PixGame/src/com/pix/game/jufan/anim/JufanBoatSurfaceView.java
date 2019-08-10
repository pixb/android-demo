package com.pix.game.jufan.anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.os.Handler;
import android.util.Log;

import com.pix.game.base.ui.BaseAnimSurfaceView;
import com.pix.game.util.AnimBitmapLoader;
/**
 * 聚范轮船动画
 * @author tpx
 *
 */
public class JufanBoatSurfaceView extends BaseAnimSurfaceView {
	
	private static final String TAG = "JufanBoatView";
	
	private static final String BASE_PATH = "jufan";
	/** 轮船的图片 */
	private Bitmap mBoatBmp ;
	/** 轮船入场状态 */
	private static final int STATE_BOAT_IN = 1;
	/** 船浮动状态 */
	private static final int STATE_BOAT_FLOATING = 2;
	
	private static final int STATE_BOAT_OUT = 3;
	
	/** 结束状态 */
	private static final int STATE_FINISH = 5 ;
	/** 重绘间隔时间 30ms */
	private static final int SLEEP_TIME = 30;
	/** 轮船进入移动时间 */
	private static final int BOAT_MOVE_IN_TIME = 4000;
	/** 轮船游出的时间 */
	private static final int BOAT_MOVE_OUT_TIME = 3000;
	
	private Paint mPaint ;

	private JufanBoatAnimCallback mAnimCallback;

	private long mSenderId;
	private String mSenderName;
	
	
	//=============================轮船相关
	/** 轮船进入移动步数计数器 */
	private int mBoatMoveInStepCount;
	/** 轮船移动总步数 */
	private int mBoatMoveInStepNum;
	/** 轮船的初始缩放比 */
	private static final float BOAT_SCALE_START = 0.3f;
	/** 轮船移动缩放比 */
	private static final float BOAT_SCALE_END = 1f;
	/** 轮船移动开始点x坐标 */
	private int mBoatMoveStartX;
	/** 轮船移动开始点y坐标 */
	private int mBoatMoveStartY;
	/** 轮船移动x方向距离 */
	private int mBoatMoveXDistance ;
	/** 轮船移动y方向距离 */
	private int mBoatMoveYDistance ;
	/** 轮船图片缩放系数 */
	private float mBoatScale = BOAT_SCALE_START;
	/** 轮船移动的补差x距离*/
	private float mBoatMoveOffsetXDistance;
	/** 轮船移动的补差y */
	private float mBoatMoveOffsetYDistance;
	/** 轮船x轴每步走动的距离 */
	private float mBoatMoveInStepXDistance;
	/** 轮船y轴每步走动的距离 */
	private float mBoatMoveInStepYDistance;
	/** 轮船每步的缩放值 */
	private float mBoatMoveInStepScale;
	
	//轮船浮动相关
	private int mFirstFloatStepCount ;
	/** 第一次浮动每步走的距离 */
	private float mFirstFloatYStepDistance ;
	private boolean isFirstFloating = true ;
	
	private int mSecondFloatStepCount ;
	private float mSecondFloatYStepDistance;
	private boolean isSecondFloating = false ;
	
	//轮船游出的相关参数
	/** 轮船进入移动步数计数器 */
	private int mBoatMoveOutStepCount;
	/** 轮船移动总步数 */
	private int mBoatMoveOutStepNum;
	
	/** 轮船的初始缩放比 */
	private static final float BOAT_MOVE_OUT_SCALE_START = 1f;
	/** 轮船移动缩放比 */
	private static final float BOAT_MOVE_OUT_SCALE_END = 1.5f;
	
	private static final float SEA_ALPHA_END =255 * 0.3f;
	
	/** 轮船x轴每步走动的距离 */
	private float mBoatMoveOutStepXDistance;
	/** 轮船y轴每步走动的距离 */
	private float mBoatMoveOutStepYDistance;
	/** 轮船每步的缩放值 */
	private float mBoatMoveOutStepScale;
	
	/** 轮船游走移动x方向距离 */
	private int mBoatMoveOutXDistance ;
	/** 轮船游走移动y方向距离 */
	private int mBoatMoveOutYDistance ;
	
	
	//==========================================水面相关
	/** 水面的图片 */
	private Bitmap mSeaBmp1 ;
	private Bitmap mSeaBmp2 ;
	private Bitmap mSeaBmp3 ;

	private static final int SEA_SCALE = 2;
	
	/** 波浪1开始x坐标 */
	private int mSea1MoveStartX;
	/** 波浪1开始y坐标 */
	private int mSea1MoveStartY;
	
	/** 波浪2开始x坐标 */
	private int mSea2MoveStartX;
	/** 波浪2开始y坐标 */
	private int mSea2MoveStartY;
	
	/** 波浪3开始x坐标 */
	private int mSea3MoveStartX;
	/** 波浪3开始y坐标 */
	private int mSea3MoveStartY;
	
	/** 波浪1移动x方向距离 */
	private int mSea1MoveXDistance ;
	/** 波浪1移动y方向距离 */
	private int mSea1MoveYDistance ;
	/** 波浪2移动x方向距离 */
	private int mSea2MoveXDistance ;
	/** 波浪2移动y方向距离 */
	private int mSea2MoveYDistance ;
	/** 波浪3移动x方向距离 */
	private int mSea3MoveXDistance ;
	/** 波浪3移动y方向距离 */
	private int mSea3MoveYDistance ;
	
	private float mSea1MoveXStepDistance ;
	private float mSea1MoveYStepDistance ;
	
	private float mSea2MoveXStepDistance ;
	private float mSea2MoveYStepDistance ;
	
	private float mSea3MoveXStepDistance ;
	private float mSea3MoveYStepDistance ;
	
	private float mSea1MoveXOffset ;
	private float mSea1MoveYOffset ;
	
	private float mSea2MoveXOffset ;
	private float mSea2MoveYOffset ;
	
	private float mSea3MoveXOffset ;
	private float mSea3MoveYOffset ;
	
	private float mSea1Alpha ;
	private float mSea2Alpha ;
	private float mSea3Alpha ;
	private float mBoatMoveInSeaStepAlpha;
	private float mBoatMoveOutSeaStepAlpha ;
	
	//===================================烟花相关
	private Bitmap [] mFireworksFrames = new Bitmap[12];
	//烟花的索引
	private int mFireworks1Index ;
	private int mFireworks2Index ;
	private int mFireworks3Index ;
	private int mFireworks4Index ;
	private int mFireworks5Index ;
	
	//烟花的缩放比
	private float mFireworks1Scale = 1f;
	private float mFireworks2Scale = 0.6f;
	private float mFireworks3Scale = 0.4f;
	private float mFireworks4Scale = 0.4f;
	private float mFireworks5Scale = 0.8f;
	
	//烟花的位置偏移量
	private float mFireworks1XOffset;
	private float mFireworks1YOffset;
	private float mFireworks2XOffset;
	private float mFireworks2YOffset;
	private float mFireworks3XOffset;
	private float mFireworks3YOffset;
	private float mFireworks4XOffset;
	private float mFireworks4YOffset;
	private float mFireworks5XOffset;
	private float mFireworks5YOffset;
	
	private static final int FIREWORKS_2_START_STEP = 20 ;
	private static final int FIREWORKS_3_START_STEP = 40 ;
	private static final int FIREWORKS_4_START_STEP = 30 ;
	private static final int FIREWORKS_5_START_STEP = 30 ;
	/** 烟花的总步数 */
	private int mFireworksStepCount;
	

	public JufanBoatSurfaceView(Context context) {
		super(context);
	}
	
	private final int HANDLER_DRAW = 1;
	private final int HANDLER_LISTENER = 2;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HANDLER_DRAW:
				
				break;
			case HANDLER_LISTENER:
				if (mOnDriverListener != null) {
					mOnDriverListener.onDriverFinish();
				}
				break;
			}
		};
	};
	public void stop() {
		Log.d(TAG, "stop(),timestamp:" + System.currentTimeMillis());
		state = STATE_FINISH;
		isRunning = false;
	}
	
	private void destoryBitmap() {
		if(mBoatBmp != null) {
			mBoatBmp.recycle();
			mBoatBmp = null;
		}
		if(mSeaBmp1 != null) {
			mSeaBmp1.recycle();
			mSeaBmp1 = null;
		}
		if(mSeaBmp2 != null) {
			mSeaBmp2.recycle();
			mSeaBmp2 = null;
		}
		if(mSeaBmp3 != null) {
			mSeaBmp3.recycle();
			mSeaBmp3 = null;
		}
		if(mFireworksFrames!= null) {
			for(int i = 0; i < 12 ;i ++) {
				if(mFireworksFrames[i] != null) {
					mFireworksFrames[i].recycle();
					mFireworksFrames[i] = null;
				}

			}
			mFireworksFrames = null;
		}

		System.gc();
	}
	
	private void initView() {
		String boatStr = BASE_PATH + "/live_boat.png";
		String seaStr1 = BASE_PATH + "/live_sea_1.png";
		String seaStr2 = BASE_PATH + "/live_sea_2.png";
		String seaStr3 = BASE_PATH + "/live_sea_3.png";
		String fireworksStr =BASE_PATH + "/fireworks/fireworks";
		
		
		mPaint = new Paint();
		
		mBoatBmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				boatStr, opt);
		mSeaBmp1 = AnimBitmapLoader.getInstance().getBitmap(getContext(), seaStr1, opt);
		mSeaBmp2 = AnimBitmapLoader.getInstance().getBitmap(getContext(), seaStr2, opt);
		mSeaBmp3 = AnimBitmapLoader.getInstance().getBitmap(getContext(), seaStr3, opt);
		if (mBoatBmp == null || mSeaBmp1 == null || mSeaBmp2 == null || mSeaBmp3 == null) {
			Log.d(TAG, "init(),(Bitmap == null)");
			if (mOnDriverListener != null) {
				mOnDriverListener.onError("轮船动画");
			}
			state = STATE_FINISH;
		}
		
		//加载烟花序列帧
		for(int i = 0;i < 12;i++) {
			Bitmap bm = AnimBitmapLoader.getInstance().getBitmap(getContext(),
					fireworksStr + "_" + i +".png", opt);
			if(bm == null) {
				if (mOnDriverListener != null) {
					mOnDriverListener.onError("轮船动画");
				}
				state = STATE_FINISH;
			}
			mFireworksFrames[i] = bm;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
	}
	
	/**
	 * 绘制轮船
	 * @param canvas
	 */
	private void drawBoat(Canvas canvas) {
		canvas.save();
		canvas.translate(mBoatMoveStartX + mBoatMoveOffsetXDistance ,
				mBoatMoveStartY + mBoatMoveOffsetYDistance);
		canvas.scale(mBoatScale, mBoatScale);
		canvas.drawBitmap(mBoatBmp, 0, 0, null);
		canvas.restore();
	}
	/**
	 * 绘制海浪
	 * */
	private void drawSea(Canvas canvas) {
		drawSea2(canvas);
		drawSea1(canvas);
		drawSea3(canvas);
	}
	
	private void drawSea1(Canvas canvas) {
		canvas.save();
		canvas.translate(mSea1MoveStartX + mSea1MoveXOffset ,
				mSea1MoveStartY + mSea1MoveYOffset);
		canvas.scale(SEA_SCALE, SEA_SCALE);
		mPaint.setAlpha((int)mSea1Alpha);
		canvas.drawBitmap(mSeaBmp1, 0, 0, mPaint);
		canvas.restore();
//		Log.d(TAG, "drawSea1(),mSea1Alpha:" + mSea1Alpha);
	}
	
	private void drawSea2(Canvas canvas) {
		canvas.save();
		canvas.translate(mSea2MoveStartX + mSea2MoveXOffset ,
				mSea2MoveStartY + mSea2MoveYOffset);
		canvas.scale(SEA_SCALE, SEA_SCALE);
		mPaint.setAlpha((int)mSea2Alpha);
		canvas.drawBitmap(mSeaBmp2, 0, 0, mPaint);
		canvas.restore();
		//Log.d(TAG, "drawSea2(),mSea2Alpha:" + mSea2Alpha);
	}
	
	private void drawSea3(Canvas canvas) {
		canvas.save();
		canvas.translate(mSea3MoveStartX + mSea3MoveXOffset ,
				mSea3MoveStartY + mSea3MoveYOffset);
		canvas.scale(SEA_SCALE, SEA_SCALE);
		mPaint.setAlpha((int)mSea3Alpha);
		canvas.drawBitmap(mSeaBmp3, 0, 0, mPaint);
		canvas.restore();
		//Log.d(TAG, "drawSea3(),mSea3Alpha:" + mSea3Alpha);
	}
	
	private void drawFireworks(Canvas canvas) {
		if(mFireworks1Index < mFireworksFrames.length) {
			drawFireworks1(canvas);
		}
		
		if(mFireworksStepCount > FIREWORKS_2_START_STEP && mFireworks2Index < mFireworksFrames.length) {
			drawFireworks2(canvas);
		}
		
		if(mFireworksStepCount > FIREWORKS_3_START_STEP && mFireworks3Index < mFireworksFrames.length) {
			drawFireworks3(canvas);
		}
		
		if(mFireworksStepCount > FIREWORKS_4_START_STEP && mFireworks4Index < mFireworksFrames.length) {
			drawFireworks4(canvas);
		}
		
		if(mFireworksStepCount > FIREWORKS_5_START_STEP && mFireworks5Index < mFireworksFrames.length) {
			drawFireworks5(canvas);
		}
	}
	
	private void drawFireworks1(Canvas canvas) {
		canvas.save();
		canvas.translate(mFireworks1XOffset,mFireworks1YOffset);
		canvas.scale(mFireworks1Scale, mFireworks1Scale);
		canvas.drawBitmap(mFireworksFrames[mFireworks1Index], 0, 0,null);
		canvas.restore();
	}
	
	private void drawFireworks2(Canvas canvas) {
		canvas.save();
		canvas.translate(mFireworks2XOffset,mFireworks2YOffset);
		canvas.scale(mFireworks2Scale,mFireworks2Scale);
		canvas.drawBitmap(mFireworksFrames[mFireworks2Index], 0, 0,null);
		canvas.restore();
	}
	
	private void drawFireworks3(Canvas canvas) {
		canvas.save();
		canvas.translate(mFireworks3XOffset,mFireworks3YOffset);
		canvas.scale(mFireworks3Scale,mFireworks3Scale);
		canvas.drawBitmap(mFireworksFrames[mFireworks3Index], 0, 0,null);
		canvas.restore();
	}
	
	private void drawFireworks4(Canvas canvas) {
		canvas.save();
		canvas.translate(mFireworks4XOffset,mFireworks4YOffset);
		canvas.scale(mFireworks4Scale,mFireworks4Scale);
		canvas.drawBitmap(mFireworksFrames[mFireworks4Index], 0, 0,null);
		canvas.restore();
	}
	private void drawFireworks5(Canvas canvas) {
		canvas.save();
		canvas.translate(mFireworks5XOffset,mFireworks5YOffset);
		canvas.scale(mFireworks5Scale,mFireworks5Scale);
		canvas.drawBitmap(mFireworksFrames[mFireworks5Index], 0, 0,null);
		canvas.restore();
	}
	
	
	
	/**
	 * 动画变换
	 */
	private void move() {
		moveSea();
		switch(state) {
		case STATE_BOAT_IN:
			moveBoatIn();
			break;
		case STATE_BOAT_FLOATING:
			moveFloating();
			moveFireworks();
			break ;
		case STATE_BOAT_OUT:
			moveBoatOut();
			break;
		}
	}
	
	private void moveSea() {
		mSea1MoveXOffset -= mSea1MoveXStepDistance;
		mSea2MoveXOffset += mSea2MoveXStepDistance;
		mSea3MoveXOffset -= mSea3MoveXStepDistance;
	}
	
	private void moveBoatIn() {
		if((mBoatMoveInStepCount++) >= mBoatMoveInStepNum) {
			state = STATE_BOAT_FLOATING ;
			return;
		}
		//轮船变化
		mBoatMoveOffsetXDistance += mBoatMoveInStepXDistance;
		mBoatMoveOffsetYDistance += mBoatMoveInStepYDistance;
		mBoatScale += mBoatMoveInStepScale;
		mSea1Alpha += mBoatMoveInSeaStepAlpha;
		mSea2Alpha += mBoatMoveInSeaStepAlpha;
		mSea3Alpha += mBoatMoveInSeaStepAlpha;
		
	
	}
	
	/**
	 * 轮船上下浮动
	 */
	private void moveFloating() {
		
		if(isFirstFloating) {
			if((++mFirstFloatStepCount) <= 15) {//第一次下降帧数
				mBoatMoveOffsetYDistance += mFirstFloatYStepDistance ;
				mSea3MoveYOffset -= mFirstFloatYStepDistance / 2;
			}
			else {
				mBoatMoveOffsetYDistance -= mFirstFloatYStepDistance;
				mSea3MoveYOffset += mFirstFloatYStepDistance / 2;
				if(mFirstFloatStepCount == 40) {//第一次上升帧数
					isFirstFloating = false;
					isSecondFloating = true;
				}
				
			}
		} 
		else if(isSecondFloating) {
			if((++mSecondFloatStepCount) <= 60) {//第二次下降帧数
				mBoatMoveOffsetYDistance += mSecondFloatYStepDistance;
				mSea3MoveYOffset -= mSecondFloatYStepDistance / 2;
			}
			else {
				mBoatMoveOffsetYDistance -= mSecondFloatYStepDistance;
				mSea3MoveYOffset += mSecondFloatYStepDistance / 2;
				if(mSecondFloatStepCount == 100) {//第二次上升帧数
					isSecondFloating = false;
					state = STATE_BOAT_OUT;
				}
				
			}
		} 
		
		
	}
	
	private void moveFireworks() {
		mFireworksStepCount ++;
		if(mFireworksStepCount % 10 == 0) {
			if((++mFireworks1Index) > 12 ) {
				mFireworks1Index = 12;
			}
		}
		
		if(mFireworksStepCount > FIREWORKS_2_START_STEP) {
			if(mFireworksStepCount % 6 == 0) {
				if((++mFireworks2Index) > mFireworksFrames.length ) {
					mFireworks2Index = mFireworksFrames.length;
				}
			}
		}
		
		if(mFireworksStepCount > FIREWORKS_3_START_STEP) {
			if(mFireworksStepCount % 4 == 0) {
				if((++mFireworks3Index) > mFireworksFrames.length ) {
					mFireworks3Index = mFireworksFrames.length;
				}
			}
		}
		
		if(mFireworksStepCount > FIREWORKS_4_START_STEP) {
			if(mFireworksStepCount % 5 == 0) {
				if((++mFireworks4Index) > mFireworksFrames.length ) {
					mFireworks4Index = mFireworksFrames.length;
				}
			}
		}
		
		if(mFireworksStepCount > FIREWORKS_5_START_STEP) {
			if(mFireworksStepCount % 8 == 0) {
				if((++mFireworks5Index) > mFireworksFrames.length ) {
					mFireworks5Index = mFireworksFrames.length;
				}
			}
		}
	}
	
	private void moveBoatOut() {
		if((++mBoatMoveOutStepCount) > mBoatMoveOutStepNum) {
			state = STATE_FINISH;
			stop();
			return ;
		}
		//轮船变化
		mBoatMoveOffsetXDistance += mBoatMoveOutStepXDistance;
		mBoatMoveOffsetYDistance += mBoatMoveOutStepYDistance;
		mBoatScale += mBoatMoveOutStepScale;
		mSea1Alpha -= mBoatMoveOutSeaStepAlpha;
		mSea2Alpha -= mBoatMoveOutSeaStepAlpha;
		mSea3Alpha -= mBoatMoveOutSeaStepAlpha;
	}
	
	
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, "onLayout(),state:" + state);
		if (state != 0) {
			return;
		}
		initView();
		if (state == STATE_FINISH) {// 这种情况是由于内存不够导致图片不能解析的而为null
			return;
		}
		// 参数初始化
		mBoatMoveInStepCount = 0;
		mBoatMoveOffsetXDistance = 0;
		mBoatMoveOffsetYDistance = 0;
		//============================轮船移动时参数
		mBoatMoveStartX = -(int)(mBoatBmp.getWidth() * mBoatScale);
		mBoatMoveStartY = (int)(getHeight() / 3)  + (int)(100 * opt);
		mBoatMoveXDistance = getWidth() * 2 / 5 ;
		mBoatMoveYDistance = mBoatMoveXDistance / 10;

		// 算出轮船总补数 轮船移动总时间 / 重绘间隔时间
		mBoatMoveInStepNum = BOAT_MOVE_IN_TIME / SLEEP_TIME;
		// 算出轮船移动x,y每步移动距离 移动距离 / 步数
		mBoatMoveInStepXDistance = mBoatMoveXDistance / mBoatMoveInStepNum;
		mBoatMoveInStepYDistance = mBoatMoveYDistance / mBoatMoveInStepNum;
		// 算出轮船移动每步缩放距离 缩放值 / 步数
		mBoatMoveInStepScale = (BOAT_SCALE_END - BOAT_SCALE_START) / mBoatMoveInStepNum;
		//海水透明度变化
		mBoatMoveInSeaStepAlpha = SEA_ALPHA_END / mBoatMoveInStepNum;

		//==============================波浪相关参数
		mSea1MoveStartX = 0 ;
		mSea1MoveStartY = getHeight() * 70 / 100;

		mSea2MoveStartX = getWidth() - mSeaBmp2.getWidth() * SEA_SCALE ;
		mSea2MoveStartY = getHeight() * 55 / 100;

		mSea3MoveStartX = 0 ;
		mSea3MoveStartY = getHeight() * 40 / 100;

		mSea1MoveXDistance = mSeaBmp1.getWidth() * SEA_SCALE - getWidth() ;
		mSea1MoveYDistance = 0 ;

		mSea2MoveXDistance = mSeaBmp2.getWidth() * SEA_SCALE - getWidth() ;
		mSea2MoveYDistance = 0 ;

		mSea3MoveXDistance = mSeaBmp3.getWidth() * SEA_SCALE - getWidth() ;
		mSea3MoveYDistance = 0 ;



		mFirstFloatYStepDistance = opt / 2;
		mSecondFloatYStepDistance = opt * 7 / 12;

		//==============================烟花相关
		mFireworks1XOffset = 0;
		mFireworks1YOffset = 0;
		mFireworks2XOffset = 300 * opt;
		mFireworks2YOffset = 0;
		mFireworks3XOffset = 250 * opt;
		mFireworks3YOffset = 200 * opt;
		mFireworks4XOffset = 0;
		mFireworks4YOffset = 0;
		mFireworks5XOffset = 300 * opt;
		mFireworks5YOffset = 100 * opt;

		//==============================轮船游出相关参数
		mBoatMoveOutStepNum = BOAT_MOVE_OUT_TIME / SLEEP_TIME;
		mBoatMoveOutXDistance = getWidth() + mBoatMoveStartX + (int)(100 * opt);
		mBoatMoveOutYDistance = mBoatMoveOutXDistance / 5 ;
		mBoatMoveOutStepXDistance = mBoatMoveOutXDistance / mBoatMoveOutStepNum;
		mBoatMoveOutStepYDistance = mBoatMoveOutYDistance / mBoatMoveOutStepNum;
		mBoatMoveOutStepScale = (BOAT_MOVE_OUT_SCALE_END - BOAT_MOVE_OUT_SCALE_START) / mBoatMoveOutStepNum;
		//海水移除透明度变化值
		mBoatMoveOutSeaStepAlpha = SEA_ALPHA_END / mBoatMoveOutStepNum;
		//算出海水1移动距离
		int animTotalStep = mBoatMoveInStepNum + 140 +mBoatMoveOutStepNum ;
		mSea1MoveXStepDistance = mSea1MoveXDistance / animTotalStep;
		mSea2MoveXStepDistance = mSea2MoveXDistance / animTotalStep;
		mSea3MoveXStepDistance = mSea3MoveXDistance / animTotalStep;
		state = STATE_BOAT_IN;
	}

	void setJufanBoatAnimCallback(JufanBoatAnimCallback callback) {
		this.mAnimCallback = callback;
	}

	public interface JufanBoatAnimCallback {
		public void onBoatFloating(long senderId,String senderName) ;
	}

	@Override
	public void run() {
		if (state != STATE_FINISH) {
			isRunning = true;
		}
		long curTime = 0;
		while (isRunning && state != STATE_FINISH) {
			curTime = System.currentTimeMillis();
			drawCanvas();
			move();
			mHandler.sendEmptyMessage(HANDLER_DRAW);
			curTime = System.currentTimeMillis() - curTime;
			try {
				if (curTime < SLEEP_TIME) {
					Thread.sleep(SLEEP_TIME - curTime);
				}
			} catch (InterruptedException e) {
				break;
			}
		}
		// 销毁图片
		destoryBitmap();
		mHandler.sendEmptyMessage(HANDLER_LISTENER);
		
	}

	@Override
	protected void drawCanvas() {
		mCanvas = mSurfaceHolder.lockCanvas(); // 得到一个canvas实例
		mCanvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
//		mCanvas.drawColor(Color.RED);// 清除画布  
		try {
			switch(state) {
			case STATE_BOAT_IN:
				drawSea(mCanvas);
				drawBoat(mCanvas);
				break;
			case STATE_BOAT_FLOATING:
				drawSea(mCanvas);
				drawBoat(mCanvas);
				drawFireworks(mCanvas);
				break; 
			case STATE_BOAT_OUT:
				drawSea(mCanvas);
				drawBoat(mCanvas);
				break;
			}

		} catch(Exception e) {
			
		} finally {
			if (mCanvas != null)
				mSurfaceHolder.unlockCanvasAndPost(mCanvas); // 将画好的画布提交
		}
				
	}

}
