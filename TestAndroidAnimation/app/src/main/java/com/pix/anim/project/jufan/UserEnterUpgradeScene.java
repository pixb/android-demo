package com.pix.anim.project.jufan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.pix.anim.R;
import com.pix.anim.bean.AnimFramesSprite;
import com.pix.anim.bean.AnimSprite;
import com.pix.anim.bean.SurfaceAnimScene;
import com.pix.anim.utils.AnimBitmapLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚范中入场等级的动画 所有偏移量，位移量根据opt计算或者屏幕宽度计算，或者资源尺寸百分比计算，以求达到不同分辨率下可以适配的效果
 * 
 * @author tpx
 * 
 */
public class UserEnterUpgradeScene extends SurfaceAnimScene {

	// =======================基础参数
	private final String TAG = "JufanEnterRoomView";
	/** 动画移动间隔时间 */
	private static final int SLEEP_TIME = 50;
	/** 用户信息 */
	private EnterUser mUser;
	/** 背景序列帧的高度 */
	private float FRAME_BG_HEIGHT = 56;
	/** 背景序列帧的宽度 */
	private float FRAME_BG_WIDTH = 658;
	// ========================动画的类型
	/** 进入房间动画类型 */
	public static final byte TYPE_ENTER_ROOM = 0;
	/** 升级的类型 */
	public static final byte TYPE_UPGRADE = 1;
	// ========================状态定义
	/** 背景开始进入 */
	private static final int STATE_BG_ENTER = 1;
	/** 文字开始出现状态 */
	private static final int STATE_TEXT_ENTER = 2;
	/** 所有内容开始移出状态 */
	private static final int STATE_CONTENT_OUT = 3;
	/** 文字开始进入的时间 */
	private int textEnterStartTime = 0;
	/** 所有内容开始移出 */
	private int sceneOutStartTime = 1000;
	/** 动画的结束时间 */
	private int finishTime = 1300;
	// =====================各等级参数
	/**
	 * 背景绘制时间计数器
	 */
	private int enterBGTimeCount = 0; 
	/**
	 * 文字显示时间计数器
	 */
	private int textEnterTimeCount = 0;
	/**
	 * 背景移除时间计数器
	 */
	private int bgOutTimeCount = 0;
	// 等级1的参数背景
	/** 等级1的序列帧 */
	private static final int LV1_BG_FRAMES_SIZE = 6;
	/** 等级1背图片集合 */
	private List<Bitmap> mLV1BGFrameList;
	private int [] mLv1BGFrameIds = new int [6];
	/** 等级1的背景序列精灵 */
	private AnimFramesSprite mLV1BGFrameSprite ;
	/** 等级1的进入时结束索引值 */
	private static final int LV1_ENTER_END_INDEX = 5;
	/** 背景序列帧索引 */
	private int mBGFrameIndex1 = 0;
	/** 背景的x坐标 */
	private float mBGX;
	/** 背景的Y坐标 */
	private float mBGY;
	// 等级2的背景参数
	/** 等级2的序列帧 */
	private static final int LV2_BG_FRAMES_SIZE = 18;
	/** 等级2背图片集合 */
	private List<Bitmap> mLV2BGFrameList ;
	private int [] mLv2BGFrameIds = new int[18];
	/** 等级2的背景序列精灵 */
	private AnimFramesSprite mLV2BGFrameSprite ;
	/** 等级2的进入时结束索引值 */
	private static final int LV2_ENTER_END_INDEX = 5;
	/** 等级2闪动结束的索引值 */
	private static final int LV2_FLASH_END_INDEX = 17;
	/** 背景序列帧索引 */
	private int mBGFrameIndex2 = 0;
	// 等级3的背景
	/** 等级3的序列帧 */
	private static final int LV3_BG_FRAMES_SIZE = 59;
	/** 等级3背图片集合 */
	private List<Bitmap> mLV3BGFrameList;
	private int [] mLv3BGFramesIds = new int[59];
	/** 等级3的背景序列精灵 */
	private AnimFramesSprite mLV3BGFrameSprite ;
	/** 等级3的进入时结束索引值 */
	private static final int LV3_ENTER_END_INDEX = 11;
	/** 等级3闪动结束的索引值 */
	private static final int LV3_FLASH_END_INDEX = 58;
	/** 背景序列帧索引 */
	private int mBGFrameIndex3 = 0;
	// 等级4的背景
	/** 等级4的序列帧 */
	private static final int LV4_BG_FRAMES_SIZE = 44;
	/** 等级4背图片集合 */
	private List<Bitmap> mLV4BGFrameList;
	private int [] mLv4BGFrameIds = new int[44];
	/** 等级4的背景序列精灵 */
	private AnimFramesSprite mLV4BGFrameSprite ;
	/** 等级3的进入时结束索引值 */
	private static final int LV4_ENTER_END_INDEX = 13;
	/** 等级3闪动结束的索引值 */
	private static final int LV4_FLASH_END_INDEX = 43;
	/** 背景序列帧索引 */
	private int mBGFrameIndex4 = 0;

	// ========== 文字相关参数 =====================
	/** 文字出现经过的时间 */
	private static final int TIME_TEXT_ENTER = 300;
	/** 内容Text字号 */
	private static final int TEXT_SIZE = 30;
	/** 文字x轴的偏移量 */
	private float mTextOffsetX;
	/** 文字y轴的偏移量 */
	private float mTextOffsetY;
	/** 文字的颜色 */
	private int mNameTextColor;
	/** 尾部的文字颜色 */
	private int mTailTextColor;
	/** 文字的 0~255 */
	private int mTextAlpha;
	/** 文字每移动一步的透明度变化值 */
	private int mTextPerMoveAlpha;

