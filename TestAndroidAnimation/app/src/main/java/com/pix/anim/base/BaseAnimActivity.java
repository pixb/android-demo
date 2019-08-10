package com.pix.anim.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;
import com.pix.anim.base.property.PropertyAnimationActivity;
import com.pix.anim.base.tween.TweenAnimationActivity;

/**
 * Android 基本动画展示的Activity
 */
public class BaseAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_anim);
    }
    public void onClick(View v)
    {
        Button btn = (Button)v;
        if(btn.getText().equals("补间动画Tween")) {
            startActivity(new Intent(this,TweenAnimationActivity.class));
        }
        if(btn.getText().equals("属性动画Property")) {
            startActivity(new Intent(this,PropertyAnimationActivity.class));
        }

    }
}
