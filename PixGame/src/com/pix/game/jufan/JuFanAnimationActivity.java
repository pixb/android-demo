package com.pix.game.jufan;

import com.pix.game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JuFanAnimationActivity extends Activity {

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
		startActivity(intent);
	}
    
}
