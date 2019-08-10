package com.pix.refreshandload.refreshload.pullableview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/6/6.
 */
public class PullableRecycleView extends RecyclerView implements Pullable {
    public PullableRecycleView(Context context) {
        super(context);
    }

    public PullableRecycleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PullableRecycleView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        return false;
    }

    @Override
    public boolean canPullUp() {
        return false;
    }
}
