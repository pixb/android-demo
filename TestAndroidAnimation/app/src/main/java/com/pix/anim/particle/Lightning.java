package com.pix.anim.particle;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * 闪电特殊，没有子元素
 * @author lxf
 *
 */
public class Lightning extends ParticleItem {

	private final String TAG = "Lightning";
	
	private Paint paint = new Paint();
	private final int minSize = 10;
	private final int maxSize = 50; // 长度在0-50像素
	private ArrayList<Rect> lines = new ArrayList<Rect>();
	
	private int alpha = 255;
	
	public Lightning(int width, int height){
		super(width, height,0,0);
		reset();
	}

	public synchronized void draw(Canvas canvas){
		if(canvas == null){
			return;
		}
//		paint.setAlpha(alpha);
//		paint.setColor(0xffffffff);
		canvas.drawColor((alpha << 24) | (255 << 16) | (255 << 8) | 255);
		paint.setARGB(alpha, 255, 125, 0);
		for(Rect rect : lines){
			canvas.drawLine(rect.left, rect.top, rect.right, rect.bottom, paint);
		}
	}
	
	public synchronized void reset(){
		int x = rand.nextInt((int)m_fWidth);
		int y = rand.nextInt((int)m_fHeight / 2);
		int w = 0;
		int h = 0;
		lines.clear();
		while(x < m_fWidth && y < m_fHeight){
			Rect rect = new Rect();
			
			rect.left = x;
			rect.top = y;
			w = minSize + rand.nextInt(maxSize);
			h = minSize + rand.nextInt(maxSize);
			w *= rand.nextBoolean() ? 1 : -1;
			x += w;
			y += h;
			
			rect.right = x;
			rect.bottom = y;
			lines.add(rect);
			//加入闪电节点分出的闪电
			int len = rand.nextInt(6);
			for(int i = 0; i < len; i ++){
				Rect r = new Rect();
				r.left = x;
				r.top = y;
				w = rand.nextInt(maxSize);
				h = minSize + rand.nextInt(maxSize);
				w *= rand.nextBoolean() ? 1 : -1;
				
				r.right = x + w;
				r.bottom = y + h;
				lines.add(r);
			}
			
		}
		alpha = 255;
		Log.w(TAG, "light is reset");
	}
	
	public void move(){
		alpha -= 30;
		if(alpha < 0){
			alpha = 0;
		}
	}
	
}
