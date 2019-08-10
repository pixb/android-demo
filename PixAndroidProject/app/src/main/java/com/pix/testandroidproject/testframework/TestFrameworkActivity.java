package com.pix.testandroidproject.testframework;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.pix.testandroidproject.R;
import com.pix.testandroidproject.databinding.PixActivityTestFrameworkBinding;

/**
 * 测试Android框架的Anctivity
 */
public class TestFrameworkActivity extends Activity {
    PixActivityTestFrameworkBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.pix_activity_test_framework);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_framework_mvc:

                break;
        }
    }

}
