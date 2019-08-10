package com.pix.anim.project.guagua;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;

import com.pix.anim.bean.GuaGuaAnimView;
import com.pix.anim.utils.AnimBitmapLoader;

import java.util.ArrayList;

public class RepeatGiftView extends GuaGuaAnimView {

	protected final String TAG = "EffectAnimation";
	private Thread spriteThread;
	protected GiftList mGiftList;
	private Bitmap giftBitmap;
	private Bitmap[] bit_bombs;
	private int step = 0;
	private int sleepTime = 30;
	private int giftTime = 600;
	private int textSize = 60;
	private float textX = 0;
	private float textY = 0;

	private int state = 0;
	private final int START = 1;
	private final int FINISH = 2;
	private boolean isStop = false;
	private int addBeforLayoutCount = 0;//在此view被添加到父容器后，调用addGift可能会执行在onLayout前获取不到宽高

	public RepeatGiftView(Context context, Bitmap gift) {
		super(context);
		giftBitmap = gift;
	}

	private void init() {
		bit_bombs = new Bitmap[6];
		for (int i = 0; i < bit_bombs.length; i++) {
			bit_bombs[i] = AnimBitmapLoader.getInstance().getBitmap(
					getContext(), "gift/star_frame" + (i + 1) + ".png", opt);
			if (bit_bombs[i] == null) {
				if (mGiftListener != null) {
					mGiftListener.onError("礼物动画");
				}
				state = FINISH;// 解决内存不足无法解析图片而导致动画无法运行
				break;
			}
		}
	}

	public void start() {
		running = false;
		mGiftList = new GiftList();
		spriteThread = new Thread(run);
		spriteThread.start();
	}

	@Override
	public void stop() {
		isStop = true;
	}
	
	private Paint paint = new Paint();

	@Override
	protected void onDraw(Canvas canvas) {
		switch (state) {
		case START:
			if (mGiftList != null) {
				if (mGiftList.getItemSize() > 0) {
					paint.setTextSize(textSize);
					paint.setFakeBoldText(true);
					paint.setTextSkewX(-0.25f);
					paint.setColor(0xffffffff);
					canvas.drawText(" X " + mGiftList.drawCount, textX, textY,
							paint);
				}
				mGiftList.draw(canvas);
			}
			break;
		}
	}

	private Runnable run = new Runnable() {
		public void run() {
			running = true;
			long curTime = 0;
			while (running && state != FINISH) {
				curTime = System.currentTimeMillis();
				if (mGiftList != null) {
					mGiftList.move();
				}

				mHandler.sendEmptyMessage(HANDLER_DRAW);
				curTime = System.currentTimeMillis() - curTime;
				try {
					if (curTime < sleepTime) {
						Thread.sleep(sleepTime - curTime);
					}
				} catch (InterruptedException e) {
					break;
				}
				if(isStop && mGiftList.getItemSize() == 0){
					running = false;
				}
			}
			state = FINISH;
			mHandler.sendEmptyMessage(HANDLER_LISTENER);
			destoryBitmap();
		}
	};

	private final int HANDLER_DRAW = 1;
	private final int HANDLER_LISTENER = 2;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HANDLER_DRAW:
				invalidate();
				break;
			case HANDLER_LISTENER:
				if (mGiftListener != null) {
					mGiftListener.onGiftAnimFinish();
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
		step = giftTime / sleepTime;
		textX = getWidth() / 2 + giftBitmap.getWidth() / 2;
		textY = getHeight() / 4 - giftBitmap.getHeight() / 2
				+ (giftBitmap.getHeight() - textSize) / 2 + textSize / 2;
		if(addBeforLayoutCount > 0){
			for(int i = 0; i < addBeforLayoutCount; i ++){
				mGiftList.add(new GiftJump(getWidth(), getHeight(), step, giftBitmap,
						bit_bombs));
			}
			addBeforLayoutCount = 0;
		}
		state = START;
	};

	/**
	 * 增加连击礼物
	 */
	public void addGift() {
		if(state == 0){
			addBeforLayoutCount ++;
			return;
		}
		mGiftList.add(new GiftJump(getWidth(), getHeight(), step, giftBitmap,
				bit_bombs));
	}
	/**
	 * 获取礼物数量
	 * @return
	 */
	public int getGiftNum() {
		return mGiftList.getCount();
	}

	private void destoryBitmap() {
//		for(int i = 0; i < bit_bombs.length; i ++){
//			bit_bombs[i].recycle();
//			bit_bombs[i] = null;
//		}
	}

	/**
	 * 礼物队列
	 * 
	 * @author admin
	 * 
	 */
	class GiftList {
		// 效果容器
		protected ArrayList<GiftJump> list = new ArrayList<GiftJump>();
		private int drawCount = 0;
		private int count = 0;

		public GiftList() {
		}

		public synchronized void draw(Canvas canvas) {
			if (list.size() == 0) {
				return;
			}
			for (GiftJump item : list) {
				item.draw(canvas);
			}
		}

		public synchronized void move() {
			if (list.size() == 0) {
				return;
			}
			for (GiftJump item : list) {
				item.move();
			}
			clearItem();
		}

		private void clearItem() {
			int len = list.size();
			for (int i = 0; i < len; i++) {
				GiftJump item = list.get(i);
				if (item.isStop()) {
					list.remove(item);
					--len;
				}
				if(item.isShowNum()){
					drawCount++;
				}
			}
		}

		public synchronized void add(GiftJump item) {
			count++;
			list.add(item);
		}

		public synchronized int getItemSize() {
			return list.size();
		}

		public int getCount() {
			return count;
		}

		public int getDrawCount() {
			return drawCount;
		}

		public void destoryBitmap() {
		}
	}

}
