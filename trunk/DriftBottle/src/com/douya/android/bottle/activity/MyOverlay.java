package com.douya.android.bottle.activity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.autonavi.mapapi.GeoPoint;
import com.autonavi.mapapi.MapView;
import com.autonavi.mapapi.Overlay;
import com.autonavi.mapapi.Projection;
 
public class MyOverlay extends Overlay { 
	private final int mRadius = 5;
	private Location location;
	private Drawable marker;
	public MyOverlay(Location location,Drawable marker){
		this.location = location;
		this.marker = marker;
	}
	@Override  
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {  
	  Projection projection = mapView.getProjection();  
	 
	  if (shadow == false) {  
	    //获得当前的位置  
	    Double latitude = location.getLatitude()*1E6;  
	    Double longitude = location.getLongitude()*1E6;  
	    GeoPoint geoPoint;  
	    geoPoint = new GeoPoint(latitude.intValue(),longitude.intValue());  
	 
	// 把位置转换为屏幕像素  
	    Point point = new Point();  
	    projection.toPixels(geoPoint, point);  
	 
	    RectF oval = new RectF(point.x - mRadius, point.y - mRadius,  
	                           point.x + mRadius, point.y + mRadius);  
	 
	    // 设置paint  
	    Paint paint = new Paint();  
	    paint.setARGB(250, 255, 0, 0);  
	    paint.setAntiAlias(true);  
	    paint.setFakeBoldText(true);  
	 
	    Paint backPaint = new Paint();  
	    backPaint.setARGB(175, 50, 50, 50);  
	    backPaint.setAntiAlias(true);  
	 
	    RectF backRect = new RectF(point.x + 2 + mRadius,  
	                               point.y - 3*mRadius,  
	                               point.x + 65, point.y + mRadius);  
	 
	    // 绘制标记  
	    //canvas.drawOval(oval, paint);  
	    canvas.drawRoundRect(backRect, 5, 5, backPaint);  
	    canvas.drawBitmap(((BitmapDrawable)marker).getBitmap(), point.x, point.y, backPaint);
	    canvas.drawText("普通瓶", point.x + 2 * mRadius, point.y, paint);  
	  }  
	  super.draw(canvas, mapView, shadow);  
	} 

 
	 
	  @Override  
	  public boolean onTap(GeoPoint point, MapView mapView) {  
	    return false;  
	  }  


 
} 

