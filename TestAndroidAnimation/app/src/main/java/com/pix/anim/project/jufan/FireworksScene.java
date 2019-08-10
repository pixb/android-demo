package com.pix.anim.project.jufan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.pix.anim.bean.AnimFramesSprite;
import com.pix.anim.bean.SurfaceAnimScene;
import com.pix.anim.utils.AnimBitmapLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 烟花的动画
 */
public class FireworksScene extends SurfaceAnimScene {

    private static final int STATE_DRAWFIREWORKS = 1;

    private byte mode = 0;
    public static final byte MODE_BITMAP = 0;
    public static final byte MODE_PAINT = 1;

    //===================================烟花相关
    private AnimFramesSprite mFWFramesSprite1;
    private AnimFramesSprite mFWFramesSprite2;
    private AnimFramesSprite mFWFramesSprite3;
    private AnimFramesSprite mFWFramesSprite4;
    private AnimFramesSprite mFWFramesSprite5;
    private List<Bitmap> mFireworksFrames ;
    private static final int FIREWORKS_FRAMES_NUM = 12;
    //烟花的索引
    private int mFireworks1Index ;
    private int mFireworks2Index ;
    private int mFireworks3Index ;
    private int mFireworks4Index ;
    private int mFireworks5Index ;

    private static final int FIREWORKS_SCALE_RATE = 1;

    //烟花的缩放比
    private float mFireworks1Scale = 1f * FIREWORKS_SCALE_RATE;
    private float mFireworks2Scale = 0.6f * FIREWORKS_SCALE_RATE;
    private float mFireworks3Scale = 0.4f * FIREWORKS_SCALE_RATE;
    private float mFireworks4Scale = 0.4f * FIREWORKS_SCALE_RATE;
    private float mFireworks5Scale = 0.8f * FIREWORKS_SCALE_RATE;

    //烟花的位置偏移量
    private float mFireworks1XOffset;
    private float mFireworks1YOffset;
    private float mFireworks2XOffset;
    private float mFireworks2YOffset;
    private float mFireworks3XOffset;
    private float mFireworks3YOffset;
    private float mFireworks4XOffset;
    private float mFireworks4YOffset;
    private float mFireworks5XOffset;
    private float mFireworks5YOffset;

    private static final int FIREWORKS_2_START_STEP = 20 ;
    private static final int FIREWORKS_3_START_STEP = 40 ;
    private static final int FIREWORKS_4_START_STEP = 30 ;
    private static final int FIREWORKS_5_START_STEP = 30 ;
    /** 烟花的总步数 */
    private int mFireworksStepCount;

    public FireworksScene(Context context) {
        super(context);
    }

    @Override
    protected void drawScene(Canvas canvas) {
        switch (mState) {
            case STATE_DRAWFIREWORKS:
                drawFireworks(canvas);
                break;
            case STATE_FINISH:
                break;
        }
    }

    private void drawFireworks(Canvas canvas) {
        if(mFireworks1Index < FIREWORKS_FRAMES_NUM) {
            drawFireworks1(canvas);
        }

        if(mFireworksStepCount > FIREWORKS_2_START_STEP && mFireworks2Index <FIREWORKS_FRAMES_NUM) {
            drawFireworks2(canvas);
        }

        if(mFireworksStepCount > FIREWORKS_3_START_STEP && mFireworks3Index < FIREWORKS_FRAMES_NUM) {
            drawFireworks3(canvas);
        }

        if(mFireworksStepCount > FIREWORKS_4_START_STEP && mFireworks4Index < FIREWORKS_FRAMES_NUM) {
            drawFireworks4(canvas);
        }

        if(mFireworksStepCount > FIREWORKS_5_START_STEP && mFireworks5Index < FIREWORKS_FRAMES_NUM) {
            drawFireworks5(canvas);
        }
    }
    private void drawFireworks1(Canvas canvas) {
        mFWFramesSprite1.setPostion(mFireworks1XOffset,mFireworks1YOffset);
        mFWFramesSprite1.setScale(mFireworks1Scale, mFireworks1Scale);
        mFWFramesSprite1.draw(canvas,mFireworks1Index);
    }

    private void drawFireworks2(Canvas canvas) {
        mFWFramesSprite2.setPostion(mFireworks2XOffset,mFireworks2YOffset);
        mFWFramesSprite2.setScale(mFireworks2Scale, mFireworks2Scale);
        mFWFramesSprite2.draw(canvas,mFireworks2Index);
    }

