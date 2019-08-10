package com.pix.testandroid.view.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.pix.testandroid.R;
import com.pix.testandroid.view.common.spinner.TestSpinnerConnectionMenuActivity;
import com.pix.testandroid.view.common.spinner.TestSpinnerDemoThreeActivity;
import com.pix.testandroid.view.common.spinner.TestSpinnerDemoTwoActivity;
/**
 * 展示Spinner的使用范例
 */
public class TestSpinnerActivity extends AppCompatActivity {
    private static final String TAG = "TestSpinnerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_spinner);
        setTitle(TAG);
    }
    public void onClick(View v) {
        Button btn = (Button) v;
        if(((Button) v).getText().equals("Spinner联动菜单")) {
            startActivity(new Intent(this, TestSpinnerConnectionMenuActivity.class));
        }
         if(((Button) v).getText().equals("XML解析+联动")) {
            startActivity(new Intent(this, TestSpinnerDemoTwoActivity.class));
        }
         if(((Button) v).getText().equals("SpinnerDemo3")) {
            startActivity(new Intent(this, TestSpinnerDemoThreeActivity.class));
        }
         if(((Button) v).getText().equals("")) {

        }
         if(((Button) v).getText().equals("")) {

        }
         if(((Button) v).getText().equals("")) {

        }
         if(((Button) v).getText().equals("")) {

        }

    }
}
