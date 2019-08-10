package com.pix.anim.project.jufan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import com.pix.anim.bean.GuaGuaAnimView;
import com.pix.anim.utils.AnimBitmapLoader;

import java.util.Random;

/**
 * 聚范中汽车礼物的动画
 * 所有偏移量，位移量根据opt计算或者屏幕宽度计算，或者资源尺寸百分比计算，以求达到不同分辨率下可以适配的效果
 * @author tpx
 *
 */
public class JufanCarView extends GuaGuaAnimView {

	private final String TAG = "JufanCarView";
	// 座驾的运动状态
	private int state = 0;
	//车入场状态
	private final int STATE_CAR_MOVE_IN = 1;
	//车入场并带火状态
	private final int STATE_CAR_MOVE_IN_AND_FIRE = 2;
	//车漂移状态
	private final int STATE_CAR_DRIFTED = 3;
	//车漂移结束状态
	private final int STATE_CAR_DRIFTED_END = 4;
	//灯闪动状态
	private final int STATE_CAR_LIGHT_FLASH = 5;
	//车反复移动的状态
	private final int STATE_CAR_REPEATED = 6;
	//车退场的状态
	private final int STATE_CAR_MOVE_OUT = 7;
	//结束状态
	private final int STATE_FINISH = 10;
	/** 精灵线程 */
	private Thread mSpriteThread;
	private Paint mPaint;
	/** 重绘间隔时间 30ms */
	private static final int SLEEP_TIME = 30;
	/** 汽车进入移动时间 */
	private static final int CAR_MOVE_IN_TIME = 2000;
	/** 汽车漂移的时间 */
	private static final int CAR_MOVE_DRIFTED_TIME = 300;
	/** 汽车漂移结束时间 */
	private static final int CAR_MOVE_DRIFTED_END_TIME = 30 * 70;
	/** 汽车灯闪动时间 */
	private static final int CAR_LIGHT_FLASH_TIME = 1500;
	/** 车退场时间 */
	private static final int CAR_MOVE_OUT_TIME = 500;
	/** 汽车进入移动步数计数器 */
	private int mCarMoveStepCount;
	/** 汽车移动总步数 */
	private int mCarMoveStepNum;
	/** 汽车漂移状态步数计数器 */
	private int mCarDriftedStepCount;
	/** 汽车漂移状态总步数 */
	private int mCarDriftedStepNum;

	// ===================================汽车相关参数===========================================
	/** 汽车的图片 */
	private Bitmap mCarBmp;
	/** 汽车的初始缩放比 */
	private static final float CAR_SCALE_START = 0.1f;
	/** 汽车移动缩放比 */
	private static final float CAR_SCALE_END = 1f;
	/** 汽车移动开始点x坐标 */
	private int mCarMoveStartX;
	/** 汽车移动开始点y坐标 */
	private int mCarMoveStartY;
	/** 汽车移动x方向距离 */
	private int mCarMoveXDistance ;
	/** 汽车移动y方向距离 */
	private int mCarMoveYDistance ;
	/** 汽车图片缩放系数 */
	private float mCarScale = CAR_SCALE_START;
	/** 汽车移动的补差x距离*/
	private float mCarMoveOffsetXDistance;
	/** 汽车移动的补差y */
	private float mCarMoveOffsetYDistance;
	/** 汽车x轴每步走动的距离 */
	private float mCarMoveStepXDistance;
	/** 汽车y轴每步走动的距离 */
	private float mCarMoveStepYDistance;
	/** 汽车每步的缩放值 */
	private float mCarStepScale;

	// ===================================车灯相关参数===========================================
	/** 车灯图片 */
	private Bitmap mCarLightBmp;
	/** 汽车灯光移动开始点x坐标 */
	private float mCarLightMoveStartX;
	/** 汽车灯光移动开始点y坐标 */
	private float mCarLightMoveStartY;
	/** 汽车灯光图片缩放系数 */
	private float mCarLightScale = CAR_SCALE_START;
	/** 汽车灯光的缩放x坐标 */
	private float mCarLightOffsetX;
	/** 汽车灯光的缩放y坐标 */
	private float mCarLightOffsetY;
	/** 汽车灯光x轴每步走动的距离 */
	private float mCarLightStepXDistance;
	/** 汽车灯光y轴每步走动的距离 */
	private float mCarLightStepYDistance;
	/** 汽车灯光每步的缩放值 */
	private float mCarLightStepScale;
	private float mCarLeftLightAddOffsetX ;
	private float mCarLeftLightAddOffsetY ;
	private float mCarRightLightAddOffsetX ;
	private float mCarRightLightAddOffsetY ;
	
	//=====================================漂移相关参数==========================================
	/** 汽车漂移时x轴每步走动的距离 */
	private float mCarDriftedStepXDistance;
	/** 汽车漂移时y轴每步走动的距离 */
	private float mCarDriftedStepYDistance;
	/** 汽车移动x方向距离 */
	private float mCarDriftedXDistance ;
	/** 汽车移动y方向距离 */
	private float mCarDriftedYDistance ;
	/** 汽车漂移的补差x距离*/
	private float mCarDriftedOffsetXDistance;
	/** 汽车漂移的补差y */
	private float mCarDriftedOffsetYDistance;
	
	//=====================================车漂移结束相关参数=====================================
	/** 车漂移结束的步总数 */
	private float mCarDriftedEndStepNum;
	/** 车漂移结束的步数计数器 */
	private float mCarDriftedEndStepCount ;
	
	
	//====================================车入场时后面的火========================================
	/** 车入场时后面的火帧序列 */
	private Bitmap [] mCarInFireFrames = new Bitmap[15];
	/** 车入场火索引 */
	private int mCarInFireIndex = 0;
	/** 车入场火开始步数 */
	private int mCarInfireStartStep = 0;
	/** 汽车入场火开始点x坐标 */
	private float mCarInFireStartX;
	/** 汽车入场火开始点y坐标 */
	private float mCarInFireStartY;
	/** 汽车入场火补差距离 */
	private float mCarInFireOffsetY ;
	
