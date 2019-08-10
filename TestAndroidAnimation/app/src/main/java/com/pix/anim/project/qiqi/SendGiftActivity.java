package com.pix.anim.project.qiqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.pix.anim.R;

public class SendGiftActivity extends AppCompatActivity {
    private FrameLayout giftContainer;
    private FrameLayout scaneContainer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gift);
        giftContainer = (FrameLayout) findViewById(R.id.gg_act_gift_container);
        scaneContainer = (FrameLayout)findViewById(R.id.gg_scane_container);
        NightAnimView night = new NightAnimView(this);
        scaneContainer.addView(night);
        //night.start();
    }

    public void onClick(View view){
        Button button = (Button) view;
        giftContainer.removeAllViews();
        if(button.getText().toString().equals("LV1")){
//
        }else if(button.getText().toString().equals("LV2")){
        }else if(button.getText().toString().equals("LV3")){
        }else if(button.getText().toString().equals("LV4")) {
        }else if(button.getText().toString().equals("LV4")) {
        }else if(button.getText().toString().equals("LV5")) {
        }else if(button.getText().toString().equals("LV6")) {
            GGGiftView gift = new GGGiftView(this);
            giftContainer.addView(gift);
            gift.start();
        }else if(button.getText().toString().equals("LV7")) {
        }

    }
}
