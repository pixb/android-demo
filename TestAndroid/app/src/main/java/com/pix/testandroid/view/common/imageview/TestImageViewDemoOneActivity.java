package com.pix.testandroid.view.common.imageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pix.testandroid.R;

public class TestImageViewDemoOneActivity extends AppCompatActivity {
    private static final String TAG = "TestImageViewDemoOne";
    private ImageView image_main_pic;
    private Button button_main_up;
    private Button button_main_next;
    // 定义一个数组，用来存放所有图片的id
    private int[] imgId = { R.drawable.img001, R.drawable.img012,
            R.drawable.img017, R.drawable.img021, R.drawable.img030,
            R.drawable.img031, R.drawable.img033, R.drawable.img038,
            R.drawable.img039 };
    // 定义数组下标的起始值
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_view_demo_one);
        setTitle(TAG);
        image_main_pic = (ImageView) findViewById(R.id.image_main_pic);
        button_main_up = (Button) findViewById(R.id.button_main_up);
        button_main_next = (Button) findViewById(R.id.button_main_next);

        // OnClickListener listener = new MyListener();

        // 当多个控件都要使用同一个监听器的时候，使用匿名监听器，代码重用性就不高。于是使用有名字的监听器
        // 对于只使用一次的监听器，常用匿名内部监听器来实现
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "您点击了button",
                // Toast.LENGTH_SHORT).show();

                switch (v.getId()) {
                    case R.id.button_main_next:
                        index++;
                        break;
                    case R.id.button_main_up:
                        index--;
                        break;
                }

                if (index >= imgId.length) {
                    index = 0;
                }
                if (index < 0) {
                    index = imgId.length - 1;
                }

                Log.d(TAG,"==" + index);
                // Log.d("=====", index + "");
                // System.out.println("==" + index);

                image_main_pic.setImageResource(imgId[index]);
            }
        };

        button_main_up.setOnClickListener(listener);
        button_main_next.setOnClickListener(listener);
    }
}
