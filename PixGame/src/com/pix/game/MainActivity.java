package com.pix.game;

import com.pix.game.box2d.Box2dActivity;
import com.pix.game.jufan.JuFanAnimationActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 游戏开发主类
 * @author pix
 *
 */
public class MainActivity extends Activity implements OnClickListener{
	private Button mBox2dBtn;
	private Button mJufanBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    
    private void init() {
    	mBox2dBtn = (Button) findViewById(R.id.btn_main_box2d);
    	mBox2dBtn.setOnClickListener(this);
    	mJufanBtn =  (Button) findViewById(R.id.btn_main_jufan);
    	mJufanBtn.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_main_box2d:
			startActivity(new Intent(MainActivity.this,Box2dActivity.class));
			break;
		case R.id.btn_main_jufan:
			startActivity(new Intent(MainActivity.this,JuFanAnimationActivity.class));
			break;
		}
		
	}
   
}
