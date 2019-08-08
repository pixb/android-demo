package com.pix.testpropertyanimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testpropertyanimation.test.TestSampleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if(btn.getText().equals("TestSample")) {
            startActivity(new Intent(this,TestSampleActivity.class));
        }
    }
}
