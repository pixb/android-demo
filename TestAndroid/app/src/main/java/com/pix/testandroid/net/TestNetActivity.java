package com.pix.testandroid.net;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;
import com.pix.testandroid.net.http.TestHttpActivity;

public class TestNetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_net);
    }

    public void onClick(View v)
    {
        Button btn = (Button)v;
        if(btn.getText().equals("Test Http")) {
            startActivity(new Intent(this, TestHttpActivity.class));
        }
        if(btn.getText().equals("Test Tcp")) {
//            startActivity(new Intent(this,ProjectAnimActivity.class));
        }

        if(btn.getText().equals("Test Udp")) {
//            startActivity(new Intent(this, ParticleAnimationActivity.class));
        }

    }
}
