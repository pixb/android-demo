package com.pix.anim.particle;

import java.util.Random;

/**
 * 效果基础类，表示一中效果，粒子中的效果对象
 * @author lxf
 *
 */
public abstract class ParticleItem implements EffectBase{
	/**
	 * 显示区域的宽度
	 */
	protected float m_fWidth;
	/**
	 * 显示区域的高度
	 */
	protected float m_fHeight;
	
	//绘制的偏移量
	protected float m_fOffsetX  = 0f;
	protected float m_fOffsetY = 0f;
	
	/**
	 * 效果元素的随机对象
	 */
	protected Random rand;
	
	public ParticleItem(float width, float height,float offsetX,float offsetY){
		this.m_fWidth = width;
		this.m_fHeight = height;
		this.m_fOffsetX = offsetX ;
		this.m_fOffsetY = offsetY ;
		rand =new Random();
	}
}