	// ===================等级相关
	private static final int LV1_LEVEL_BG_COLOR = 0xFFFD8D08;
	private static final int LV2_LEVEL_BG_COLOR = 0xFF3DA8D0;
	private static final int LV3_LEVEL_BG_COLOR = 0xFFFE2B56;
	private static final int LV4_LEVEL_BG_COLOR = 0xFFEB6844;
	/** 等级图标 */
	private Bitmap mLevelIconBitmap;
	/** 等级图标精灵 */
	private AnimSprite mLevelIconSprite ;
	// 等级背景的x坐标
	private float mLevelBGX;
	// 等级背景的y坐标
	private float mLevelBGY;
	/** 等级背景的宽度 */
	private static final float LEVEL_BG_WIDTH = 50;
	/** 等级背景圆角半径 */
	private static final float LEVEL_BG_R = 5f;
	/** 等级背景的高度 */
	private static final float LEVEL_BG_HEIGHT = 24;
	/** 等级Text字号 */
	private static final int LEVEL_TEXT_SIZE = 15;
	// ===================内容移出相关参数
	/** 所有内容开始移出经过时间 */
	private static final int TIME_SCENE_OUT = 300;
	/** 所有内容移出x轴经过的距离 */
	private float mContentOutTotalXDistance;
	/** 内容移出x轴每步走的距离 */
	private float mContentOutPerXDistance;

	// ====================星星动画相关
	/** 星星的图片资源 */
	private Bitmap mStarBitmap;
	/** 星星的精灵 */
	private AnimSprite mStarSprite1 ;
	private AnimSprite mStarSprite2 ;
	private AnimSprite mStarSprite3 ;
	/*** 星星最小缩放值 */
	private static final float SCALE_STAR_MIN = 0.2f;
	/** 星星最大缩放值 */
	private static final float SCALE_STAR_MAX = 1.0f;
	/** 星星1的缩放比 */
	private float mStar1Scale = 0.3f;
	/** 星星1缩放每步变换值 */
	private float mStar1MoveScale = 0.1f;
	/** 星星2的缩放比 */
	private float mStar2Scale = 0.6f;
	/** 星星2缩放每步变换值 */
	private float mStar2MoveScale = 0.1f;
	/** 星星3的缩放比 */
	private float mStar3Scale = 0.9f;
	/** 星星3缩放每步变换值 */
	private float mStar3MoveScale = 0.1f;

	// 强光相关
	/*** 强光图片 */
	private Bitmap mStrongLightBitmap;
	/** 强光的精灵 */
	private AnimSprite mSLSprite ;
	/** 强光x轴移动的总距离 */
	private float mSLMoveTotalX;
	/** 强光x轴每步移动距离 */
	private float mSLMoveStepX;
	/** 强光x轴偏移量 */
	private float mSLOffsetX;
	/** 强光的alpha值，开始255 */
	private int mSLAlpha = 255;

	// ============等级3的星星序列帧
	/** 等级3星星序列帧图片 */
	private List<Bitmap> mLv3StarsList ;
	private int [] mLv3StarsFrameIds = new int[26];
	/** 等级3星星序列个数 */
	private static final int LV3_STARTS_FRAMES_SIZE = 26;
	/** 等级3星星的序列精灵 */
	private AnimFramesSprite mStarsFSprite ;
	/** 等级3星星的索引值 */
	private int mLv3StarsIndex;

	// =============等级4的花序列帧
	/** 等级4花序列个数 */
	private static final int LV4_FLOWERS_SIZE = 55;
	/** 等级4花序列图片 */
	private List<Bitmap> mLV4FlowersList;
	private int [] mLv4FloweresFrameIds = new int[55];
	/** 等级4花序列精灵 */
	private AnimFramesSprite mLV4FlowersFSprite ;
	/** 等级4花的索引值 */
	private int mLv4FloweresIndex;
	// ======================升级相关
	/** 升级1的文字颜色 */
	private static final int COLOR_UP1_TEXT = 0xFFFFCC01;
	/** 升级2的文字颜色 */
	private static final int COLOR_UP2_TEXT = 0xFFFFFCB3;
	// 升级1背景动画
	/** 升级1动画背景的帧个数 */
	private static final int UG_LV1_BG_FRAMES_SIZE = 11;
	/** 升级1的图片序列 */
	private List<Bitmap> mUGLV1BGFrames ;
	private int [] mUpgradeLv1BGFrameIds = new int[11];
	/** 升级1的背景动画序列帧精灵 */
	private AnimFramesSprite mUGLV1BGFSprite ;
	/** 背景进入结束索引值 */
	private static final int UG_LV1_FRAME_ENTER_END_INDEX = 4;
	/** 背景完全显示结束索引值 */
	private static final int UG_LV1_FRAME_SHOW_END_INDEX = 10;
	/** 等级1升级背景索引值 */
	private int mUGLv1BGFramesIndex;

	// 升级2背景动画
	private static final int LV2_UG_BG_FRAMES_SIZE = 40;
	/** 升级2等动画背景序列 */
	private List<Bitmap> mUGLV2BGFrames ;
	private int [] mUpgradeLv2BGFrameIds = new int[40];
	/*** 升级2等动画背景序列精灵 */
	private AnimFramesSprite mUGLV2BGFSprite ;
	/** 背景进入结束索引值 */
	private static final int UG_LV2_FRAME_ENTER_END_INDEX = 8;
	/** 背景完全显示结束索引值 */
	private static final int UG_LV2_FRAME_SHOW_END_INDEX = 39;
	/** 等级2升级背景索引值 */
	private int mUGLv2BGFramesIndex;

	// 升级2的光序列动画
	/** 升级2的灯光序列 */
	private AnimFramesSprite mUGLv2LightFrames ;
	private List<Bitmap> mUpgradeLv2LightList;
	private final static int UG_LV2_LIGHT_FRAMES_SIZE = 26;
	private int [] mUpgradeLv2LightFrameIds = new int[26];

	/** 等级2升级的x补差，图片尺寸特殊 */
	float fixUp2X = 0;
	/**
	 * 等级2升级灯光索引值
	 */
	private int mUGLv2LightFramesIndex;

	// 周榜和月榜的图标
	private Bitmap mWeekMonthIconBitmap;
	/** 周月榜的x坐标 */
	private float mWMX;
	/** 周月榜图标的精灵 */
	private AnimSprite mWMSprite ;

	public UserEnterUpgradeScene(Context context) {
		super(context);
	}

