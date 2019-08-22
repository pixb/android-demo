package com.pix.download;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pix.download.bean.DownloadBean;
import com.pix.downloadlib.PixDownload;
import com.pix.downloadlib.bean.LoadInfo;
import com.pix.downloadlib.utils.EventBusManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView{
    private static final String TAG = "MainActivity";
    private MainPrecenter mPrecenter;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        PixDownload.init(getApplication());
        mPrecenter = new MainPrecenter(this);
        mPrecenter.init();

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void updateList(List<DownloadBean> list) {
        if(mAdapter == null && list != null) {
            mAdapter = new ListAdapter(this,list);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener {
        private List<DownloadBean> loadInfoList;
        private Context mContext;

        public ListAdapter(Context context, List<DownloadBean> list) {
            mContext = context;
            loadInfoList = list;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            DownloadBean info = loadInfoList.get(i);
            viewHolder.mNameTV.setText(info.fileName);
            viewHolder.mProgressBar.setMax(info.totalSize);
            viewHolder.mProgressBar.setProgress(info.completedSize);
            viewHolder.mDownBtn.setTag(info.fileName);
            if(info.totalSize == info.completedSize) {
                viewHolder.mDownBtn.setText("完成");
            }
        }

        @Override
        public int getItemCount() {
            return loadInfoList.size();
        }

        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            String fileName = (String) btn.getTag();
            if(btn.getText().equals("下载")) {
                btn.setText("暂停");
                mPrecenter.downLoadFile(fileName);
            }
            else if(btn.getText().equals("暂停")) {
                btn.setText("下载");
                mPrecenter.pauseDownload(fileName);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mNameTV;
            public Button mDownBtn;
            public ProgressBar mProgressBar;

            public ViewHolder(View itemView) {
                super(itemView);
                mNameTV = (TextView) itemView.findViewById(R.id.tv_resouce_name);
                mDownBtn = (Button) itemView.findViewById(R.id.btn_start);
                mDownBtn.setOnClickListener(ListAdapter.this);
                mProgressBar = (ProgressBar) itemView.findViewById(R.id.pb_progress);
            }
        }
    }
}
