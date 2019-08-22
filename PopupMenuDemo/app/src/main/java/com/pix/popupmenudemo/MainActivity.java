package com.pix.popupmenudemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button mBtn;
    PopupMenu mPM;
    PopMenu popMenu;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.btn_main);
        mPM = new PopupMenu(this,mBtn);
        popMenu = new PopMenu(this);
        popMenu.addItem("File");
        popMenu.addItem("Edit");
        popMenu.addItem("View");
        popMenu.addItem("Code");

        menu = mPM.getMenu();
        // 通过代码添加菜单项
        menu.add(Menu.NONE, Menu.FIRST + 0, 0, "复制");
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "粘贴");

        // 通过XML文件添加菜单项
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popupmenu, menu);

        // 监听事件
        mPM.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news:
                        Toast.makeText(MainActivity.this, "新建",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.open:
                        Toast.makeText(MainActivity.this, "打开",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Menu.FIRST + 0:
                        Toast.makeText(MainActivity.this, "复制",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Menu.FIRST + 1:
                        Toast.makeText(MainActivity.this, "粘贴",
                                Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public void onClick(View v){
        Button btn = (Button) v;
        if(btn.getText().equals("PopupMenu")) {
            mPM.show();
        }

        if(btn.getText().equals("PopMenu")) {
            popMenu.showAsDropDown(v);
        }

    }
}
