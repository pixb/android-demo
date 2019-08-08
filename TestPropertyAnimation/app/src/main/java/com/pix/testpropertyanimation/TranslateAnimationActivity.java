package com.pix.testpropertyanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;


public class TranslateAnimationActivity extends Activity {
    ImageView m_ivCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_animation);
        m_ivCat = (ImageView) findViewById(R.id.iv_cat);
    }
    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("xml Translate动画")) {
            Animation xmlTranslateAnimation = AnimationUtils.loadAnimation(this,R.anim.translate_anim);
            m_ivCat.startAnimation(xmlTranslateAnimation);
        }
        if(btn.getText().equals("java Translate动画")) {
            Animation javaTranslateAnimation = new TranslateAnimation(0, 100, 0, 0);
            javaTranslateAnimation.setDuration(500);
            javaTranslateAnimation.setInterpolator(this, android.R.anim.cycle_interpolator);//设置动画插入器
            javaTranslateAnimation.setFillAfter(true);//设置动画结束后保持当前的位置（即不返回到动画开始前的位置）
            m_ivCat.startAnimation(javaTranslateAnimation);
        }
    }
}