package com.pix.terminal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
public class TerminalDialog extends Dialog implements CompoundButton.OnCheckedChangeListener{
    //终端的消息列表
    private RecyclerView mTerminalRV ;
    private LinearLayoutManager mLayoutManager ;
    //日志列表数据源
    private ArrayList<TerminalLog> mLogList = new ArrayList<>();
    private TerminalMsgAdapter mTerminalMsgAdapter ;

    private CheckBox mRlspCB;
    private CheckBox mNavigateCB;
    private CheckBox mCmspCB;
    private CheckBox mPlayerCB;

    public TerminalDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.li_dialog_terminal);
        mTerminalRV = (RecyclerView) findViewById(R.id.rv_terminal);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mTerminalRV.setLayoutManager(mLayoutManager);
        mTerminalMsgAdapter = new TerminalMsgAdapter(mLogList);
        mTerminalRV.setAdapter(mTerminalMsgAdapter);
        mNavigateCB = (CheckBox) findViewById(R.id.cb_terminal_item_navigate);
        mRlspCB = (CheckBox) findViewById(R.id.cb_terminal_item_rlsp);
        mCmspCB = (CheckBox) findViewById(R.id.cb_terminal_item_cmsp);
        mPlayerCB = (CheckBox) findViewById(R.id.cb_terminal_item_player);
        mNavigateCB.setOnCheckedChangeListener(this);
        mRlspCB.setOnCheckedChangeListener(this);
        mCmspCB.setOnCheckedChangeListener(this);
        mPlayerCB.setOnCheckedChangeListener(this);
        testData();
    }

    public void testData() {
        for (int i = 0 ;i < 10 ;i++ ) {
            mLogList.add(0,new TerminalLog(TerminalLog.NAVIGATE_LOG,"Navigate日志---->" + i));
            mLogList.add(0,new TerminalLog(TerminalLog.RLSP_LOG,"Rlsp日志---->" + i));
            mLogList.add(0,new TerminalLog(TerminalLog.CMSP_LOG,"cmsp日志---->" + i));
            mLogList.add(0,new TerminalLog(TerminalLog.PLAYER_LOG,"player日志---->" + i));
        }
        mTerminalMsgAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_terminal_item_navigate:
                mTerminalMsgAdapter.setVisibleState(isChecked,TerminalLog.NAVIGATE_LOG);
                break ;
            case R.id.cb_terminal_item_rlsp:
                mTerminalMsgAdapter.setVisibleState(isChecked,TerminalLog.RLSP_LOG);
                break ;
            case R.id.cb_terminal_item_cmsp:
                mTerminalMsgAdapter.setVisibleState(isChecked,TerminalLog.CMSP_LOG);
                break ;
            case R.id.cb_terminal_item_player:
                mTerminalMsgAdapter.setVisibleState(isChecked,TerminalLog.PLAYER_LOG);
                break ;
        }
    }
}
