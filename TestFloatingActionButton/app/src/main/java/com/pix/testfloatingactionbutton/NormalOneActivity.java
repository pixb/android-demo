package com.pix.testfloatingactionbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NormalOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_one);
    }

    public void fbClick(View v) {
        Toast.makeText(this,"FloatingActionButton click!",Toast.LENGTH_LONG).show();
    }
}
