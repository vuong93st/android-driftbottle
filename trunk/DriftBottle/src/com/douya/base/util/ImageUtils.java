/**
 * @(#)com.douya.base.util.ImageUtils.java
 * 版权声明 山东益信通科贸有限公司, 版权所有 违者必究
 *
 *<br> Copyright:Copyright (c) 2010-2011
 *<br> Company： 山东益信
 *<br> Author： 葛云杰(geyj@douya.cn)
 *<br> Date：2011-09-17
 *<br> Version：1.0
 */
package com.douya.base.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;

/**
 * 图片工具类
 * @author GeYunjie
 *
 */
public class ImageUtils {
	private static final String TAG = "ImageUtil";

	public static InputStream getRequest(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		if (conn.getResponseCode() == 200) {
			return conn.getInputStream();
		}
		return null;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	public static Drawable loadImageFromUrl(String url) {
		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(i, "src");
		return d;
	}

	public static Drawable getDrawableFromUrl(String url) throws Exception {
		return Drawable.createFromStream(getRequest(url), null);
	}

	public static Bitmap getBitmapFromUrl(String url) throws Exception {
		byte[] bytes = getBytesFromUrl(url);
		return byteToBitmap(bytes);
	}

	public static Bitmap getRoundBitmapFromUrl(String url, int pixels)
			throws Exception {
		byte[] bytes = getBytesFromUrl(url);
		Bitmap bitmap = byteToBitmap(bytes);
		return getRoundedCornerBitmap(bitmap, pixels);
	}

	public static Drawable geRoundDrawableFromUrl(String url, int pixels)
			throws Exception {
		byte[] bytes = getBytesFromUrl(url);
		BitmapDrawable bitmapDrawable = (BitmapDrawable) byteToDrawable(bytes);
		return toRoundCorner(bitmapDrawable, pixels);
	}

	public static byte[] getBytesFromUrl(String url) throws Exception {
		return readInputStream(getRequest(url));
	}

	public static Bitmap byteToBitmap(byte[] byteArray) {
		if (byteArray.length == 0) {
			return null;
		}
		return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	}

	public static Drawable byteToDrawable(byte[] byteArray) {
		ByteArrayInputStream ins = new ByteArrayInputStream(byteArray);
		return Drawable.createFromStream(ins, null);
	}

	public static byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/**
	 * 去色同时加圆角
	 * 
	 * @param bmpOriginal
	 *            原图
	 * @param pixels
	 *            圆角弧度
	 * @return 修改后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
		return getRoundedCornerBitmap(toGrayscale(bmpOriginal), pixels);
	}

	/**
	 * 把图片变成圆角
	 * 
	 * @param bitmap
	 *            需要修改的图片
	 * @param pixels
	 *            圆角的弧度
	 * @return 圆角图片
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		// 得到与图像相同大小的区域 由构造的四个值决定区域的位置以及大小
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		// 值越大角度越明显
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		// drawRoundRect的第2,3个参数一样则画的是正圆的一角,如果数值不同则是椭圆的一角
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 获得带倒影的图片方法
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}

	/**
	 * 使圆角功能支持BitampDrawable
	 * 
	 * @param bitmapDrawable
	 * @param pixels
	 * @return
	 */
	public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
			int pixels) {
		Bitmap bitmap = bitmapDrawable.getBitmap();
		bitmapDrawable = new BitmapDrawable(getRoundedCornerBitmap(bitmap,
				pixels));
		return bitmapDrawable;
	}

