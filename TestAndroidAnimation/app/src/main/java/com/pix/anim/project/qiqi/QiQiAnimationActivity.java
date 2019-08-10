package com.pix.anim.project.qiqi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;

public class QiQiAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_qi_animation);
    }

    public void onClick(View view){
        Button button = (Button) view;
        Intent intent = new Intent();
        if(button.getText().toString().equals("SendGiftActivity(送礼物)")){
            intent.setClass(this, SendGiftActivity.class);
        }else if(button.getText().toString().equals("SendFlowerActivity(送鲜花)")){
            intent.setClass(this, SendFlowerActivity.class);
        }else if(button.getText().toString().equals("")){
        }
        startActivity(intent);
    }

}
