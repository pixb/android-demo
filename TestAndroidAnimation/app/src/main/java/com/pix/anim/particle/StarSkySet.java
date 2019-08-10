package com.pix.anim.particle;

import android.graphics.Canvas;


/**
 ************************************************************************************
* Module Name: 星空粒子集合类</br>
* File Name: <b>StarSky.java</b></br>
* Description: 用多个星星粒子元素，构建星星粒子集合</br>
* Author: TPX</br>
* 版权 2008-2015，金华长风信息技术有限公司</br>
* 所有版权保护
* 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
**************************************************************************************
 */
public class StarSkySet extends ParticleSet {

	public StarSkySet(float width, float height,float offsetX,float offsetY, int sknowNum){
		super(width, height,offsetX,offsetY, sknowNum);
	}

	protected void initEffectSet() {
		for(int i = 0; i < itemNum; i ++){
			list.add(new StarItem(m_fWidth, m_fHeight,m_fOffsetX,m_fOffsetY));
		}		
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
	}
	
}