	//====================================车前轮火焰========================================
	/** 车前轮火焰序列 */
	private Bitmap [] mFrontWheelFireFrames = new Bitmap[5];
	/** 车前轮火焰索引 */
	private int mFrontWheelFireIndex = 0;
	/** 车前轮火x坐标 */
	private float mFrontWheelFireX ;
	/** 车前轮火y坐标 */
	private float mFrontWheelFireY ;
	/** 前轮火的x补差 */
	private float mFrontWheelFireXOffset ;
	/** 前轮火的y补差 */
	private float mFrontWheelFireYOffset ;

	//====================================车后轮火焰========================================
	/** 车后轮火焰序列 */
	private Bitmap [] mBehindWheelFireFrames = new Bitmap[5];
	/** 车后轮火焰索引 */
	private int mBehindWheelFireIndex = 0;
	/** 车后轮火x坐标 */
	private float mBehindWheelFireX ;
	/** 车后轮火y坐标 */
	private float mBehindWheelFireY ;
	/** 前后火的x补差 */
	private float mBehindWheelFireXOffset  ;
	/** 前后火的y补差 */
	private float mBehindWheelFireYOffset ;
	
	//====================================车左轮烟雾========================================
	/** 左轮烟雾序列 */
	private Bitmap [] mLeftWheelSmogFrames = new Bitmap[10];
	/** 左轮烟雾索引 */
	private int mLeftWheelSmogIndex = 0;
	/** 左轮烟雾x坐标 */
	private float mLeftWheelSmogX ;
	/** 左轮烟雾y坐标 */
	private float mLeftWheelSmogY ;
	/** 左轮烟雾的x补差 */
	private float mLeftWheelSmogXOffset ;
	/** 左轮烟雾的y补差 */
	private float mLeftWheelSmogYOffset ;
		
	//====================================车右轮烟雾========================================
	/** 车前轮火焰序列 */
	private Bitmap [] mRightWheelSmogFrames = new Bitmap[10];
	/** 车前轮火焰索引 */
	private int mRightWheelSmogIndex = 0;
	/** 车前轮火x坐标 */
	private float mRightWheelSmogX ;
	/** 车前轮火y坐标 */
	private float mRightWheelSmogY ;
	/** 前轮火的x补差 */
	private float mRightWheelSmogXOffset ;
	/** 前轮火的y补差 */
	private float mRightWheelSmogYOffset ;
	
	//=================================车上小亮点参数=======================================
	/** 车上亮点的图片 */
	private Bitmap mCarDotBmp ;
	/** 车上亮点x坐标 */
	private float mCarDotX ;
	/** 车上亮点y坐标 */
	private float mCarDotY ;
	//车上亮点的的位置偏移量
	private float mCarDot1XOffset ;
	private float mCarDot1YOffset ;
	private float mCarDot2XOffset ;
	private float mCarDot2YOffset ;
	private float mCarDot3XOffset ;
	private float mCarDot3YOffset ;
	private float mCarDot4XOffset ;
	private float mCarDot4YOffset ;
	private float mCarDot5XOffset ;
	private float mCarDot5YOffset ;
	//车上各亮点的Alpha峰值 %50 ~%100
	private int mCarDot1AlphaMax ;
	private int mCarDot2AlphaMax ;
	private int mCarDot3AlphaMax ;
	private int mCarDot4AlphaMax ;
	private int mCarDot5AlphaMax ;
	//车上各亮点的Alpha变化时间 100 ~ 200
	private float mCarDot1Time ;
	private float mCarDot2Time ;
	private float mCarDot3Time ;
	private float mCarDot4Time ;
	private float mCarDot5Time ;
	//个点的Alpha每帧变化值
	private float mCarDot1PerAlpha ;
	private float mCarDot2PerAlpha ;
	private float mCarDot3PerAlpha ;
	private float mCarDot4PerAlpha ;
	private float mCarDot5PerAlpha ;
	//各点的Alpha实时值
	private int mCarDot1Alpha ;
	private int mCarDot2Alpha ;
	private int mCarDot3Alpha ;
	private int mCarDot4Alpha ;
	private int mCarDot5Alpha ;
	private Random mRandom  = new Random();
	
	//===================================车亮点的相关参数======================================
	/** 车灯闪烁的步总数 */
	private float mCarLightFlashStepNum;
	/** 车灯闪烁步数计数器 */
	private float mCarLightFlashStepCount ;
	/** 车灯的Alpha值 */
	private int mCarLightAlpha = 255;
	/** 车灯每步变化值 */
	private int mCarLightPerAlpha = 36;
	/** 是否是第一次闪烁 */
	private boolean isFirstFlash = true;
	
	//===================================车后退前进状态参数===================================
	/** 是否是第一次后退前进 */
	private boolean isFirstRepeated = true;
	/** 是否是第二次后退前进 */
	private boolean isSecondRepeated = false;
	/** 车第一次倒退前进每步走x的距离 */
	private float mCarRepeatedFirstPerXDistance;
	/** 车第一次倒退前进每步走y的距离 */
	private float mCarRepeatedFirstPerYDistance;
	/** 车第二次倒退前进每步走x的距离 */
	private float mCarRepeatedSecondPerXDistance ;
	/** 车第二次倒退前进每步走y的距离 */
	private float mCarRepeatedSecondPerYDistance ;
	/** 第三次后退每步走的x距离 */
	private float mCarRepeatedThirdPerXDistance ;
	/** 车第三次倒退每步走y的距离 */
	private float mCarRepeatedThirdPerYDistance ;
	private int mCarRepeatedFirstBackStepNum  = 5;
	private int mCarRepeatedFirstBackStepCount ;
	private int mCarRepeatedFirstForwardStepNum = 20;
	private int mCarRepeatedFirstForwardStepCount ;
	
	private int mCarRepeatedSecondBackStepNum  = 15;
	private int mCarRepeatedSecondBackStepCount ;
	private int mCarRepeatedSecondForwardStepNum = 30;
	private int mCarRepeatedSecondForwardStepCount ;
	
	private int mCarRepeatedThirdBackStepNum  = 20;
	private int mCarRepeatedThirdBackStepCount ;
	
	
	//============================车出去时参数
	/** 车子开出去时要走的总步数 */
	private int mCarOutStepNum ;
	/** 车子开出去时步数计数器 */
	private int mCarOutStepCount ;
	/** 车子开出去时x方向要走的距离 */
	private int mCarOutXDistance ;
	/** 车子开出去时y方向要走的距离 */
	private int mCarOutYDistance ;
	/** 车子开出去x每步要走的距离 */
	private int mCarOutXStepDistance ;
	/** 车子开出去时y方向每步的距离 */
	private int mCarOutYStepDistance ;
	
	
	
	
	
