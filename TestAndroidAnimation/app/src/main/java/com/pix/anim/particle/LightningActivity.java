package com.pix.anim.particle;

import android.app.Activity;
import android.os.Bundle;

public class LightningActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LightningScence(this));
    }
}
