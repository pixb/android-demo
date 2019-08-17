package com.pix.testtablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pix.testtablayout.test.TestTabLayout01Activity;
import com.pix.testtablayout.test.TestTabLayout02Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test01Click(View v) {
        startActivity(new Intent(this,TestTabLayout01Activity.class));
    }

    public void test02Click(View v) {
        startActivity(new Intent(this, TestTabLayout02Activity.class));
    }
}
