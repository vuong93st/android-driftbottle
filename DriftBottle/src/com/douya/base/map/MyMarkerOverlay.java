package com.douya.base.map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.autonavi.mapapi.GeoPoint;
import com.autonavi.mapapi.MapView;
import com.autonavi.mapapi.Overlay;
import com.autonavi.mapapi.Projection;

/**
 * 绘制位置图标、文本
 * @author GeYunjie
 *
 */
public class MyMarkerOverlay extends Overlay {
	private GeoPoint[] points;
	private Drawable marker;
	private String[] markerTips;

	public MyMarkerOverlay(GeoPoint point, Drawable marker) {
		this.points = new GeoPoint[1];
		this.points[0] = point;
		this.marker = marker;
	}
	
	public MyMarkerOverlay(GeoPoint point, String markerTip) {
		this.points = new GeoPoint[1];
		this.points[0] = point;
		this.markerTips = new String[1];
		this.markerTips[0] = markerTip;
	}
	
	public MyMarkerOverlay(GeoPoint point, Drawable marker,String markerTip) {
		this.points = new GeoPoint[1];
		this.points[0] = point;
		this.marker = marker;
		this.markerTips = new String[1];
		this.markerTips[0] = markerTip;
	}
	
	public MyMarkerOverlay(GeoPoint[] points, Drawable marker,String[] markerTips) {
		this.points = points;
		this.marker = marker;
		this.markerTips = markerTips;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		Projection projection = mapView.getProjection();

		if (shadow == false) {

			// 设置paint
			Paint paint = new Paint();
			paint.setARGB(250, 255, 0, 0);
			paint.setAntiAlias(true);
			paint.setFakeBoldText(true);
			
			//画图标
			for(int i=0;i<points.length;i++){
				Point screenpoints = new Point();
				projection.toPixels(points[i], screenpoints);
				canvas.drawBitmap(((BitmapDrawable) marker).getBitmap(),
						screenpoints.x-20, screenpoints.y-20, paint);
				if(null != markerTips && markerTips.length>i && null != markerTips[i]){
					canvas.drawText(markerTips[i], screenpoints.x, screenpoints.y, paint);
				}
			}
		}
		super.draw(canvas, mapView, shadow);
	}

	@Override
	public boolean onTap(GeoPoint point, MapView mapView) {
		return false;
	}

}
