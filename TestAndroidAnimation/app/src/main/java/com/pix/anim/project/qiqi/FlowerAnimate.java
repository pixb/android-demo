package com.pix.anim.project.qiqi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.pix.anim.R;
import com.pix.anim.utils.WindowsDPUtils;

import java.util.ArrayList;

/**
 * 这个是一个鲜花动画的类，鲜花像屏幕内逐渐变小
 * @author 2012120005B
 *
 */
public class FlowerAnimate {
	/** 鲜花的初始缩放比 */
	private static final float FLOWER_INIT_SCALE = 1.5f;
	/** 鲜花结束缩放比 */
	private static final float FLOWER_END_SCALE = 0.2f;
	/** 鲜花的出现动画过程时间 */
	private static final long FLOWER_APPEAR_DURATION = 300;
	/** 鲜花移动的过程时间 */
	private static final long FLOWER_TRANSLATE_DURATION = 1000;
	/** 鲜花的ImageView实例 */
	private ImageView flowerImageView;
	
	/** -- 动画的开始点数组 -- */
	public int[] startP = null;
	/** -- 动画的结束点数组 -- */
	public int[] endP = null;

	/** -- 正在播放送鲜花动画的数量 ,初始为0-- */
	private int flowerPlayingCount = 0;

	/** 初始化鲜花位置  */
	public void initPosition(Activity activity) {
		if (startP == null || endP == null) {		//数组为null创建数组
			startP = new int[2];
			endP = new int[2];

			View btnSendFlower = activity.findViewById(R.id.btnSendFlower);
			View playerView = activity.findViewById(R.id.videoViewGroup);
			
			btnSendFlower.getLocationOnScreen(startP);
			playerView.getLocationOnScreen(endP);
			
			endP[0] += playerView.getWidth() / 2;
			endP[1] += playerView.getHeight() / 2;
		}
	}

	/**
	 * 播放送鲜花动画
	 * @author: Xue Wenchao
	 * @return: void
	 */
	public void playSendFlowerAnimation(Activity context) {
		++flowerPlayingCount;
		if (flowerPlayingCount > 1) {
			return;
		}
		playSendFlowerAnimationReal(context);
	}

	//实际播放动画的方法，被动画监听类似递归调用
	private void playSendFlowerAnimationReal(final Activity roomActivity) {
		Bitmap bitmap = null;
		
		//取得图片资源
		try {
			bitmap = BitmapFactory.decodeResource(roomActivity.getResources(), R.drawable.room_anim_flower);
		}
		catch (Exception e) {
		}
		if (bitmap == null) {
			return;
		}
		//初始化位置
		initPosition(roomActivity);
		//取得最大父布局ViewGroup，将ImageView加入到要显示的布局中
		final ViewGroup rootLayout = (ViewGroup) roomActivity.findViewById(R.id.room_root_layout);
		if (flowerImageView == null) {
			flowerImageView = new ImageView(roomActivity);
			flowerImageView.setImageBitmap(bitmap);
			rootLayout.addView(flowerImageView);
		}
		/** 动画开始的X坐标，屏幕的一半减去，鲜花缩放后的一半 */
		float startX = WindowsDPUtils.getWindowsWidthPX(roomActivity) / 2 - bitmap.getWidth() * FLOWER_INIT_SCALE / 2;
		/** 开始Y，屏幕尺寸减去图片缩放后的高度 */
		float startY = startP[1] - bitmap.getHeight() * FLOWER_INIT_SCALE;
		float endX = endP[0];
		float endY = endP[1];

		//鲜花出现动画，渐显，2倍大小
		AlphaAnimation appearAlphaAnimation = new AlphaAnimation(0, 1);
		appearAlphaAnimation.setDuration(FLOWER_APPEAR_DURATION);
//		ScaleAnimation appearScaleAnimation = new ScaleAnimation(1, 1, 1, 1);
//		appearScaleAnimation.setDuration(FLOWER_APPEAR_DURATION);
//		appearScaleAnimation.setFillAfter(false);

		//位移动画，缩小+贝塞尔曲线
		ScaleAnimation tralateScaleAnimation = new ScaleAnimation(FLOWER_INIT_SCALE, FLOWER_END_SCALE, FLOWER_INIT_SCALE, FLOWER_END_SCALE);
		tralateScaleAnimation.setDuration(FLOWER_TRANSLATE_DURATION);
		tralateScaleAnimation.setStartOffset(FLOWER_APPEAR_DURATION);

		ArrayList<PointF> points = new ArrayList<PointF>();
		points.add(new PointF(startX, startY));
		points.add(new PointF(endX + (WindowsDPUtils.getWindowsWidthPX(roomActivity) - endX) / 2, endY + startY / 5));
		points.add(new PointF(endX, endY));
		BezierAnimation tralateBezierAnimation = new BezierAnimation(points);
		tralateBezierAnimation.setDuration(FLOWER_TRANSLATE_DURATION);
		tralateBezierAnimation.setStartOffset(FLOWER_APPEAR_DURATION);

		//执行动画
		AnimationSet set = new AnimationSet(false);
//		set.addAnimation(appearScaleAnimation);
		set.addAnimation(appearAlphaAnimation);
		set.addAnimation(tralateScaleAnimation);
		set.addAnimation(tralateBezierAnimation);
		flowerImageView.startAnimation(set);
		set.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {		//播放结束，将鲜花移除
				--flowerPlayingCount;
				if (flowerPlayingCount <= 0) {
					rootLayout.removeView(flowerImageView);
					flowerImageView = null;
				}
				else {
					playSendFlowerAnimationReal(roomActivity);
				}
			}
		});
	}
}
