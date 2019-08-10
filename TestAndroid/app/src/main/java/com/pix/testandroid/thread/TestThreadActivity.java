package com.pix.testandroid.thread;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;

public class TestThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_thread);
    }
    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("异步任务AsyncTask")) {
            startActivity(new Intent(this,TestAsyncTaskActivity.class));
        }
        if(btn.getText().equals("HandlerThread")) {
            startActivity(new Intent(this,TestHandlerThreadActivity.class));
        }

    }
}
