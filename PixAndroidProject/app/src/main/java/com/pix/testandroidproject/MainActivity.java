package com.pix.testandroidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pix.testandroidproject.testbinding.TestBindingActivity;

/**
 * 主Activity,展示所有测试的列表
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_binding:
                startActivity(new Intent(this, TestBindingActivity.class));
                break;
            case R.id.btn_test_framwork:
                
                break;
        }
    }
}
