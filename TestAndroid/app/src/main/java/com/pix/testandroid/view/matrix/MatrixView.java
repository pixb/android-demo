package com.pix.testandroid.view.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.pix.testandroid.R;
import com.pix.testandroid.utils.AnimBitmapLoader;
import com.pix.testandroid.widget.ScaleFramLayout;

/**
 * Created by tangpengxiang on 2016/11/19.
 */

public class MatrixView extends ScaleFramLayout {
    private static final String TAG = "MatrixView";
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private Paint mPaint;
    private float mTransX;
    private float mTransY;
    private float mScaleX = 1;
    private float mScaleY = 1;
    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mBitmap = AnimBitmapLoader.getInstance().readBitmap(context, R.drawable.boly);
        mMatrix = new Matrix();
        mPaint = new Paint();
    }

    public void setMatrixTranslate(float transX,float transY) {
        this.mTransX = transX;
        this.mTransY = transY;
        postInvalidate();
    }

    public void setMatrixScale(float scaleX,float scaleY) {
        this.mScaleX = scaleX;
        this.mScaleY = scaleY;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw(),mTransX:" + mTransX + ",mTransY:" + mTransY
                + ",mScaleX:"+ mScaleX + ",mScaleY:" + mScaleY);
        mMatrix.setTranslate(mTransX,mTransY);
        mMatrix.postScale(mScaleX,mScaleY);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
    }
}
