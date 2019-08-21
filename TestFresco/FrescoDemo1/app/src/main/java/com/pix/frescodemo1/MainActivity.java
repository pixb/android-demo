package com.pix.frescodemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Button btn = (Button) v;
        if(btn.getText().equals("最简单的使用")) {
            startActivity(new Intent(this,Demo1Activity.class));
        }
        if(btn.getText().equals("宽高自适应")) {
            startActivity(new Intent(this,Demo2Activity.class));
        }
    }
}
