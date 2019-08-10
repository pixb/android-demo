package com.pix.testandroid.view.common.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.pix.testandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestListViewSimpleAdapterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "TestListViewSimpleAdapterActivity";
    private ListView mListView;
    private ArrayList<HashMap<String,String>> mDataList;
    private SimpleAdapter mSimpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_view_simple_adapter);
        setTitle(TAG);
        this.mListView = (ListView) findViewById(R.id.listview_simpleadapter);
        mDataList = new ArrayList<>();
        /*
		 * 常用的SimpleAdapter的构造方法有五个参数：
		 *
		 * @param context ：表示上下文对象或者环境对象。
		 *
		 * @param data ：表示数据源。往往采用List<Map<String, Object>>集合对象。
		 *
		 * @param resource ：自定义的ListView中每个item的布局文件。用R.layout.文件名的形式来调用。
		 *
		 * @param from ：其实是数据源中Map的key组成的一个String数组。
		 *
		 * @param to ：表示数据源中Map的value要放置在item中的哪个控件位置上。其实就是自定义的item布局文件中每个控件的id。
		 * 通过R.id.id名字的形式来调用。
        */
        mSimpleAdapter = new SimpleAdapter(this,mDataList,R.layout.item_testlistview_simpleadapter
        ,new String[]{"username", "password"},new int[] {R.id.text_item_listview_username,
                R.id.text_item_listview_pwd });
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mSimpleAdapter);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("username", "pixtang_" + i);
            map.put("password", "123456_" + i);
            mDataList.add(map);
        }
        mSimpleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //第一种写法，获取数组或数组中的数据，通过position获得
        // HashMap<String,String> data = mDataList.get(position);

        // 第二种写法，
        //HashMap<String,String> data = (HashMap<String, String>) parent.getItemAtPosition(position);

        // 第三种写法：
        //HashMap<String,String> data = (HashMap<String, String>) parent.getSelectedItem();

        // 第四种写法：
        // String data = adapter.getItem(position);
        HashMap<String,String> data = (HashMap<String, String>) mSimpleAdapter.getItem(position);

        Toast.makeText(this,"postion:" + position + ",dat:" + data.toString(),Toast.LENGTH_LONG).show();
    }
}
