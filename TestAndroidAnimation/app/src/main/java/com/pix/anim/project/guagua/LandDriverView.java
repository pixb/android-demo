package com.pix.anim.project.guagua;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;

import com.pix.anim.bean.GuaGuaAnimView;
import com.pix.anim.utils.AnimBitmapLoader;

public class LandDriverView extends GuaGuaAnimView {

	private final String TAG = "LandDriverView";

	private final int timeControl = 1;

	private long animTime = 2000;
	private long sleepTime = 30 * timeControl;
	private Thread spriteThread;

	private Bitmap bit_land; // 路面
	private Bitmap bit_driver; // 座驾
	private Bitmap bit_ring; // 座驾下面的圆圈

	private float land_y;
	private float land_tranY = 0;
	private float land_speedY = 0;
	private int land_time = 500;

	private int driverInTime = 300 * timeControl;
	private float driver_x = 0;
	private float driver_y = 0;
	private float scale = 0.125f;
	private float scaleX = 0;
	private float scaleY = 0;
	private float driver_scaleSpeed = 0;
	private float driver_speedX = 0;
	private float driver_speedY = 0;

	private int driverStopTime = 2000 * timeControl;
	private int driverStopStep = 0;
	private float ringScale = 1;
	private float ringScaleStep = -0.1f;
	private float ringX = 0;
	private float ringY = 0;

	// 座驾的运动状态
	private int state = 0;
	private final int DRIVER_LAND = 1;
	private final int DRIVER_IN = 2;
	private final int DRIVER_STOP = 3;
	private final int DRIVER_OUT = 4;
	private final int DRIVER_FINISH = 5;

	private int driverType = 0;
	/**
	 * 吊车
	 */
	public static final int LAND_CRANE = 13038;
	/**
	 * 三轮车
	 */
	public static final int LAND_TRICYCLE = 13039;
	/**
	 * 奥迪
	 */
	public static final int LAND_AODI = 13502;
	/**
	 * 宾利黄金版
	 */
	public static final int LAND_BENTLEY_GOLD = 13504;
	/**
	 * 奔驰200
	 */
	public static final int LAND_BENZ_200 = 13509;
	/**
	 * 奔驰600a
	 */
	public static final int LAND_BENZ_600A = 13506;
	/**
	 * 宝马
	 */
	public static final int LAND_BMW = 13507;
	/**
	 * 法拉利
	 */
	public static final int LAND_FERRARI = 13072;
	/**
	 * 保时捷
	 */
	public static final int LAND_PORSCHE = 13500;
	/**
	 * 凯越
	 */
	public static final int LAND_EXCELLE = 13068;
	/**
	 * 帕萨特
	 */
	public static final int LAND_PASSAT = 13071;
	/**
	 * 奇瑞QQ
	 */
	public static final int LAND_CHERYQQ = 13501;
	/**
	 * 红旗
	 */
	public static final int LAND_REDFLAG = 13503;
	/**
	 * 途锐
	 */
	public static final int LAND_TOUAREG = 13070;
	/**
	 * 幽灵战警
	 */
	public static final int LAND_YOULINGZHANJING = 13015;
	/**
	 * 蓝色幽灵.改
	 */
	public static final int LAND_LANSEYOULING = 13019;
	/**
	 * 青之焰.改
	 */
	public static final int LAND_QINGZHIYAN = 13020;
	/**
	 * 速度之星.改
	 */
	public static final int LAND_SPEEDSTAR = 13021;
	/**
	 * 暗夜骑士.改
	 */
	public static final int LAND_ANYEQISHI = 13022;
	/**
	 * 爆裂天使
	 */
	public static final int LAND_BAOLIEANGLE = 13034;
	/**
	 * 王道霸主
	 */
	public static final int LAND_KINGBAZHU = 13035;

