package com.pix.anim.project.qiqi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.pix.anim.R;

import java.io.IOException;

public class QiQiRepeatGiftActivity extends AppCompatActivity {

    private static final int SEND_LV1_GIFT = 0x1001;
    private static final int SEND_LV2_GIFT = 0x1002;
    private static final int SEND_LV3_GIFT = 0x1003;


    private int count = 0;
    private FrameLayout giftContainer;
    private QiQiRepeatGiftView repeatGift;
    private Bitmap gift;
    private Handler mHandler ;
    private EditText mDelayedET;
    private EditText mNumberET ;
    private int mDelayedTime = 100;
    private int mGiftNumber = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_qi_repeat_gift);
        this.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case SEND_LV1_GIFT:

                        break;

                    case SEND_LV2_GIFT:

                        break ;

                    case SEND_LV3_GIFT:
                        if(msg.obj instanceof SendGiftBean) {
                            SendGiftBean bean = (SendGiftBean)msg.obj ;
                            if(bean.mCount >= 0) {
                                bean.mCount--;
                                Message tmsg = new Message();
                                tmsg.what = SEND_LV3_GIFT;
                                tmsg.obj = bean ;
                                mHandler.sendMessageDelayed(tmsg, mDelayedTime);
                                repeatGift.addGift(bean.mStartX,bean.mStartY,bean.mEndX,bean.mEndY,bean.mGift);
                            }
                        }

                        break ;

                    default:
                        break;
                }
            }
        };
        this.mDelayedET = (EditText) findViewById(R.id.et_delayed);
        this.mNumberET = (EditText) findViewById(R.id.et_number);
        giftContainer = (FrameLayout) findViewById(R.id.act_gift_container_qiqi);
        try {
            gift = BitmapFactory.decodeStream(getAssets().open("coffee.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        repeatGift = new QiQiRepeatGiftView(this, gift);
        giftContainer.addView(repeatGift);
        repeatGift.start();
    }

    public void onClick(View view){
        mDelayedTime = Integer.parseInt(this.mDelayedET.getText().toString());
        mGiftNumber = Integer.parseInt(this.mNumberET.getText().toString()) - 1;

//		giftContainer.removeAllViews();
        repeatGift.addGift(500,1000,300,300,gift);

        Message msg = new Message();
        SendGiftBean giftBean  = new SendGiftBean();
        giftBean.mStartX = 500;
        giftBean.mStartY = 1000;
        giftBean.mEndX = 300 ;
        giftBean.mEndY = 300 ;
        giftBean.mCount = mGiftNumber - 1 ;
        giftBean.mGift = gift ;
        msg.what = SEND_LV3_GIFT;
        msg.obj = giftBean ;
        mHandler.sendMessageDelayed(msg, mDelayedTime);

    }

    public class SendGiftBean {
        public float mStartX ;
        public float mStartY ;
        public float mEndX ;
        public float mEndY ;
        public Bitmap mGift ;
        public int mCount ;
    }
}
