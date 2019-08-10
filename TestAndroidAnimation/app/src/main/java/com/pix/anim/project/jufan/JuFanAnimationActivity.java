package com.pix.anim.project.jufan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;

public class JuFanAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jufan_animation);
    }

    public void onClick(View view){
        Button button = (Button) view;
        Intent intent = new Intent();
        if(button.getText().toString().equals("CarAnimation")){
            intent.setClass(this, CarActivity.class);
        }
        else if(button.getText().toString().equals("EnterRoomAnimation"))
        {
            intent.setClass(this, EnterRoomActivity.class);
        }
        startActivity(intent);
    }
}
