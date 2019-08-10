package com.pix.anim.particle;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;


public class LightningScence extends View {

	private boolean running = false;
	private Lightning lightning;
	private Random rand;
	private int countTime = 0;
	private int during = 900;

	public LightningScence(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public LightningScence(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LightningScence(Context context) {
		super(context);
	}
	
	

	@Override
	protected void onDraw(Canvas canvas) {
		//绘制背景色
		canvas.drawRGB(0, 0, 0);
		if(lightning == null){
			initLightning();
		}
		else{
			lightning.draw(canvas);
		}
	}

	private void initLightning() {
		int width = getWidth();
		int height = getHeight();

		if (width == 0) {
			width = 800;
		}
		if (height == 0) {
			height = 1000;
		}
		rand = new Random();
		lightning = new Lightning(width, height);
		spriteThread.start();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		running = false;
	}

	private Thread spriteThread = new Thread() {
		public void run() {
			running = true;
			while (running) {
				lightning.move();

				try {
					Thread.sleep(50);
					countTime += 50;
					if (countTime > during) {
						countTime = 0;
						during = 900 + rand.nextInt(1800);
						lightning.reset();
					}
				} catch (InterruptedException e) {
					break;
				}
				mHandler.sendEmptyMessage(0);
			}

		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			invalidate();
		};
	};

}
