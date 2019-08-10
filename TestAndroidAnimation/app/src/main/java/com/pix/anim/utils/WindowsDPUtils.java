package com.pix.anim.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;

public class WindowsDPUtils {

	public static int getWindowsWidthDP(Activity activity) {
		
		return px2dp(activity, getWindowsWidthPX(activity)) ;
	}
	
	public static int getWindowsHightDP(Activity activity) {
		return px2dp(activity, getWindowsHightDP(activity)) ;
	}
	
	@SuppressLint("NewApi")
	public static int getWindowsWidthPX(Activity activity) {
		
		Point point = new Point();
		activity.getWindowManager().getDefaultDisplay().getSize(point);
		return  point.x;
	}
	
	@SuppressLint("NewApi")
	public static int getWindowsHightPX(Activity activity) {
		
		Point point = new Point();
		activity.getWindowManager().getDefaultDisplay().getSize(point);
		return  point.y;
	}
	
	private static int px2dp(Context context, float pxValue) {
		
		/**  -- 密度计算 -- */
		float density = context.getResources().getDisplayMetrics().density;
		
		/** 像素 / 密度 = dp */
		return (int) Math.round(pxValue / density);
	}

}
