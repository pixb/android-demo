package com.pix.pictextview;

import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guagua.live.lib.widget.ui.PicAndTextView;

public class MainActivity extends AppCompatActivity {
    private PicAndTextView m_ptvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_ptvContent = (PicAndTextView) findViewById(R.id.pic_text);
        TextPaint tp1 = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        tp1.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 15, Resources.getSystem().getDisplayMetrics()));
        tp1.setColor(0xffec7a00);

        TextPaint tp2 = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        tp2.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 15, Resources.getSystem().getDisplayMetrics()));
        tp2.setColor(0xffFF0000);
        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.li_guardian_gold);
        PicAndTextView.LayoutParam layoutParam=new PicAndTextView.LayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);
        layoutParam.setMargins(0,0,20,0);
        iv1.setLayoutParams(layoutParam);
        m_ptvContent.clean();
        m_ptvContent.addTextChild("名字名字",tp1);
        m_ptvContent.addNewChild(iv1);
        m_ptvContent.addTextChild("内容1234567890abcdefghijklmnopqrstuvwxyz一二三四五六七八九十");
    }
}
