package com.pix.anim.particle;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.pix.anim.R;


/**
 * 粒子场景基类
 * 用粒子绘制在本View上，从而构建一个由粒子组成的View
 * @author lxf
 *
 */
public abstract class ParticleScence extends View {

	protected final String TAG = "EffectAnimation";
	
	/**
	 * 运行时的线程
	 */
	private Thread spriteThread;
	/**
	 * 是否停止标记位
	 */
	private boolean running = false;
	
	protected ParticleSet scence;
	private int itemNum;
	
	/**
	 * 构造
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public ParticleScence(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ParticleScence(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ParticleScence);
		itemNum = a.getInt(R.styleable.ParticleScence_itemNum, 0);
		a.recycle();
	}

	public ParticleScence(Context context,int itemNum) {
		super(context);
		this.itemNum = itemNum;
	}

	/**
	 * 需要初始话的效果场景
	 * @return
	 */
	protected abstract ParticleSet initScence(int itemNum);

	private void init(){
		running = false;		//初始化时线程处于停止状态
		if(itemNum == 0){		//100个粒子
			itemNum = 100;
		}
		
		//初始化场景
		scence = initScence(itemNum);
		//创建线程
		spriteThread = new Thread(run);
		//启动线程
		spriteThread.start();
	}
	
	//View绘制
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(scence != null){			//存在场景
			scence.draw(canvas);
		}
		else{						//不存在场景
			init();
		}
	}
	
	//销毁View时的收尾工作
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		running = false;
	}
	/**
	 * 休眠30ms绘制
	 */
	private Runnable run = new Runnable(){
		public void run() {
			running = true;
			while (running) {
				if(scence != null){
					scence.move();
				}
				
				mHandler.sendEmptyMessage(0);
				try {
					Thread.sleep(30);	
				} catch (InterruptedException e) {
					break;
				}
			}

		}
	};
	
	/**
	 * 重绘的Handler
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//请求重绘
			invalidate();
		};
	};
	
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	};
}
