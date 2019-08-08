package com.pix.testnativesocket;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{
    private ITcpSocketClient tcpSocketClient;
    private EditText etContent;
    private RecyclerView rcvList;
    LinearLayoutManager mLayoutManager;
    private MsgAdapter msgAdapter;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_connect).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        etContent = (EditText) findViewById(R.id.et_input);
        rcvList = (RecyclerView) findViewById(R.id.rcv_list);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcvList.setLayoutManager(mLayoutManager);
        msgAdapter = new MsgAdapter(dataList);
        rcvList.setAdapter(msgAdapter);
        tcpSocketClient = NativeTcpClient.getSingleInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                if(tcpSocketClient.isConnected()) {
                    String content = etContent.getText().toString();
                    if(!TextUtils.isEmpty(content)) {
                        tcpSocketClient.sendData(content.getBytes());
                    }
                }
                else {
                    dataList.add("服务器未连接,请先连接服务器!");
                }
                break;
            case R.id.btn_connect:
                if(!tcpSocketClient.isConnected()) {
                    tcpSocketClient.setHostAndPort("192.168.26.130",7777);
                    tcpSocketClient.connect();
                }
                else {
                    dataList.add("服务器已连接,请先停止服务器!");
                }

                break;
            case R.id.btn_stop:
                if(tcpSocketClient.isConnected()) {
                    tcpSocketClient.close();
                }
                else {
                    dataList.add("未连接服务器!");
                }

                break;
        }
        msgAdapter.notifyDataSetChanged();
    }

    public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
        List<String> mDataList;
        MsgAdapter(List<String> dataList) {
            this.mDataList = dataList;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_layout,parent,false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String msg = mDataList.get(position);
            holder.tvContent.setText(msg);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvContent;
            public ViewHolder(View itemView) {
                super(itemView);
                tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            }
        }
    }
}
