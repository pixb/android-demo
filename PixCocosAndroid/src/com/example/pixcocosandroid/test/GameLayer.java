package com.example.pixcocosandroid.test;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
/**
 * 布景层
 * @author pix
 *
 */
public class GameLayer extends CCLayer {
	private static final String TAG = "GameLayer";
	//申明一个精灵对象
	private CCSprite mPlayerSprite;
	private CCSprite mASprite ;
	private CCSprite mBSprite ;
	public GameLayer() {
		mPlayerSprite = CCSprite.sprite("player.png");
		//第一次测试=================================================
		/*
		//初始化精灵对象
		mPlayerSprite = CCSprite.sprite("player.png");
		//设置精灵对象的位置
//		mPlayerSprite.setPosition(100, 100);
		//通常用于标识坐标，或者向量
		CGPoint point = CGPoint.ccp(100f,100f);
		mPlayerSprite.setPosition(point);
		//将精灵对象添加到布景层中
		addChild(mPlayerSprite);
		CGPoint targetPoint = CGPoint.ccp(400, 100);
		//生成一个跳跃动作对象
		CCJumpTo jumpTo = CCJumpTo.action(3, targetPoint, 200, 3);
		//使用精灵对象执行该动作
		mPlayerSprite.runAction(jumpTo);
		*/
		
		//第二次测试=================================================
		/*
		//初始化精灵对象
		mPlayerSprite = CCSprite.sprite("player.png");
		CGPoint playerPos = CGPoint.ccp(100, 100);
		mPlayerSprite.setPosition(playerPos);
		addChild(mPlayerSprite);
		//1、生成动作对象
		CCFlipX flipX = CCFlipX.action(true);
		//2、执行动作对象
		mPlayerSprite.runAction(flipX);
		
		//隐藏测试
		CCHide hide = CCHide.action();
		//执行动作
		mPlayerSprite.runAction(hide);
		*/
		
		//另外还有CCFlipY和CCShow类似
		
		//第三次测试==================================================
		/*
		mPlayerSprite = CCSprite.sprite("player.png");
		this.addChild(mPlayerSprite);
		mPlayerSprite.setPosition(100, 100);
		CGPoint movePoint = CGPoint.ccp(400,400);
//		//移动动作
//		CCMoveTo moveTo = CCMoveTo.action(2, movePoint);
//		//执行动作
//		mPlayerSprite.runAction(moveTo);
		
		//旋转动画,角度制
		CCRotateTo rotateTo = CCRotateTo.action(3,180);
		//执行动作
		mPlayerSprite.runAction(rotateTo);
		*/
		//另外还有 CCScaleTo和CCBlink类似
		
		
		//第四次测试===================================================
//		mASprite = CCSprite.sprite("player.png");
//		mBSprite = CCSprite.sprite("player.png");
//		this.addChild(mASprite);
//		this.addChild(mBSprite);
//		
//		CGPoint initPoint = CGPoint.ccp(100, 100);
//		mASprite.setPosition(initPoint);
//		mBSprite.setPosition(initPoint);
		
		
		//==============================================
//		//增量计算
//		CGPoint deltaPoint = CGPoint.ccp(0, 200);
//		//1、向量的加法
//		CGPoint targetPoint = CGPoint.ccpAdd(initPoint, deltaPoint);
//		
//		mBSprite.setPosition(targetPoint); 
		
		//向量减法
		//CGPoint subPoint = CGPoint.ccpSub(initPoint, deltaPoint);
		
		//向量的乘法
		//CGPoint newPoint = CGPoint.ccpMult(initPoint, 2);
		
		//计算单位向量
//		CGPoint newPoint2 = CGPoint.ccpNormalize(initPoint);
		//===================================================
		//向量与动作
//		CGPoint targetPoint = CGPoint.ccp(100,100);
//		
//		CCMoveBy moveBy = CCMoveBy.action(3, targetPoint);
//		
//		mBSprite.runAction(moveBy);
//		
//		CGPoint jumpPoint = CGPoint.ccp(400,0);
//		CCJumpBy jumpBy = CCJumpBy.action(3, jumpPoint, 300, 2);
//		
//		mASprite.runAction(jumpBy);
//		
//		mBSprite.runAction(CCHide.action());
		
		//第五次测试、组合动画======================================================
		
//		mPlayerSprite = CCSprite.sprite("player.png");
//		CGPoint initPoint = CGPoint.ccp(100, 100);
//		this.addChild(mPlayerSprite);
//		mPlayerSprite.setPosition(initPoint);
//		
//		CGPoint targetPoint = CGPoint.ccp(300, 300);
//		CCMoveTo moveTo = CCMoveTo.action(2, targetPoint);
//		
//		CCRotateTo rotateTo = CCRotateTo.action(2, 180);
//		
//		CCScaleTo scaleTo = CCScaleTo.action(2, 2);
		//顺序执行
//		CCSequence sequence = CCSequence.actions(moveTo, rotateTo,scaleTo);
		//同步执行
		//CCSpawn spawn = CCSpawn.actions(moveTo, rotateTo,scaleTo);
		
		//回调函数
//		CCCallFuncN func = CCCallFuncN.action(this, "onActionFinished");
//		
//		CCSequence seq = CCSequence.actions(moveTo, func);
		
		//跟随动画 CCFollow
		
//		mPlayerSprite.runAction(seq);
		
		//第六次测试============================================
////		//淡入淡出动画
//		mPlayerSprite = CCSprite.sprite("player.png");
//		CGPoint initPoint = CGPoint.ccp(100, 300);
//		mPlayerSprite.setPosition(initPoint);
//		this.addChild(mPlayerSprite);
////		//从完全透明到不透明
////		CCFadeIn fadeIn = CCFadeIn.action(3);
////		//从不透明到透明
////		CCFadeOut fadeOut = CCFadeOut.action(3);
////		mPlayerSprite.runAction(fadeOut);
//		
//		//颜色变换动画
////		ccColor3B color3B = ccColor3B.ccc3(-120, 255, -120);
////		CCTintTo tintTo = CCTintTo.action(3, color3B);
////		//在当前精灵颜色的基础上再加一个值
////		CCTintBy tintBy = CCTintBy.action(3, color3B);
//		
//		//重复动画
//		CGPoint targetPoint = CGPoint.ccp(400, 300);
//		CCMoveTo  moveTo  = CCMoveTo.action(2, targetPoint);
//		CCMoveTo  moveTo2 = CCMoveTo.action(2, initPoint);
//		CCSequence seq = CCSequence.actions(moveTo, moveTo2);
//		//用于指定动作的次数
//		CCRepeat repeat = CCRepeat.action(seq, 3);
//		CCRepeatForever repeatFE = CCRepeatForever.action(seq);
//		mPlayerSprite.runAction(repeatFE);
		
		//第七次测试=======================================================
		//接受用户的触摸时间，首先必须对当前图层进行设置
		//设置接受
		this.setIsTouchEnabled(true);
//		//测试屏幕大小
//		CGSize winSize = CCDirector.sharedDirector().winSize();
//		CGSize refSize = CCDirector.sharedDirector().winSizeRef();
//		CGSize disSize = CCDirector.sharedDirector().displaySize();
//		org.cocos2d.opengl.GLSurfaceView v = CCDirector.sharedDirector().getOpenGLView();
//		Log.d(TAG, "winSize(),width:" + winSize.width + ",height:" +winSize.height);
//		Log.d(TAG, "winSizeRef(),width:" + refSize.width + ",height:" +refSize.height);
//		Log.d(TAG, "displaySize(),width:" + disSize.width + ",height:" +disSize.height);
//		Log.d(TAG, "getOpenGLView(),width:" + v.getWidth() + ",height:" +v.getHeight());
//		CGSize layerSize = this.getContentSize();
//		Log.d(TAG, "layer size:width:" + layerSize.width + ",height:" + layerSize.height);
		
		this.schedule("func",1);
		//解注册
		this.unschedule("func");
	}
	
