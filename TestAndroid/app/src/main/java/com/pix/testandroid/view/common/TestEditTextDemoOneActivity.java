package com.pix.testandroid.view.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pix.testandroid.R;

public class TestEditTextDemoOneActivity extends AppCompatActivity {
    private static final String TAG = "TestEditTextDemoOneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_edit_text_demo_one);
        setTitle(TAG);
    }
}
