package com.pix.testbutterknife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_hello)
    TextView mHelloTV; //@BindView注解加控件id标注要查找的控件
    @BindView(R.id.btn_adapter)
    Button mAdapterBtn;
    @BindView(R.id.btn_auto_bind)
    Button btnAutoBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//在当前activity自动绑定
        mHelloTV.setText("Test ButterKnife hello...");
    }

    @OnClick({R.id.tv_hello, R.id.btn_adapter, R.id.btn_auto_bind})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_adapter:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.btn_auto_bind:
                startActivity(new Intent(this, AutoBindActivity.class));
                break;
        }
    }

}
