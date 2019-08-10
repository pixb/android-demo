package com.pix.anim.particle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;

public class ParticleAnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particle_animation);
    }

    public void onClick(View view) {
        Button button = (Button) view;
        Intent intent = new Intent();
        if (button.getText().toString().equals("Blaze(火焰效果)")) {
            intent.setClass(this, BlazeActivity.class);
        }
        else if (button.getText().toString().equals("FireWork(烟花效果)")) {
            intent.setClass(this, FireWorkActivity.class);
        }
        else if (button.getText().toString().equals("Lightning(闪电效果)")) {
            intent.setClass(this, LightningActivity.class);
        }
        else if (button.getText().toString().equals("StarSky(星空效果)")) {
            intent.setClass(this, StarSkyActivity.class);
        }
        else if (button.getText().toString().equals("SnowSky(下雪效果)")) {
            intent.setClass(this, SnowActivity.class);
        }
        else if (button.getText().toString().equals("SnowSky2(下雪效果2)")) {
            intent.setClass(this, Snow2Activity.class);
        }
        startActivity(intent);
    }

}