	/**
	 * 将多个Bitmap合并成一个图片。
	 * 
	 * @param int 将多个图合成多少列
	 * @param Bitmap
	 *            ... 要合成的图片
	 * @return
	 */
	public static Bitmap combineBitmaps(int columns, Bitmap... bitmaps) {
		if (columns <= 0 || bitmaps == null || bitmaps.length == 0) {
			throw new IllegalArgumentException(
					"Wrong parameters: columns must > 0 and bitmaps.length must > 0.");
		}
		int maxWidthPerImage = 0;
		int maxHeightPerImage = 0;
		for (Bitmap b : bitmaps) {
			maxWidthPerImage = maxWidthPerImage > b.getWidth() ? maxWidthPerImage
					: b.getWidth();
			maxHeightPerImage = maxHeightPerImage > b.getHeight() ? maxHeightPerImage
					: b.getHeight();
		}
		int rows = 0;
		if (columns >= bitmaps.length) {
			rows = 1;
			columns = bitmaps.length;
		} else {
			rows = bitmaps.length % columns == 0 ? bitmaps.length / columns
					: bitmaps.length / columns + 1;
		}
		Bitmap newBitmap = Bitmap.createBitmap(columns * maxWidthPerImage, rows
				* maxHeightPerImage, Config.RGB_565);

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {
				int index = x * columns + y;
				if (index >= bitmaps.length)
					break;
				newBitmap = mixtureBitmap(newBitmap, bitmaps[index],
						new PointF(y * maxWidthPerImage, x * maxHeightPerImage));
			}
		}
		return newBitmap;
	}

	/**
	 * Mix two Bitmap as one.
	 * 
	 * @param bitmapOne
	 * @param bitmapTwo
	 * @param point
	 *            where the second bitmap is painted.
	 * @return
	 */
	public static Bitmap mixtureBitmap(Bitmap first, Bitmap second,
			PointF fromPoint) {
		if (first == null || second == null || fromPoint == null) {
			return null;
		}
		Bitmap newBitmap = Bitmap.createBitmap(first.getWidth(),
				first.getHeight(), Config.ARGB_4444);
		Canvas cv = new Canvas(newBitmap);
		cv.drawBitmap(first, 0, 0, null);
		cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
		cv.save(Canvas.ALL_SAVE_FLAG);
		cv.restore();
		return newBitmap;
	}

	/**
	 * 可用于生成缩略图。 Creates a centered bitmap of the desired size. Recycles the
	 * input.
	 * 
	 * @param source
	 */
	public static Bitmap extractMiniThumb(Bitmap source, int width, int height) {
		return extractMiniThumb(source, width, height, true);
	}

	public static Bitmap extractMiniThumb(Bitmap source, int width, int height,
			boolean recycle) {
		if (source == null) {
			return null;
		}

		float scale;
		if (source.getWidth() < source.getHeight()) {
			scale = width / (float) source.getWidth();
		} else {
			scale = height / (float) source.getHeight();
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scale, scale);
		Bitmap miniThumbnail = transform(matrix, source, width, height, false);

		if (recycle && miniThumbnail != source) {
			source.recycle();
		}
		return miniThumbnail;
	}

	public static Bitmap transform(Matrix scaler, Bitmap source,
			int targetWidth, int targetHeight, boolean scaleUp) {
		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			/*
			 * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
					Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
					+ Math.min(targetWidth, source.getWidth()), deltaYHalf
					+ Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
					- dstY);
			c.drawBitmap(source, src, dst, null);
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}

		Bitmap b1;
		if (scaler != null) {
			// this is used for minithumb and crop, so we want to filter here.
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
					source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}

		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);

		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
				targetHeight);

		if (b1 != source) {
			b1.recycle();
		}

		return b2;
	}

	/**
	 * TODO: 图片剪切. TODO: 填入方法说明.
	 * 
	 * @param mBitmap
	 * @param r
	 * @param config
	 * @return
	 */
	public static Bitmap cutBitmap(Bitmap mBitmap, Rect r, Bitmap.Config config) {
		int width = r.width();
		int height = r.height();

		Bitmap croppedImage = Bitmap.createBitmap(width, height, config);

		Canvas cvs = new Canvas(croppedImage);
		Rect dr = new Rect(0, 0, width, height);
		cvs.drawBitmap(mBitmap, r, dr, null);
		return croppedImage;
	}

	/**
	 * TODO: 旋转图片. TODO: Rotates the bitmap by the specified degree.If a new
	 * bitmap is created, the original bitmap is recycled.
	 * 
	 * @param b
	 * @param degrees
	 * @return
	 */
	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();

			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				ex.printStackTrace();
				// We have no memory to rotate. Return the original bitmap.
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return b;
	}

	/**
	 * TODO: 缩放.
	 * 
	 * @param bitmap
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Drawable scaleImage(Bitmap bitmap, int newWidth, int newHeight) {
		// load the origial Bitmap
		Bitmap BitmapOrg = bitmap;

		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();

		// calculate the scale
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the Bitmap
		matrix.postScale(scaleWidth, scaleHeight);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
				height, matrix, true);

		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return new BitmapDrawable(resizedBitmap);
	}

	/**
	 * Save Bitmap to a file.保存图片到SD卡。
	 * 
	 * @param bitmap
	 * @param file
	 * @return error message if the saving is failed. null if the saving is
	 *         successful.
	 * @throws IOException
	 */
	public static void saveBitmapToFile(Bitmap bitmap, String _file)
			throws IOException {
		BufferedOutputStream os = null;
		try {
			File file = new File(_file);
			// String _filePath_file.replace(File.separatorChar +
			// file.getName(), "");
			int end = _file.lastIndexOf(File.separator);
			String _filePath = _file.substring(0, end);
			File filePath = new File(_filePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			file.createNewFile();
			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 图片合成
	 * 
	 * @param bitmap
	 * @return
	 */
	public Bitmap createBitmap(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0,0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	/**
	 * 
	 * TODO: 图片缩放. TODO: 获得原bitmap,再从原bitmap的基础上生成新图片,这样效率很低.
	 * 
	 * @param drawable
	 * @param w
	 * @param h
	 * @return
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(null, newbmp);
	}

	/**
	 * 
	 * TODO: 只是对bitmap加载到内存一次,所以效率比较高,解析速度快. TODO: 填入方法说明.
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Drawable zoomDrawable(String path, int width, int height) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 该值设为true那么将不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息
		opts.inJustDecodeBounds = true;
		// 当opts不为null时,但decodeFile返回空,不为图片分配内存,只获取图片的大小,并保存在opts的outWidth和outHeight
		BitmapFactory.decodeFile(path, opts);

		opts.inDither = false;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		int srcWidth = opts.outWidth;
		int srcHeight = opts.outHeight;
		int destWidth = 0;
		int destHeight = 0;
		// 缩放的比例
		double ratio = 0.0;
		// 按比例计算缩放后的图片大小,maxLength是长或宽允许的最大长度
		if (srcWidth > srcHeight) {
			ratio = srcWidth / width;
			destWidth = width;
			destHeight = (int) (srcHeight / ratio);
		} else {
			ratio = srcHeight / height;
			destHeight = height;
			destWidth = (int) (srcWidth / ratio);
		}

		// 缩放的比例,缩放是很难按准备的比例进行缩放的,目前我只发现只能通过inSampleSize来进行缩放,其值表明缩放的倍数,SDK中建议其值是2的指数值
		opts.inSampleSize = (int) ratio + 1;
		// inJustDecodeBounds设为false表示把图片读进内存中
		opts.inJustDecodeBounds = false;
		// 设置大小,这个一般是不准确的,是以inSampleSize的为准,但是如果不设置却不能缩放
		opts.outHeight = destHeight;
		opts.outWidth = destWidth;
		// 获取缩放后图片
		Bitmap destBm = BitmapFactory.decodeFile(path, opts);
		return new BitmapDrawable(destBm);
	}

	/**
	 * 
	 * 根据视频Uri地址取得指定的视频缩略图
	 * 
	 * @param cr
	 * 
	 * @param uri
	 *            本地视频Uri标示
	 * 
	 * @return 返回bitmap类型数据
	 */
	public static Bitmap getVideoThumbnail(ContentResolver cr, Uri uri) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Cursor cursor = cr.query(uri,
				new String[] { MediaStore.Video.Media._ID }, null, null, null);

		if (cursor == null || cursor.getCount() == 0) {
			return null;
		}
		cursor.moveToFirst();
		String videoId = cursor.getString(cursor
				.getColumnIndex(MediaStore.Video.Media._ID)); // image id in
																// image table.s

		if (videoId == null) {
			return null;
		}
		cursor.close();
		long videoIdLong = Long.parseLong(videoId);
		bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoIdLong,
				Images.Thumbnails.MICRO_KIND, options);

		return bitmap;
	}

	/**
	 * 
	 * TODO: 图片加水印. TODO: 填入方法说明.
	 * 
	 * @param src
	 * @param watermark
	 * @return
	 */
	public static Bitmap watermark(Bitmap src, Bitmap watermark) {
		Log.i(TAG, "watermark");
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int width = watermark.getWidth();
		int height = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(watermark, w - width - 6, h - height - 2, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}
}