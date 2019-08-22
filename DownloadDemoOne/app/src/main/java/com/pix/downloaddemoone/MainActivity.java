package com.pix.downloaddemoone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View v) {
        Button btn = (Button) v;
        Intent intent = null;
        if(btn.getText().equals("下载示例一")) {
            intent = new Intent(MainActivity.this,DownloadOneActivity.class);
        }
        if(btn.getText().equals("下载示例二")) {
            intent = new Intent(MainActivity.this,DownloadTwoActivity.class);
        }
        if(btn.getText().equals("下载示例三")) {
            intent = new Intent(MainActivity.this,DownloadThreeActivity.class);
        }
        if(btn.getText().equals("下载示例四")) {
            intent = new Intent(MainActivity.this,DownloadFourActivity.class);
        }
        startActivity(intent);
    }
}
