package com.pix.testandroid.storage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;
import com.pix.testandroid.storage.sharedpreferences.TestSPDemoOneActivity;

/**
 *  Test Storage page
 */
public class TestStorageActivity extends AppCompatActivity {
    private static final String TAG = "TestStorageActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_storage);
    }
    public void onClick(View v) {
        Button btn = (Button) v;
        if(btn.getText().equals("SharedPreferences")) {
           startActivity(new Intent(this, TestSPDemoOneActivity.class));
        }
        if(btn.getText().equals("SharedPreferences")) {

        }
        if(btn.getText().equals("SharedPreferences")) {

        }
    }
}
