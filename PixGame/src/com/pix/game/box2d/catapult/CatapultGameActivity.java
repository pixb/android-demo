package com.pix.game.box2d.catapult;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CatapultGameActivity extends Activity {
	/**
	 * 弹弓游戏显示类
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐去电池等图标和一切修饰部分（状态栏部分）
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 隐去标题栏（程序的名字）
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 游戏界面横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(new MainView(this));
	}
}
