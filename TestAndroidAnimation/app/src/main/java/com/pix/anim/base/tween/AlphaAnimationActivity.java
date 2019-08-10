package com.pix.anim.base.tween;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.pix.anim.R;

public class AlphaAnimationActivity extends Activity {

    ImageView m_ivCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_animation);
        m_ivCat = (ImageView) findViewById(R.id.iv_cat);
    }

    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("xml Alpha动画")) {
            Animation xmlAlphaAnimation = AnimationUtils.loadAnimation(this,R.anim.alpha_anim);
            m_ivCat.startAnimation(xmlAlphaAnimation);
        }

        if(btn.getText().equals("java Alpha动画")) {
            Animation javaAlphaAnimation = new AlphaAnimation(1.0f,0.0f);
            javaAlphaAnimation.setDuration(500);
            javaAlphaAnimation.setFillAfter(false);
            m_ivCat.startAnimation(javaAlphaAnimation);
        }
    }
}
