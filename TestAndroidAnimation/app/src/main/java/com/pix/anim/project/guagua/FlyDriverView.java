package com.pix.anim.project.guagua;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;


import com.pix.anim.bean.GuaGuaAnimView;

import java.util.Random;

public class FlyDriverView extends GuaGuaAnimView {

	private Paint paint = new Paint();
	private Thread spriteThread;
	/**
	 * 休息的时间
	 */
	private int sleepTime = 50;
	/**
	 * 移动的时间
	 */
	private int tranTime = 300;
	/**
	 * 第一个飞行图片
	 */
	private Bitmap bit_fly1;
	private Bitmap bit_fly2;
	private Bitmap[] bit_frames;
	private Bitmap bit_ring;
	private Bitmap bit_light;
	private Bitmap bit_layer;

	private float x = 0;
	private float y = 0;
	private float tranX = 0;
	private float scaleOne = 0.5f;
	private float scaleTwo = 0.8f;
	// 第三次变换动画
	private int stopTime = 1500;
	private int countTime = 0;
	private int frame = 0;
	// 光位置
	private float lightX = 0;
	private float lightY = 0;
	// 圆圈位置
	private float ringX = 0;
	private float ringY = 0;
	private float ringSpeed = 0;
	private int ringFrame = 10;
	private int ringCount = 0;
	private int ringAlpha = 0;
	// 飞船停止时上下浮动位移
	private float stopY = 0;
	private float stopSpeed = 0;
	// 旋转停留时间
	private int rotateTime = 500; // 旋转停留时间
	private int rotateCount = 0; // 旋转时间计数
	private float rotateAngle = -30;// 旋转角度
	private float rotateAngleCount = 0;// 旋转角度计数
	private float rotateTranX = 0;
	// 飞船上层浮动长条
	private float layer_TranW = 0;
	private float layer_tranX = 0;
	private float layer_x1 = 0;
	private float layer_y1 = 0;
	private float layer_x2 = 0;
	private float layer_y2 = 0;
	private float layer_x3 = 0;
	private float layer_y3 = 0;
	private float layer_x4 = 0;
	private float layer_y4 = 0;
	private float layer_x5 = 0;
	private float layer_y5 = 0;

	private int state = 0;
	private final int ONE = 1;
	private final int TWO = 2;
	private final int THREE = 30;
	private final int THREE_STOP = 31;
	private final int THREE_ROTATE = 32;
	private final int THREE_OUT = 33;
	private final int DISPLAY_OUT = 4;
	private final int FINISH = 5;
	
	private int driverType = 0;
	/**
	 * ufo形式的动画
	 */
	public static final int DRIVER_UFO = 13041;
	/**
	 * 火箭形式的动画
	 */
	public static final int DRIVER_ROCKET = 13042;
	/**
	 * 扫把形式的动画
	 */
	public static final int DRIVER_BROOM = 13040;
	
	/**
	 * 飘云朵的动画
	 */
	public static final int DRIVER_CLOUD = 12044;

	/**
	 * 
	 * @param context
	 * @param driverType 参考{@link#DRIVER_UFO},{@link#DRIVER_ROCKET},{@link#DRIVER_BROOM},{@link#DRIVER_CLOUD}}
	 */
	public FlyDriverView(Context context, int driverType) {
		super(context);
		this.driverType = driverType;
	}

