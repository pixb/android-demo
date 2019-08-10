package com.pix.anim.particle;


public class SnowSet2 extends ParticleSet {

	public SnowSet2(float width, float height, float offsetX, float offsetY, int itemNum) {
		super(width, height, offsetX, offsetY, itemNum);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initEffectSet() {
		// TODO Auto-generated method stub
		for(int i = 0; i < itemNum; i ++){
			list.add(new SnowItem2(m_fWidth, m_fHeight,0,0));
		}	
	}


}
