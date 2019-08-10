package com.pix.anim.project.jufan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.pix.anim.R;

public class CarActivity extends Activity {

    private FrameLayout carContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jufan_car);
        carContainer = (FrameLayout) findViewById(R.id.act_land_driver_container);
    }

    public void onClick(View view){
        Button button = (Button) view;
        carContainer.removeAllViews();
        if(button.getText().toString().equals("JuFanCar")){
            JufanCarView car = new JufanCarView(this);
            carContainer.addView(car);
            car.start();
        }
        if(button.getText().toString().equals("JuFanBoat")){
            JufanBoatView boat = new JufanBoatView(this);
            carContainer.addView(boat);
            boat.start();
        }
        if(button.getText().toString().equals("Fireworks")){
            FireworksScene fireworksScene = new FireworksScene(this);
            carContainer.addView(fireworksScene);
            fireworksScene.start();
        }
    }
}
