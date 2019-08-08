package com.pix.testshape;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author tangpengxiang on 2018/3/12.
 * @version 1.0.0
 * @description
 * @modify
 */
public class ShapeFragment extends Fragment {
    private ShapeModule module;
    private static final String ARGS_MODULE = "args_module";

    public static ShapeFragment newInstance(ShapeModule module){
        ShapeFragment fragment = new ShapeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_MODULE,module);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle  = getArguments();
        module = (ShapeModule) bundle.getSerializable(ARGS_MODULE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shape,null,false);
        TextView title = view.findViewById(R.id.tv_title);
        ViewStub viewStub = view.findViewById(R.id.viewstub);
        if(null != module) {
            title.setText(module.title);
            viewStub.setLayoutResource(module.layoutRes);
            viewStub.inflate();
        }
        return view;
    }
}