	//====================================车出去时火焰========================================
	/** 出去火焰序列 */
	private Bitmap [] mCarOutFireFrames = new Bitmap[4];
	/** 出去火焰索引 */
	private int mCarOutFireIndex = 0;
	/** 出去火x坐标 */
	private float mCarOutFireX ;
	/** 出去火y坐标 */
	private float mCarOutFireY ;
	/** 出去火的x补差 */
	private float mCarOutFireXOffset ;
	/** 出去火的y补差 */
	private float mCarOutFireYOffset;
	
	
	public JufanCarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 
	 * @param context
	 * 
	 */
	public JufanCarView(Context context) {
		super(context);
	}

	private void init() {
		Log.d(TAG, "init()");
		//创建绘制车上亮点的Paint,抗锯齿
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		String driverStr = "jufan/live_car.png";
		String carLightStr = "jufan/live_car_light.png";
		String carInFireStr = "jufan/car_in_fire/car_in_fire";
		String frontWheelFireStr = "jufan/front_wheel_fire/front_wheel_fire";
		String behindWheelFireStr = "jufan/behind_wheel_fire/behind_wheel_fire";
		String leftWheelSmogStr = "jufan/left_wheel_smog/left_wheel_smog";
		String rightWheelSmogStr = "jufan/right_wheel_smog/right_wheel_smog";
		String carDotStr = "jufan/live_car_dot.png";
		String carOutFireStr = "jufan/car_out_fire/car_out_fire";

		mCarBmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				driverStr, opt);
		mCarLightBmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				carLightStr, opt);

		if (mCarBmp == null || mCarLightBmp == null) {
			Log.d(TAG, "init(),(mCarBmp == null || mCarLightBmp == null)");
			if (mDriverlistener != null) {
				mDriverlistener.onError("汽车动画");
			}
			state = STATE_FINISH;
		}
		//加载车后火的序列帧
		for(int i = 0;i < 15;i++) {
			int index = i + 1;
			Bitmap bm = AnimBitmapLoader.getInstance().getBitmap(getContext(),
					carInFireStr + "_" + index +".png", opt);
			if(bm == null) {
				if (mDriverlistener != null) {
					mDriverlistener.onError("汽车动画");
				}
				state = STATE_FINISH;
			}
			mCarInFireFrames[i] = bm;
		}
		//加载前轮火序列帧
		for(int i = 0;i < 5;i++) {
			Bitmap bm = AnimBitmapLoader.getInstance().getBitmap(getContext(),
					frontWheelFireStr + "_" + i +".png", opt);
			if(bm == null) {
				if (mDriverlistener != null) {
					mDriverlistener.onError("汽车动画");
				}
				state = STATE_FINISH;
			}
			mFrontWheelFireFrames[i] = bm;
		}
		//加载后轮火序列帧
		for (int i = 0 ;i < 5 ;i++) {
			Bitmap bm = AnimBitmapLoader.getInstance().getBitmap(getContext(), 
					behindWheelFireStr + "_" + i + ".png", opt);
			if(bm == null) {
				if (mDriverlistener != null) {
					mDriverlistener.onError("汽车动画");
				}
				state = STATE_FINISH;
			}
			mBehindWheelFireFrames[i] = bm;
		}
		//加载左轮烟雾序列帧
		for (int i = 0; i < 10; i++) {
			Bitmap bm = AnimBitmapLoader.getInstance().getBitmap(getContext(), 
					leftWheelSmogStr + "_" + i + ".png", opt);
			if(bm == null) {
				if (mDriverlistener != null) {
					mDriverlistener.onError("汽车动画");
				}
				state = STATE_FINISH;
			}
			mLeftWheelSmogFrames[i] = bm;
		}
		//加载左轮烟雾序列帧
		for (int i = 0; i < 10; i++) {
			Bitmap bm = AnimBitmapLoader.getInstance().getBitmap(getContext(), 
					rightWheelSmogStr + "_" + i + ".png", opt);
			if(bm == null) {
				if (mDriverlistener != null) {
					mDriverlistener.onError("汽车动画");
				}
				state = STATE_FINISH;
			}
			mRightWheelSmogFrames[i] = bm;
		}
		
		//加载车上小亮点图片
		mCarDotBmp = AnimBitmapLoader.getInstance().getBitmap(getContext(), carDotStr, opt);
		if(mCarDotBmp == null) {
			if (mDriverlistener != null) {
				mDriverlistener.onError("汽车动画");
			}
			state = STATE_FINISH;
		}
		
		//加载出去时火的图片
		for (int i = 0; i < 4; i++) {
			Bitmap bm = AnimBitmapLoader.getInstance().getBitmap(getContext(), 
					carOutFireStr + "_" + i + ".png", opt);
			if(bm == null) {
				if (mDriverlistener != null) {
					mDriverlistener.onError("汽车动画");
				}
				state = STATE_FINISH;
			}
			mCarOutFireFrames[i] = bm;
		}
		
	}

	/**
	 * 启动动画
	 */
	public void start() {
		Log.d(TAG, "start(),timestamp:" + System.currentTimeMillis());
		running = false;
		mSpriteThread = new Thread(run);
		mSpriteThread.start();
	}

	/**
	 * 停止动画
	 */
	public void stop() {
		Log.d(TAG, "stop(),timestamp:" + System.currentTimeMillis());
		state = STATE_FINISH;
		running = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		switch (state) {
		case STATE_CAR_MOVE_IN:
			drawCarAndLight(canvas);
			break;
		case STATE_CAR_MOVE_IN_AND_FIRE:
			drawCarInFireFrame(canvas);
			drawCarAndLight(canvas);
			break;
		case STATE_CAR_DRIFTED:
			drawCarAndLight(canvas);
			break;
		case STATE_CAR_DRIFTED_END:
			if(mLeftWheelSmogIndex < 10) {
				drawLeftWheelSmog(canvas);
			}
			if(mRightWheelSmogIndex < 10) {
				drawRightWheelSmog(canvas);
			}
			drawCarAndLight(canvas);
			if(mFrontWheelFireIndex < 5) {
				drawFrontWheelFire(canvas);
			}
			if(mBehindWheelFireIndex < 5) {
				drawBehindWheelFire(canvas);
			}
			drawCarDots(canvas);
			break;
		case STATE_CAR_LIGHT_FLASH:
			drawCarAndLight(canvas);
			drawCarDots(canvas);
			break;
		case STATE_CAR_REPEATED:
			drawCarAndLight(canvas);
			drawCarDots(canvas);
			break;
			
		case STATE_CAR_MOVE_OUT:
			drawCarAndLight(canvas);
			if(mCarOutStepCount > 3) {
				drawCarOutFireFrame(canvas);
			}
			break;
		}
	}
	
	/**
	 * 绘制汽车
	 * @param canvas
	 */
	private void drawCar(Canvas canvas) {
		canvas.save();
		canvas.translate(mCarMoveStartX + mCarMoveOffsetXDistance + mCarDriftedOffsetXDistance,
				mCarMoveStartY + mCarMoveOffsetYDistance + mCarDriftedOffsetYDistance);
		canvas.scale(mCarScale, mCarScale);
		canvas.drawBitmap(mCarBmp, 0, 0, null);
		canvas.restore();
		Log.d(TAG, "drawCar(),mCarOffsetY:" + mCarMoveOffsetXDistance
				+ ",mCarOffsetY:" + mCarMoveOffsetYDistance + ",state:" + state );
	}
	/**
	 * 绘制左车灯
	 * @param canvas
	 */
	private void drawCarLeftLight(Canvas canvas) {
		canvas.save();
		canvas.translate(
				mCarLightMoveStartX + mCarLightOffsetX + mCarLeftLightAddOffsetX * mCarLightScale + mCarDriftedOffsetXDistance
			   ,mCarLightMoveStartY + mCarLightOffsetY + mCarLeftLightAddOffsetY * mCarLightScale + mCarDriftedOffsetYDistance);
		mPaint.setAlpha(mCarLightAlpha);
		canvas.scale(mCarLightScale, mCarLightScale);
		canvas.drawBitmap(mCarLightBmp, 0, 0, mPaint);
		Log.d(TAG, "drawCarLeftLight(),mCarLightOffsetY:" + mCarLightOffsetX
				+ ",mCarLightOffsetY:" + mCarLightOffsetY);
		canvas.restore();
	}
	private void drawCarRightLight(Canvas canvas) {
		canvas.save();
		canvas.translate(
				mCarLightMoveStartX + mCarLightOffsetX + mCarRightLightAddOffsetX * mCarLightScale + mCarDriftedOffsetXDistance, 
				mCarLightMoveStartY	+ mCarLightOffsetY + + mCarRightLightAddOffsetY * mCarLightScale + mCarDriftedOffsetYDistance);
		mPaint.setAlpha(mCarLightAlpha);
		canvas.scale(mCarLightScale, mCarLightScale);
		canvas.drawBitmap(mCarLightBmp, 0, 0, mPaint);
		Log.d(TAG, "drawCarRightLight(),mCarLightOffsetY:" + mCarLightOffsetX
				+ ",mCarLightOffsetY:" + mCarLightOffsetY);
		canvas.restore();
	}
	
	
	/**
	 * 绘制入场尾部火焰
	 * @param canvas
	 */
	private void drawCarInFireFrame(Canvas canvas) {
		Log.d(TAG, "drawCarInFireFrame()");
		canvas.save();
		canvas.translate(mCarInFireStartX , mCarInFireStartY + mCarInFireOffsetY);
		canvas.drawBitmap(mCarInFireFrames[mCarInFireIndex], 0, 0, null);
		canvas.restore();
	}
	/**
	 * 绘制车和车灯光
	 * @param canvas
	 */
	private void drawCarAndLight(Canvas canvas) {
		drawCar(canvas);
		drawCarLeftLight(canvas);
		drawCarRightLight(canvas);
	}
	/**
	 * 绘制前车轮火焰
	 * @param canvas
	 */
	private void drawFrontWheelFire(Canvas canvas){
		Log.d(TAG, "drawFrontWheelFire()");
		canvas.save();
		canvas.translate(
				mFrontWheelFireX + mFrontWheelFireXOffset
			,   mFrontWheelFireY + mFrontWheelFireYOffset);
		canvas.drawBitmap(mFrontWheelFireFrames[mFrontWheelFireIndex], 0, 0, null);
		canvas.restore();
	}
	/**
	 * 绘制后面车轮的火
	 * @param canvas
	 */
	private void drawBehindWheelFire(Canvas canvas){
		Log.d(TAG, "drawBehindWheelFire()");
		canvas.save();
		canvas.translate(
				mBehindWheelFireX + mBehindWheelFireXOffset
			,   mBehindWheelFireY + mBehindWheelFireYOffset);
		canvas.drawBitmap(mBehindWheelFireFrames[mBehindWheelFireIndex], 0, 0, null);
		canvas.restore();
	}
	/**
	 * 绘制左车轮的烟雾
	 * @param canvas
	 */
	private void drawLeftWheelSmog(Canvas canvas) {
		Log.d(TAG, "drawLeftWheelSmog()");
		canvas.save();
		canvas.translate(
				mLeftWheelSmogX + mLeftWheelSmogXOffset
			,   mLeftWheelSmogY + mLeftWheelSmogYOffset);
		canvas.drawBitmap(mLeftWheelSmogFrames[mLeftWheelSmogIndex], 0, 0, null);
		canvas.restore();
	}
	
	/**
	 * 绘制右车轮的烟雾
	 * @param canvas
	 */
	private void drawRightWheelSmog(Canvas canvas) {
		Log.d(TAG, "drawRightWheelSmog()");
		canvas.save();
		canvas.translate(
				mRightWheelSmogX + mRightWheelSmogXOffset
			,   mRightWheelSmogY + mRightWheelSmogYOffset);
		canvas.drawBitmap(mRightWheelSmogFrames[mRightWheelSmogIndex], 0, 0, null);
		canvas.restore();
	}
	/**
	 * 绘制车上的小亮点
	 * @param canvas
	 */
	private void drawCarDots(Canvas canvas){
		drawCarDot1(canvas);
		drawCarDot2(canvas);
		drawCarDot3(canvas);
		drawCarDot4(canvas);
		drawCarDot5(canvas);
	}
	/**
	 * 绘制车上的第一个小亮点
	 * @param canvas
	 */
	private void drawCarDot1(Canvas canvas) {
		canvas.save();
		canvas.translate(
				mCarDotX + mCarDot1XOffset + mCarDriftedOffsetXDistance , 
				mCarDotY + mCarDot1YOffset + mCarDriftedOffsetYDistance );
		mPaint.setAlpha(mCarDot1Alpha);
		canvas.drawBitmap(mCarDotBmp, 0, 0, mPaint);
		Log.d(TAG, "drawCarDot1(),mCarDot1XOffset:" + mCarDot1XOffset
				+ ",mCarDot1XOffset:" + mCarDot1XOffset);
		canvas.restore();
	}
	
	/**
	 * 绘制车上的第二个小亮点
	 * @param canvas
	 */
	private void drawCarDot2(Canvas canvas) {
		canvas.save();
		canvas.translate(
				mCarDotX + mCarDot2XOffset + mCarDriftedOffsetXDistance , 
				mCarDotY + mCarDot2YOffset + mCarDriftedOffsetYDistance );
		mPaint.setAlpha(mCarDot2Alpha);
		canvas.drawBitmap(mCarDotBmp, 0, 0, mPaint);
		Log.d(TAG, "drawCarDot2(),mCarDot2XOffset:" + mCarDot2XOffset
				+ ",mCarDot2XOffset:" + mCarDot2XOffset);
		canvas.restore();
	}
	
	/**
	 * 绘制车上的第三个小亮点
	 * @param canvas
	 */
	private void drawCarDot3(Canvas canvas) {
		canvas.save();
		canvas.translate(
				mCarDotX + mCarDot3XOffset + mCarDriftedOffsetXDistance , 
				mCarDotY + mCarDot3YOffset + mCarDriftedOffsetYDistance );
		mPaint.setAlpha(mCarDot3Alpha);
		canvas.drawBitmap(mCarDotBmp, 0, 0, mPaint);
		Log.d(TAG, "drawCarDot3(),mCarDot3XOffset:" + mCarDot3XOffset
				+ ",mCarDot3XOffset:" + mCarDot3XOffset);
		canvas.restore();
	}
	
	/**
	 * 
	 * @param canvas
	 */
	private void drawCarDot4(Canvas canvas) {
		canvas.save();
		canvas.translate(
				mCarDotX + mCarDot4XOffset + mCarDriftedOffsetXDistance, 
				mCarDotY + mCarDot4YOffset + mCarDriftedOffsetYDistance );
		mPaint.setAlpha(mCarDot4Alpha);
		canvas.drawBitmap(mCarDotBmp, 0, 0, mPaint);
		Log.d(TAG, "drawCarDot4(),mCarDot4XOffset:" + mCarDot4XOffset
				+ ",mCarDot4XOffset:" + mCarDot4XOffset);
		canvas.restore();
	}
	
	/**
	 * 
	 * @param canvas
	 */
	private void drawCarDot5(Canvas canvas) {
		canvas.save();
		canvas.translate(
				mCarDotX + mCarDot5XOffset + mCarDriftedOffsetXDistance , 
				mCarDotY + mCarDot5YOffset + mCarDriftedOffsetYDistance );
		mPaint.setAlpha(mCarDot5Alpha);
		canvas.drawBitmap(mCarDotBmp, 0, 0, mPaint);
		Log.d(TAG, "drawCarDot5(),mCarDot5XOffset:" + mCarDot5XOffset
				+ ",mCarDot5XOffset:" + mCarDot5XOffset);
		canvas.restore();
	}
	
	/**
	 * 绘制出场尾部火焰
	 * @param canvas
	 */
	private void drawCarOutFireFrame(Canvas canvas) {
		Log.d(TAG, "drawCarOutFireFrame()");
		canvas.save();
		canvas.translate(mCarOutFireX + mCarOutFireXOffset , mCarOutFireY + mCarOutFireYOffset);
		canvas.drawBitmap(mCarOutFireFrames[mCarOutFireIndex], 0, 0, null);
		canvas.restore();
	}
	
	
	
	private void move() {
		switch (state) {
		case STATE_CAR_MOVE_IN:
			moveDriverIn();
			break;
		case STATE_CAR_MOVE_IN_AND_FIRE:	
			moveDriverIn();
			moveCarInFire();
			break;
		case STATE_CAR_DRIFTED:
			moveDrifted();
			break;
		case STATE_CAR_DRIFTED_END:
			moveDriftedEnd();
			moveCarDot();
			break;
		case STATE_CAR_LIGHT_FLASH:
			moveCarDot();
			moveCarLightFlash();
			break;
		case STATE_CAR_REPEATED:
			moveCarDot();
			moveCarRepeat();
			break;
		case STATE_CAR_MOVE_OUT:
			moveCarOut();
			moveCarOutFire();
			break;
		}
	}
	/**
	 * 汽车入场变化
	 */
	private void moveDriverIn() {
		Log.d(TAG, "moveDriverIn()");
		if(mCarMoveStepCount >= mCarMoveStepNum) {
			return;
		}
		if(mCarMoveStepCount >= mCarInfireStartStep) {
			state = STATE_CAR_MOVE_IN_AND_FIRE;
		}
		//汽车变化
		mCarMoveOffsetXDistance += mCarMoveStepXDistance;
		mCarMoveOffsetYDistance += mCarMoveStepYDistance;
		mCarScale += mCarStepScale;
		//车灯变化
		mCarLightOffsetX += mCarLightStepXDistance;
		mCarLightOffsetY += mCarLightStepYDistance;
		mCarLightScale += mCarLightStepScale;
		mCarMoveStepCount++;
	}
	/**
	 * 汽车入场尾部火焰变化
	 */
	private void moveCarInFire() {
		if((++mCarInFireIndex) >= 15) {
			state = STATE_CAR_DRIFTED;
			mCarInFireIndex = 14;
			
		}
	}
	/**
	 * 汽车漂移变化
	 */
	private void moveDrifted() {
		Log.d(TAG, "moveDriverIn()");
		if (mCarDriftedStepCount >= mCarDriftedStepNum) {
			 state = STATE_CAR_DRIFTED_END;
			return;
		}
		//汽车变化
		mCarDriftedOffsetXDistance += mCarDriftedStepXDistance;
		mCarDriftedOffsetYDistance += mCarDriftedStepYDistance;
		mCarDriftedStepCount++;
	}
	/**
	 * 车漂移结束态，变化的汇总
	 */
	private void moveDriftedEnd() {
		moveFrontWheelFire();
		moveBehindWheelFire();
		moveLeftWheelSmog();
		moveRightWheelSmog();
		if(mCarDriftedEndStepCount >= mCarDriftedEndStepNum) {
			//进入灯闪状态
			state = STATE_CAR_LIGHT_FLASH;
			return ;
		}
		mCarDriftedEndStepCount++;
	}
	/**
	 * 前车轮火变化
	 */
	private void moveFrontWheelFire(){
		Log.d(TAG, "moveFrontWheelFire(),mFrontWheelFireIndex:" +mFrontWheelFireIndex); 
		if(mCarDriftedEndStepCount % 2 == 0) {
			return ;
		}
		if((++mFrontWheelFireIndex) > 4) {
			mFrontWheelFireIndex = 5;
		}
	}
	/**
	 * 后车轮火变化
	 */
	private void moveBehindWheelFire(){
		Log.d(TAG, "moveBehindWheelFire(),mBehindWheelFireIndex:" +mBehindWheelFireIndex); 
		if(mCarDriftedEndStepCount % 2 == 0) {
			return ;
		}
		if((++mBehindWheelFireIndex) > 4) {
			mBehindWheelFireIndex = 5;
		}
		
	}
	/**
	 * 左车轮烟雾变化
	 */
	private void moveLeftWheelSmog() {
		Log.d(TAG, "moveLeftWheelSmog(),mLeftWheelSmogIndex:" +mLeftWheelSmogIndex); 
		if(mCarDriftedEndStepCount % 2 == 0) {
			return ;
		}
		if((++mLeftWheelSmogIndex) > 9) {
			mLeftWheelSmogIndex = 10;
		}
	}
	
	/**
	 * 右车轮烟雾变化
	 */
	private void moveRightWheelSmog() {
		if(mCarDriftedEndStepCount % 2 == 0) {
			return ;
		}
		Log.d(TAG, "moveRightWheelSmog(),mRightWheelSmogIndex:" +mRightWheelSmogIndex); 
		if((++mRightWheelSmogIndex) > 9) {
			mRightWheelSmogIndex = 10;
		}
	}
	/**
	 * 车上小点变化
	 */
	private void moveCarDot() {
		if(mCarDriftedEndStepCount >= mCarDriftedEndStepNum) {
			//进入灯闪状态
//			state = STATE_CAR_LIGHT_FLASH;
//			return ;
		}
		//移动点1的Alpha
		if((mCarDot1Alpha + mCarDot1PerAlpha) <= 0) {
			initCarDot1Param();
		}
		else if((mCarDot1Alpha + mCarDot1PerAlpha) > mCarDot1AlphaMax) {
			mCarDot1PerAlpha *= (-1);
		}
		
		if((mCarDot2Alpha + mCarDot2PerAlpha) <= 0) {
			initCarDot2Param();
		}
		else if((mCarDot2Alpha + mCarDot2PerAlpha) > mCarDot2AlphaMax) {
			mCarDot2PerAlpha *= (-1);
		}
		
		if((mCarDot3Alpha + mCarDot3PerAlpha) <= 0) {
			initCarDot3Param();
		}
		else if((mCarDot3Alpha + mCarDot3PerAlpha) > mCarDot3AlphaMax) {
			mCarDot3PerAlpha *= (-1);
		}
		
		if((mCarDot4Alpha + mCarDot4PerAlpha) <= 0) {
			initCarDot4Param();
		}
		else if((mCarDot4Alpha + mCarDot4PerAlpha) > mCarDot4AlphaMax) {
			mCarDot4PerAlpha *= (-1);
		}
		
		if((mCarDot5Alpha + mCarDot5PerAlpha) <= 0) {
			initCarDot5Param();
		}
		else if((mCarDot5Alpha + mCarDot5PerAlpha) > mCarDot5AlphaMax) {
			mCarDot5PerAlpha *= (-1);
		}
		mCarDot1Alpha += mCarDot1PerAlpha;
		mCarDot2Alpha += mCarDot2PerAlpha;
		mCarDot3Alpha += mCarDot3PerAlpha;
		mCarDot4Alpha += mCarDot4PerAlpha;
		mCarDot5Alpha += mCarDot5PerAlpha;
	}
	
	/**
	 * 车灯闪烁变化
	 */
	private void moveCarLightFlash() {
		if((++mCarLightFlashStepCount)>mCarLightFlashStepNum) {
			mCarLightAlpha = 255;
			state = STATE_CAR_REPEATED;
			return ;
		}
		
		if(isFirstFlash) {
			if(((mCarLightAlpha -= mCarLightPerAlpha)) <= 0) {
				mCarLightAlpha = 255;
				isFirstFlash = false;
			}
		}
		else {
			mCarLightAlpha -= mCarLightPerAlpha;
			if(mCarLightAlpha <= 0) {
				mCarLightPerAlpha *= -2;
			}
			if(mCarLightAlpha >= 255) {
				mCarLightAlpha = 255 ;
			}
		}
	}
	
	private void moveCarRepeat() {
		if(isFirstRepeated) { //首次后退前进
			if(mCarRepeatedFirstBackStepCount>mCarRepeatedFirstBackStepNum 
					&& mCarRepeatedFirstForwardStepCount > mCarRepeatedFirstForwardStepNum) {
				isFirstRepeated = false;
				isSecondRepeated = true;
				return ;
			}
			if((++mCarRepeatedFirstBackStepCount) < mCarRepeatedFirstBackStepNum) { //后退
				mCarDriftedOffsetXDistance -= mCarRepeatedFirstPerXDistance;
				mCarDriftedOffsetYDistance -=mCarRepeatedFirstPerYDistance;
				mCarLightAlpha -= 40;
			}
			else { // 前进
				mCarDriftedOffsetXDistance += mCarRepeatedFirstPerXDistance;
				mCarDriftedOffsetYDistance += mCarRepeatedFirstPerYDistance;
				mCarRepeatedFirstForwardStepCount++;
				mCarLightAlpha += 10;
				if(mCarLightAlpha >= 255) {
					mCarLightAlpha = 255 ;
				}
			}
		}
		
		if(isSecondRepeated) {
			if(mCarRepeatedSecondBackStepCount>mCarRepeatedSecondBackStepNum 
					&& mCarRepeatedSecondForwardStepCount > mCarRepeatedSecondForwardStepNum) {
				isSecondRepeated = false;
				return ;
			}
			if((++mCarRepeatedSecondBackStepCount) < mCarRepeatedSecondBackStepNum) { //前进步走完
				mCarDriftedOffsetXDistance -= mCarRepeatedSecondPerXDistance;
				mCarDriftedOffsetYDistance -=mCarRepeatedSecondPerYDistance;
				mCarLightAlpha -= 10;
			}
			else {
				mCarDriftedOffsetXDistance += mCarRepeatedSecondPerXDistance;
				mCarDriftedOffsetYDistance += mCarRepeatedSecondPerYDistance;
				mCarRepeatedSecondForwardStepCount++;
				mCarLightAlpha += 5 ;
				if(mCarLightAlpha >= 255) {
					mCarLightAlpha = 255 ;
				}
			}
		}
		
		if(!isFirstRepeated && !isSecondRepeated) {
			if((++mCarRepeatedThirdBackStepCount) < mCarRepeatedThirdBackStepNum) {
				mCarDriftedOffsetXDistance -= mCarRepeatedThirdPerXDistance;
				mCarDriftedOffsetYDistance -= mCarRepeatedThirdPerYDistance;
				mCarLightAlpha -= 10;
			}
			else {
				state = STATE_CAR_MOVE_OUT ;
				mCarLightAlpha = 255;
			}
		}
	}
	
	private void moveCarOut() {
		mCarDriftedOffsetXDistance += mCarOutXStepDistance;
		mCarDriftedOffsetYDistance += mCarOutYStepDistance;
		if((++mCarOutStepCount) >= mCarOutStepNum) {
			stop();
		}
	}
	
	private void moveCarOutFire() {
		if(mCarOutStepCount > 3 && mCarOutStepCount % 2 == 0) {
			if((++mCarOutFireIndex) >= 4) {
				mCarOutFireIndex = 3;
			}
		}
	}
	

	private Runnable run = new Runnable() {
		public void run() {
			if (state != STATE_FINISH) {
				running = true;
			}
			long curTime = 0;
			while (running && state != STATE_FINISH) {
				curTime = System.currentTimeMillis();
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
	};

	private void destoryBitmap() {
		// if (bit_land != null) {
		// bit_land.recycle();
		// bit_land = null;
		// }
		// if (bit_driver != null) {
		// bit_driver.recycle();
		// bit_driver = null;
		// }
		// if (bit_ring != null) {
		// bit_ring.recycle();
		// bit_ring = null;
		// }
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
				if (mDriverlistener != null) {
					mDriverlistener.onDriverFinish();
				}
				break;
			}
		};
	};

	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, "onLayout()");
		if (state != 0) {
			return;
		}
		init();
		if (state == STATE_FINISH) {// 这种情况是由于内存不够导致图片不能解析的而为null
			return;
		}
		// 参数初始化
		mCarMoveStepCount = 0;
		mCarDriftedStepCount = 0;
		mCarMoveOffsetXDistance = 0;
		mCarMoveOffsetYDistance = 0;
		mCarLightOffsetX = 0;
		mCarLightOffsetY = 0;
		//============================汽车移动时参数
		mCarMoveStartX = -(int)(mCarBmp.getWidth() * mCarScale);
		mCarMoveStartY = (int)(getHeight() / 3);
		mCarMoveXDistance = getWidth() / 6;
		mCarMoveYDistance = getHeight() / 15;
		//入场火
		mCarInFireOffsetY = 33 * opt;
		//车灯
		mCarLeftLightAddOffsetX = 346 * opt;
		mCarLeftLightAddOffsetY = 53 * opt;
		mCarRightLightAddOffsetX = 580 * opt;
		mCarRightLightAddOffsetY = 50 * opt;
		//灯光的起始位置，根据汽车参数相关
		mCarLightMoveStartX = mCarMoveStartX;
		mCarLightMoveStartY = mCarMoveStartY;
		// 算出汽车总补数 汽车移动总时间 / 重绘间隔时间
		mCarMoveStepNum = CAR_MOVE_IN_TIME / SLEEP_TIME;
		// 算出汽车移动x,y每步移动距离 移动距离 / 步数
		mCarMoveStepXDistance = mCarMoveXDistance / mCarMoveStepNum;
		mCarMoveStepYDistance = mCarMoveYDistance / mCarMoveStepNum;
		//算出汽车灯光x,y每步移动距离,因为和车是一体的所以值相同
		mCarLightStepXDistance = mCarMoveStepXDistance;
		mCarLightStepYDistance = mCarMoveStepYDistance;
		// 算出汽车移动每步缩放距离 缩放值 / 步数
		mCarStepScale = (CAR_SCALE_END - CAR_SCALE_START) / mCarMoveStepNum;
		//算出车灯每步缩放值，与车相同
		mCarLightStepScale = mCarStepScale;
		//==============================车入场火帧参数
		mCarInfireStartStep = mCarMoveStepNum  - 15;
		mCarInFireStartX = 0;
		mCarInFireStartY = getHeight() / 3;
		//=============================汽车漂移时参数
		//汽车漂移的参数
		mCarDriftedXDistance = getWidth() / -50 ;
		mCarDriftedYDistance = getWidth() / 50 ;
		//汽车漂移步数
		mCarDriftedStepNum = CAR_MOVE_DRIFTED_TIME / SLEEP_TIME;
		//算出汽车漂移时每步的的移动距离
		mCarDriftedStepXDistance = mCarDriftedXDistance / mCarDriftedStepNum;
		mCarDriftedStepYDistance = mCarDriftedYDistance / mCarDriftedStepNum;
		//==============================车漂移结束参数
		mCarDriftedEndStepNum = CAR_MOVE_DRIFTED_END_TIME / SLEEP_TIME;
		
		//车前轮火位置
		mFrontWheelFireX = mCarMoveStartX;
		mFrontWheelFireY = mCarMoveStartY;
		mFrontWheelFireXOffset = 253 * opt ;
		mFrontWheelFireYOffset = 180 * opt ;
		//车后轮火位置
		mBehindWheelFireX = mCarMoveStartX;
		mBehindWheelFireY = mCarMoveStartY;
		mBehindWheelFireXOffset = 70 * opt ;
		mBehindWheelFireYOffset = 70 * opt ;
		//车左轮烟雾
		mLeftWheelSmogX = mCarMoveStartX;
		mLeftWheelSmogY = mCarMoveStartY;
		mLeftWheelSmogXOffset = 153 * opt;
		mLeftWheelSmogYOffset = 220 * opt;
		//车右轮烟雾
		mRightWheelSmogX = mCarMoveStartX;
		mRightWheelSmogY = mCarMoveStartY;
		mRightWheelSmogXOffset = 553 * opt ;
		mRightWheelSmogYOffset = 190 * opt ;
		
		//===========================车上小亮点参数
		mCarDotX = mCarMoveStartX;
		mCarDotY = mCarMoveStartY;
		mCarDot1XOffset = 200 * opt + getWidth() / 25;
		mCarDot1YOffset = 133 * opt - getWidth() / 25;
		mCarDot2XOffset = 353 * opt + getWidth() / 25;
		mCarDot2YOffset = 123 * opt - getWidth() / 25;
		mCarDot3XOffset = 446 * opt + getWidth() / 25;
		mCarDot3YOffset = 126 * opt - getWidth() / 25;
		mCarDot4XOffset = 500 * opt + getWidth() / 25;
		mCarDot4YOffset = 143 * opt - getWidth() / 25;
		mCarDot5XOffset = 540 * opt + getWidth() / 25;
		mCarDot5YOffset = 186 * opt - getWidth() / 25;
		//初始化个点alpha变化值
		initCarDot1Param();
		initCarDot2Param();
		initCarDot3Param();
		initCarDot4Param();
		initCarDot5Param();
		
		//=======================车灯闪烁参数
		mCarLightFlashStepNum = CAR_LIGHT_FLASH_TIME / SLEEP_TIME;
		
		//=======================车后退前进的参数
		//第一次
		mCarRepeatedFirstPerXDistance = opt ;
		mCarRepeatedFirstPerYDistance = opt / 2;
		//第二次
		mCarRepeatedSecondPerXDistance = opt ;
		mCarRepeatedSecondPerYDistance = opt / 2 ;
		
		//第三次
		mCarRepeatedThirdPerXDistance = opt * 2;
		mCarRepeatedThirdPerYDistance = opt ;
		
		//========================车子开出去时的参数
		mCarOutStepNum = CAR_MOVE_OUT_TIME / SLEEP_TIME ;
		mCarOutXDistance = getWidth();
		mCarOutYDistance = getWidth() / 5 ;
		mCarOutXStepDistance = (mCarOutXDistance / mCarOutStepNum) * 2;
		mCarOutYStepDistance = (mCarOutYDistance / mCarOutStepNum) * 2;
		
		//===========================出去火序列帧参数
		mCarOutFireX = mCarMoveStartX;
		mCarOutFireY = mCarMoveStartY;
		mCarOutFireXOffset = 45 * opt;
		mCarOutFireYOffset = 50 * opt;
		state = STATE_CAR_MOVE_IN;
	};
	/**
	 * 生成一个alpha 50% ~ 100%
	 * @return
	 */
	private int getRandomCarDotAlpha() {
		float a = mRandom.nextFloat() ;
		return (int)(a * 255);
	}
	
	private float getRandomCarDotTime() {
		float t = mRandom.nextInt(1000) + 100;
		Log.d(TAG, "getRandomCarDotTime(),t:" + t);
		return t;
	}
	private void initCarDot1Param() {
		mCarDot1AlphaMax = (int)getRandomCarDotAlpha();
		mCarDot1Time = getRandomCarDotTime();
		int frame = (int)mCarDot1Time / 30;
		mCarDot1PerAlpha = mCarDot1AlphaMax / (frame / 2);
		if(mCarDot1PerAlpha <= 0) {
			initCarDot1Param();
		}
		
	}
	private void initCarDot2Param() {
		mCarDot2AlphaMax = (int)getRandomCarDotAlpha();
		mCarDot2Time = getRandomCarDotTime();
		int frame = (int)mCarDot2Time / 30;
		mCarDot2PerAlpha = mCarDot2AlphaMax / (frame / 2);
		if(mCarDot2PerAlpha <= 0) {
			initCarDot2Param();
		}
	}
	private void initCarDot3Param() {
		mCarDot3AlphaMax = (int)getRandomCarDotAlpha();
		mCarDot3Time = getRandomCarDotTime();
		int frame = (int)mCarDot3Time / 30;
		mCarDot3PerAlpha = mCarDot3AlphaMax / (frame / 2);
		if(mCarDot3PerAlpha <= 0) {
			initCarDot3Param();
		}
	}
	private void initCarDot4Param() {
		mCarDot4AlphaMax = (int)getRandomCarDotAlpha();
		mCarDot4Time = getRandomCarDotTime();
		int frame = (int)mCarDot4Time / 30;
		mCarDot4PerAlpha = mCarDot4AlphaMax / (frame / 2);
		if(mCarDot4PerAlpha <= 0) {
			initCarDot4Param();
		}
	}
	private void initCarDot5Param() {
		mCarDot5AlphaMax = (int)getRandomCarDotAlpha();
		mCarDot5Time = getRandomCarDotTime();
		int frame = (int)mCarDot5Time / 30;
		mCarDot5PerAlpha = mCarDot5AlphaMax / (frame / 2);
		if(mCarDot5PerAlpha <= 0) {
			initCarDot5Param();
		}
	}
}
