package com.pix.testandroid.view.common.checkbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.pix.testandroid.MainActivity;
import com.pix.testandroid.R;

public class TestCheckBoxDemoActivity extends AppCompatActivity {
    private static final String TAG = "TestCheckBoxDemoActivity";
    private CheckBox checkBox_main_hobby1;
    private CheckBox checkBox_main_hobby2;
    private CheckBox checkBox_main_hobby3;
    private CheckBox checkBox_main_hobby4;
    private Button button_main_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_check_box_demo);
        setTitle(TAG);
        checkBox_main_hobby1 = (CheckBox) findViewById(R.id.checkBox_main_hobby1);
        checkBox_main_hobby2 = (CheckBox) findViewById(R.id.checkBox_main_hobby2);
        checkBox_main_hobby3 = (CheckBox) findViewById(R.id.checkBox_main_hobby3);
        checkBox_main_hobby4 = (CheckBox) findViewById(R.id.checkBox_main_hobby4);
        button_main_submit = (Button) findViewById(R.id.button_main_submit);

        button_main_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestCheckBoxDemoActivity.this, "您选择了：" + getResult(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        // 定义一个有名字的监听器类。之所以不用匿名内部类形式，是因为有多个控件都要使用这同一个监听器
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                Toast.makeText(TestCheckBoxDemoActivity.this, "您选择了：" + getResult(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        checkBox_main_hobby1.setOnCheckedChangeListener(listener);
        checkBox_main_hobby2.setOnCheckedChangeListener(listener);
        checkBox_main_hobby3.setOnCheckedChangeListener(listener);
        checkBox_main_hobby4.setOnCheckedChangeListener(listener);

    }
    // 获取多选项中被勾选的结果。利用isChecked()方法来判断哪个选项被勾选
    private String getResult() {
        StringBuilder sb = new StringBuilder();
        if (checkBox_main_hobby1.isChecked()) {
            sb.append(checkBox_main_hobby1.getText());
        }
        if (checkBox_main_hobby2.isChecked()) {
            sb.append(checkBox_main_hobby2.getText());
        }
        if (checkBox_main_hobby3.isChecked()) {
            sb.append(checkBox_main_hobby3.getText());
        }
        if (checkBox_main_hobby4.isChecked()) {
            sb.append(checkBox_main_hobby4.getText());
        }
        return sb.toString();
    }

}
