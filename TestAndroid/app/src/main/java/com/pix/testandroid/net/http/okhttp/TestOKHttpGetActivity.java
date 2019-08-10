package com.pix.testandroid.net.http.okhttp;

import android.app.DownloadManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pix.testandroid.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 测试使用OKHttp发送get请求
 */
public class TestOKHttpGetActivity extends AppCompatActivity {
    private static final String TAG = "TestOKHttpGetActivity" ;
    private static final int GET_RESPONSE = 1;
    private TextView m_tvMessage;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_okhttp_get);
        setTitle("TestOKHttpGetActivity");
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case GET_RESPONSE:
                        m_tvMessage.setText((String)(msg.obj));
                        break;
                }

            }
        };
        m_tvMessage = (TextView) findViewById(R.id.tv_message);
    }

    public void onClick(View v) {
        if(((Button) v).getText().equals("Test")) {
            get();
        }
    }
    private void get() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("https://github.com/hongyangAndroid")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if(response.isSuccessful()) {
                    String str = response.body().string();
                    Log.d(TAG,"onResponse(),response.body().string():" + str);
                    Message msg = mHandler.obtainMessage();
                    msg.what = GET_RESPONSE;
                    msg.obj = str;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }
}
