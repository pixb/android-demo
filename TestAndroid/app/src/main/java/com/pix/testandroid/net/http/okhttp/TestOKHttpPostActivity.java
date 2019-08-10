package com.pix.testandroid.net.http.okhttp;

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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestOKHttpPostActivity extends AppCompatActivity {
    private static final String TAG = "TestOKHttpPostActivity" ;
    private static final int GET_RESPONSE = 1;
    private TextView m_tvMessage;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_okhttp_post);
        setTitle("TestOKHttpPostActivity");
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
            post();
        }
    }
    private void post() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .build();
        Request request = new Request.Builder()
                .url("http://www.badu.com")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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
