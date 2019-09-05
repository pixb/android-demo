package com.pix.terminal;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 * <p/>
 * <p>Company: 浙江齐聚科技有限公司<a href="www.guagua.cn">www.guagua.cn</a></p>
 *
 * @author TPX
 * @version 1.0.0
 * @description
 * @modify
 */
public class TerminalMsgAdapter extends RecyclerView.Adapter<TerminalMsgAdapter.ViewHolder>{

    private ArrayList<TerminalLog> mLogList ;
    private byte displayState = TerminalLog.NAVIGATE_LOG | TerminalLog.RLSP_LOG | TerminalLog.CMSP_LOG | TerminalLog.PLAYER_LOG;


    public TerminalMsgAdapter(ArrayList<TerminalLog> logs) {
        mLogList = logs ;
    }

    public void setVisibleState(boolean flag,byte type) {
        if(flag) {
            displayState |= type;
        }
        else {
            displayState ^= type;
        }

        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_item_terminal,parent,false);
        return new ViewHolder(v,new WeakReference<TerminalMsgAdapter>(this));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TerminalLog log = mLogList.get(position);
        if((log.type & displayState) == log.type) {
            holder.itemView.setVisibility(View.VISIBLE);
        }
        else {
            holder.itemView.setVisibility(View.GONE);
        }
        holder.bindData(log);
    }

    @Override
    public int getItemCount() {
        return mLogList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
         private WeakReference<TerminalMsgAdapter> mReference ;
         private TextView logTV;
         private View itemView;

         public ViewHolder(View itemView, WeakReference<TerminalMsgAdapter> reference) {
             super(itemView);
             mReference = reference;
             logTV = (TextView) itemView.findViewById(R.id.tv_terminal_item);
             this.itemView = itemView;
         }

         public void bindData(TerminalLog log) {
             logTV.setText(log.logStr);
             logTV.setAlpha(0.8f);
             switch (log.type) {
                 case TerminalLog.NAVIGATE_LOG:
                     logTV.setBackgroundColor(Color.RED);
                     break ;
                 case TerminalLog.RLSP_LOG:
                     logTV.setBackgroundColor(Color.BLUE);
                     break ;
                 case TerminalLog.CMSP_LOG:
                     logTV.setBackgroundColor(Color.GREEN);
                     break ;
                 case TerminalLog.PLAYER_LOG:
                     logTV.setBackgroundColor(Color.BLACK);
                     break ;
             }
         }

     }
}
