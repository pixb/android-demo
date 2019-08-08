package com.pix.testpropertyanimation.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pix.testpropertyanimation.R;
import com.pix.testpropertyanimation.utils.Utils;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author tangpengxiang on 2018/3/1.
 * @version 1.0.0
 * @description
 * @modify
 */
public class TranslationSampleLayout extends RelativeLayout {
    private ImageView imageView;
    private Button btn;
    // 动画的状态
    private int transState;

    public TranslationSampleLayout(Context context) {
        super(context);
    }

    public TranslationSampleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranslationSampleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        imageView = findViewById(R.id.image_view);
        btn = findViewById(R.id.btn_clickme);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (transState) {
                    case 0:
                        if(null != imageView) {
                            imageView.animate().translationX(Utils.dp2pix(50));
                        }
                        break;
                    case 1:
                        if(null != imageView) {
                            imageView.animate().translationX(0);
                        }
                        break;
                    case 2:
                        if(null != imageView) {
                            imageView.animate().translationY(Utils.dp2pix(50));
                        }
                        break;
                    case 3:
                        if(null != imageView) {
                            imageView.animate().translationY(0);
                        }
                        break;
                }
                transState++;
                if(transState == 4) {
                    transState = 0;
                }
            }
        });
    }
}