	@Override
	public void init() {
		Log.d(TAG, "init()");
		//加载等级2的背景资源ID
				for (int i = 0; i < mLv2BGFrameIds.length;i++) {
					mLv2BGFrameIds[i] = getResources().getIdentifier("bar3000" + i,"drawable",getContext().getPackageName());
				}
				//加载等级3背景的资源ID
				for (int i = 0 ;i < mLv3BGFramesIds.length ;i ++) {
					mLv3BGFramesIds[i] = getResources().getIdentifier("bar2000" + i , "drawable", getContext().getPackageName());
				}
				//加载等级3需要的星星序列ID
				for(int i = 0; i <mLv3StarsFrameIds.length ;i++) {
					mLv3StarsFrameIds[i] = getResources().getIdentifier("b000" + i , "drawable", getContext().getPackageName());
				}
				//加载等级4的背景序列ID
				for (int i = 0 ;i < mLv4BGFrameIds.length;i++) {
					mLv4BGFrameIds[i] = getResources().getIdentifier("bar1000" + i,"drawable",getContext().getPackageName());
				}
				//加载等级4需要的花
				for(int i = 0; i < mLv4FloweresFrameIds.length ;i++) {
					mLv4FloweresFrameIds[i] = getResources().getIdentifier("a000" + i,"drawable",getContext().getPackageName());
				}
				//加载等级1的背景序列ID
				for(int i = 0; i < mLv1BGFrameIds.length; i++ ) {
					mLv1BGFrameIds[i] = getResources().getIdentifier("bar4000" + i,"drawable",getContext().getPackageName());
				}

				//加载升级1的背景序列ID
				for(int i = 0; i< mUpgradeLv1BGFrameIds.length;i++) {
					mUpgradeLv1BGFrameIds[i] = getResources().getIdentifier("uga" + i,"drawable",getContext().getPackageName());
				}

				//加载等级2的背景序列ID
				for (int i = 0 ;i < mUpgradeLv2BGFrameIds.length;i++) {
					mUpgradeLv2BGFrameIds[i] = getResources().getIdentifier("ugb" + i,"drawable",getContext().getPackageName());
				}
				//加载升级等级2的灯光序列IDS
				for (int i = 0;i <mUpgradeLv2LightFrameIds.length;i++) {
					mUpgradeLv2LightFrameIds[i] = getResources().getIdentifier("uglight" + i,"drawable",getContext().getPackageName());
				}
		
		/**
		 * 设置间隔时间
		 */
		setSleepTime(SLEEP_TIME);
		// 进入动画类型
		if (this.mUser.animType == TYPE_ENTER_ROOM) {
			switch (getLevel()) {
			case 2:
				// 级别2的背景
				mLV2BGFrameList = new ArrayList<Bitmap>();
				for (int i = 0; i < LV2_BG_FRAMES_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance().readBitmap(getContext(),mLv2BGFrameIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mLV2BGFrameList.add(bm);
				}
				mLV2BGFrameSprite = new AnimFramesSprite();
				mLV2BGFrameSprite.init(mLV2BGFrameList);
				textEnterStartTime = LV2_ENTER_END_INDEX * mSleepTime + 100;
				sceneOutStartTime =  1500;
				finishTime = 1600;

				break;
			case 3:
				// 级别3的背景
				mLV3BGFrameList = new ArrayList<Bitmap>();
				for (int i = 0; i < LV3_BG_FRAMES_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance().readBitmap(getContext(),mLv3BGFramesIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mLV3BGFrameList.add(bm);
				}
				mLV3BGFrameSprite = new AnimFramesSprite();
				mLV3BGFrameSprite.init(mLV3BGFrameList);
				textEnterStartTime = LV3_ENTER_END_INDEX * mSleepTime + 100;
				sceneOutStartTime =  2000;
				finishTime =  2300;

				// 加载等级3的星星
				mLv3StarsList = new ArrayList<Bitmap>();
				for (int i = 0; i < LV3_STARTS_FRAMES_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance()
							.readBitmap(getContext(),mLv3StarsFrameIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mLv3StarsList.add(bm);
				}
				mStarsFSprite = new AnimFramesSprite();
				mStarsFSprite.init(mLv3StarsList);

				// 加载强光图片
				mStrongLightBitmap = AnimBitmapLoader.getInstance()
						.readBitmap(getContext(), R.drawable.strong_light);
				if (mStrongLightBitmap == null) {
					mState = STATE_FINISH;
				}
				mSLSprite = new AnimSprite();
				mSLSprite.init(mStrongLightBitmap);

				break;
			case 4:
				// 级别4的背景
				mLV4BGFrameList = new ArrayList<Bitmap>();
				for (int i = 0; i < LV4_BG_FRAMES_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance().readBitmap(getContext(),mLv4BGFrameIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mLV4BGFrameList.add(bm);
				}
				mLV4BGFrameSprite = new AnimFramesSprite();
				mLV4BGFrameSprite.init(mLV4BGFrameList);
				textEnterStartTime = LV4_ENTER_END_INDEX * mSleepTime + 100;
				sceneOutStartTime = 2000;
				finishTime =  2300;
				// 加载等级3的星星
				mLv3StarsList = new ArrayList<Bitmap>();
				for (int i = 0; i < LV3_STARTS_FRAMES_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance()
							.readBitmap(getContext(),mLv3StarsFrameIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mLv3StarsList.add(bm);
				}
				mStarsFSprite = new AnimFramesSprite();
				mStarsFSprite.init(mLv3StarsList);

				// 加载等级4的花
				mLV4FlowersList = new ArrayList<Bitmap>();
				for (int i = 0; i < LV4_FLOWERS_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance().readBitmap(getContext(),mLv4FloweresFrameIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mLV4FlowersList.add(bm);
				}
				mLV4FlowersFSprite = new AnimFramesSprite();
				mLV4FlowersFSprite.init(mLV4FlowersList);
				break;
			case 1:

			default:
				mLV1BGFrameList = new ArrayList<Bitmap>();
				// 级别1的背景
				for (int i = 0; i < LV1_BG_FRAMES_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance().readBitmap(getContext(),mLv1BGFrameIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mLV1BGFrameList.add(bm);
				}
				mLV1BGFrameSprite = new AnimFramesSprite();
				mLV1BGFrameSprite.init(mLV1BGFrameList);
				textEnterStartTime = LV1_ENTER_END_INDEX * mSleepTime + 100;
				sceneOutStartTime = 1300;
				finishTime =  1600;
				break;
			}

			// 加载周月榜按钮
			if (mUser.iWeekMonthType == 0) {
				mWeekMonthIconBitmap = AnimBitmapLoader.getInstance()
						.readBitmap(getContext(), R.drawable.icon_month);
				if (mWeekMonthIconBitmap == null) {
					mState = STATE_FINISH;
				}
				mWMSprite = new AnimSprite();
				mWMSprite.init(mWeekMonthIconBitmap);
			}

			if (mUser.iWeekMonthType == 1) {
				mWeekMonthIconBitmap = AnimBitmapLoader.getInstance()
						.readBitmap(getContext(), R.drawable.icon_week);
				if (mWeekMonthIconBitmap == null) {
					mState = STATE_FINISH;
				}
				mWMSprite = new AnimSprite();
				mWMSprite.init(mWeekMonthIconBitmap);
			}

		}
		// 升级类型
		else {
			if (mUser.level <= 20) {
				// 升级级别1的背景
				mUGLV1BGFrames = new ArrayList<Bitmap>();
				for (int i = 0; i < UG_LV1_BG_FRAMES_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance().readBitmap(getContext(),mUpgradeLv1BGFrameIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mUGLV1BGFrames.add(bm);
				}
				mUGLV1BGFSprite = new AnimFramesSprite();
				mUGLV1BGFSprite.init(mUGLV1BGFrames);
				textEnterStartTime = UG_LV1_FRAME_ENTER_END_INDEX * mSleepTime
						+ 100;
				sceneOutStartTime = textEnterStartTime + 800;
				finishTime = textEnterStartTime + 1100;
			}
			// 大于1的
			else {
				// 升级级别1的背景
				mUGLV2BGFrames = new ArrayList<Bitmap>();
				for (int i = 0; i < LV2_UG_BG_FRAMES_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance().readBitmap(getContext(),mUpgradeLv2BGFrameIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mUGLV2BGFrames.add(bm);
				}
				mUGLV2BGFSprite = new AnimFramesSprite();
				mUGLV2BGFSprite.init(mUGLV2BGFrames);
				textEnterStartTime = UG_LV2_FRAME_ENTER_END_INDEX * mSleepTime
						+ 100;
				sceneOutStartTime =  1300;
				finishTime =  1600;

				// 加载升级2所需要的灯光
				// 升级级别1的背景
				mUpgradeLv2LightList = new ArrayList<Bitmap>();
				for (int i = 0; i < UG_LV2_LIGHT_FRAMES_SIZE; i++) {
					Bitmap bm = AnimBitmapLoader.getInstance().readBitmap(getContext(),mUpgradeLv2LightFrameIds[i]);
					if (bm == null) {
						mState = STATE_FINISH;
					}
					mUpgradeLv2LightList.add(bm);
				}
				mUGLv2LightFrames = new AnimFramesSprite();
				mUGLv2LightFrames.init(mUpgradeLv2LightList);
			}
		}
		int lvIconId = 0;
		switch (getLevel()) {
		case 1:
			lvIconId = R.drawable.lv1_icon;
			break;
		case 2:
			lvIconId = R.drawable.lv2_icon;
			break;
		case 3:
			lvIconId = R.drawable.lv3_icon;
			break;
		case 4:
			lvIconId = R.drawable.lv4_icon;
			break;
		}

		// 加载等级图标的图片
		mLevelIconBitmap = AnimBitmapLoader.getInstance().readBitmap(getContext(),lvIconId);
		if (mLevelIconBitmap == null) {
			mState = STATE_FINISH;
		}
		mLevelIconSprite = new AnimSprite();
		mLevelIconSprite.init(mLevelIconBitmap);

		// 加载星星的图片
		mStarBitmap = AnimBitmapLoader.getInstance().readBitmap(getContext(), R.drawable.star);
		if (mStarBitmap == null) {
			mState = STATE_FINISH;
		}
		
	}

	@Override
	public void initParams() {
		Log.d(TAG,"initParams()");
		if(mUser == null) {
			return ;
		}
		// ========背景相关
		mBGX = 20 * mOpt;
		mBGY = getHeight() / 2;
		// 升级2的特殊x补差
		float fixUp2X = 0;
		if (mUser.animType == TYPE_UPGRADE && mUser.level >= 21) { // 升级的比较特殊
			mBGX = -15 * mOpt;
			fixUp2X = 50 * mOpt;
			mBGY = getHeight() / 2 - mUGLV2BGFSprite.getHeight() / 2
					+ FRAME_BG_HEIGHT * mOpt / 2 + 2 * mOpt;
		}
		// =========所有内容移出
		// 所有内容移除的x距离
		mContentOutTotalXDistance = FRAME_BG_WIDTH * mOpt;
		// 内容移除每步x距离
		mContentOutPerXDistance = -mContentOutTotalXDistance
				/ (TIME_SCENE_OUT / mSleepTime);
		// =========等级背景
		// 等级背景x坐标
		mLevelBGX = mBGX + 12 * mOpt + fixUp2X;
		// 等级背景y坐标
		mLevelBGY = getHeight() / 2
				+ ((FRAME_BG_HEIGHT * mOpt - 25 * mOpt) / 2);
		// 文字每步透明度变化
		mTextPerMoveAlpha = 255 / (TIME_TEXT_ENTER / mSleepTime);
		enterBGTimeCount = 0;
		textEnterTimeCount = 0;
		bgOutTimeCount = 0;
		mTextOffsetX = 0;
		mSLOffsetX = 0;
		mLv4FloweresIndex = 0;
		mUGLv2LightFramesIndex = 0;
		// 开始状态
		mState = STATE_BG_ENTER;
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		switch (mState) {
//		case STATE_BG_ENTER: // 绘制背景进入
//			drawLv4Floweres(canvas);
//			drawBGEnter(canvas);
//			drawLv3Stars(canvas);
//			drawStrongLight(canvas);
//			break;
//		case STATE_TEXT_ENTER:
//			drawLv4Floweres(canvas);
//			drawBGEnter(canvas);
//			drawLv3Stars(canvas);
//			drawContent(canvas);
//			drawStars(canvas);
//			drawUpgradeLv2Light(canvas);
//			drawWMIcon(canvas);
//			break;
//		case STATE_CONTENT_OUT:
//			drawBGEnter(canvas);
//			drawContent(canvas);
//			drawStars(canvas);
//			break;
//		}
	}

	/**
	 * 背景进入
	 * 
	 * @param canvas
	 */
	private void drawBGEnter(Canvas canvas) {
		Log.d(TAG, "drawBGEnter()");
		if (mUser.animType == TYPE_ENTER_ROOM) { // 进入房间绘制背景
			switch (getLevel()) {
			case 2:
				mLV2BGFrameSprite.setPostion(mBGX, mBGY);
				mLV2BGFrameSprite.setOffsetX(mTextOffsetX);
				mLV2BGFrameSprite.draw(canvas, mBGFrameIndex2);
				break;
			case 3:
				mLV3BGFrameSprite.setPostion(mBGX, mBGY);
				mLV3BGFrameSprite.setOffsetX(mTextOffsetX);
				mLV3BGFrameSprite.draw(canvas, mBGFrameIndex3);
				break;
			case 4:
				mLV4BGFrameSprite.setPostion(mBGX, mBGY);
				mLV4BGFrameSprite.setOffsetX(mTextOffsetX);
				mLV4BGFrameSprite.draw(canvas, mBGFrameIndex4);
				break;
			case 1:
			default:
				mLV1BGFrameSprite.setPostion(mBGX, mBGY);
				mLV1BGFrameSprite.setOffsetX(mTextOffsetX);
				mLV1BGFrameSprite.draw(canvas, mBGFrameIndex1);
				break;
			}
		} else { // 升级绘制背景
			if (mUser.level <= 20) {
				mUGLV1BGFSprite.setPostion(mBGX, mBGY);
				mUGLV1BGFSprite.setOffsetX(mTextOffsetX);
				mUGLV1BGFSprite.draw(canvas, mUGLv1BGFramesIndex);
			} else {
				mUGLV2BGFSprite.setPostion(mBGX, mBGY);
				mUGLV2BGFSprite.setOffsetX(mTextOffsetX);
				mUGLV2BGFSprite.draw(canvas, mUGLv2BGFramesIndex);
			}
		}
		Paint gpt = new Paint();
		gpt.setColor(Color.GREEN);
		canvas.drawText("drawBG", 100, 100, gpt);
	}

	/**
	 * 绘制内容
	 */
	private void drawContent(Canvas canvas) {
		drawText(canvas);
		drawLeve(canvas);
	}

	/**
	 * 绘制文字
	 * 
	 * @param canvas
	 */
	private void drawText(Canvas canvas) {
		//处理名字长度
		mNameTextColor = Color.WHITE;
		mTailTextColor = Color.WHITE;
		if (mUser.animType == TYPE_UPGRADE) {
			if (mUser.level < 21) {
				mTailTextColor = COLOR_UP1_TEXT;
			} else {
				mTailTextColor = COLOR_UP2_TEXT;
			}
		}
		// 文字的X坐标
		float textX = mLevelBGX + LEVEL_BG_WIDTH * mOpt + 20 * mOpt;
		// 文字的y坐标
		float textY = getHeight() / 2 + 25 * mOpt;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(mNameTextColor);
		mPaint.setTextSize(TEXT_SIZE * mOpt);
		mPaint.setAlpha(mTextAlpha);
//		if (getLevel() >= 3) {
//			// 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
//			mPaint.setShadowLayer(5, 3, 3, mNameTextColor);
//		}
		canvas.save();
		// 剧中
		FontMetrics fontMetrics = mPaint.getFontMetrics();
		float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
		float offY = fontTotalHeight / 2 - fontMetrics.bottom;
		canvas.translate(textX + mTextOffsetX, textY + mTextOffsetY + offY);
		String name = "范爷";
		if (mUser != null) {
			name = mUser.name;
		}
		canvas.drawText(name, 0, 0, mPaint);
		canvas.restore();

		// 绘制后面的字体
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(mTailTextColor);
		mPaint.setTextSize(TEXT_SIZE * mOpt);
		mPaint.setAlpha(mTextAlpha);
		canvas.save();
		// 剧中
		FontMetrics fontMetrics2 = mPaint.getFontMetrics();
		float fontTotalHeight2 = fontMetrics2.bottom - fontMetrics2.top;
		float offY2 = fontTotalHeight2 / 2 - fontMetrics2.bottom;
		float maginLeft = 20 * mOpt;
		float nameWidth = mPaint.measureText(name);
		float name2TransX = textX + mTextOffsetX + nameWidth + maginLeft;
		canvas.translate(name2TransX, textY + mTextOffsetY + offY2);
		String name2 = "进入房间";
		if (mUser.animType == TYPE_UPGRADE) {
			name2 = "荣升" + mUser.level + "级";
		}
		canvas.drawText(name2, 0, 0, mPaint);
		canvas.restore();
		float name2Width = mPaint.measureText(name2);
		float wmMarginLeft = 30 * mOpt;
		mWMX = name2TransX + name2Width + wmMarginLeft;
	}

	/**
	 * 绘制等级
	 */
	private void drawLeve(Canvas canvas) {
		drawLevelBG(canvas);
		drawLevelText(canvas);
	}

	private void drawLevelBG(Canvas canvas) {
		mPaint.setAlpha(mTextAlpha);
		mPaint.setStyle(Paint.Style.FILL);
		switch (getLevel()) {
		case 1:
			mPaint.setColor(LV1_LEVEL_BG_COLOR);
			break;

		case 2:
			mPaint.setColor(LV2_LEVEL_BG_COLOR);
			break;
		case 3:
			mPaint.setColor(LV3_LEVEL_BG_COLOR);
			break;
		case 4:
			mPaint.setColor(LV4_LEVEL_BG_COLOR);
			break;
		}
		canvas.save();
		canvas.translate(mLevelBGX + mTextOffsetX, mLevelBGY + mTextOffsetY);
		RectF re3 = new RectF(0, 0, LEVEL_BG_WIDTH * mOpt, LEVEL_BG_HEIGHT
				* mOpt);
		// 绘制圆角矩形
		canvas.drawRoundRect(re3, LEVEL_BG_R * mOpt, LEVEL_BG_R * mOpt, mPaint);
		canvas.restore();
		// 绘制图标
		mLevelIconSprite.setPostion(mLevelBGX + mTextOffsetX + 4 * mOpt,
				mLevelBGY + mTextOffsetY + 3 * mOpt);
		mLevelIconSprite.draw(canvas);
	}

	/**
	 * 绘制等级文字
	 * 
	 * @param canvas
	 */
	private void drawLevelText(Canvas canvas) {
		mPaint.setColor(mNameTextColor);
		mPaint.setTextSize(LEVEL_TEXT_SIZE);
		mPaint.setAlpha(mTextAlpha);

		// 等级文字的x坐标
		float textX = LEVEL_BG_WIDTH * mOpt + mLevelBGX - 25 * mOpt;
		// 等级文字的y坐标
		float textY = getHeight() / 2 + 27 * mOpt;

		canvas.save();
		// 剧中
		FontMetrics fontMetrics = mPaint.getFontMetrics();
		float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
		float offY = fontTotalHeight / 2 - fontMetrics.bottom;
		canvas.translate(textX + mTextOffsetX, textY + mTextOffsetY + offY);
		String level = "1";
		if (mUser != null) {
			level = mUser.level + "";

		}
		canvas.drawText(level, 0, 0, mPaint);
		canvas.restore();
	}

	/**
	 * 绘制星星
	 * 
	 * @param canvas
	 */
	private void drawStars(Canvas canvas) {
		if (mUser.animType == TYPE_UPGRADE) {
			return;
		}
		// 1级不会绘制星星
		if (getLevel() == 1) {
			return;
		}
		// 计算坐标
		float mStar1X = mLevelBGX - 5 * mOpt;
		float mStar1Y = mLevelBGY - 8 * mOpt;
		float mStar2X = mLevelBGX + 40 * mOpt;
		float mStar2Y = mLevelBGY - 8 * mOpt;
		float mStar3X = mLevelBGX + 20 * mOpt;
		float mStar3Y = mLevelBGY + 15 * mOpt;
		// 绘制星星2
		mStarSprite2 = new AnimSprite();
		mStarSprite2.init(mStarBitmap);
		mStarSprite2.setAlpha(mTextAlpha);
		mStarSprite2.setScale(mStar2Scale, mStar2Scale);
		mStarSprite2.setScalePoint(mStar2X + mStarSprite2.getWidth() / 2,
				mStar2Y + mStarSprite2.getHeight() / 2);
		mStarSprite2.setPostion(mStar2X, mStar2Y);
		mStarSprite2.setOffset(mTextOffsetX, mTextOffsetY);
		mStarSprite2.draw(canvas);
		// 等级2绘制1和3
		if (getLevel() == 2) {
			return;
		}
		// 绘制星星1
		mStarSprite1 = new AnimSprite();
		mStarSprite1.init(mStarBitmap);
		mStarSprite1.setScale(mStar1Scale, mStar1Scale);
		mStarSprite1.setScalePoint(mStar1X + mStarSprite1.getWidth() / 2,
				mStar1Y + mStarSprite1.getHeight() / 2);
		mStarSprite1.setPostion(mStar1X, mStar1Y);
		mStarSprite1.setOffset(mTextOffsetX, mTextOffsetY);
		mStarSprite1.draw(canvas);
		// 绘制星星3
		mStarSprite3 = new AnimSprite();
		mStarSprite3.init(mStarBitmap);
		mStarSprite3.setScale(mStar3Scale, mStar3Scale);
		mStarSprite3.setScalePoint(mStar3X + mStarSprite3.getWidth() / 2,
				mStar3Y + mStarSprite3.getHeight() / 2);
		mStarSprite3.setPostion(mStar3X, mStar3Y);
		mStarSprite3.setOffset(mTextOffsetX, mTextOffsetY);
		mStarSprite3.draw(canvas);
	}

	/**
	 * 绘制等级3需要的强光
	 * 
	 * @param canvas
	 */
	private void drawStrongLight(Canvas canvas) {
		// 不是3级没有强光
		if (getLevel() != 3 || mUser.animType != TYPE_ENTER_ROOM) {
			return;
		}
		// 大于最大距离，不用绘制了
		if (mSLOffsetX >= FRAME_BG_WIDTH * mOpt) {
			return;
		}
		mSLMoveTotalX = FRAME_BG_WIDTH * mOpt;
		mSLMoveStepX = mSLMoveTotalX / LV3_ENTER_END_INDEX;
		float slx = mBGX - mSLSprite.getWidth() / 2 + mSLOffsetX;
		float sly = mBGY - mSLSprite.getHeight() / 2 + FRAME_BG_HEIGHT * mOpt
				/ 2;
		mSLSprite.setAlpha(mSLAlpha);
		mSLSprite.setPostion(slx, sly);
		mSLSprite.draw(canvas);
	}

	/**
	 * 绘制等级3和4需要的星星
	 * 
	 * @param canvas
	 */
	private void drawLv3Stars(Canvas canvas) {
		Log.d(TAG, "drawLv3Stars(),mLv3StarsIndex:" + mLv3StarsIndex);
		// 不是3，4级没有星星
		if (getLevel() != 3 && getLevel() != 4) {
			return;
		}
		if (mUser.animType == TYPE_UPGRADE) {
			return;
		}
		float starsX = mLevelBGX + LEVEL_BG_WIDTH * mOpt;
		float starsY = mBGY;
		mStarsFSprite.setPostion(starsX, starsY);
		mStarsFSprite.draw(canvas, mLv3StarsIndex);
	}

	/**
	 * 绘制等级4需要的花的序列帧
	 * 
	 * @param canvas
	 */
	private void drawLv4Floweres(Canvas canvas) {
		Log.d(TAG, "drawLv4Floweres(),mLv4FloweresIndex:" + mLv4FloweresIndex);
		// 不是4级没有花
		if (getLevel() != 4 || mUser.animType != TYPE_ENTER_ROOM) {
			return;
		}
		float flowerX = 0;
		float flowerY = mBGY - mLV4FlowersFSprite.getHeight() / 2
				+ FRAME_BG_HEIGHT * mOpt / 2;
		mLV4FlowersFSprite.setPostion(flowerX, flowerY);
		mLV4FlowersFSprite.draw(canvas, mLv4FloweresIndex);
	}

	/**
	 * 绘制等级2需要等灯光
	 * 
	 * @param canvas
	 */
	private void drawUpgradeLv2Light(Canvas canvas) {
		// 不是2级没有花
		if (mUser.level < 21 || mUser.animType != TYPE_UPGRADE) {
			return;
		}
		float lightx = mBGX + 50 * mOpt + 5 * mOpt;
		float lighty = mLevelBGY - 17 * mOpt;
		mUGLv2LightFrames.setAlpha(mTextAlpha);
		mUGLv2LightFrames.setPostion(lightx, lighty);
		mUGLv2LightFrames.draw(canvas, mUGLv2LightFramesIndex);
	}

	/**
	 * 绘制周月榜的图标
	 * 
	 * @param canvas
	 */
	private void drawWMIcon(Canvas canvas) {

		if (mUser.iWeekMonthType != 0 && mUser.iWeekMonthType != 1) {
			return;
		}
		// 不是入场没有榜按钮
		if (mUser.animType != TYPE_ENTER_ROOM) {
			return;
		}
		float wmy = mBGY - (mWMSprite.getHeight() - FRAME_BG_HEIGHT * mOpt);
		mWMSprite.setAlpha(mTextAlpha);
		mWMSprite.setPostion(mWMX + mTextOffsetX, wmy);
		mWMSprite.draw(canvas);
	}

	@Override
	public void move() {
		switch (mState) {
		case STATE_BG_ENTER:
			moveBGEnter();
			moveStrongLight();
			moveLv3Stars();
			moveLv4Floweres();
			enterBGTimeCount += SLEEP_TIME;
			break;
		case STATE_TEXT_ENTER:
			moveText();
			moveBGFlash();
			moveStars();
			moveLv3Stars();
			moveLv4Floweres();
			moveUpgradeLv2Light();
			textEnterTimeCount += SLEEP_TIME;
			break;

		case STATE_CONTENT_OUT:
			moveContentOut();
			moveStars();
			bgOutTimeCount += SLEEP_TIME;
			break;

		case STATE_FINISH:
			break;
		}
	}

	/**
	 * 背景变换
	 */
	private void moveBGEnter() {
		switch (getLevel()) {
		case 2:
			if ((++mBGFrameIndex2) > (LV2_ENTER_END_INDEX)) {
				mBGFrameIndex2 = LV2_ENTER_END_INDEX;
			}
			break;
		case 3:
			if ((++mBGFrameIndex3) > (LV3_ENTER_END_INDEX)) {
				mBGFrameIndex3 = LV3_ENTER_END_INDEX;
			}
			break;
		case 4:
			if ((++mBGFrameIndex4) > (LV4_ENTER_END_INDEX - 1)) {
				mBGFrameIndex4 = LV4_ENTER_END_INDEX;
			}
			break;
		case 1:
		default:
			if ((++mBGFrameIndex1) > (LV1_ENTER_END_INDEX)) {
				mBGFrameIndex1 = LV1_ENTER_END_INDEX;
			}
			break;
		}

		// 升级相关
		if (mUser.level <= 20) { // 低等升级
			if ((++mUGLv1BGFramesIndex) > (UG_LV1_FRAME_ENTER_END_INDEX)) {
				mUGLv1BGFramesIndex = UG_LV1_FRAME_ENTER_END_INDEX;
			}
		} else { // 高等升级
			if ((++mUGLv2BGFramesIndex) > (UG_LV2_FRAME_ENTER_END_INDEX)) {
				mUGLv2BGFramesIndex = UG_LV2_FRAME_ENTER_END_INDEX;
			}
		}

		// 文字进入
		if (enterBGTimeCount > textEnterStartTime) {
			mState = STATE_TEXT_ENTER;
		}
	}

	/**
	 * 文字变换
	 */
	private void moveText() {
		mTextAlpha += mTextPerMoveAlpha;
		if (mTextAlpha >= 255) {
			mTextAlpha = 255;
		}
		// 进入移出状态
		if (textEnterTimeCount > sceneOutStartTime) {
			switch (getLevel()) {
			case 1:

				break;
			case 2:
				mBGFrameIndex2 = LV2_ENTER_END_INDEX;
				break;
			case 3:
				mBGFrameIndex3 = LV3_ENTER_END_INDEX;
				break;
			case 4:
				mBGFrameIndex4 = LV4_ENTER_END_INDEX;
				break;
			}
			mState = STATE_CONTENT_OUT;
		}
	}

	/**
	 * 所有内容移出
	 */
	private void moveContentOut() {
		switch (getLevel()) {
		case 1:
			if ((--mBGFrameIndex1) < 0) {
				mBGFrameIndex1 = 0;
			}
			break;
		case 2:
			if ((--mBGFrameIndex2) < 0) {
				mBGFrameIndex2 = 0;
			}
			break;
		case 3:
			if ((mBGFrameIndex3 -= 2) < 0) {
				mBGFrameIndex3 = 0;
			}
			break;
		case 4:
			if ((mBGFrameIndex4 -= 2) < 0) {
				mBGFrameIndex4 = 0;
			}
			break;
		}

		mTextOffsetX += mContentOutPerXDistance;
		// 动画结束
		if (bgOutTimeCount > finishTime) {
			mState = STATE_FINISH;
			animFinished();
		}
	}

	private void moveBGFlash() {
		switch (getLevel()) {
		case 2:
			if ((++mBGFrameIndex2) > (LV2_FLASH_END_INDEX)) {
				mBGFrameIndex2 = LV2_ENTER_END_INDEX;
			}
			break;
		case 3:
			if ((++mBGFrameIndex3) > (LV3_FLASH_END_INDEX)) {
				mBGFrameIndex3 = LV3_FLASH_END_INDEX;
			}
			break;
		case 4:
			if ((++mBGFrameIndex4) > (LV4_FLASH_END_INDEX)) {
				mBGFrameIndex4 = LV4_FLASH_END_INDEX;
			}
			break;
		}
		if (mUser.level <= 20) {
			if ((++mUGLv1BGFramesIndex) > (UG_LV1_FRAME_SHOW_END_INDEX)) {
				mUGLv1BGFramesIndex = UG_LV1_FRAME_SHOW_END_INDEX;
			}
		} else {
			if ((++mUGLv2BGFramesIndex) > (UG_LV2_FRAME_SHOW_END_INDEX)) {
				mUGLv2BGFramesIndex = UG_LV2_FRAME_SHOW_END_INDEX;
			}
		}
	}

	/**
	 * 星星变化
	 */
	private void moveStars() {
		if (mStar1Scale >= SCALE_STAR_MAX || mStar1Scale <= SCALE_STAR_MIN) {
			mStar1MoveScale *= (-1);
		}
		mStar1Scale += mStar1MoveScale;

		if (mStar2Scale >= SCALE_STAR_MAX || mStar2Scale <= SCALE_STAR_MIN) {
			mStar2MoveScale *= (-1);
		}
		mStar2Scale += mStar2MoveScale;

		if (mStar3Scale >= SCALE_STAR_MAX || mStar3Scale <= SCALE_STAR_MIN) {
			mStar3MoveScale *= (-1);
		}
		mStar3Scale += mStar3MoveScale;
	}

	/**
	 * 等级3需要的强光变换
	 */
	private void moveStrongLight() {
		mSLOffsetX += mSLMoveStepX;
		mSLAlpha -= 20;
	}

	/**
	 * 等级3需要的星星帧变换
	 */
	private void moveLv3Stars() {
		if ((++mLv3StarsIndex) > LV3_STARTS_FRAMES_SIZE - 1) {
			mLv3StarsIndex = LV3_STARTS_FRAMES_SIZE - 1;
		}
	}

	/**
	 * 等级4需要花序列帧变换
	 */
	private void moveLv4Floweres() {
		if ((++mLv4FloweresIndex) > LV4_FLOWERS_SIZE - 1) {
			mLv4FloweresIndex = mLv4FloweresIndex - 1;
		}
	}

	/**
	 * 变换升级2的灯
	 */
	private void moveUpgradeLv2Light() {
		if ((++mUGLv2LightFramesIndex) > UG_LV2_LIGHT_FRAMES_SIZE - 1) {
			mUGLv2LightFramesIndex = UG_LV2_LIGHT_FRAMES_SIZE - 1;
		}
	}

	/**
	 * 销毁图片资源
	 */
	private void destoryBitmap() {
		Log.d(TAG, "destoryBitmap()");
		if(mLV1BGFrameSprite != null) {
			mLV1BGFrameSprite.destroy();
			mLV1BGFrameSprite = null;
		}
		if(mLV2BGFrameSprite != null) {
			mLV2BGFrameSprite.destroy();
			mLV2BGFrameSprite = null;
		}
		if(mLV3BGFrameSprite != null) {
			mLV3BGFrameSprite.destroy();
			mLV3BGFrameSprite = null;
		}
		if(mLV4BGFrameSprite != null) {
			mLV4BGFrameSprite.destroy();
			mLV4BGFrameSprite = null;
		}
		if(mStarsFSprite != null) {
			mStarsFSprite.destroy();
			mStarsFSprite = null;
		}
		if(mLV4FlowersFSprite != null) {
			mLV4FlowersFSprite.destroy();
			mLV4FlowersFSprite = null;
		}
		if(mUGLV1BGFSprite != null) {
			mUGLV1BGFSprite.destroy();
			mUGLV1BGFSprite = null;
		}
		if(mUGLV2BGFSprite != null) {
			mUGLV2BGFSprite.destroy();
			mUGLV2BGFSprite = null;
		}
		if(mLevelIconSprite != null) {
			mLevelIconSprite.destroy();
			mLevelIconSprite = null;
		}
		if(mStarSprite1 != null) {
			mStarSprite1.destroy();
			mStarSprite1 = null;
		}
		if(mSLSprite != null) {
			mSLSprite.destroy();
			mSLSprite = null;
		}
		if(mUGLv2LightFrames != null) {
			mUGLv2LightFrames.destroy();
			mUGLv2LightFrames = null;
		}
		if(mWMSprite != null) {
			mWMSprite.destroy();
			mWMSprite = null;
		}
	}

	@Override
	public void onDestroy() {
		destoryBitmap();
	}

	/**
	 * 进入场景的用户信息
	 * 
	 * @author pix
	 * 
	 */
	public static class EnterUser {
		public EnterUser() {
		}

		public EnterUser(String name, int level, byte type, int weekMonthType) {
			this.name = name;
			this.level = level;
			this.animType = type;
			this.iWeekMonthType = weekMonthType;
		}

		public String name = "";
		public int level = 1;
		public byte animType = -1;
		/**
		 * 周月榜的类型
		 */
		public int iWeekMonthType;
	}

	private int getLevel() {
		if (mUser == null) {
			return 1;
		}

		if (mUser.level > 20 && mUser.level < 41) {
			return 1;
		}
		// 等级2范围
		else if (mUser.level >= 41 && mUser.level < 61) {
			return 2;
		}
		// 等级3范围
		else if (mUser.level >= 61 && mUser.level < 81) {
			return 3;
		}
		// 等级4
		else {
			return 4;
		}
	}

	@Override
	protected void drawScene(Canvas canvas) {
		switch (mState) {
		case STATE_BG_ENTER: // 绘制背景进入
			drawLv4Floweres(canvas);
			drawBGEnter(canvas);
			drawLv3Stars(canvas);
			drawStrongLight(canvas);
			break;
		case STATE_TEXT_ENTER:
			drawLv4Floweres(canvas);
			drawBGEnter(canvas);
			drawLv3Stars(canvas);
			drawContent(canvas);
			drawStars(canvas);
			drawUpgradeLv2Light(canvas);
			drawWMIcon(canvas);
			break;
		case STATE_CONTENT_OUT:
			drawBGEnter(canvas);
			drawContent(canvas);
			drawStars(canvas);
			break;
		}
		if(mState == 0 || mState == STATE_FINISH) {
			return ;
		}
		Paint paint =  new Paint();
		paint.setColor(Color.GREEN);
		canvas.drawRect(new Rect(100,200,300,400), paint);
		
	}
	
	public void runUser(EnterUser user) {
		this.mUser = user;
		editUserNameLength();
		start();
	}
	
	public void editUserNameLength() {
		if(mUser.name.length() <= 8) {
			return ;
		}
		mUser.name = mUser.name.subSequence(0, 8) + "...";
	}
}
