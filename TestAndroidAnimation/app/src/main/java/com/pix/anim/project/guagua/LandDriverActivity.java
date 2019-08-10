package com.pix.anim.project.guagua;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.pix.anim.R;

public class LandDriverActivity extends AppCompatActivity {

    private FrameLayout carContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_driver);
        carContainer = (FrameLayout) findViewById(R.id.act_land_driver_container);
    }

    public void onClick(View view) {
        Button button = (Button) view;
        carContainer.removeAllViews();
        if (button.getText().toString().equals("crane")) {
            LandDriverView land = new LandDriverView(this, LandDriverView.LAND_CRANE);
            carContainer.addView(land);
            land.start();
        } else if (button.getText().toString().equals("aodi")) {
            LandDriverView land = new LandDriverView(this, LandDriverView.LAND_CHERYQQ);
            carContainer.addView(land);
            land.start();
        } else if (button.getText().toString().equals("tricycle")) {
            LandDriverView land = new LandDriverView(this, LandDriverView.LAND_TRICYCLE);
            carContainer.addView(land);
            land.start();
        }
    }
}
