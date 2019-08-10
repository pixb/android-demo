package com.pix.anim.project.qiqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;

import com.pix.anim.bean.GGSprite;
import com.pix.anim.bean.GuaGuaAnimView;
import com.pix.anim.bean.Vec2;
import com.pix.anim.particle.RoseSet;
import com.pix.anim.particle.SnowSet;
import com.pix.anim.particle.StarSkySet;
import com.pix.anim.utils.AnimBitmapLoader;

/**
 ************************************************************************************
* Module Name: 齐齐礼物特效类</br>
* File Name: <b>GGGiftView.java</b></br>
* Description: 这个类是齐齐送礼物的特效实现类</br>
* Author: TPX</br>
* 版权 2008-2015，金华长风信息技术有限公司</br>
* 所有版权保护
* 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
**************************************************************************************
 */
public class GGGiftView extends GuaGuaAnimView {
	
	private static final String TAG = "GGGiftView";
	
	/** 完成的状态 **/
	private static final int FINISH = 1;
	/** 开始移动的状态*/
	private static final int MOVE = 2;
	/** 开始播放粒子特效的状态 */
	private static final int EFFECT_PLAY = 3;
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
	
	/** -- 每次移动X的距离 -- **/
	private float tranX = 0;
	/** -- 每次移动y的距离 -- **/
	private float tranY = 0;
	
	/** -- 状态 -- */
	private int state = 0;
	/** 背景的精灵对象 */
	private GGSprite mBGSprite ;
	/** 星空的粒子集合 */
	private StarSkySet mStarSkySet ;
	/** 雪花的粒子集合 */
	private SnowSet mSnowSet ;
	/** 玫瑰花的粒子集合 */
	private RoseSet mRoseSet ;
	/** 背景精灵的Bitmap **/
	private Bitmap mBGBitmap ;
	/** 玫瑰花的Bitmap */
	private Bitmap mRoseBitmap ;
	/**BGSprite End Pos**/
	private Vec2 mBGEndPos ;
	
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

	public GGGiftView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	


	//放置View的位置,初始化参数
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, TAG + " onLayout() Func run");
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
		//初始化背景精灵的位置
		mBGSprite.getPostion().x = getWidth() / 2 -  mBGSprite.getWidth() / 2;
		mBGSprite.getPostion().y = getHeight() * 3 / 4;
		//移动的x周距离
		tranY = getHeight() / tranStep;
		
	}
	
	/**
	 * 初始化的方法
	 * 将资源中图片加载，编程Bitmap
	 */
	private void init() {
		// TODO Auto-generated method stub
		mBGSprite = new GGSprite();
		try {
			mBGBitmap = BitmapFactory.decodeStream(getContext().getAssets().open(
					"qiqigift/qiqigift.lv6/" + "gggift_lv6_bg.png"));
			if(mBGBitmap != null){
				mBGSprite.setBitmap(mBGBitmap);
			}
			mRoseBitmap = AnimBitmapLoader.getInstance().getBitmap(getContext(), "qiqigift/qiqigift.lv6/" + "gggift_lv6_rose.png",0.4f);
			
			state = MOVE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			state = FINISH;
		} catch(OutOfMemoryError e)
		{
			if(mDriverlistener != null){
				mDriverlistener.onError("QiQi gift");
			}
			state = FINISH;//如果内存不够导致解析图片没有，则不会播放动画
			System.gc();
		}
		mBGEndPos = new Vec2(getWidth() / 2 -  mBGSprite.getWidth() / 2,getHeight() / 4);
		mStarSkySet = new StarSkySet(mBGSprite.getWidth()
									, mBGSprite.getHeight()
									,mBGEndPos.x,mBGEndPos.y
									, 20);
		
		mSnowSet = new SnowSet(mBGSprite.getWidth()
				, mBGSprite.getHeight()
				,mBGEndPos.x,mBGEndPos.y
				, 20);
		
		if(mRoseBitmap != null) {
			mRoseSet = new RoseSet(mBGSprite.getWidth()
					, mBGSprite.getHeight()
					,mBGEndPos.x ,mBGEndPos.y 
					, 100,mRoseBitmap);
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
	
	protected void onDraw(android.graphics.Canvas canvas) {
		switch (state) {
		case MOVE:
//			drawBGSprite(canvas);
			mBGSprite.draw(canvas);
			break;
		case EFFECT_PLAY:
			mRoseSet.draw(canvas);
			mBGSprite.draw(canvas);
//			mStarSkySet.draw(canvas);
			mSnowSet.draw(canvas);
		
		default:
		}
	};
	
	
	private void move() {
		Log.d(TAG, TAG + " move Func run");
		switch (state) {
		case MOVE:
			bgSpriteMove();
			break;
		case EFFECT_PLAY:
			effectMove();
//			running = false;
//			state = FINISH;
			break;
		}
	}


	private void effectMove() {
		// TODO Auto-generated method stub
//		mStarSkySet.move();
		mSnowSet.move();
		mRoseSet.move();
	}



	/**
	 *  背景精灵移动
	 * TODO
	 * @return: void
	 */
	private void bgSpriteMove() {
		if(mBGSprite.getBitmap() == null)
		{
			return ;
		}
		// TODO Auto-generated method stub
		mBGSprite.getPostion().y -= tranY;
		if (mBGSprite.getPostion().y < mBGEndPos.y) {
			state = EFFECT_PLAY;
			mBGSprite.getPostion().y = mBGEndPos.y ;
		}
		
	}
	
	private void destoryBitmap(){
		if(mBGSprite.getBitmap() != null){
			mBGSprite.getBitmap().recycle();
			mBGSprite.setBitmap(null);
		}
//		for(int i = 0; i < bit_frames.length; i ++){
//			bit_frames[i].recycle();
//		}
	}
}
