package com.pix.game.box2d;

import com.pix.game.R;
import com.pix.game.box2d.catapult.CatapultGameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Box2dActivity extends Activity implements OnClickListener{
	private Button mBtn1;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box2d);
        init();
    }
	
	private void init() {
		mBtn1 = (Button) findViewById(R.id.btn_box2d_1);
		mBtn1.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_box2d_1:
				startActivity(new Intent(Box2dActivity.this,CatapultGameActivity.class));
			break ;
		}
		
	}
}
