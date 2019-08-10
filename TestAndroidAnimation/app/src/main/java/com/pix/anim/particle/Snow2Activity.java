package com.pix.anim.particle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Snow2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SnowScence2(this));
    }

}
