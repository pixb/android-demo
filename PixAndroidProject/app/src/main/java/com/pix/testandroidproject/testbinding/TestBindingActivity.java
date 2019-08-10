package com.pix.testandroidproject.testbinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pix.testandroidproject.R;
import com.pix.testandroidproject.databinding.PixActivityTestBindingBinding;

/**
 * Anroid Binding技术测试
 */
public class TestBindingActivity extends AppCompatActivity {
    PixActivityTestBindingBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pix_activity_test_binding);
        mBinding = DataBindingUtil.setContentView(this,R.layout.pix_activity_test_binding);
    }
}
