package com.pix.anim.base.tween;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.pix.anim.R;

public class ScaleAnimationActivity extends Activity {
    ImageView m_ivCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_animation);
        m_ivCat = (ImageView) findViewById(R.id.iv_cat);
    }

    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("xml Scale动画")) {
            Animation xmlScaleAnimation = AnimationUtils.loadAnimation(this,R.anim.scale_anim);
            m_ivCat.startAnimation(xmlScaleAnimation);
        }

        if(btn.getText().equals("java Scale动画")) {
            Animation javaScaleAnimation = new ScaleAnimation(0.0f, 1.5f, 0.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            javaScaleAnimation.setDuration(500);//设置动画持续时间为500毫秒
            javaScaleAnimation.setFillAfter(true);//如果fillAfter的值为true,则动画执行后，控件将停留在执行结束的状态
            javaScaleAnimation.setFillBefore(false);//如果fillBefore的值为true，则动画执行后，控件将回到动画执行之前的状态
            javaScaleAnimation.setRepeatCount(3);//设置动画循环次数
            javaScaleAnimation.setRepeatMode(Animation.REVERSE);
            javaScaleAnimation.setStartOffset(0);
            javaScaleAnimation.setInterpolator(this, android.R.anim.decelerate_interpolator);//设置动画插入器
            m_ivCat.startAnimation(javaScaleAnimation);
        }
    }
}
