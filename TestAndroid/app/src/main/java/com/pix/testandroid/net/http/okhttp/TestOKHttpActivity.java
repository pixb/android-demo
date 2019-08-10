package com.pix.testandroid.net.http.okhttp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;

public class TestOKHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_okhttp);
    }

    public void onClick(View v)
    {
        Button btn = (Button)v;
        if(btn.getText().equals("Test Get Request")) {
            startActivity(new Intent(this, TestOKHttpGetActivity.class));
        }
        if(btn.getText().equals("Test Post Request")) {
            startActivity(new Intent(this,TestOKHttpPostActivity.class));
        }

        if(btn.getText().equals("Test Upload File")) {
            startActivity(new Intent(this, TestOKHttpUploadActivity.class));
        }

    }
}
