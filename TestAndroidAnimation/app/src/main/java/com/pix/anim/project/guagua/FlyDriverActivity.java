package com.pix.anim.project.guagua;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pix.anim.R;
import com.pix.anim.bean.GuaGuaAnimView;

public class FlyDriverActivity extends AppCompatActivity {

    private FrameLayout carContainer;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_driver);
        carContainer = (FrameLayout) findViewById(R.id.act_fly_driver_container);
    }

    public void onClick(View view){
        Button button = (Button) view;
        carContainer.removeAllViews();
        if(button.getText().toString().equals("broom")){
//			FlyDriverView broom = new FlyDriverView(this, FlyDriverView.DRIVER_BROOM);
            GuaGuaAnimView broom = GuaGuaAnimFactory.getDriverAnimView(this, FlyDriverView.DRIVER_BROOM);
            carContainer.addView(broom);
            broom.start();
        }else if(button.getText().toString().equals("ufo")){
            FlyDriverView ufo = new FlyDriverView(this, FlyDriverView.DRIVER_UFO);
            carContainer.addView(ufo);
            ufo.start();
        }else if(button.getText().toString().equals("rocket")){
            FlyDriverView rocket = new FlyDriverView(this, FlyDriverView.DRIVER_ROCKET);
            carContainer.addView(rocket);
            rocket.start();
        }else if(button.getText().toString().equals("novice")){
            NoviceDialogView ndv = new NoviceDialogView(this);
            carContainer.addView(ndv);
            ndv.start();
        }else if(button.getText().toString().equals("rpbomb")) {
            BoomRPView brp = new BoomRPView(this);
            brp.setBoomRPEndListener(new BoomRPView.BoomRPEndListener() {

                @Override
                public void end() {
                    // TODO Auto-generated method stub
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(FlyDriverActivity.this, "End....", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            carContainer.addView(brp);
            brp.start();
        }

    }
}
