package com.pix.testandroid.view.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;
import com.pix.testandroid.view.common.imageview.TestImageViewDemoOneActivity;

public class TestImageViewActivity extends AppCompatActivity {
    private static final String TAG = "TestImageViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_view);
        setTitle(TAG);
    }
    public void onClick(View v) {
        Button btn = (Button) v;
        if(btn.getText().equals("基本的ImageView")) {
            startActivity(new Intent(this, TestImageViewDemoOneActivity.class));
        }
    }
}
