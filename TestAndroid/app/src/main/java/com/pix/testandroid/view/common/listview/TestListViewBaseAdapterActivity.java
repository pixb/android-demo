package com.pix.testandroid.view.common.listview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pix.testandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestListViewBaseAdapterActivity extends AppCompatActivity {
    private static final String TAG = "TestListViewBaseAdapterActivity";
    private ListView listView_main_regmsg;
    private int[] imgIds = new int[]{R.drawable.pic01, R.drawable.pic02,
            R.drawable.pic03, R.drawable.pic04,R.mipmap.ic_launcher
            ,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher
            ,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher
            ,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher
            ,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher
            ,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private static int holderNumTmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_view_base_adapter);
        setTitle(TAG);
        listView_main_regmsg = (ListView) findViewById(R.id.listView_main_regmsg);

        // 创建数据源
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imgIds.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username", "pixboly_" + i);
            map.put("pwd", "123456_" + i);
            map.put("imgId", imgIds[i]);
            list.add(map);
        }

        // SimpleAdapter adapter = new SimpleAdapter(this, list,
        // R.layout.item_listview_main, new String[] { "username", "pwd",
        // "imgId" }, new int[] {
        // R.id.text_item_listview_username,
        // R.id.text_item_listview_pwd,
        // R.id.imageView_item_listview_headpic });

        // 给ListView设置适配器
        listView_main_regmsg.setAdapter(new MyAdapter(list, this));
    }
    class MyAdapter extends BaseAdapter {
        private List<Map<String, Object>> list = null;
        private Context context;

        public MyAdapter(List<Map<String, Object>> list, Context context) {
            this.list = list;
            this.context = context;
            holderNumTmp = 0;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i("BaseAdapter", "==position:" + position + ":");

            // 作法1：不考虑ConverView重用的写法：
            // View view = LayoutInflater.from(context).inflate(
            // R.layout.item_listview_main, parent, false);
            // TextView text_item_listview_username = (TextView) view
            // .findViewById(R.id.text_item_listview_username);
            // TextView text_item_listview_pwd = (TextView) view
            // .findViewById(R.id.text_item_listview_pwd);
            // ImageView imageView_item_listview_headpic = (ImageView) view
            // .findViewById(R.id.imageView_item_listview_headpic);
            //
            // text_item_listview_username.setText(list.get(position)
            // .get("username").toString());
            // text_item_listview_pwd.setText(list.get(position).get("pwd")
            // .toString());
            // imageView_item_listview_headpic.setImageResource((Integer) list
            // .get(position).get("imgId"));
            // return view;

            // 作法2：考虑ConvertView重用的做法：
            ViewHolder mHolder = null;
            if (convertView == null) {
                mHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_listview_main, parent, false);
                mHolder.text_item_listview_username = (TextView) convertView
                        .findViewById(R.id.text_item_listview_username);
                mHolder.text_item_listview_pwd = (TextView) convertView
                        .findViewById(R.id.text_item_listview_pwd);
                mHolder.imageView_item_listview_headpic = (ImageView) convertView
                        .findViewById(R.id.imageView_item_listview_headpic);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
                Log.i("BaseAdapter","getView(),mHolder.holderNumber:" + mHolder.holderNumber);
            }

            mHolder.text_item_listview_username.setText(list.get(position)
                    .get("username").toString());
            mHolder.text_item_listview_pwd.setText(list.get(position)
                    .get("pwd").toString());
            mHolder.imageView_item_listview_headpic
                    .setImageResource((Integer) list.get(position).get("imgId"));
            return convertView;
        }

        class ViewHolder {
            public ViewHolder() {
                holderNumber = ++holderNumTmp ;
            }
            private int holderNumber ;
            private TextView text_item_listview_username;
            private TextView text_item_listview_pwd;
            private ImageView imageView_item_listview_headpic;
        }
    }
}
