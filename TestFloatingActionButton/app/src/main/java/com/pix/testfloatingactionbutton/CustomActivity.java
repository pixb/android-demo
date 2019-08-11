package com.pix.testfloatingactionbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
    }

    public void ibClick(View v) {
        Toast.makeText(this,"Custom FloatingActionButton click!",Toast.LENGTH_LONG).show();
    }
}
