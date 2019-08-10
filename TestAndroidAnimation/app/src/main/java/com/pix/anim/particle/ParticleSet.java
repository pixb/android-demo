package com.pix.anim.particle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;


/**
 * 粒子集合类
 * 对多个粒子的描述
 * @author lxf
 *
 */
public abstract class ParticleSet {

	protected int itemNum = 0;//效果元素数量
	//效果场景宽高
	protected float m_fWidth;
	protected float m_fHeight;
	protected float m_fOffsetX = 0;
	protected float m_fOffsetY = 0;
	//如果有图片的需要此属性
	protected Bitmap mBitmap;
	//效果容器
	protected ArrayList<ParticleItem> list = new ArrayList<ParticleItem>();
	
	/**
	 * 效果场景构造
	 * @param width		显示区域宽
	 * @param height	显示区域宽
	 * @param itemNum	显示区域元素数量
	 */
	public ParticleSet(float width, float height,float offsetX ,float offsetY ,int itemNum){
		this.m_fWidth = width;
		this.m_fHeight = height;
		this.m_fOffsetX = offsetX;
		this.m_fOffsetY = offsetY ;
		this.itemNum = itemNum;
		initEffectSet();
	}
	
	public ParticleSet(float width, float height, float offsetX ,float offsetY , int itemNum,Bitmap bitmap){
		this.m_fWidth = width;
		this.m_fHeight = height;
		this.m_fOffsetX = offsetX;
		this.m_fOffsetY = offsetY ;
		this.itemNum = itemNum;
		mBitmap = bitmap;
		initEffectSet();
	}
	
	/**
	 *初始化粒子的集合
	 */
	protected abstract void initEffectSet();
	
	/**
	 * 绘制出所有的粒子
	 * @param canvas
	 */
	public void draw(Canvas canvas){
		if(list.size() == 0){
			throw new RuntimeException("请初在initScence的方法中加入效果元素!");
		}
		for(EffectBase item : list){
			item.draw(canvas);
		}
	}
	/**
	 * 所有粒子进行变化
	 */
	public void move(){
		if(list.size() == 0){
			throw new RuntimeException("请初在initScence的方法中加入效果元素!");
		}
		for(EffectBase item : list){
			item.move();
		}
	}
	
	public void setOffset(float offsetX,float offsetY) {
		this.m_fOffsetX = offsetX ;
		this.m_fOffsetY = offsetY ;
	}
}
