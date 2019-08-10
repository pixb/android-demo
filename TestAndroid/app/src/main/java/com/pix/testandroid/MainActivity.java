package com.pix.testandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.net.TestNetActivity;
import com.pix.testandroid.storage.TestStorageActivity;
import com.pix.testandroid.thread.TestThreadActivity;
import com.pix.testandroid.view.TestMyViewActivity;
import com.pix.testandroid.view.TestWidgetActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v)
    {
        Button btn = (Button)v;
        if(btn.getText().equals("网络访问")) {
            startActivity(new Intent(this,TestNetActivity.class));
        }
        if(btn.getText().equals("数据解析")) {
//            startActivity(new Intent(this,ProjectAnimActivity.class));
        }

        if(btn.getText().equals("数据Storage")) {
            startActivity(new Intent(this, TestStorageActivity.class));
        }

        if(btn.getText().equals("自定义View")) {
            startActivity(new Intent(this, TestMyViewActivity.class));
        }

        if(btn.getText().equals("线程相关Thread")) {
            startActivity(new Intent(this, TestThreadActivity.class));
        }

        if(btn.getText().equals("常用控件")) {
            startActivity(new Intent(this, TestWidgetActivity.class));
        }

    }
}
