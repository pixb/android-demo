package com.pix.anim.particle;


public class SnowSet extends ParticleSet {

	public SnowSet(float width, float height, float offsetX, float offsetY, int itemNum) {
		super(width, height, offsetX, offsetY, itemNum);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initEffectSet() {
		// TODO Auto-generated method stub
		for(int i = 0; i < itemNum; i ++){
			list.add(new SnowItem(m_fWidth, m_fHeight,m_fOffsetX,m_fOffsetY));
		}	
	}


}
