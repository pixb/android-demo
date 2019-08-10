package com.pix.testandroid.view.sub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pix.testandroid.R;

public class TestTopBarActivity extends AppCompatActivity {
    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TestTopBarActivity");
        setContentView(R.layout.activity_test_top_bar);
        this.mTopBar = (TopBar) findViewById(R.id.ttd_topbar);
    }
}
