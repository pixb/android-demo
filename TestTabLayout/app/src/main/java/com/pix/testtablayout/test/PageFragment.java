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
public class PageFragment extends Fragment {
    private static final String PAGE_ARGS = "page_args";
    private int page;
    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(PAGE_ARGS,page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bd = getArguments();
        if(null != bd) {
            page = bd.getInt(PAGE_ARGS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_page,null,false);
        TextView tvPage = v.findViewById(R.id.tv_page);
        tvPage.setText("第 -" + page + "- 页");
        return v;
    }
}
