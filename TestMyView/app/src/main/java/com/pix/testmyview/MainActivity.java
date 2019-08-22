package com.pix.testmyview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view) {
        Button btn = (Button) view;
        if(btn.getText().equals("基本View测试")) {
            startActivity(new Intent(this,BaseViewActivity.class));
        }
        if(btn.getText().equals("自定义属性测试")) {
            startActivity(new Intent(this,CustomAttributeActivity.class));
        }
        if(btn.getText().equals("垂直自定义布局")) {
            startActivity(new Intent(this,ViewGroupDemo1Activity.class));
        }
    }

}
