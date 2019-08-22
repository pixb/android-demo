package com.pix.testmyview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.pix.testmyview.utils.AnimBitmapLoader;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	/** 线程的休眠时间 */
	private static final int SLEEP_TIME = 50;
	/** 用于控制SurfaceView的SurfaceHolder */
	private SurfaceHolder mSfh;
	private float mTextX = 10;
	private float mTextY = 10;
	/** 画笔对象 */
	private Paint mPaint;
	/** 游戏的线程 */
	private Thread mThread;
	/** 线程是否运行标志 */
	private boolean isThreadRun;
	/** 游戏的画布 */
	private Canvas mCanvas;
	/** 屏幕的宽度 */
	private float mScreenWidth;
	/** 屏幕的高度 */
	private float mScreenHeight;
	
	private Bitmap mBitmap ;

	public MySurfaceView(Context context) {
		super(context);
		// 取得surface holder
		mSfh = getHolder();
		// 添加状态回调函数
		mSfh.addCallback(this);
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(40);
		mBitmap = AnimBitmapLoader.getInstance().readBitmap(getContext(), R.mipmap.ic_launcher);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mScreenWidth = getWidth();
		mScreenHeight = getHeight();
		// 实例化线程
		mThread = new Thread(this);
		// 启动线程
		isThreadRun = true;
		mThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isThreadRun = false;
	}

	/**
	 * 自定义绘制函数
	 */
	private void myDraw() {
		try {
			mCanvas = mSfh.lockCanvas();
			// RGB填充
			// mCanvas.drawRGB(0, 0, 0);
			// mCanvas.drawText("Game", mTextX, mTextY, mPaint);

			/**
			 * -----Canvas Path Rect练习 // 绘制颜色，刷屏
			 * mCanvas.drawColor(Color.BLACK); // 绘制文本
			 * mCanvas.drawText("Draw Text", 100, 100, mPaint); // 绘制像素点
			 * mCanvas.drawPoint(100, 200, mPaint); // 绘制多个像素点
			 * mCanvas.drawPoints(new float[] { 100, 300, 300, 300 }, mPaint);
			 * // 绘制直线 mCanvas.drawLine(50, 250, 500, 250, mPaint); // 绘制多条直线
			 * mCanvas.drawLines(new float[] { 100, 500, 500, 500, 700, 500,
			 * 1100, 500 }, mPaint); // 绘制矩形 mCanvas.drawRect(100, 600, 400,
			 * 800, mPaint); // 绘制矩形2 Rect rect = new Rect(100, 900, 600, 1000);
			 * mCanvas.drawRect(rect, mPaint); // 绘制圆角矩形 RectF rRectF = new
			 * RectF(700, 700, 1000, 800); mCanvas.drawRoundRect(rRectF, 20, 20,
			 * mPaint); // 绘制圆形 mCanvas.drawCircle(100, 1200, 100, mPaint); //
			 * 绘制弧形 mCanvas.drawArc(new RectF(100, 1400,500, 1500), 0, 230,
			 * true, mPaint); // 绘制椭圆 mCanvas.drawOval(new RectF(750, 400, 900,
			 * 500), mPaint); // 绘制指定路径图形 Path path = new Path(); // 设置路径的起点
			 * path.moveTo(550, 1200); // 路线1 path.lineTo(800, 1200); // 路线2
			 * path.lineTo(700, 1500); // 路径结束 path.close();
			 * mCanvas.drawPath(path,mPaint); //绘制指定路径图形 Path pathCircle = new
			 * Path(); //添加一个圆形路径
			 * pathCircle.addCircle(400,1200,200,Path.Direction.CCW);
			 * //绘制带圆形的路径文本 mCanvas.drawTextOnPath("Path Text", pathCircle,
			 * 10,20,mPaint);
			 *PaintFlagsDrawFilter pfdf = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG); 
			 */
			
//			/** ---Paint 练习 */
//			// 绘制颜色，刷屏
//			mCanvas.drawColor(Color.WHITE);
//			// ----画笔抗锯齿
//			Paint paint1 = new Paint();
//			mCanvas.drawCircle(100, 100, 50, paint1);
//			// 抗锯齿
//			paint1.setAntiAlias(true);
//			mCanvas.drawCircle(300, 100, 50, paint1);
//
//			// ----画笔的透明度
//			mCanvas.drawText("无透明度", 100, 200, new Paint());
//			Paint paint2 = new Paint();
//			paint2.setAlpha(0x77);
//			mCanvas.drawText("半透明度", 300, 200, paint2);
//
//			// ----文本锚点
//			mCanvas.drawText("锚点", 100, 250, new Paint());
//			Paint paint3 = new Paint();
//			// 锚点定在文本中心绘制
//			paint3.setTextAlign(Paint.Align.CENTER);
//			mCanvas.drawText("锚点", 100, 300, paint3);
//
//			// ----文本的长度
//			Paint paint4 = new Paint();
//			float textLen = paint4.measureText("文本宽度：");
//			mCanvas.drawText("文本宽度：" + textLen, 100, 350, new Paint());
//
//			// ----设置画笔样式
//			mCanvas.drawRect(new Rect(100, 400, 200, 500), new Paint());
//			Paint paint5 = new Paint();
//			// 设置画笔不填充
//			paint5.setStyle(Style.STROKE);
//			mCanvas.drawRect(new Rect(300,400,400,500), paint5);
//			
//			//设置画笔颜色
//			Paint paint6 = new Paint();
//			paint6.setColor(Color.RED);
//			mCanvas.drawText("红色", 100, 600, paint6);
//			
//			//设置画笔的粗细程度
//			mCanvas.drawLine(100, 650, 500,650,new Paint());
//			Paint paint7 = new Paint();
//			paint7.setStrokeWidth(7);
//			mCanvas.drawLine(100, 700, 500, 700, paint7);
//			
//			//设置绘制字体的大小
//			Paint paint8 = new Paint();
//			paint8.setTextSize(20);
//			mCanvas.drawText("绘制20号字", 100, 750, paint8);
//			
//			//设置画笔ARGB
//			Paint paint9 = new Paint();
//			paint9.setARGB(0x77, 0xff, 0x00, 0x00);
//			mCanvas.drawText("半透明红色", 100, 800, paint9);
			
			//mCanvas.drawBitmap(mBitmap, 100, 100, null);
			drawFireworkBase(mCanvas);
		} catch (Exception e) {

		} finally {
			if (mCanvas != null) {
				mSfh.unlockCanvasAndPost(mCanvas);
			}
		}
	}
	
	/**
	 * 绘制烟火的上升火
	 * @param canvas
	 */
	private void drawFireworkBase (Canvas canvas) {
		mPaint.setColor(0xFFFFCC00);
		Path path = new Path();
		// 设置路径的起点
		path.moveTo(400, 400);
		// 路线1
		path.lineTo(420, 380);
		// 路线2
		path.lineTo(540, 680);
		// 线路3
		path.lineTo(520, 700);
		path.close();
		mCanvas.drawPath(path,mPaint);
	}

	/**
	 * 游戏的逻辑
	 */
	private void logic() {

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mTextX = event.getX();
		mTextY = event.getY();
		return true;
	}

	@Override
	public void run() {
		while (isThreadRun) {
			try {
				long start = System.currentTimeMillis();
				logic();
				myDraw();
				long end = System.currentTimeMillis();
				if ((end - start) < SLEEP_TIME) {
					Thread.sleep(SLEEP_TIME - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
