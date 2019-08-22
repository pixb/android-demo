package com.pix.testmyview.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class AnimCache {

	private int cacheMemory = 4 * 1024 * 1024;
	private final LruCache<String, Bitmap> sHardBitmapCache = new LruCache<String, Bitmap>(
			cacheMemory) {
		@Override
		public int sizeOf(String key, Bitmap value) {
			return value.getRowBytes() * value.getHeight();
		}

		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			// 硬引用缓存区满，将一个最不经常使用的oldvalue推入到软引用缓存区
			sHardBitmapCache.remove(key);
		}
	};

	// 缓存bitmap
	public boolean putBitmap(String key, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (sHardBitmapCache) {
				sHardBitmapCache.put(key, bitmap);
			}
			return true;
		}
		return false;
	}

	// 从缓存中获取bitmap
	public Bitmap getBitmap(String key) {
		synchronized (sHardBitmapCache) {
			final Bitmap bitmap = sHardBitmapCache.get(key);
			if (bitmap != null)
				return bitmap;
		}
		return null;
	}
}
