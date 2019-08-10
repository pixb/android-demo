package com.pix.testandroid.view.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;

public class TestEditTextActivity extends AppCompatActivity {
    private static final String TAG = "TestEditTextActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_edit_text);
        setTitle(TAG);
    }
    public void onClick(View v) {
        Button btn = (Button) v;
        if(btn.getText().equals("EditTextDemo1")) {

        }
    }
}
