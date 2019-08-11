package com.pix.testfloatingactionbutton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void normal1Click(View v) {
        startActivity(new Intent(this,NormalOneActivity.class));
    }

    public void customClick(View v) {
        startActivity(new Intent(this,CustomActivity.class));
    }
}
