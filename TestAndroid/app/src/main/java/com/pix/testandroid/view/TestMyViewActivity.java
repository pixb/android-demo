package com.pix.testandroid.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;
import com.pix.testandroid.view.matrix.TestMatrixActivity;
import com.pix.testandroid.view.sub.FlashTextViewActivity;
import com.pix.testandroid.view.sub.TestTopBarActivity;

public class TestMyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_my_view);
    }

    public  void onClick(View v) {

        Button btn = (Button)v;
        if(btn.getText().equals("闪动的文字")) {
            startActivity(new Intent(this, FlashTextViewActivity.class));
        }
        if(btn.getText().equals("矩阵Matrix")) {
            startActivity(new Intent(this, TestMatrixActivity.class));
        }
        if(btn.getText().equals("组合控件TopBar")) {
            startActivity(new Intent(this,TestTopBarActivity.class));
        }
    }
}
