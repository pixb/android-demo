package com.pix.testpropertyanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;


public class RotateAnimationActivity extends Activity {
    ImageView m_ivCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_animation);
        m_ivCat = (ImageView) findViewById(R.id.iv_cat);
    }
    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("xml Rotate动画")) {
            Animation xmlRotateAnimation = AnimationUtils.loadAnimation(this,R.anim.rotate_anim);
            m_ivCat.startAnimation(xmlRotateAnimation);
        }
        if(btn.getText().equals("java Rotate动画")) {
            Animation javaRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            javaRotateAnimation.setDuration(500);
            javaRotateAnimation.setFillAfter(true);
            javaRotateAnimation.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);//设置动画插入器
            m_ivCat.startAnimation(javaRotateAnimation);
        }
    }
}