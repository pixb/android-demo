package com.pix.testandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;

import java.io.IOException;

/**
 * <p>
 * Copyright: Copyright (c) 2016
 * </p>
 * <p/>
 * <p>
 * Company: 浙江齐聚科技有限公司<a href="www.guagua.cn">www.guagua.cn</a>
 * </p>
 * 
 * @author tpx
 * @version 1.0.0
 * @description 加载图片的类
 * @modify
 */
public class AnimBitmapLoader {

	private AnimCache animCache;
	private static AnimBitmapLoader instance;
	Options opts = new Options();

	private AnimBitmapLoader() {
		animCache = new AnimCache();
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		opts.inJustDecodeBounds = true;
	}

	public static AnimBitmapLoader getInstance() {
		if (instance == null) {
			instance = new AnimBitmapLoader();
		}
		return instance;
	}

	/**
	 * 获取图片bitmap
	 * 
	 * @param context
	 * @param path
	 *            assets下的路径
	 * @param opt
	 *            缩放的比例
	 * @return
	 */
	public Bitmap getBitmap(Context context, String path, float opt) {
		// 这里暂时不用缓存了，因为实时对图片进行了释放处理，减小实时内存
		// Bitmap bitmap = animCache.getBitmap(path);
		Bitmap bitmap = null;
		if (bitmap != null) {
			return bitmap;
		}
		try {
			bitmap = decodeBitmap(context, path, opt);
		} catch (IOException e) {
			return null;
		} catch (OutOfMemoryError e) {
			try {
				bitmap = decodeBitmap(context, path, opt);
			} catch (OutOfMemoryError oe) {
				System.gc();
			} catch (Exception e1) {

			}
		}
		// if(bitmap != null){
		// animCache.putBitmap(path, bitmap);
		// }
		return bitmap;
	}

	private Bitmap decodeBitmap(Context context, String path, float opt)
			throws IOException, OutOfMemoryError {
		Bitmap bitmap = null;
		try {

			if (opt < 1) {
				// BitmapFactory.Options options = new Options();
				// options.inSampleSize = 2;
				// bitmap = BitmapFactory.decodeStream(
				// context.getAssets().open(path), null, options);
				// bitmap = Bitmap.createScaledBitmap(bitmap,
				// (int)(opt * bitmap.getWidth() * 2), ((int)opt *
				// bitmap.getHeight() * 2),
				// false);

				bitmap = BitmapFactory.decodeStream(
						context.getAssets().open(path), null, opts);

				Matrix matrix = new Matrix();
				matrix.setScale(opt, opt);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, false);

			} else if (opt > 1) {
				bitmap = BitmapFactory.decodeStream(context.getAssets().open(
						path));
				// bitmap = Bitmap.createScaledBitmap(bitmap,
				// (int)(opt * bitmap.getWidth()), ((int)opt *
				// bitmap.getHeight()),
				// false);
				Matrix matrix = new Matrix();
				matrix.setScale(opt, opt);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, false);
			} else {

				bitmap = BitmapFactory.decodeStream(context.getAssets().open(
						path));
			}

		} catch (IOException e) {
			throw new IOException();
		} catch (OutOfMemoryError e) {
			System.gc();
			throw new OutOfMemoryError();
		}
		return bitmap;
	}

	/**
	 * 获取图片bitmap
	 * 
	 * @param context
	 * @param path
	 *            assets下的路径
	 * @return
	 */
	public Bitmap getBitmap(Context context, String path, float width,
			float heigh) {
		Bitmap bitmap = animCache.getBitmap(path);
		if (bitmap != null) {
			return bitmap;
		}
		try {
			bitmap = decodeBitmap(context, path, width, heigh);
		} catch (IOException e) {
			return null;
		} catch (OutOfMemoryError e) {
			try {
				bitmap = decodeBitmap(context, path, width, heigh);
			} catch (OutOfMemoryError oe) {
				System.gc();
			} catch (Exception e1) {

			}
		}
		if (bitmap != null) {
			animCache.putBitmap(path, bitmap);
		}
		return bitmap;
	}

	private Bitmap decodeBitmap(Context context, String path, float width,
			float heigh) throws IOException, OutOfMemoryError {
		Bitmap bitmap = null;
		try {

			bitmap = BitmapFactory.decodeStream(context.getAssets().open(path));
			bitmap = Bitmap.createScaledBitmap(bitmap, (int) width,
					((int) heigh), false);

		} catch (OutOfMemoryError e) {
			System.gc();
			throw new OutOfMemoryError();
		}
		return bitmap;
	}

	/**
	 * 从drawable-xhdpi读取图片，方式565减小图片占用内存的大小
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public Bitmap readBitmap(Context context, int resId) {
		try {
			Options opts = new Options();
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			return BitmapFactory.decodeResource(context.getResources(), resId,
					opts);
		} catch (OutOfMemoryError e) {
			System.gc();
		}
		return null;
	}

}
