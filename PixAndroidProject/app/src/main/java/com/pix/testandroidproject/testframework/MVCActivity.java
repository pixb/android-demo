package com.pix.testandroidproject.testframework;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pix.testandroidproject.R;

/**
 * android原生mvc demo，其实就是最普通的实现方式，为了和接下来的mvp,mvvm来作比较
 */
public class MVCActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pix_activity_mvc);
    }
}
