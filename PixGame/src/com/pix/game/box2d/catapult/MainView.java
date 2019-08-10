package com.pix.game.box2d.catapult;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;

import com.pix.game.base.ui.BaseAnimSurfaceView;
import com.pix.game.util.AnimBitmapLoader;

/**
 * 弹弓游戏场景
 * 
 * @author pix
 * 
 */
public class MainView extends BaseAnimSurfaceView {

	private static final String TAG = "MainView";

	// 加载了图片文件之后，定义一个常量FLOOR_HEIGHT。这个是地面的高度，为了能够更精确的摆放物体。这个高度就是手机屏幕下边缘到游戏中模拟的地图的高度。
	private static final float FLOOR_HEIGHT = 82f;
	
	private int STATE_FINISH  = 100;
	
	private static final int  SLEEP_TIME = 30;

	/** 背景上层图 */
	private Bitmap mTopBGBmp;
	/** 背景的底层贴图 */
	private Bitmap mBottomBGBmp;
	/** 第一只松鼠的图片 */
	private Bitmap mSquirrel1Bmp;
	/** 第二只松鼠的图片 */
	private Bitmap mSquirrel2Bmp;
	/** 发射器底座1图片 */
	private Bitmap mCatapultBase1Bmp;
	/** 发射器底座2图片 */
	private Bitmap mCatapultBase2Bmp;

	public MainView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		String topBGStr = "catapult/bg.png";
		String bottomBGStr = "catapult/fg.png";
		String squirrelStr1 = "catapult/squirrel_1.png";
		String squirrelStr2 = "catapult/squirrel_2.png";
		String catapultBaseStr1 = "catapult/catapult_base_1.png";
		String catapultBaseStr2 = "catapult/catapult_base_2.png";
		
		mTopBGBmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				topBGStr, opt);
		mBottomBGBmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				bottomBGStr, opt);
		mSquirrel1Bmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				squirrelStr1, opt);
		mSquirrel2Bmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				squirrelStr2, opt);
		mCatapultBase1Bmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				catapultBaseStr1, opt);
		mCatapultBase2Bmp = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				catapultBaseStr2, opt);
		
		if(mTopBGBmp == null || mBottomBGBmp == null || mSquirrel1Bmp == null
				|| mSquirrel2Bmp == null || mCatapultBase1Bmp == null || mCatapultBase2Bmp == null) {
			if (mOnDriverListener != null) {
				mOnDriverListener.onError("轮船动画");
			}
			state = STATE_FINISH;
		}
		
	}

	@Override
	protected void drawCanvas() {
		try {
			mCanvas = mSurfaceHolder.lockCanvas(); // 得到一个canvas实例
			if (mCanvas != null) {
				mCanvas.drawColor(Color.WHITE);// 刷屏
				mCanvas.drawBitmap(mTopBGBmp, 0 - mScreenWidth / 2, 0, mPaint);

				mCanvas.drawBitmap(mCatapultBase2Bmp, 260 - mScreenWidth, mScreenHeight
						- FLOOR_HEIGHT - mCatapultBase2Bmp.getHeight()
						- mCatapultBase2Bmp.getHeight() / 4, mPaint);
				mCanvas.drawBitmap(mCatapultBase1Bmp, 265 - mScreenWidth, mScreenHeight
						- FLOOR_HEIGHT - mCatapultBase1Bmp.getHeight()
						- mCatapultBase1Bmp.getHeight() / 4, mPaint);

				mCanvas.drawBitmap(mSquirrel1Bmp, 50 - mScreenWidth, mScreenHeight - FLOOR_HEIGHT
						- mSquirrel1Bmp.getHeight(), mPaint);
				mCanvas.drawBitmap(mCatapultBase2Bmp, 350 - mScreenWidth, mScreenHeight - FLOOR_HEIGHT
						- mCatapultBase2Bmp.getHeight(), mPaint);

				mCanvas.drawBitmap(mBottomBGBmp, 0 - mScreenWidth, mScreenHeight
						- mBottomBGBmp.getHeight(), mPaint);
			}
		} catch (Exception ex) {
		} finally {
			if (mCanvas != null)
				mSurfaceHolder.unlockCanvasAndPost(mCanvas); // 将画好的画布提交
		}

	}
	
	private final int HANDLER_DRAW = 1;
	private final int HANDLER_LISTENER = 2;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HANDLER_DRAW:
				drawCanvas();
				break;
			case HANDLER_LISTENER:
				if (mOnDriverListener != null) {
					mOnDriverListener.onDriverFinish();
				}
				break;
			}
		};
	};

	@Override
	public void run() {
		if (state != STATE_FINISH) {
			isRunning = true;
		}
		long curTime = 0;
		while (isRunning && state != STATE_FINISH) {
			curTime = System.currentTimeMillis();
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
	
	private void destoryBitmap() {
		
	}

}
