package com.pix.testandroid.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;
import com.pix.testandroid.view.common.TestCheckBoxActivity;
import com.pix.testandroid.view.common.TestEditTextActivity;
import com.pix.testandroid.view.common.TestImageViewActivity;
import com.pix.testandroid.view.common.TestListViewActivity;
import com.pix.testandroid.view.common.TestOtherWidgetActivity;
import com.pix.testandroid.view.common.TestRadioButtonActivity;
import com.pix.testandroid.view.common.TestSpinnerActivity;
import com.pix.testandroid.view.common.spinner.TestSpinnerConnectionMenuActivity;

public class TestWidgetActivity extends AppCompatActivity {
    private static final String TAG = "TestWidgetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_widget);
        setTitle(TAG);
    }


    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("TextView")) {

        }
        if(btn.getText().equals("Button")) {

        }
        if(btn.getText().equals("ImageView")) {
            startActivity(new Intent(this, TestImageViewActivity.class));
        }
        if(btn.getText().equals("RadioButton")) {
            startActivity(new Intent(this, TestRadioButtonActivity.class));
        }
        if(btn.getText().equals("CheckBox")) {
            startActivity(new Intent(this, TestCheckBoxActivity.class));
        }
        if(btn.getText().equals("Spinner")) {
            startActivity(new Intent(this, TestSpinnerActivity.class));
        }
        if(btn.getText().equals("ListView")) {
            startActivity(new Intent(this, TestListViewActivity.class));
        }
        if(btn.getText().equals("EditText")) {
            startActivity(new Intent(this, TestEditTextActivity.class));
        }
        if(btn.getText().equals("Other")) {
            startActivity(new Intent(this, TestOtherWidgetActivity.class));
        }
    }
}
