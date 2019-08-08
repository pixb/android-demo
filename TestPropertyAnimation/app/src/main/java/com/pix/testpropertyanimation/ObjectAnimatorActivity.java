package com.pix.testpropertyanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class ObjectAnimatorActivity extends AppCompatActivity {
    ImageView m_ivCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        m_ivCat = (ImageView) findViewById(R.id.iv_cat);
    }
    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("xmlAlpha动画")) {
            Animator anim = AnimatorInflater.loadAnimator(this, R.animator.alpha_object_anim);
            anim.setTarget(m_ivCat);
            anim.start();
        }

        if(btn.getText().equals("javaAlpha动画")) {
            ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(m_ivCat, "alpha", 0f, 1f);
            alphaAnimation.setDuration(500);
            alphaAnimation.setRepeatCount(0);
            alphaAnimation.setRepeatMode(ValueAnimator.REVERSE);
            alphaAnimation.setStartDelay(200);
            alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            alphaAnimation.start();
        }

        if(btn.getText().equals("xmlScale动画")) {
            Animator anim = AnimatorInflater.loadAnimator(this, R.animator.scale_object_anim);
            anim.setTarget(m_ivCat);
            anim.start();
        }

        if(btn.getText().equals("javaScale动画")) {
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(m_ivCat, "scaleX", 1f, 1.5f);
            scaleXAnimator.setDuration(500);
            scaleXAnimator.setRepeatCount(1);
            scaleXAnimator.setRepeatMode(ValueAnimator.REVERSE);
            scaleXAnimator.start();
        }

        if(btn.getText().equals("xmlRotate动画")) {
            Animator anim = AnimatorInflater.loadAnimator(this, R.animator.rotate_object_anim);
            anim.setTarget(m_ivCat);
            anim.start();
        }

        if(btn.getText().equals("javaRotate动画")) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(m_ivCat, "rotation", 0f, 360f);
            objectAnimator.setDuration(500);
            objectAnimator.setRepeatCount(1);
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.start();
        }

        if(btn.getText().equals("xmlTrans动画")) {
            Animator anim = AnimatorInflater.loadAnimator(this, R.animator.trans_object_anim);
            anim.setTarget(m_ivCat);
            anim.start();
        }

        if(btn.getText().equals("javaTrans动画")) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(m_ivCat, "translationX", 0f, 100f);
            objectAnimator.setDuration(500);
            objectAnimator.setRepeatCount(1);
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.start();
        }

        if(btn.getText().equals("xmlSet动画")) {
            Animator anim = AnimatorInflater.loadAnimator(this, R.animator.set_object_anim);
            anim.setTarget(m_ivCat);
            anim.start();
        }

        if(btn.getText().equals("javaSet动画")) {
            AnimatorSet animatorSet = new AnimatorSet();

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(m_ivCat, "scaleX", 1f, 1.5f);
            scaleXAnimator.setDuration(500);
            scaleXAnimator.setRepeatCount(1);
            scaleXAnimator.setRepeatMode(ValueAnimator.REVERSE);
            scaleXAnimator.start();

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(m_ivCat, "scaleY", 1f, 1.5f);
            scaleYAnimator.setDuration(500);
            scaleYAnimator.setRepeatCount(1);
            scaleYAnimator.setRepeatMode(ValueAnimator.REVERSE);

            animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
            animatorSet.start();
        }

        if(btn.getText().equals("PropertyValuesHolder")) {
            PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.5f);
            PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.5f);
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(m_ivCat, scaleXValuesHolder, scaleYValuesHolder);
            objectAnimator.setDuration(500);
            objectAnimator.setRepeatCount(1);
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.start();

        }

        if(btn.getText().equals("ViewPropertyAnimator")) {
            ViewPropertyAnimator viewPropertyAnimator=m_ivCat.animate();
            viewPropertyAnimator.scaleXBy(1.0f).scaleX(1.5f).scaleYBy(1.0f).scaleY(1.5f).setDuration(500).start();
        }
    }
}
