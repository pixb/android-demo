package com.pix.anim.bean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

public abstract class GuaGuaAnimView extends View {

	/**
	 * 以宽为基准的比例（如宽为720，opt=720/720=1,宽为360则为opt=720/360=2）
	 */
	protected float opt = 1;
	protected boolean running = false;
	
	public GuaGuaAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initOpt();
	}

	public GuaGuaAnimView(Context context) {
		super(context);
		initOpt();
	}
	
	private void initOpt(){
		// 适配参数
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);

		float width = wm.getDefaultDisplay().getWidth();
		if(width>0) {
			opt = width / 720;
		}
		else {
			opt = 1 ;
		}
		
	}
	
	/**
	 * 停止动画
	 */
	public void stop(){
		running = false;
	}
	
	/**
	 * 开始播放动画
	 */
	public abstract void start();
	

	@Override
	protected final void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		running = false;
	}
	
	protected OnDriverListener mDriverlistener;
	protected OnGiftListener mGiftListener;
	/**
	 * 设置座驾动画监听
	 * @param listener
	 */
	public void setOnDriverListener(OnDriverListener listener){
		mDriverlistener = listener;
	}
	
	public interface OnDriverListener {
		void onDriverFinish();
		/**
		 * 由内存不够活内存溢出导致的动画没有播放错误
		 */
		void onError(String errorInfo);
	}
	/**
	 * 设置礼物动画监听
	 * @param listener
	 */
	public void setOnGiftListener(OnGiftListener listener){
		mGiftListener = listener;
	}
	
	
	public interface OnGiftListener{
		void onGiftAnimFinish();
		/**
		 * 由内存不够活内存溢出导致的动画没有播放错误
		 */
		void onError(String errorInfo);
	}
	
}
