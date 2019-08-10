package com.pix.game.jufan;

import com.pix.game.R;
import com.pix.game.base.ui.ScaleFramLayout;
import com.pix.game.jufan.anim.JufanBoatSurfaceView;
import com.pix.game.jufan.anim.JufanCarSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class CarActivity extends Activity{

	private ScaleFramLayout carContainer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jufan_car);
		carContainer = (ScaleFramLayout) findViewById(R.id.act_land_driver_container);
	}
	
	public void onClick(View view){
		Button button = (Button) view;
		carContainer.removeAllViews();
		if(button.getText().toString().equals("JuFanCar")){
			JufanCarSurfaceView car = new JufanCarSurfaceView(this);
			carContainer.addView(car);
		}
		if(button.getText().toString().equals("JuFanBoat")){
			JufanBoatSurfaceView boat = new JufanBoatSurfaceView(this);
			carContainer.addView(boat);
		}
	}
	
}
