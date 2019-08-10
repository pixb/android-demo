package com.pix.testandroid.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pix.testandroid.R;

import java.util.Random;

/**
 * 这个类用来测试HandlerThread的使用
 * 自定HandlerThread的作用:使用Handler-Looper机制向子线程中发消息。
 */
public class TestHandlerThreadActivity extends AppCompatActivity {
    private static final String TAG = "TestHandlerThreadActivity";
    private static final int UPDATE_NUMBER = 1;
    private TextView mMessageTextView;
    private Handler mHandler = new Handler();
    private HandlerThread mUpdateHandlerThread;
    private Handler mUpdateHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_handler_thread);
        this.mMessageTextView = (TextView) findViewById(R.id.tv_message);
        this.mUpdateHandlerThread = new HandlerThread("update");
        this.mUpdateHandlerThread.start();
        mUpdateHandler = new Handler(mUpdateHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPDATE_NUMBER:
                        updateNumber();
                        break;
                }
            }
        };
        mUpdateHandler.sendEmptyMessage(UPDATE_NUMBER);
    }



    private void updateNumber() {
        //模拟耗时
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mUpdateHandler.sendEmptyMessage(UPDATE_NUMBER);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMessageTextView.setText("随机数:" + new Random().nextInt(10000));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mUpdateHandler.removeCallbacksAndMessages(null);
    }
}
