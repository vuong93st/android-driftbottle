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
 * 绘制线段
 * @author GeYunjie
 *
 */
public class MyLineOverlay extends Overlay {
	private GeoPoint[] geoPoints;
	private Drawable[] markers;
	private String[] markerTips;
	private Drawable marker;

	/**
	 * 绘制线段构造器
	 * @param geoPoints 位置点数组
	 */
	public MyLineOverlay(GeoPoint[] geoPoints) {
		this.geoPoints = geoPoints;
	}
	/**
	 * 绘制线段构造器
	 * @param geoPoints 位置点数组
	 * @param markerTips 位置文本数组
	 */
	public MyLineOverlay(GeoPoint[] geoPoints,String[] markerTips) {
		this.geoPoints = geoPoints;
		this.markerTips = markerTips;
	}
	
	/**
	 * 绘制线段构造器
	 * @param geoPoints 位置点数组
	 * @param markers 图标数组
	 */
	public MyLineOverlay(GeoPoint[] geoPoints,Drawable[] markers) {
		this.geoPoints = geoPoints;
		this.markers = markers;
	}
	/**
	 * 绘制线段构造器
	 * @param geoPoints 位置点数组
	 * @param marker 图标
	 */
	public MyLineOverlay(GeoPoint[] geoPoints,Drawable marker) {
		this.geoPoints = geoPoints;
		this.marker = marker;
	}
	/**
	 * 绘制线段构造器
	 * @param geoPoints 位置点数组
	 * @param markers 图标数组
	 * @param markerTips 提示文字数组
	 */
	public MyLineOverlay(GeoPoint[] geoPoints,Drawable[] markers,String[] markerTips) {
		this.geoPoints = geoPoints;
		this.markers = markers;
		this.markerTips = markerTips;
	}
	/**
	 * 绘制线段构造器
	 * @param geoPoints 位置点数组
	 * @param marker 图标（所有点用同一个）
	 * @param markerTips 提示文字
	 */
	public MyLineOverlay(GeoPoint[] geoPoints,Drawable marker,String[] markerTips) {
		this.geoPoints = geoPoints;
		this.marker = marker;
		this.markerTips = markerTips;
	}
	
	/**
	 * 绘制线段构造器
	 * @param startPoint 线段开始位置
	 * @param endPoint 线段结束位置
	 */
	public MyLineOverlay(GeoPoint startPoint,GeoPoint endPoint) {
		this.geoPoints = new GeoPoint[2];
		this.geoPoints[0] = startPoint;
		this.geoPoints[1] = endPoint;
	}

	/**
	 * 绘制线段构造器
	 * @param startPoint 线段开始位置
	 * @param endPoint 线段结束位置
	 * @param marker 两端图标
	 */
	public MyLineOverlay(GeoPoint startPoint,GeoPoint endPoint, Drawable marker) {
		this.geoPoints = new GeoPoint[2];
		this.geoPoints[0] = startPoint;
		this.geoPoints[1] = endPoint;
		this.marker = marker;
	}
	
	/**
	 * 绘制线段构造器
	 * @param startPoint 线段开始位置
	 * @param endPoint 线段结束位置
	 * @param startMarker 开始位置图标
	 * @param startMarkerTip 开始位置提示文字
	 * @param endMarker 结束位置图标
	 * @param endMarkerTip 结束位置提示文字
	 */
	public MyLineOverlay(GeoPoint startPoint,GeoPoint endPoint, Drawable startMarker,String startMarkerTip, Drawable endMarker,String endMarkerTip) {
		this.geoPoints = new GeoPoint[2];
		this.geoPoints[0] = startPoint;
		this.geoPoints[1] = endPoint;
		this.markers = new Drawable[2];
		this.markers[0] = startMarker;
		this.markers[1] = endMarker;
		this.markerTips = new String[2];
		this.markerTips[0] = startMarkerTip;
		this.markerTips[1] = endMarkerTip;
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
			paint.setStrokeWidth(2);//设置线宽
			paint.setStrokeCap(Paint.Cap.SQUARE);
			//画图标
			for(int i=0;i<geoPoints.length;i++){
				Point screenpoint = new Point();
				projection.toPixels(geoPoints[i], screenpoint);					
				if(i>0 && null != geoPoints[i]){
					Point oldscreenpoint = new Point();
					projection.toPixels(geoPoints[i-1], oldscreenpoint);	
					canvas.drawLine(oldscreenpoint.x, oldscreenpoint.y,screenpoint.x, screenpoint.y, paint);
				}
				if(null != markers && markers.length>i && null != markers[i]){
					canvas.drawBitmap(((BitmapDrawable) markers[i]).getBitmap(),
							screenpoint.x+2, screenpoint.y, paint);
				}
				if(marker!=null){
					canvas.drawBitmap(((BitmapDrawable) marker).getBitmap(),
							screenpoint.x+2, screenpoint.y, paint);
				}
				if(null != markerTips && markerTips.length>i && null != markerTips[i]){
					canvas.drawText(markerTips[i], screenpoint.x, screenpoint.y, paint);
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
