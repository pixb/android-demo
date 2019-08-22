package com.pix.testmyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BaseViewActivity extends AppCompatActivity {

    private ScaleFramLayout mRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view);
        mRootLayout = (ScaleFramLayout) findViewById(R.id.layout_root);
    }
    public void onClick(View view) {
        mRootLayout.removeAllViews();
        Button btn = (Button) view;
        if (btn.getText().equals("MyView")) {
            MyView mv = new MyView(this);
            mRootLayout.addView(mv);
            mv.start();
        }

        if (btn.getText().equals("MySurfaceView")) {
            MySurfaceView mv = new MySurfaceView(this);
            mRootLayout.addView(mv);
        }

    }
}
