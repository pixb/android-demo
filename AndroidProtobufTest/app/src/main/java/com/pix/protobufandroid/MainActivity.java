package com.pix.protobufandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pix.protobufandroid.protobuf.Login;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //构建对象
        Login login = new Login.Builder()
                .cmd("login")
                .name("张三")
                .passwd("123456")
                .userid(7369L).build();

        Log.d(TAG,"encode," + login.toString());

        //序列化
        byte [] data = login.encode();


        //反序列化
        try {
            Login deLogin = Login.ADAPTER.decode(data);
            Log.d(TAG,"deLogin,cmd:" + deLogin.cmd);
            Log.d(TAG,"deLogin,name:" + deLogin.name);
            Log.d(TAG,"deLogin,passwd:" + deLogin.passwd);
            Log.d(TAG,"deLogin,userid:" + deLogin.userid);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
