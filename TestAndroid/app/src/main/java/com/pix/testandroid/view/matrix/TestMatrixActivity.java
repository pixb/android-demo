package com.pix.testandroid.view.matrix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;

public class TestMatrixActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_matrix);
    }

    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("矩阵平移Translate")) {
            startActivity(new Intent(this,TestMatrixTransActivity.class));
        }
        if(btn.getText().equals("矩阵缩放Scale")) {
            startActivity(new Intent(this,TestMatrixScaleActivity.class));
        }
        if(btn.getText().equals("矩阵旋转Rotate")) {
            startActivity(new Intent(this,TestMatrixRotateActivity.class));
        }
        if(btn.getText().equals("矩阵错切Skew")) {
            startActivity(new Intent(this,TestMatrixSkewActivity.class));
        }
    }
}
