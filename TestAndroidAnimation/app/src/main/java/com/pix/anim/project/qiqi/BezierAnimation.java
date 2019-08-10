package com.pix.anim.project.qiqi;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>Copyright: Copyright (c) 2013</p>
 * 
 * <p>Company: 呱呱视频社区<a href="www.guagua.cn">www.guagua.cn</a></p>
 *
 * @description 贝塞尔曲线位移动画
 *
 *
 * @author Xue Wenchao
 * @modify 
 * @version 1.0.0
 */
public class BezierAnimation extends Animation {
	// 保存动画的关键帧
	private List<PointF> mPointList = new ArrayList<PointF>();

	public BezierAnimation(List<PointF> mPointList) {
		this.setFillAfter(true);
		this.setFillEnabled(true);
		this.setDuration(1000);

		this.mPointList = mPointList;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation transformation) {
		if (mPointList.size() < 1) {
			return;
		}

		Matrix matrix = transformation.getMatrix();
		float t = interpolatedTime;
		float dx = 0;
		float dy = 0;
		int n = mPointList.size();
		PointF point;
		for (int j = 0; j < n; j++) {
			point = mPointList.get(j);
			dx += Math.pow(1 - t, n - 1 - j) * Math.pow(t, j) * C(n - 1, n - 1 - j) * point.x;
			dy += Math.pow(1 - t, n - 1 - j) * Math.pow(t, j) * C(n - 1, n - 1 - j) * point.y;
		}
		matrix.postTranslate(dx, dy);
	}

	public double C(int a, int b) {
		int i, temp;
		int s1 = 1, s2 = 1, s3 = 1;
		if (a < b) {
			i = a;
			a = b;
			b = i;
		}
		temp = b;
		while (temp > 0) {
			s1 *= temp;
			temp--;
		}
		temp = a;
		while (temp > 0) {
			s2 *= temp;
			temp--;
		}
		temp = a - b;
		while (temp > 0) {
			s3 *= temp;
			temp--;
		}
		return s2 / (s1 * s3);
	}

}
