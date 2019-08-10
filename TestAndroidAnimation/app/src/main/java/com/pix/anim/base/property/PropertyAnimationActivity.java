package com.pix.anim.base.property;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;
import com.pix.anim.base.tween.AnimationSetActivity;
import com.pix.anim.base.tween.RotateAnimationActivity;
import com.pix.anim.base.tween.ScaleAnimationActivity;
import com.pix.anim.base.tween.TranslateAnimationActivity;

public class PropertyAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
    }

    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("ObjectAnimator"))
        {
            startActivity(new Intent(this,ObjectAnimatorActivity.class));
        }

        if(btn.getText().equals("ScaleAnimation"))
        {
            startActivity(new Intent(this,ScaleAnimationActivity.class));
        }

        if(btn.getText().equals("RotateAnimation"))
        {
            startActivity(new Intent(this,RotateAnimationActivity.class));
        }

        if(btn.getText().equals("TranslateAnimation"))
        {
            startActivity(new Intent(this,TranslateAnimationActivity.class));
        }

        if(btn.getText().equals("AnimationSet"))
        {
            startActivity(new Intent(this,AnimationSetActivity.class));
        }
    }
}
