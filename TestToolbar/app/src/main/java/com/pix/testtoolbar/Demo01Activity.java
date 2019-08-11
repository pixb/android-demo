package com.pix.testtoolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Demo01Activity extends AppCompatActivity {
    private static final String TAG = "Demo01Activity";
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo01);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(TAG);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setSubtitle("sub title");
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }
}
