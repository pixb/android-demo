package com.pix.testandroid.net.http;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;
import com.pix.testandroid.net.http.okhttp.TestOKHttpActivity;

public class TestHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http);
        setTitle("Test Http");
    }

    public void onClick(View v)
    {
        Button btn = (Button)v;
        if(btn.getText().equals("Test OKHttp")) {
            startActivity(new Intent(this, TestOKHttpActivity.class));
        }
        if(btn.getText().equals("Test HttpUrlConnection")) {
//            startActivity(new Intent(this,ProjectAnimActivity.class));
        }

        if(btn.getText().equals("Test HttpClient")) {
//            startActivity(new Intent(this, ParticleAnimationActivity.class));
        }

    }
}