	//delta用来表示增量，这里表示的是上次调用这个函数与本次调用函数之间的时间差
	public void func(float delta) {
		Log.d(TAG,"func(),delta:" + delta);
	}
	
	
	
	//当用户开始触摸屏幕时，此方法被回调
	//ccoverToGL可以将以左上角为原点的坐标，转换成以左下角为原点的坐标系坐标
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
//		Log.i(TAG, "ccTouchesBegan(),x:" + event.getX() + ",y:" + event.getY());
//		CGPoint p1 = CGPoint.ccp(event.getX(),event.getY());
//		CGPoint p2 = CCDirector.sharedDirector().convertToGL(p1);
//		Log.i(TAG, "ccTouchesBegan(),ccx:" + p2.x + ",ccy:" + p2.y);
		return super.ccTouchesBegan(event);
	}
	//当用户手指离开屏幕时，此方法被回调
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
//		Log.i(TAG, "ccTouchesEnded(),x:" + event.getX() + ",y:" + event.getY());
//		CGPoint p1 = CGPoint.ccp(event.getX(),event.getY());
//		CGPoint p2 = CCDirector.sharedDirector().convertToGL(p1);
//		Log.i(TAG, "ccTouchesEnded(),ccx:" + p2.x + ",ccy:" + p2.y);
		return super.ccTouchesEnded(event);
	}
	
	//当用户手指在屏幕上在屏幕上移动时，执行该方法
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
//		Log.i(TAG, "ccTouchesMoved(),x:" + event.getX() + ",y:" + event.getY());
//		CGPoint p1 = CGPoint.ccp(event.getX(),event.getY());
//		CGPoint p2 = CCDirector.sharedDirector().convertToGL(p1);
//		Log.i(TAG, "ccTouchesMoved(),ccx:" + p2.x + ",ccy:" + p2.y);
		return super.ccTouchesMoved(event);
	}



	public void onActionFinished(Object sender) {
		Log.i(TAG,"onActionFinished()");
	}
}
