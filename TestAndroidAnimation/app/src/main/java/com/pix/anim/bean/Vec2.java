package com.pix.anim.bean;

/**
 * ***********************************************************************************
* Module Name: GG矢量类</br>
* File Name: <b>Vec2.java</b></br>
* Description: 描述两方向矢量</br>
* Author: TPX</br>
* 版权 2008-2015，金华长风信息技术有限公司</br>
* 所有版权保护
* 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
**************************************************************************************
 */
public class Vec2 {
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public float x;
	public float y;
	
	public static Vec2 create(float x,float y) {
		return new Vec2(x,y);
	}
}