	public LandDriverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// init();
	}

	/**
	 * 
	 * @param context
	 * @param driverType
	 *            参考{@link#CRANE},{@link#TRICYCLE}
	 */
	public LandDriverView(Context context, int driverType) {
		super(context);
		this.driverType = driverType;
		// init();
	}

	private void init() {
		// 初始化图片
		String typeStr = getTypeStr();

		String driverStr = "land/driver_" + typeStr + ".png";
		String landStr = "land/driver_land.png";
		String ringStr = "land/land_ring.png";

		bit_driver = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				driverStr, opt);
		bit_land = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				landStr, opt);
		bit_ring = AnimBitmapLoader.getInstance().getBitmap(getContext(),
				ringStr, opt);

		if (bit_driver == null || bit_land == null || bit_ring == null) {
			if(mDriverlistener != null){
				mDriverlistener.onError("汽车动画");
			}
			state = DRIVER_FINISH;
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
	public void stop() {
		state = DRIVER_FINISH;
		running = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		switch (state) {
		case DRIVER_LAND:
			drawLand(canvas);
			break;
		case DRIVER_IN:
			drawDriverIn(canvas);
			break;
		case DRIVER_STOP:
			drawDriverStop(canvas);
			break;
		case DRIVER_OUT:
			drawDriverOut(canvas);
			break;
		}
	}

	private void move() {
		switch (state) {
		case DRIVER_LAND:
			moveLand();
			break;
		case DRIVER_IN:
			moveDriverIn();
			break;
		case DRIVER_STOP:
			moveDriverStop();
			break;
		case DRIVER_OUT:
			moveDriverOut();
			break;
		}
	}

	private void drawLand(Canvas canvas) {
		canvas.save();
		canvas.clipRect(0, land_y, getWidth(), land_tranY);
		canvas.drawBitmap(bit_land, 0, land_y, null);
		canvas.restore();
	}

	private void drawDriverIn(Canvas canvas) {
		canvas.drawBitmap(bit_land, 0, land_y, null);
		canvas.save();
		canvas.scale(scale, scale);
		canvas.drawBitmap(bit_driver, scaleX, scaleY, null);
		canvas.restore();
	}

	private void drawDriverOut(Canvas canvas) {
		canvas.drawBitmap(bit_land, 0, land_y, null);
		canvas.drawBitmap(bit_driver, driver_x,
				driver_y - bit_driver.getHeight(), null);
	}

	private void drawDriverStop(Canvas canvas) {
		canvas.drawBitmap(bit_land, 0, land_y, null);
		// 绘制圆圈
		drawRing(canvas);
		canvas.drawBitmap(bit_driver, driver_x,
				driver_y - bit_driver.getHeight(), null);
	}

	private void drawRing(Canvas canvas) {
		canvas.save();
		canvas.scale(ringScale, ringScale);
		canvas.drawBitmap(bit_ring, ringX, ringY, null);
		canvas.restore();
	}

	private void moveLand() {
		land_tranY += land_speedY;
		if (land_tranY > land_y + bit_land.getHeight() + land_speedY) {
			state = DRIVER_IN;
			scaleX = driver_x / scale;
			scaleY = driver_y / scale;
		}
	}

	private void moveDriverIn() {
		scale += driver_scaleSpeed;
		driver_x -= driver_speedX;
		driver_y += driver_speedY;
		if (scale > 1) {
			scale = 1;
			state = DRIVER_STOP;
			moveDriverStop();
		}
		scaleX = driver_x / scale;
		scaleY = (driver_y - bit_driver.getHeight() * scale) / scale;
	}

	private int rate = 0;// 频率，圆圈变化的频率设置为5次（每次sleepTime）

	private void moveDriverStop() {
		driverStopStep += sleepTime;
		if (driverStopStep > driverStopTime) {
			state = DRIVER_OUT;
			return;
		}
		rate++;
		if (rate >= 5) {
			rate = 0;
			ringScale += ringScaleStep;
			if (ringScale >= 1) {
				ringScale = 1;
				ringScaleStep *= -1;
			}
			if (ringScale <= 0.5) {
				ringScale = 0.5f;
				ringScaleStep *= -1;
			}
		}

		float w = (bit_ring.getWidth() - ringScale * bit_ring.getWidth()) / 2;
		float h = (bit_ring.getHeight() + ringScale * bit_ring.getHeight()) / 2;
		if (driverType != LAND_CRANE) {
			// h = (bit_ring.getHeight() + ringScale * bit_ring.getHeight() +
			// 40) / 2;
			w = (bit_ring.getWidth() - ringScale * bit_ring.getWidth() - 60 * opt) / 2;
			h = 3.316f / 2.616f * h;
		}
		ringX = (driver_x + w) / ringScale;
		ringY = (driver_y - h) / ringScale;
	}

	private void moveDriverOut() {
		driver_x -= driver_speedX;
		driver_y += driver_speedY;
		if (driver_x < -2 * bit_driver.getWidth()) {
			state = DRIVER_FINISH;
			running = false;
		}
	}

	private Runnable run = new Runnable() {
		public void run() {
			if (state != DRIVER_FINISH) {
				running = true;
			}
			long curTime = 0;
			while (running && state != DRIVER_FINISH) {
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

	private void destoryBitmap() {
//		if (bit_land != null) {
//			bit_land.recycle();
//			bit_land = null;
//		}
//		if (bit_driver != null) {
//			bit_driver.recycle();
//			bit_driver = null;
//		}
//		if (bit_ring != null) {
//			bit_ring.recycle();
//			bit_ring = null;
//		}
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
		if (state != 0) {
			return;
		}
		init();
		if(state == DRIVER_FINISH){//这种情况是由于内存不够导致图片不能解析的而为null
			return;
		}
		land_y = (getHeight() - bit_land.getHeight()) / 2;
		land_tranY = land_y;
		land_speedY = bit_land.getHeight() / (land_time / sleepTime);

		float driverInStep = driverInTime / sleepTime;
		driver_x = getWidth();
		driver_y = land_y;
		driver_speedY = bit_land.getHeight() / (driverInStep * 2);
		if (driverType == LAND_CRANE) {
			driver_speedX = driver_speedY * 3.31666f;
		} else {
			driver_speedX = driver_speedY * 2.61666f;
		}

		driver_scaleSpeed = (1 - scale) / driverInStep;
		state = DRIVER_LAND;
	};

	private String getTypeStr() {
		String typeStr = "";
		switch (driverType) {
		case LAND_CRANE:
			typeStr = "crane";
			break;
		case LAND_TRICYCLE:
			typeStr = "tricycle";
			break;
		case LAND_AODI:
			typeStr = "aodi";
			break;
		case LAND_BENTLEY_GOLD:
			typeStr = "bentley_gold";
			break;
		case LAND_BENZ_200:
			typeStr = "benz200";
			break;
		case LAND_BENZ_600A:
			typeStr = "benz600a";
			break;
		case LAND_BMW:
			typeStr = "bmw";
			break;
		case LAND_FERRARI:
			typeStr = "ferrari";
			break;
		case LAND_PORSCHE:
			typeStr = "porsche";
			break;
		case LAND_EXCELLE:
			typeStr = "excelle";
			break;
		case LAND_PASSAT:
			typeStr = "passat";
			break;
		case LAND_CHERYQQ:
			typeStr = "cheryqq";
			break;
		case LAND_REDFLAG:
			typeStr = "redflag";
			break;
		case LAND_TOUAREG:
			typeStr = "touareg";
			break;
		case LAND_YOULINGZHANJING:
			typeStr = "youlingzhanjing";
			break;
		case LAND_LANSEYOULING:
			typeStr = "lanseyouling";
			break;
		case LAND_QINGZHIYAN:
			typeStr = "qingzhiyan";
			break;
		case LAND_SPEEDSTAR:
			typeStr = "speedstar";
			break;
		case LAND_ANYEQISHI:
			typeStr = "anyeqishi";
			break;
		case LAND_BAOLIEANGLE:
			typeStr = "baolieangle";
			break;
		case LAND_KINGBAZHU:
			typeStr = "kingbazhu";
			break;
		}
		return typeStr;
	}
}
