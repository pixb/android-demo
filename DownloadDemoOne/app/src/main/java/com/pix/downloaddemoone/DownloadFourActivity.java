package com.pix.downloaddemoone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class DownloadFourActivity extends AppCompatActivity {
    private RecyclerView mRcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_four);
        mRcv = (RecyclerView) findViewById(R.id.rcv_list);
    }
}
