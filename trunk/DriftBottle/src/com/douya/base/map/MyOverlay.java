package com.douya.base.map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.autonavi.mapapi.GeoPoint;
import com.autonavi.mapapi.MapView;
import com.autonavi.mapapi.Overlay;
import com.autonavi.mapapi.Projection;

public class MyOverlay extends Overlay {
	private final int mRadius = 5;
	private GeoPoint point;
	private Drawable marker;
	private String markerTip;

	public MyOverlay(GeoPoint point, Drawable marker,String markerTip) {
		this.point = point;
		this.marker = marker;
		this.markerTip = markerTip;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		Projection projection = mapView.getProjection();

		if (shadow == false) {

			// 把位置转换为屏幕像素
			Point screenpoint = new Point();
			projection.toPixels(point, screenpoint);

			RectF oval = new RectF(screenpoint.x - mRadius, screenpoint.y
					- mRadius, screenpoint.x + mRadius, screenpoint.y + mRadius);

			// 设置paint
			Paint paint = new Paint();
			paint.setARGB(250, 255, 0, 0);
			paint.setAntiAlias(true);
			paint.setFakeBoldText(true);


			Paint backPaint = new Paint();
			backPaint.setARGB(175, 50, 50, 50);
			backPaint.setAntiAlias(true);

			RectF backRect = new RectF(screenpoint.x + 2 + mRadius,
					screenpoint.y - 3 * mRadius, screenpoint.x + 65,
					screenpoint.y + mRadius);

			// 绘制标记
			// canvas.drawOval(oval, paint);
			//canvas.drawRoundRect(backRect, 5, 5, backPaint);
			canvas.drawBitmap(((BitmapDrawable) marker).getBitmap(),
					screenpoint.x, screenpoint.y + 2 * mRadius, backPaint);

			canvas.drawText(markerTip, screenpoint.x, screenpoint.y, paint);
		}
		super.draw(canvas, mapView, shadow);
	}

	@Override
	public boolean onTap(GeoPoint point, MapView mapView) {
		return false;
	}

}
