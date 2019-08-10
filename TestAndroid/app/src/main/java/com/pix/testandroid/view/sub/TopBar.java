package com.pix.testandroid.view.sub;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pix.testandroid.R;

/**
 * 自定义组合控件
 */
public class TopBar extends RelativeLayout implements View.OnClickListener{
    private static final String TAG = "TopBar";
    private TextView mTitle;
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mTitleString;

    private Button mLeftButton;
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftTextString;

    private Button mRightButton;
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightTextString;

    private RelativeLayout.LayoutParams mLeftLayoutParams;
    private RelativeLayout.LayoutParams mRightLayoutParams;
    private RelativeLayout.LayoutParams mTitleLayoutParams;
    private OnTopBarClickListener mListener;

    private boolean mLeftButtonVisable = true;
    private boolean mRightButtonVisable = true;
    private Context mContext;

    public TopBar(Context context) {
        super(context);
        this.mContext = context;
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context,attrs);

    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        this.mContext = context;
        init(context,attrs);
    }

    public void init(Context context,AttributeSet attrs) {
        Log.d(TAG,"init()");
        //通过这个方法，将你在attrs.xml中定义的declare-styleable的所有属性存储到TypedArray中
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        //从TypedArray中取出对应的值来为要设置的属性赋值
        mLeftTextColor = typedArray.getColor(R.styleable.TopBar_leftTextColor,0);
        mLeftBackground = typedArray.getDrawable(R.styleable.TopBar_leftBackground);
        mLeftTextString = typedArray.getString(R.styleable.TopBar_leftText);

        mRightTextColor = typedArray.getColor(R.styleable.TopBar_rightTextColor,0);
        mRightBackground = typedArray.getDrawable(R.styleable.TopBar_rightBackground);
        mRightTextString = typedArray.getString(R.styleable.TopBar_rightText);

        mTitleTextColor = typedArray.getColor(R.styleable.TopBar_titleTextColor,0);
        mTitleString = typedArray.getString(R.styleable.TopBar_title);
        mTitleTextSize = typedArray.getDimension(R.styleable.TopBar_titleTextSize,10);
        //释放
        typedArray.recycle();

        //添加控件
        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitle = new TextView(context);

        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setText(mLeftTextString);

        mRightButton.setText(mRightTextString);
        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);

        mTitle.setText(mTitleString);
        mTitle.setTextColor(mTitleTextColor);
        mTitle.setTextSize(mTitleTextSize);

        //设置布局属性
        mTitle.setGravity(Gravity.CENTER);

        mLeftLayoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        mLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        //添加到ViewGroup
        addView(mLeftButton,mLeftLayoutParams);

        mRightLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        //添加到ViewGroup
        addView(mRightButton,mRightLayoutParams);

        mTitleLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        mTitleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        //添加到ViewGroup
        addView(mTitle,mTitleLayoutParams);


    }

    public void setLeftButtonVisable(boolean flag) {
        mLeftButtonVisable = flag;
        if(mLeftButton != null) {
            if(flag) {
                mLeftButton.setVisibility(View.VISIBLE);
            }
            else {
                mLeftButton.setVisibility(View.GONE);
            }
        }
    }

    public void setRightButtonVisable(boolean flag) {
        mRightButtonVisable = flag;
        if(mRightButton != null) {
            if(flag) {
                mRightButton.setVisibility(View.VISIBLE);
            }
            else {
                mRightButton.setVisibility(View.GONE);
            }
        }
    }
    public void setOnTopBarClickLinstener(OnTopBarClickListener listener) {
        this.mListener = listener;
    }
    @Override
    public void onClick(View v) {
        if(v.equals(mLeftButton)) {
            if(mListener != null) {
                mListener.onLeftButtonClick();
            }
        }
        if(v.equals(mRightButton)) {
            if(mListener != null) {
                mListener.onRightButtonClick();
            }
        }
    }
    public interface OnTopBarClickListener {
        public void onLeftButtonClick();
        public void onRightButtonClick();
    }
}
