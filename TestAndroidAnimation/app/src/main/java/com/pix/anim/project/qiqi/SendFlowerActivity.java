package com.pix.anim.project.qiqi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;

public class SendFlowerActivity extends Activity implements View.OnClickListener {

    FlowerAnimate flowerAnimate;

    private Button btnSendFlower ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_flower);
        this.btnSendFlower = (Button) findViewById(R.id.btnSendFlower) ;
        this.btnSendFlower.setOnClickListener(this);
        this.flowerAnimate = new FlowerAnimate() ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendFlower:
                flowerAnimate.playSendFlowerAnimation(this);
                break;

            default:
                break;
        }
    }
}
