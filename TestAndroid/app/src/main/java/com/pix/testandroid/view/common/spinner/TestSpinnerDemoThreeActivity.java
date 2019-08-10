package com.pix.testandroid.view.common.spinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.pix.testandroid.MainActivity;
import com.pix.testandroid.R;

public class TestSpinnerDemoThreeActivity extends AppCompatActivity {
    private static final String TAG = "TestSpinnerDemoThreeActivity";
    private Spinner spinner_main_edu;
    private Button button_main_submit;
    private TextView text_main_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_spinner_demo_three);
        setTitle(TAG);
        button_main_submit = (Button) findViewById(R.id.button_main_submit);
        text_main_info = (TextView) findViewById(R.id.text_main_info);
        // 设置数据源
         String[] strArr = new String[] { "初中", "高中", "中专", "大专", "大本", "研究生"
         };


        spinner_main_edu = (Spinner) findViewById(R.id.spinner_main_edu);
        // 构建适配器。Spinner控件常用ArrayAdapter适配器，只显示文本。
        // ArrayAdapter是数组适配器。第一个参数是上下文对象或者说是环境对象，第二个参数是显示数据的布局id，
        // 布局id可以自定义布局，也可以使用系统自带的布局。如果使用系统的布局，则使用android.R.layout.的形式来调用。
        // 第三个参数是需要加载的数据源数组。至于是哪种类型的数组，取决于ArrayAdapter的泛型类型。
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                TestSpinnerDemoThreeActivity.this,
                android.R.layout.simple_list_item_single_choice, strArr);
        // 给控件设置适配器
        spinner_main_edu.setAdapter(adapter);

        spinner_main_edu
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // 方法1：利用AdapterView的getItemAtPosition(position)获取item的内容
                        String data = parent.getItemAtPosition(position)
                                .toString();
                        // 方法2：利用AdapterView的getSelectedItem()获取item的内容
                        String data2 = parent.getSelectedItem().toString();
                        // String data3 = spinner_main_edu.getItemAtPosition(
                        // position).toString();
                        // String data4 = spinner_main_edu.getSelectedItem()
                        // .toString();

                        text_main_info.setText(data + ":" + data2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        // 以下代码看似正确，实际上是错误的。java.lang.RuntimeException: setOnItemClickListener
        // cannot be used with a spinner.
        // spinner_main_edu
        // .setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //
        // @Override
        // public void onItemClick(AdapterView<?> parent, View view,
        // int position, long id) {
        // String data2 = parent.getSelectedItem().toString();
        // text_main_info.setText(data2 + ":" + data2);
        // }
        // });

        button_main_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = spinner_main_edu.getSelectedItem().toString();

                text_main_info.setText("您选择的是: "+ data);
            }
        });

        // XmlResourceParser pullParser = getResources().getXml(
        // R.xml.citys_weather);
    }
}
