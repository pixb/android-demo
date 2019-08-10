package com.pix.anim.project.guagua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;

public class GuaGuaAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guagua_animation);
    }

    public void onClick(View view){
        Button button = (Button) view;
        Intent intent = new Intent();
        if(button.getText().toString().equals("landDriver(地上跑的座驾)")){
            intent.setClass(this, LandDriverActivity.class);
        }else if(button.getText().toString().equals("flyDriver(天上飞的座驾)")){
            intent.setClass(this, FlyDriverActivity.class);
        }else if(button.getText().toString().equals("giftClick(各礼物的动画)")){
            intent.setClass(this, GiftActivity.class);
        }
        startActivity(intent);
    }
}
