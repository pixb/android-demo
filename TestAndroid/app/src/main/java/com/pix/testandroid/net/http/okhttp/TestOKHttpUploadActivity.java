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

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestOKHttpUploadActivity extends AppCompatActivity {
    private static final String TAG = "OKHttpUploadActivity" ;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final int GET_RESPONSE = 1;
    private TextView m_tvMessage;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_okhttp_upload);
        setTitle("TestOKHttpUploadActivity");
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
            upload();
        }
    }
    private void upload() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String url = "http://testlive.jufan.tv/cgi/user/uploadHeadImg";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uid", "500000047")
                .addFormDataPart("webToken","7baece556d864fd19a07fc14cefa6f6655e6379a63961a93e75c7cd5a00ff873")
                .addFormDataPart("imgBig","abc.png",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("/mnt/sdcard/abc.png"))).build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"onFailure(),e.getMessage():" + e.getMessage());
                String str = e.getMessage();
                Message msg = mHandler.obtainMessage();
                msg.what = GET_RESPONSE;
                msg.obj = str;
                mHandler.sendMessage(msg);
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
