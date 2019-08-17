package com.pix.testtablayout.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pix.testtablayout.R;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author tangpengxiang on 2018/2/28.
 * @version 1.0.0
 * @description
 * @modify
 */
public class PageFragment2 extends Fragment {
    private static final String PAGE_ARGS = "page_args";
    private int page;

    public static final PageFragment2 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(PAGE_ARGS,page);
        PageFragment2 fragment2 = new PageFragment2();
        fragment2.setArguments(args);
        return fragment2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(null != args) {
            page = args.getInt(PAGE_ARGS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_page2,null,false);
        TextView tvPage = v.findViewById(R.id.tv_page);
        tvPage.setText("第 -" + page + "- 页");
        return v;
    }
}
