package com.pix.testandroid.view.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;
import com.pix.testandroid.view.common.checkbox.TestCheckBoxBaseActivity;
import com.pix.testandroid.view.common.checkbox.TestCheckBoxDemoActivity;

public class TestCheckBoxActivity extends AppCompatActivity {
    private static final String TAG = "TestCheckBoxActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_check_box);
        setTitle(TAG);
    }
    public void onClick(View v) {
        Button btn = (Button) v;
        if(btn.getText().equals("基本的CheckBox")) {
            startActivity(new Intent(this, TestCheckBoxBaseActivity.class));
        }
         if(btn.getText().equals("基本的CheckBox2")) {
            startActivity(new Intent(this, TestCheckBoxDemoActivity.class));
        }

    }
}
