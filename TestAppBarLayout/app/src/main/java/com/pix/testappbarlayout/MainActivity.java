package com.pix.testappbarlayout;

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

    public void scrollTypeClick(View v) {
        startActivity(new Intent(this,ScrollTypeActivity.class));
    }
    public void enterAlwaysClick(View v) {
        startActivity(new Intent(this,EnterAlwaysActivity.class));
    }
    public void eucClick(View v) {
        startActivity(new Intent(this,EUCTypeActivity.class));
    }
    public void eacClick(View v) {
        startActivity(new Intent(this,EACTypeActivity.class));
    }
    public void ctlClick(View v) {
        startActivity(new Intent(this,TestCollapsingToolbarLayoutActivity.class));
    }
}
