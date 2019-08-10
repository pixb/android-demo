package com.pix.testandroid.view.common.radiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pix.testandroid.R;

import static com.pix.testandroid.R.id.text_main_info;

public class TestRadioButtonDemoOneActivity extends AppCompatActivity {
   private static final String TAG = "RadioButtonDemo1";
    private RadioGroup radioGroup_main_finger;
    private RadioButton radio_main_stone;
    private RadioButton radio_main_scissor;
    private RadioButton radio_main_sheet;
    private Button button_main_submit;
    private TextView text_main_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_radio_button_demo_one);
        setTitle(TAG);
        radioGroup_main_finger = (RadioGroup) findViewById(R.id.radioGroup_main_finger);
        radio_main_stone = (RadioButton) findViewById(R.id.radio_main_stone);
        radio_main_scissor = (RadioButton) findViewById(R.id.radio_main_scissor);
        radio_main_sheet = (RadioButton) findViewById(R.id.radio_main_sheet);

        button_main_submit = (Button) findViewById(R.id.button_main_submit);
        text_main_info = (TextView) findViewById(R.id.text_main_info);

        // 方法一：利用提交按钮的单击监听来实现获取单选按钮的值
        button_main_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 根据RadioGroup的getCheckedRadioButtonId()方法获取到这个RadioGroup中
                // 哪个RadioButton被勾选
                int id = radioGroup_main_finger.getCheckedRadioButtonId();
                // 输出获取到的id，是个10进制的id编号。在R.java中的id内部类中能找到该id（要十进制转十六进制）。
                Log.i(TAG, "==" + id);
                // 根据id，可以利用findViewById(id)，获取到该控件对象
                RadioButton myRadioButton = (RadioButton) findViewById(id);
                // 利用控件对象的getText()方法可以获取到控件的text属性值
                text_main_info.setText(myRadioButton.getText());
            }
        });

        // 方法二：利用RadioGroup的OnCheckedChangeListener实现获取单选按钮的值
        radioGroup_main_finger
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        Log.i(TAG, "==" + checkedId);
                        // 方法1：利用switch case
                        // switch (checkedId) {
                        // case R.id.radio_main_stone:
                        // text_main_info.setText(radio_main_stone.getText());
                        // break;
                        // case R.id.radio_main_scissor:
                        // text_main_info.setText(radio_main_scissor.getText());
                        // break;
                        // case R.id.radio_main_sheet:
                        // text_main_info.setText(radio_main_sheet.getText());
                        // break;
                        // }

                        // 方法2：利用findViewById(id)生成控件对象
                        // 根据id，可以利用findViewById(id)，获取到该控件对象
                        RadioButton myRadioButton = (RadioButton) findViewById(checkedId);
                        // 利用控件对象的getText()方法可以获取到控件的text属性值
                        text_main_info.setText(myRadioButton.getText());
                    }
                });
    }
}
