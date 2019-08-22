package com.pix.testbutterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AutoBindActivity extends AppCompatActivity {

    @BindView(R.id.tv_name1)
    TextView tvName1;
    @BindView(R.id.tv_name2)
    TextView tvName2;
    @BindView(R.id.tv_name3)
    TextView tvName3;
    @BindView(R.id.btn_name1)
    Button btnName1;
    @BindView(R.id.btn_name2)
    Button btnName2;
    @BindView(R.id.btn_name3)
    Button btnName3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_bind);
        ButterKnife.bind(this);
        tvName1.setText("TextView1");
        tvName2.setText("TextView2");
        tvName3.setText("TextView3");
        btnName1.setText("Button1");
        btnName2.setText("Button2");
        btnName3.setText("Button3");
    }

    @OnClick({R.id.tv_name1, R.id.tv_name2, R.id.tv_name3, R.id.btn_name1, R.id.btn_name2, R.id.btn_name3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_name1:
                break;
            case R.id.tv_name2:
                break;
            case R.id.tv_name3:
                break;
            case R.id.btn_name1:
                break;
            case R.id.btn_name2:
                break;
            case R.id.btn_name3:
                break;
        }
    }
}
