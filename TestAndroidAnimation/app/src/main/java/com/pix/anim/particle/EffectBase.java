package com.pix.anim.particle;

import android.graphics.Canvas;

/**
 * 效果基本功能接口
 * @author lxf
 *
 */
public interface EffectBase {
	/**
	 * 绘制效果
	 * @param canvas
	 */
	public void draw(Canvas canvas);
	
	/**
	 * 效果元素变化
	 */
	public void move();
	
}
