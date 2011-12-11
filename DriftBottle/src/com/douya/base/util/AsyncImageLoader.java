package com.douya.base.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncImageLoader {
	// SoftReference是软引用,是为了更好的为了系统回收变量
	private static HashMap<String, SoftReference<Drawable>> imageCache;

	static {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public AsyncImageLoader() {

	}

	public Drawable loadDrawable(final String imageUrl,
			final ImageView imageView, final ImageCallback imageCallback) {
		if (imageCache.containsKey(imageUrl)) {
			// 从缓存中获取
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageView,
						imageUrl);
			}
		};
		// 建立新一个新的线程下载图片
		new DownloadThread(handler, imageUrl).start();
		return null;
	}

	class DownloadThread extends Thread {
		private String imageUrl;
		private Handler handler;

		public DownloadThread(Handler handler, String imageUrl) {
			this.imageUrl = imageUrl;
			this.handler = handler;
		}

		@Override
		public void run() {
			Drawable drawable = null;
			try {
				drawable = ImageUtils.geRoundDrawableFromUrl(imageUrl, 20);
			} catch (Exception e) {
				e.printStackTrace();
			}
			imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
			Message message = handler.obtainMessage(0, drawable);
			handler.sendMessage(message);
		}
	}

	// 回调接口
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, ImageView imageView,
				String imageUrl);
	}
}