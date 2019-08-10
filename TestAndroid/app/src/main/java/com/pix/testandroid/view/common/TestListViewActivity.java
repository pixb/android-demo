package com.pix.testandroid.view.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pix.testandroid.R;
import com.pix.testandroid.view.common.listview.TestListViewBaseAdapterActivity;
import com.pix.testandroid.view.common.listview.TestListViewSimpleAdapterActivity;

public class TestListViewActivity extends AppCompatActivity {
    private static final String TAG = "TestListViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_view);
        setTitle(TAG);
    }

    public void onClick(View v) {
        Button btn = (Button)v;
        if(btn.getText().equals("SimpleAdapter")) {
            startActivity(new Intent(this, TestListViewSimpleAdapterActivity.class));
        }
        if(btn.getText().equals("BaseAdapter")) {
            startActivity(new Intent(this, TestListViewBaseAdapterActivity.class));
        }

    }

}
