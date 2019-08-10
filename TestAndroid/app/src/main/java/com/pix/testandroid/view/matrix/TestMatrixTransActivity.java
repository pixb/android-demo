package com.pix.testandroid.view.matrix;

import android.graphics.Matrix;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pix.testandroid.R;

public class TestMatrixTransActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private static final String TAG = "TestMatrixTransActivity";
    MatrixView mMatrixView;
    private TextView mMessageView;
    private StringBuilder mMessageSB = new StringBuilder();
    private SeekBar mXSeekBar;
    private SeekBar mYSeekBar;
    private TextView mXSeekProgressTV;
    private TextView mYSeekProgressTV;
    private Handler mHandler = new Handler();
    private float mTransX;
    private float mTransY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_matrix_trans);
        mMatrixView = (MatrixView) findViewById(R.id.mv_content);
        mMessageView = (TextView) findViewById(R.id.tv_message);
        mXSeekBar = (SeekBar) findViewById(R.id.sb_x);
        mYSeekBar = (SeekBar) findViewById(R.id.sb_y);
        mXSeekProgressTV = (TextView) findViewById(R.id.tv_seekbar_progress_x);
        mYSeekProgressTV = (TextView) findViewById(R.id.tv_seekbar_progress_y);

        mXSeekBar.setOnSeekBarChangeListener(this);
        mYSeekBar.setOnSeekBarChangeListener(this);

        mMessageSB.append("图片宽度:200px\n");
        mMessageSB.append("图片高度:100px\n");
        mMessageView.setText(mMessageSB.toString());

    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress, boolean fromUser) {
        Log.d(TAG,"onProgressChanged(),progress:" + progress);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(seekBar == mXSeekBar) {
                    mXSeekProgressTV.setText(progress * 10 + "px");
                    mTransX = progress * 10;
                }
                else {
                    mYSeekProgressTV.setText(progress * 10 + "px");
                    mTransY = progress * 10;
                }
                mMatrixView.setMatrixTranslate(mTransX,mTransY);
            }
        });
//
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
