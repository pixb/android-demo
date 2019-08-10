package com.example.pixcocosandroid.test;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import com.example.pixcocosandroid.util.WindowsDPUtils;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
/**
 * 测试类
 * @author tpx
 *
 */
public class CocosTestActivity extends Activity {
	private static final String TAG = "CocosTestActivity";
	//Cocos2d引擎会把图形会知这个view上,注意这里是CCGLSurfaceView 如果用GLSurfaceView会报错
	private CCGLSurfaceView glView = null ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		glView = new CCGLSurfaceView(this);
		//得到导演对象
		CCDirector director = CCDirector.sharedDirector();
		//设置应用程序相关的属性
		//设置游戏程序使用的View
		director.attachInView(glView);
		//设置是否显示FPS值
		director.setDisplayFPS(true);
		//设置游戏渲染一帧数序所需要的时间
		director.setAnimationInterval(1 / 30.0f);
		
		Log.d(TAG, "win,width:" + WindowsDPUtils.getWindowsWidthPX(this) + ",height:" + WindowsDPUtils.getWindowsHightPX(this));
		Log.d(TAG, "cocos,width:" + CCDirector.sharedDirector().displaySize().width + ",height:" + CCDirector.sharedDirector().displaySize().height);
		director.setScreenSize(WindowsDPUtils.getWindowsWidthPX(this) * 0.9f,WindowsDPUtils.getWindowsHightPX(this)*0.9f);
		//生成场景对象
		CCScene scene = CCScene.node();
		//生成布景层对象
		GameLayer layer = new GameLayer();
		//将布景层添加至游戏场景当中
		scene.addChild(layer);
		//运行游戏场景
		director.runWithScene(scene);
		setContentView(glView);
	}

}