    private void drawFireworks3(Canvas canvas) {
        mFWFramesSprite3.setPostion(mFireworks3XOffset,mFireworks3YOffset);
        mFWFramesSprite3.setScale(mFireworks3Scale, mFireworks3Scale);
        mFWFramesSprite3.draw(canvas,mFireworks3Index);
    }

    private void drawFireworks4(Canvas canvas) {
        mFWFramesSprite4.setPostion(mFireworks4XOffset,mFireworks4YOffset);
        mFWFramesSprite4.setScale(mFireworks4Scale, mFireworks4Scale);
        mFWFramesSprite4.draw(canvas,mFireworks4Index);
    }

    private void drawFireworks5(Canvas canvas) {
        mFWFramesSprite5.setPostion(mFireworks5XOffset,mFireworks5YOffset);
        mFWFramesSprite5.setScale(mFireworks5Scale, mFireworks5Scale);
        mFWFramesSprite5.draw(canvas,mFireworks5Index);
    }



    @Override
    public void move() {
        switch (mState) {
            case STATE_DRAWFIREWORKS:
                moveFireworks();
                break;
            case STATE_FINISH:
                break;
        }
    }

    private void moveFireworks() {
        mFireworksStepCount ++;
        if(mFireworksStepCount % 10 == 0) {
            if((++mFireworks1Index) > FIREWORKS_FRAMES_NUM ) {
                mFireworks1Index = FIREWORKS_FRAMES_NUM;
                mState = STATE_FINISH;
                animFinished();
            }
        }

        if(mFireworksStepCount > FIREWORKS_2_START_STEP) {
            if(mFireworksStepCount % 6 == 0) {
                if((++mFireworks2Index) > FIREWORKS_FRAMES_NUM ) {
                    mFireworks2Index = FIREWORKS_FRAMES_NUM;
                }
            }
        }

        if(mFireworksStepCount > FIREWORKS_3_START_STEP) {
            if(mFireworksStepCount % 4 == 0) {
                if((++mFireworks3Index) > FIREWORKS_FRAMES_NUM ) {
                    mFireworks3Index = FIREWORKS_FRAMES_NUM;
                }
            }
        }

        if(mFireworksStepCount > FIREWORKS_4_START_STEP) {
            if(mFireworksStepCount % 5 == 0) {
                if((++mFireworks4Index) > FIREWORKS_FRAMES_NUM ) {
                    mFireworks4Index = FIREWORKS_FRAMES_NUM;
                }
            }
        }

        if(mFireworksStepCount > FIREWORKS_5_START_STEP) {
            if(mFireworksStepCount % 8 == 0) {
                if((++mFireworks5Index) > FIREWORKS_FRAMES_NUM ) {
                    mFireworks5Index = FIREWORKS_FRAMES_NUM;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mFWFramesSprite1.destroy();
        mFWFramesSprite2.destroy();
        mFWFramesSprite3.destroy();
        mFWFramesSprite4.destroy();
        mFWFramesSprite5.destroy();
    }

    @Override
    public void initParams() {
        mFireworks1XOffset = 0;
        mFireworks1YOffset = 0;
        mFireworks2XOffset = 300 * mOpt;
        mFireworks2YOffset = 0;
        mFireworks3XOffset = 250 * mOpt;
        mFireworks3YOffset = 200 * mOpt;
        mFireworks4XOffset = 0;
        mFireworks4YOffset = 0;
        mFireworks5XOffset = 300 * mOpt;
        mFireworks5YOffset = 100 * mOpt;
        mState = STATE_DRAWFIREWORKS;
    }

    @Override
    public void init() {
        mFireworksFrames = new ArrayList<>();
        for (int i = 0;i < FIREWORKS_FRAMES_NUM ;i++) {
            Bitmap bm = AnimBitmapLoader.getInstance().readBitmap(getContext(),
                    getResources().getIdentifier("fireworks_" + i,"drawable",getContext().getPackageName()));
            if(bm == null) {
                mState = STATE_FINISH;
            }
            mFireworksFrames.add(bm);
        }
        mFWFramesSprite1 = new AnimFramesSprite(mFireworksFrames);
        mFWFramesSprite2 = new AnimFramesSprite(mFireworksFrames);
        mFWFramesSprite3 = new AnimFramesSprite(mFireworksFrames);
        mFWFramesSprite4 = new AnimFramesSprite(mFireworksFrames);
        mFWFramesSprite5 = new AnimFramesSprite(mFireworksFrames);
    }
}
