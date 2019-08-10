package com.pix.testandroid.view.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;
import com.pix.testandroid.view.common.radiobutton.TestRadioButtonDemoOneActivity;

public class TestRadioButtonActivity extends AppCompatActivity {
    private static final String TAG = "TestRadioButtonActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_radio_button);
        setTitle(TAG);
    }
    public void onClick(View v) {
        Button btn = (Button) v;
        if(btn.getText().equals("RadioButtonDemo1")) {
            startActivity(new Intent(this, TestRadioButtonDemoOneActivity.class));
        }
    }
}
