package com.pix.testandroid.view.common.checkbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.pix.testandroid.R;

public class TestCheckBoxBaseActivity extends AppCompatActivity {
    private static final String TAG = "TestCheckBoxBaseActivity";
    private Button button_main_submit;
    private TextView text_main_info;
    private CheckBox checkBox_main_hobby1;
    private CheckBox checkBox_main_hobby2;
    private CheckBox checkBox_main_hobby3;
    private StringBuilder sb2 = new StringBuilder();
    private CheckBox checkBox_main_selectAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_check_box_base);
        setTitle(TAG);
        button_main_submit = (Button) findViewById(R.id.button_main_submit);
        text_main_info = (TextView) findViewById(R.id.textView_main_info);
        checkBox_main_hobby1 = (CheckBox) findViewById(R.id.checkBox_main_hobby1);
        checkBox_main_hobby2 = (CheckBox) findViewById(R.id.checkBox_main_hobby2);
        checkBox_main_hobby3 = (CheckBox) findViewById(R.id.checkBox_main_hobby3);
        checkBox_main_selectAll = (CheckBox) findViewById(R.id.checkBox_main_selectAll);
        button_main_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResult();
            }
        });
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // if (isChecked) {
                // sb2.append(buttonView.getText());
                // }
                // text_main_info.setText(sb2.toString());
                if (checkBox_main_hobby1.isChecked()
                        && checkBox_main_hobby2.isChecked()
                        && checkBox_main_hobby3.isChecked()) {
                    checkBox_main_selectAll.setChecked(true);
                } else {
                    checkBox_main_selectAll.setChecked(false);
                }
                getResult();
            }
        };
        checkBox_main_hobby1.setOnCheckedChangeListener(listener);
        checkBox_main_hobby2.setOnCheckedChangeListener(listener);
        checkBox_main_hobby3.setOnCheckedChangeListener(listener);

        checkBox_main_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (checkBox_main_selectAll.isChecked()) {
                // checkBox_main_hobby1.setChecked(true);
                // checkBox_main_hobby2.setChecked(true);
                // checkBox_main_hobby3.setChecked(true);
                // } else {
                // checkBox_main_hobby1.setChecked(false);
                // checkBox_main_hobby2.setChecked(false);
                // checkBox_main_hobby3.setChecked(false);
                // }

                boolean flag = checkBox_main_selectAll.isChecked();
                checkBox_main_hobby1.setChecked(flag);
                checkBox_main_hobby2.setChecked(flag);
                checkBox_main_hobby3.setChecked(flag);
            }
        });
    }
    public void getResult() {
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
        text_main_info.setText(sb.toString());
    }
}
