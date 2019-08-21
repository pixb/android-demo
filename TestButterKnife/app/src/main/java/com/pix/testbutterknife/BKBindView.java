package com.pix.testbutterknife;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tangpengxiang on 2017/5/5.
 */

public class BKBindView extends FrameLayout {
    private static final String TAG = "BindView";
    @BindView(R.id.tv_bind_view)
    TextView mBindTV;
    public BKBindView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BKBindView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BKBindView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context ctx) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_bind_view,this,true);
        ButterKnife.bind(this,view);
        mBindTV.setText(TAG + " bind test....");
    }
}
