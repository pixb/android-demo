package com.pix.anim.particle;

import android.graphics.Bitmap;


public class RoseSet extends ParticleSet {

	public RoseSet(float width, float height, float offsetX, float offsetY, int itemNum, Bitmap bitmap) {
		super(width, height, offsetX, offsetY, itemNum, bitmap);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initEffectSet() {
		// TODO Auto-generated method stub
		for (int i = 0 ;i < itemNum;i++) {
			list.add(new RoseItem(m_fWidth, m_fHeight, m_fOffsetX, m_fOffsetY, mBitmap));
		}
	}

}
