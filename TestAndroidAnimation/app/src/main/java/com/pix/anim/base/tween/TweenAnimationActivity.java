package com.pix.anim.base.tween;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;

public class TweenAnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_animation);
    }

    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("AlphaAnimation"))
        {
            startActivity(new Intent(this,AlphaAnimationActivity.class));
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
