package com.pix.testpropertyanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;


public class AnimationSetActivity extends Activity {
    ImageView m_ivCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_set);
        m_ivCat = (ImageView) findViewById(R.id.iv_cat);
    }
    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("xml AnimSet动画")) {
            AnimationSet xmlAnimationSet = (AnimationSet) AnimationUtils.loadAnimation(this,R.anim.animation_set_anim);
            m_ivCat.startAnimation(xmlAnimationSet);
        }
        if(btn.getText().equals("java AnimSet动画")) {
            AnimationSet animationSet = new AnimationSet(true);
            Animation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
            alphaAnimation.setDuration(500);//设置动画持续时间为500毫秒

            Animation scaleAnimation = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(500);//设置动画持续时间为500毫秒
            scaleAnimation.setRepeatMode(Animation.REVERSE);
            scaleAnimation.setStartOffset(0);
            scaleAnimation.setInterpolator(this, android.R.anim.decelerate_interpolator);//设置动画插入器

            animationSet.addAnimation(alphaAnimation);
            animationSet.addAnimation(scaleAnimation);
            m_ivCat.startAnimation(animationSet);
        }
    }
}