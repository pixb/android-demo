package com.jingchen.pulltorefresh.pullableview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

public class PullableRecycleView extends RecyclerView implements Pullable
{
	private static final String TAG = "PullableRecycleView";

	public PullableRecycleView(Context context)
	{
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
	public boolean canPullDown()
	{
		LinearLayoutManager llm = (LinearLayoutManager) getLayoutManager();
		if (getAdapter().getItemCount() == 0)
		{
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (llm.findViewByPosition(llm.findFirstVisibleItemPosition()).getTop()==0 && llm.findFirstVisibleItemPosition()==0)
		{
			// 滑到ListView的顶部了
			return true;
		} else
			return false;
	}

	@Override
	public boolean canPullUp()
	{
		LinearLayoutManager llm = (LinearLayoutManager) getLayoutManager();
		Log.d(TAG,"canPullUp(),getButtom:"+llm.findViewByPosition(llm.findLastCompletelyVisibleItemPosition()).getBottom());
		Log.d(TAG,"buttom:" + getBottom());
		if (getAdapter().getItemCount() == 0)
		{

			// 没有item的时候也可以上拉加载
			return true;
		} //else if (llm.findLastCompletelyVisibleItemPosition() == 0)
		else if(llm.findViewByPosition(llm.findLastCompletelyVisibleItemPosition()).getBottom() == getBottom())
		{
			return true;
		}
		return false;
	}
}
