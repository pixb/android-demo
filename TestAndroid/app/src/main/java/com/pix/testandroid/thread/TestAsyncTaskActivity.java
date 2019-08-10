package com.pix.testandroid.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pix.testandroid.R;
import com.pix.testandroid.view.sub.TopBar;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 这个类是测试异步任务的类
 */
public class TestAsyncTaskActivity extends AppCompatActivity implements TopBar.OnClickListener {
    private static final String TAG = "TestAsyncTaskActivity";
    private Button mOpenButton;
    private TextView mMessageTextView;
    private NetTask mNetTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_async_task);
        mOpenButton = (Button) findViewById(R.id.btn_open);
        mOpenButton.setOnClickListener(this);
        mMessageTextView = (TextView) findViewById(R.id.tv_message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open:
                String url = "https://www.baidu.com";
                new NetTask(this,mMessageTextView).execute(url);
                break;
        }
    }

    /**
     * 定义访问网络的异步任务
     */
    static class NetTask extends AsyncTask<String,Void,byte[]> {
        private ProgressDialog mProgressDialog;
        private Context mContext;
        private TextView mMsgTextView;
        // 构造方法，初始化进度对话框
        public NetTask(Context context, TextView msgTextView) {
            this.mContext = context;
            this.mMsgTextView = msgTextView;
            if(mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.setIcon(R.mipmap.ic_launcher);
                mProgressDialog.setTitle("提示：");
                mProgressDialog.setMessage("数据加载中。。。");
            }
        }
        // 事先执行方法中显示进度对话框
        @Override
        protected void onPreExecute() {
            Log.d(TAG,"onPreExecute()");
            mProgressDialog.show();
            super.onPreExecute();
        }
        // 进度条进度改变方法。一般情况下，可以不写该方法
        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }
        // 后台执行方法，这个方法执行worker Thread异步访问网络，加载数据。该方法中不可以执行任何UI操作。
        @Override
        protected byte[] doInBackground(String... params) {
            Log.d(TAG,"doInBackground()");
            BufferedInputStream bis = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                URL urlObj = new URL(params[0]);
                HttpURLConnection httpConn = (HttpURLConnection) urlObj
                        .openConnection();
                httpConn.setDoInput(true);
                // httpConn.setDoOutput(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                if (httpConn.getResponseCode() == 200) {
                    bis = new BufferedInputStream(httpConn.getInputStream());
                    byte[] buffer = new byte[1024 * 8];
                    int c = 0;
                    while ((c = bis.read(buffer)) != -1) {
                        baos.write(buffer, 0, c);
                        baos.flush();
                    }
                    // Toast.makeText(context, baos.toByteArray().toString(),
                    // Toast.LENGTH_LONG).show();
                    return baos.toByteArray();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        // 事后方法，这个方法主要作用是执行对主线程中UI的操作。可以实现主线程和子线程之间的数据交互
        @Override
        protected void onPostExecute(byte[] result) {
            Log.d(TAG,"onPostExecute()");
            super.onPostExecute(result);
            if (result == null) {
                mMsgTextView.setText("网络异常，加载数据失败！");
            } else {
                mMsgTextView.setText(new String(result));
            }
            mProgressDialog.dismiss();
            this.cancel(true);
        }

    }
}
