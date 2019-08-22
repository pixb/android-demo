package com.pix.downloaddemoone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pix.downloaddemoone.bean.DownloadEvent;
import com.pix.downloaddemoone.bean.DownloadInfo;
import com.pix.downloaddemoone.utils.EventBusManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

public class DownloadTwoActivity extends AppCompatActivity {
    private static final String TAG = "DownloadTwoActivity";

    private EditText pathET;
    private TextView progresTV;
    private ProgressBar progressBar;
    private Button downButton;
    private Button pauseButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_two);
        pathET = (EditText) this.findViewById(R.id.editText);
        progresTV = (TextView) this.findViewById(R.id.textView);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        downButton = (Button) this.findViewById(R.id.downButton);
        pauseButton = (Button) this.findViewById(R.id.pauseButton);
        downButton.setOnClickListener(new DownloadButton());
        pauseButton.setOnClickListener(new PauseButton());
        EventBusManager.getInstance().register(this);
    }

    private final class DownloadButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startService(new Intent(DownloadTwoActivity.this,DownloadFileService.class));
            try {
                v.setEnabled(false);
                pauseButton.setEnabled(true);
                DownloadEvent event = new DownloadEvent();
                event.cmd = DownloadEvent.CMD_RESUME;
                EventBusManager.getInstance().post(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class PauseButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            v.setEnabled(false);
            downButton.setEnabled(true);
            DownloadEvent event = new DownloadEvent();
            event.cmd = DownloadEvent.CMD_PAUSE;
            EventBusManager.getInstance().post(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventDownloadInfo(DownloadInfo info) {
        if(info != null) {
            Log.d(TAG,"onEventDownloadInfo(),info.fileSize:"
                    + info.fileSize + ",info.downloadSize:" + info.downloadedSize);
            int percent = 0;
            if(info.fileSize > info.downloadedSize && info.fileSize > 0 && info.downloadedSize > 0) {
                percent = info.downloadedSize * 100 / info.fileSize ;
            }
            progressBar.setMax(info.fileSize);
            progressBar.setProgress(info.downloadedSize);
            progresTV.setText(percent + "%");
            if (progressBar.getMax() == progressBar.getProgress()) {
                Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusManager.getInstance().unregister(this);
    }
}
