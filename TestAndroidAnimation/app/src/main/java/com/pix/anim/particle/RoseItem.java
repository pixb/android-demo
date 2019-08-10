package com.pix.anim.particle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * ***********************************************************************************
* Module Name: 玫瑰花粒子元素类</br>
* File Name: <b>RoseItem.java</b></br>
* Description: 描述一个玫瑰花的粒子</br>
* Author: TPX</br>
* 版权 2008-2015，金华长风信息技术有限公司</br>
* 所有版权保护
* 这是金华长风信息技术有限公司未公开的私有源代码, 本文件及相关内容未经金华长风信息技术有限公司
* 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
**************************************************************************************
 */
public class RoseItem extends ParticleItem {
	
	/** 玫瑰的Bitmap **/
	private Bitmap mRoseBitmap ;
	/** 玫瑰的x坐标 **/
	private float m_fX;
	/** 玫瑰的y坐标 **/
	private float m_fY ;
	/** 绘制的画笔*/
	private Paint mPaint ;
	/** 绘制的矩阵变化 */
	private Matrix mMatrix;
	/** 透明度值 */
	private int m_iAlpha = 255;
	/** 向X轴移动的距离*/
	private float m_fTransX;
	/** 向Y轴移动的距离*/
	private float m_fTransY ;
	/** X周的缩放值 */
	private float m_fScaleX = 1f;
	/** Y周的缩放值*/
	private float m_fScaleY = 1f;
	/** 位移变换的X轴的系数 */
	private float m_fTransCoeX = 0;
	/** 位移变换的Y周的系数*/
	private float m_fTransCoeY = 0;

	public RoseItem(float width, float height, float offsetX, float offsetY,Bitmap bm) {
		super(width, height, offsetX, offsetY);
		// TODO Auto-generated constructor stub
		this.mRoseBitmap = bm ;
		init();
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(canvas == null || mRoseBitmap == null) {
			return ;
		}
		mPaint.setAlpha(m_iAlpha);
		mMatrix.setTranslate(m_fX + m_fOffsetX -20, m_fY + m_fOffsetY - 40 );
		mMatrix.postScale(m_fScaleX, m_fScaleY);
		canvas.drawBitmap(mRoseBitmap, mMatrix, mPaint);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		m_iAlpha -= 10;
		if(m_iAlpha <= 0) {
			m_iAlpha = 255;
			init();
		}
		m_fX += m_fTransCoeX;
		m_fY += m_fTransCoeX;
//		m_fScaleX -= 0.005f;
//		m_fScaleY = m_fScaleX;
		
	}
	/**
	 * 初始化方法
	 * TODO 在创建这个粒子的时候初始化粒子生成的位置及大小
	 * @return: void
	 */
	private void init() {
		mPaint = new Paint();
		mMatrix = new Matrix();
		m_fX = rand.nextInt((int) m_fWidth) ;
		m_fY = rand.nextInt((int) m_fHeight) ;
//		m_fScaleX = rand.nextFloat();
		int scaleTmp = 30 + rand.nextInt(30);
		//m_fScaleX = m_fScaleY = scaleTmp / 100.00f;
		m_iAlpha = 180 + rand.nextInt(75);
		m_fTransCoeX = getTransCoe();
		m_fTransCoeY = getTransCoe();
//		if(m_fScaleX <= 0.7f) {
//			m_fScaleX = (m_fScaleX + 0.5f) < 1  ? m_fScaleX + 0.5f : m_fOffsetX;
//		}
//		m_fScaleY = m_fScaleX;
	}
	
	/**
	 * 取得移动的系数
	 * 随机值-2 ~ 2 的浮点数
	 * TODO
	 * @return
	 * @return: float
	 */
	private float getTransCoe() {
		int iCoe = 40 - rand.nextInt(60);
		float fCoe = iCoe / 10.00f;
		return fCoe;
	}
	
	
	


}
