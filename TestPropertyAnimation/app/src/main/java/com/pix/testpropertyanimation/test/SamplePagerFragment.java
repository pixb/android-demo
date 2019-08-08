package com.pix.testpropertyanimation.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.pix.testpropertyanimation.R;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author tangpengxiang on 2018/3/2.
 * @version 1.0.0
 * @description
 * @modify
 */
public class SamplePagerFragment extends Fragment {
    private static String MODULE_ARGS = "module_args";
    private TestSampleActivity.SamplePagerModule module;

    public static SamplePagerFragment newInstance(TestSampleActivity.SamplePagerModule module) {
        SamplePagerFragment spf = new SamplePagerFragment();
        Bundle args  = new Bundle();
        args.putSerializable(MODULE_ARGS,module);
        spf.setArguments(args);
        return spf;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bd = getArguments();
        if(null != bd) {
            this.module = (TestSampleActivity.SamplePagerModule) bd.getSerializable(MODULE_ARGS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_page_sample,null,false);
        ViewStub vs1 = view.findViewById(R.id.vs_sp1);
        ViewStub vs2 = view.findViewById(R.id.vs_sp2);
        if(null != vs1 && null != vs2) {
            vs1.setLayoutResource(module.sample1Res);
            vs1.inflate();
            vs2.setLayoutResource(module.sample2Res);
            vs2.inflate();
        }
        return view;
    }
}
