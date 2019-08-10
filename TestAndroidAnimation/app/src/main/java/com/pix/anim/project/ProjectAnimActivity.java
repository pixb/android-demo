package com.pix.anim.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;
import com.pix.anim.particle.ParticleAnimationActivity;
import com.pix.anim.project.guagua.GuaGuaAnimationActivity;
import com.pix.anim.project.jufan.JuFanAnimationActivity;
import com.pix.anim.project.qiqi.QiQiAnimationActivity;

public class ProjectAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_anim);
    }

    public void onClick(View view){
        Button button = (Button) view;
        Intent intent = new Intent();
        if(button.getText().toString().equals("GuaGuaAnimation(呱呱动画)")){
            intent.setClass(this, GuaGuaAnimationActivity.class);
        }else if(button.getText().toString().equals("QiQiAnimation(齐齐动画)")){
            intent.setClass(this, QiQiAnimationActivity.class);
        }else if(button.getText().toString().equals("JuFanAnimation(聚范动画)")){
            intent.setClass(this, JuFanAnimationActivity.class);
        }
        startActivity(intent);
    }
}
