package com.pix.testandroid.storage.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pix.testandroid.R;

public class TestSPDemoOneActivity extends AppCompatActivity {
    private static final String TAG = "TestSPDemoOneActivity";
    private static final String SP_NAME = "testandroid";
    private static final String KEY = "KEY1";
    private EditText mInputEditText;
    private TextView mMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_spdemo_one);
        mInputEditText = (EditText) findViewById(R.id.editText_input);
        mMessageTextView = (TextView) findViewById(R.id.tv_message);
    }

    public void onClick(View v) {
        Button btn = (Button) v;
        if(btn.getText().equals("Save")) {
            String inputString = mInputEditText.getText().toString();
            // save sharedpreference
            if(TextUtils.isEmpty(inputString)) {
                mMessageTextView.setText("input is NULL.");
            }
            else {
                SharedPreferences sp = this.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
                sp.edit().putString(KEY,inputString).commit();
                mMessageTextView.setText("Save KEY1=" + inputString);
            }
        }
        if(btn.getText().equals("Get KEY1 Value")) {
            SharedPreferences sp = this.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
            String saveString = sp.getString(KEY,"");
            mMessageTextView.setText("Get value: KEY1 = " + saveString);
        }
    }
}
