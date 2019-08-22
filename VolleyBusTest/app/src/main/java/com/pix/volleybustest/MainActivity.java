package com.pix.volleybustest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pix.bean.ResponseResult;
import com.pix.eventbus.EventBusManager;
import com.pix.request.TestRequest;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private TextView m_tvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_tvMain = (TextView) findViewById(R.id.tv_main);
        EventBusManager.getInstance().register(this);
        new TestRequest().reqData();
    }
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onEventResponse(ResponseResult result) {
        if(result.isSuccess()) {
            m_tvMain.setText(result.toString());
        }
    }
}
