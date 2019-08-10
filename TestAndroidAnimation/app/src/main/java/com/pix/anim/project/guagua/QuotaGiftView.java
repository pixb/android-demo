package com.pix.anim.project.guagua;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;


import com.pix.anim.bean.GuaGuaAnimView;

import java.util.ArrayList;

public class QuotaGiftView extends GuaGuaAnimView {

	protected final String TAG = "QuotaGiftView";
	private Thread spriteThread;
	protected GiftList mGiftList;
	private Bitmap giftBitmap;

	private int densityTime = 6;
	private int densityNum = 1;// 鲜花密度
	private int densityCount = 0;
	private int giftNum = 0;
	private int stepCount = 0;
	private int sleepTime = 35;

	private int state = 0;
	private final int START = 1;

	public QuotaGiftView(Context context, Bitmap gift, int giftNum) {
		super(context);
		this.giftBitmap = gift;
		this.giftNum = giftNum;
	}

	public void start() {
		densityNum += giftNum / 6;
		densityCount = densityTime;
		running = false;
		mGiftList = new GiftList();
		spriteThread = new Thread(run);
		spriteThread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		switch (state) {
		case START:
			if (mGiftList != null) {
				mGiftList.draw(canvas);
			}
			break;
		}
	}

	private Runnable run = new Runnable() {
		public void run() {
			running = true;
			long curTime = 0;
			while (running) {
				curTime = System.currentTimeMillis();
				mHandler.sendEmptyMessage(HANDLER_MOVE);
				mHandler.sendEmptyMessage(HANDLER_ADD_GIFT);
				mHandler.sendEmptyMessage(HANDLER_DRAW);
				curTime = System.currentTimeMillis() - curTime;
				try {
					if (curTime < sleepTime) {
						Thread.sleep(sleepTime - curTime);
					}
				} catch (InterruptedException e) {
					break;
				}
				if (stepCount >= giftNum && mGiftList.getItemSize() == 0) {
					running = false;
				}
			}
			destoryBitmap();
			mHandler.sendEmptyMessage(HANDLER_LISTENER);
		}
	};

	private final int HANDLER_DRAW = 1;
	private final int HANDLER_MOVE = 2;
	private final int HANDLER_ADD_GIFT = 3;
	private final int HANDLER_LISTENER = 4;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HANDLER_DRAW:
				invalidate();
				break;
			case HANDLER_MOVE:
				move_list();
				break;
			case HANDLER_ADD_GIFT:
				add_gift();
				break;
			case HANDLER_LISTENER:
				if (mGiftListener != null) {
					mGiftListener.onGiftAnimFinish();
				}
				break;
			}
		};
	};

	private void move_list() {
		if (mGiftList != null) {
			mGiftList.move();
		}
	}

	private void add_gift() {
		if(densityCount < densityTime){
			densityCount ++;
			return;
		}
		densityCount = 0;
		if (state == START && stepCount < giftNum) {
			for(int i = 0; i < densityNum && stepCount < giftNum; i ++){
				mGiftList.add(new GiftDown(getWidth(), getHeight(), giftBitmap));
				stepCount++;
			}
		}
	}

	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		state = START;
	};

	private void destoryBitmap() {
	}

	/**
	 * 礼物队列
	 * 
	 * @author admin
	 * 
	 */
	class GiftList {
		// 效果容器
		protected ArrayList<GiftDown> list = new ArrayList<GiftDown>();
		private int drawCount = 0;
		private int count = 0;

		public GiftList() {
		}

		public synchronized void draw(Canvas canvas) {
			if (list.size() == 0) {
				return;
			}
			for (GiftDown item : list) {
				item.draw(canvas);
			}
		}

		public synchronized void move() {
			if (list.size() == 0) {
				return;
			}
			for (GiftDown item : list) {
				item.move();
			}
			clearItem();
		}

		private void clearItem() {
			int len = list.size();
			for (int i = 0; i < len; i++) {
				GiftDown item = list.get(i);
				if (item.isStop()) {
					list.remove(item);
					--len;
				}
			}
		}

		public synchronized void add(GiftDown item) {
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
