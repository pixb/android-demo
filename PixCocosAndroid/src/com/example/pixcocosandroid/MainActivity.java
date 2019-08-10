package com.example.pixcocosandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.pixcocosandroid.jufan.JuFanAnimationActivity;
import com.example.pixcocosandroid.test.CocosTestActivity;

public class MainActivity extends Activity implements OnClickListener{
	
	private Button mTestBtn ;
	
	private Button mJufanBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mTestBtn = (Button) findViewById(R.id.btn_main_test);
        this.mTestBtn.setOnClickListener(this);
        this.mJufanBtn = (Button) findViewById(R.id.btn_main_jufan);
        this.mJufanBtn.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_main_test:
			startActivity(new Intent(MainActivity.this,CocosTestActivity.class));
			break;
			
		case R.id.btn_main_jufan:
			startActivity(new Intent(MainActivity.this,JuFanAnimationActivity.class));
			break ;

		default:
			break;
		}
		
	}
    
}
