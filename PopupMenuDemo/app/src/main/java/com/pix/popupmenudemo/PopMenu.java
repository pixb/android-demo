
package com.pix.popupmenudemo;
  
import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.view.ViewGroup.LayoutParams;  
import android.widget.AdapterView.OnItemClickListener;  
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;  
import android.widget.TextView;  
  

public class PopMenu {  
    private ArrayList<String> itemList;  
    private Context context;  
    private PopupWindow popupWindow ;  
    private ListView listView;  
    //private OnItemClickListener listener;  
      
  
    public PopMenu(Context context) {
        // TODO Auto-generated constructor stub  
        this.context = context;  
  
        itemList = new ArrayList<String>();
          
        View view = LayoutInflater.from(context).inflate(R.layout.popmenu, null);  
          
        //设置 listview  
        listView = (ListView)view.findViewById(R.id.listView);  
        listView.setAdapter(new PopAdapter());
          
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

    }  
  
    //设置菜单项点击监听器  
    public void setOnItemClickListener(OnItemClickListener listener) {  
        //this.listener = listener;  
        listView.setOnItemClickListener(listener);  
    }  
  
    //批量添加菜单项  
    public void addItems(String[] items) {  
        for (String s : items)  
            itemList.add(s);  
    }  
  
    //单个添加菜单项  
    public void addItem(String item) {  
        itemList.add(item);  
    }  
  
    //下拉式 弹出 pop菜单 parent 右下角  
    public void showAsDropDown(View parent) {  
        popupWindow.showAsDropDown(parent, 10,   
                //保证尺寸是根据屏幕像素密度来的  
                context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));  
          
        // 使其聚集  
        popupWindow.setFocusable(true);  
        // 设置允许在外点击消失  
        popupWindow.setOutsideTouchable(true);  
        //刷新状态  
        popupWindow.update();
        setListViewBasedOnChildren(listView);
    }  
      
    //隐藏菜单  
    public void dismiss() {  
        popupWindow.dismiss();  
    }  
  
    // 适配器  
    private final class PopAdapter extends BaseAdapter {  
  
        @Override  
        public int getCount() {  
            // TODO Auto-generated method stub  
            return itemList.size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            // TODO Auto-generated method stub  
            return itemList.get(position);  
        }  
  
        @Override  
        public long getItemId(int position) {  
            // TODO Auto-generated method stub  
            return position;  
        }  
  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            // TODO Auto-generated method stub  
            ViewHolder holder;  
            if (convertView == null) {  
                convertView = LayoutInflater.from(context).inflate(R.layout.pomenu_item, null);  
                holder = new ViewHolder();  
  
                convertView.setTag(holder);  
  
                holder.groupItem = (TextView) convertView.findViewById(R.id.textView);  
  
            } else {  
                holder = (ViewHolder) convertView.getTag();  
            }  
  
            holder.groupItem.setText(itemList.get(position));  
  
            return convertView;  
        }  
  
        private final class ViewHolder {  
            TextView groupItem;  
        }  
    }

    public void setListViewBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            int width = listItem.getMeasuredWidth();
            if(width>maxWidth)maxWidth = width;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.width = maxWidth;
        listView.setLayoutParams(params);
    }
} 