	public void init() {
		// 初始化图片
		try {
			String typeStr = "";
			switch(driverType){
			case DRIVER_UFO:
				typeStr = "fly/ufo";
				break;
			case DRIVER_ROCKET:
				typeStr = "fly/rocket";
				break;
			case DRIVER_BROOM:
				typeStr = "fly/broom";
				break;
			case DRIVER_CLOUD:
				typeStr = "fly/cloud";
			}
			//创建出图片
			bit_fly1 = BitmapFactory.decodeStream(getContext().getAssets().open(
					typeStr + "_fly1.png"));
			bit_fly2 = bit_fly1;
			if(driverType != DRIVER_UFO ){
				bit_fly2 = BitmapFactory.decodeStream(getContext().getAssets().open(
						typeStr + "_fly2.png"));
			}
			bit_ring = BitmapFactory.decodeStream(getContext().getAssets()
					.open("fly/fly_ring.png"));
			bit_light = BitmapFactory.decodeStream(getContext().getAssets()
					.open("fly/fly_light.png"));
			bit_layer = BitmapFactory.decodeStream(getContext().getAssets()
					.open("fly/fly_layer.png"));
			bit_frames = new Bitmap[4];
			bit_frames[0] = BitmapFactory.decodeStream(getContext().getAssets()
					.open(typeStr + "_frame1.png"));
			bit_frames[1] = BitmapFactory.decodeStream(getContext().getAssets()
					.open(typeStr + "_frame2.png"));
			bit_frames[2] = BitmapFactory.decodeStream(getContext().getAssets()
					.open(typeStr + "_frame3.png"));
			bit_frames[3] = BitmapFactory.decodeStream(getContext().getAssets()
					.open(typeStr + "_frame4.png"));

		} catch (Exception e) {
			state = FINISH;//如果内存不够导致解析图片没有，则不会播放动画
		}catch(OutOfMemoryError oe){
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

	/**
	 * 停止动画
	 */
	public void stop(){
		state = DISPLAY_OUT;
		running = false;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		switch (state) {
		case ONE:
			drawOne(canvas);
			break;
		case TWO:
			drawTwo(canvas);
			break;
		case THREE:
			drawTree(canvas);
			break;
		case THREE_STOP:
			drawTreeStop(canvas);
			break;
		case THREE_ROTATE:
			drawTreeRotate(canvas);
			break;
		case THREE_OUT:
			drawTreeRotate(canvas);
			break;
		default:
		}
	}

	private void drawOne(Canvas canvas) {
		float fixX = x / scaleOne;
		float fixY = y / scaleOne;
		canvas.save();
		canvas.scale(scaleOne, scaleOne);
		canvas.drawBitmap(bit_fly1, fixX, fixY, null);
		canvas.restore();
	}

	private void drawTwo(Canvas canvas) {
		float fixX = x / scaleTwo;
		float fixY = y / scaleTwo;
		canvas.save();
		canvas.scale(scaleTwo, scaleTwo);
		canvas.drawBitmap(bit_fly2, fixX, fixY, null);
		canvas.restore();
	}

	private void drawFire(Canvas canvas) {
		if(driverType == DRIVER_UFO){
			paint.setAlpha(ringAlpha);
			canvas.drawBitmap(bit_light, x + lightX, y + lightY, null);
			canvas.drawBitmap(bit_ring, x + ringX, y + ringY, paint);
		}
	}

	private void drawTree(Canvas canvas) {
		drawFire(canvas);
		canvas.drawBitmap(bit_frames[frame], x, y + stopY, null);
	}

	private void drawTreeStop(Canvas canvas) {
		drawFire(canvas);
		canvas.drawBitmap(bit_frames[frame], x, y + stopY, null);
		DrawLayer(canvas);
	}

	private void drawTreeRotate(Canvas canvas) {
		canvas.save();
		canvas.rotate(rotateAngleCount, x + bit_frames[frame].getWidth() / 2, y
				+ bit_frames[frame].getHeight() / 2);
		drawFire(canvas);
		canvas.drawBitmap(bit_frames[frame], x, y + stopY, null);
		canvas.restore();
		if(state == THREE_ROTATE){
			DrawLayer(canvas);
		}
	}

	private void move() {
		switch (state) {
		case ONE:
			moveOne();
			break;
		case TWO:
			moveTwo();
			break;
		case THREE:
			moveTree();
			break;
		case THREE_STOP:
			moveStop();
			break;
		case THREE_ROTATE:
			moveRotate();
			break;
		case THREE_OUT:
			moveOut();
			break;
		}
	}

	// 第一次进入屏幕
	private void moveOne() {
		x -= tranX;
		if (x < -bit_fly1.getWidth()) {
			state = TWO;
			y = (getHeight() - bit_fly1.getHeight()) * 3 / 4;
		}
	}

	// 第二次进入屏幕
	private void moveTwo() {
		x += tranX;
		if (x > getWidth()) {
			state = THREE;
			y = (getHeight() - bit_frames[0].getHeight()) / 2;
		}
	}

	// 第三次进入屏幕
	private void moveTree() {
		x -= tranX;
		frame++;
		if (frame >= bit_frames.length) {
			fireMove();
			frame = 0;
		}
		if (x < (getWidth() - bit_frames[0].getWidth()) / 2) {
			x = (getWidth() - bit_frames[0].getWidth()) / 2;
			state = THREE_STOP;
		}
	}

	private void moveStop() {
		frame++;
		if (frame >= bit_frames.length) {
			frame = 0;
		}
		fireMove();
		layerMove();
		updonwMove();
		countTime += sleepTime;
		if (countTime > stopTime) {
			if(driverType == DRIVER_UFO){
				state = THREE_ROTATE;
			}else{
				state = THREE_OUT;
			}
		}
	}

	private void moveRotate() {
		frame++;
		if (frame >= bit_frames.length) {
			frame = 0;
		}
		fireMove();
		layerMove();
		rotateCount += sleepTime;
		rotateAngleCount += rotateAngle;
		x += rotateTranX;
		if (rotateCount > rotateTime) {
			state = THREE_OUT;
		}
	}

	// 第三次出屏幕
	private void moveOut() {
		x -= tranX;
		frame++;
		if (frame >= bit_frames.length) {
			frame = 0;
		}
		fireMove();
		layerMove();
		if (x < -bit_frames[0].getWidth()) {
			state = DISPLAY_OUT;
			running = false;
		}
	}

	private void fireMove() {
		ringCount++;
		if (ringCount >= ringFrame) {
			ringY = bit_frames[0].getHeight() / 2;
			ringAlpha = 0;
			ringCount = 0;
		} else {
			ringY += ringSpeed;
			ringAlpha = 255 - ringCount * 50;
		}
	}

	private void updonwMove(){
		stopY += stopSpeed;
		if(stopY < Math.abs(stopSpeed) || stopY > Math.abs(stopSpeed) *12){
			stopSpeed *= -1;
		}
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
		//取得第一桢动画图片的长宽
		int fw = bit_frames[0].getWidth();
		int fh = bit_frames[0].getHeight();
		//x初始化屏宽
		x = getWidth();
		//y屏幕的1/4
		y = (getHeight() - bit_fly1.getHeight()) / 4;
		//移动的x周距离
		tranX = getWidth() / tranStep;
		lightX = (fw - bit_light.getWidth()) / 2;
		lightY = fh - bit_light.getHeight() * 1.1f;
		ringX = (fw - bit_ring.getWidth()) / 2;
		ringY = fh / 2;

		ringSpeed = fh / 30;
		stopSpeed = fh / 100;

		float rotateStep = rotateTime / sleepTime;
		rotateAngle = rotateAngle / rotateStep;
		rotateTranX = fw / 2 / rotateStep;

		initLayer();
		state = ONE;
	};

	private void initLayer() {
		int disH = bit_frames[0].getHeight();

		Random rand = new Random();
		// 浮条总位移大小和循环时间
		int layerTime = 300;
		int layerStep = layerTime / sleepTime;
		layer_TranW = getWidth() / 4 / layerStep;

		layer_x1 = getWidth() / 2;
		layer_x2 = getWidth() / 20;
		layer_x3 = getWidth() * 2 / 3;
		layer_x4 = getWidth() / 8;
		layer_x5 = getWidth() * 2 / 5;

		layer_y1 = disH / 8;
		layer_y2 = disH / 3;
		layer_y3 = disH / 2;
		layer_y4 = disH * 2 / 3;
		layer_y5 = disH * 4 / 5;
	}

	private void DrawLayer(Canvas canvas) {
		paint.setAlpha(120);
		switch(driverType){
		case DRIVER_UFO:
			canvas.drawBitmap(bit_layer, layer_x1 + layer_tranX, y + layer_y1, paint);
		case DRIVER_ROCKET:
			canvas.drawBitmap(bit_layer, layer_x5 + layer_tranX, y + layer_y5, paint);
		case DRIVER_BROOM:
			canvas.drawBitmap(bit_layer, layer_x2 + layer_tranX, y + layer_y2, paint);
			canvas.drawBitmap(bit_layer, layer_x3 + layer_tranX, y + layer_y3, paint);
			canvas.drawBitmap(bit_layer, layer_x4 + layer_tranX, y + layer_y4, paint);
			break;
		}
	}

	private void layerMove() {
		layer_tranX += layer_TranW;
		if (layer_tranX > getWidth() / 4) {
			layer_tranX = 0;
		}
	}
	
	private void destoryBitmap(){
		if(bit_fly1 != null){
			bit_fly1.recycle();
			bit_fly1 = null;
		}
		if(bit_fly2 != null){
			bit_fly2.recycle();
			bit_fly2 = null;
		}
		if(bit_ring != null){
			bit_ring.recycle();
			bit_ring = null;
		}
		if(bit_light != null){
			bit_light.recycle();
			bit_light = null;
		}
		if(bit_layer != null){
			bit_layer.recycle();
			bit_layer = null;
		}
		for(int i = 0; i < bit_frames.length; i ++){
			bit_frames[i].recycle();
		}
	}
}
