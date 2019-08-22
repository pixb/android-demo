package com.pix.testmyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/2/10.
 */

public class CustomAttributeView extends View {
    private static final String TAG = "CustomAttributeView";
    public CustomAttributeView(Context context) {
        super(context);
    }

    public CustomAttributeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.testmyview);
        String text = ta.getString(R.styleable.testmyview_text);
        int testAttr = ta.getInt(R.styleable.testmyview_testAttr, 0);
        Log.d(TAG,"CustomAttributeView(),text:" + text + ",testAttr:" + testAttr);
        // 打印出所有的属性
        // 取得属性的数量
        int count = attrs.getAttributeCount();
        for (int i = 0 ;i < count ;i++) {
            String name = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            Log.d(TAG,"CustomAttributeView(),name = " + name + ",value = " + value);
        }
    }

//    public CustomAttributeView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
}
