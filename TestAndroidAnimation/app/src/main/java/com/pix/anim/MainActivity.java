package com.pix.anim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.anim.base.BaseAnimActivity;
import com.pix.anim.base.property.PropertyAnimationActivity;
import com.pix.anim.base.tween.TweenAnimationActivity;
import com.pix.anim.particle.ParticleAnimationActivity;
import com.pix.anim.project.ProjectAnimActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v)
    {
        Button btn = (Button)v;
        if(btn.getText().equals("Android基本动画")) {
            startActivity(new Intent(this,BaseAnimActivity.class));
        }
        if(btn.getText().equals("项目中的动画")) {
          startActivity(new Intent(this,ProjectAnimActivity.class));
        }

        if(btn.getText().equals("粒子特效")) {
            startActivity(new Intent(this, ParticleAnimationActivity.class));
        }

    }
}
