package com.douya.android.bottle.activity;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.widget.Toast;

import com.autonavi.mapapi.GeoPoint;
import com.autonavi.mapapi.Geocoder;
import com.autonavi.mapapi.MapController;
import com.autonavi.mapapi.MapView;
import com.autonavi.mapapi.Overlay;
import com.douya.android.R;
import com.douya.android.bottle.activity.component.AlwaysMarqueeTextView;
import com.douya.android.bottle.activity.nearby.MyOverlay;
import com.douya.android.core.activity.LocationActivity;
import com.douya.android.core.dao.DatabaseHelper;

public class NearbyActivity extends LocationActivity{
	private AlwaysMarqueeTextView weatherTextView = null;
	String weatherCurrent = "";
	String address = "";
	DatabaseHelper dbHelper; 
	SQLiteDatabase sqliteDatabase;
	
	private String TAG = "HIPPO_GEO_DEBUG";
	private MapView mMapView;
	MapController mMapController;
	
	MyOverlay positionOverlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby);
		
		handler.post(updateUIThread);
		
		mMapView = ((MapView) findViewById(R.id.geocodingview));
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		mMapController = mMapView.getController();  // 得到 mMapView 的控制权,可以用它控制和驱动平移和缩放
		initLocation();
	}

	/**
	 * Gps 监听器调用，处理位置信息
	 * 重写位置改变方法，定位当前位置为地图中心
	 */
	@Override
	public void updateWithNewLocation(Location location) {
		// TODO Auto-generated method stub
		super.updateWithNewLocation(location);
		if (location != null) {
			double mLat = location.getLatitude();
			double mLon = location.getLongitude();
			// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
			GeoPoint geo = new GeoPoint((int)(mLat * 1E6),(int)(mLon * 1E6));
			try {
				if (geo.toString() != "") {
					// 更新我的位置标记
					//positionOverlay.setLocation(location);  
					
					//设置地图中心点
					mMapController.animateTo(geo);
					mMapController.setZoom(16);//地图缩放级别
					Drawable marker = getResources().getDrawable(R.drawable.map_bottle);
					marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
					// 添加 MyPositionOverlay  
					positionOverlay = new MyOverlay(location,marker);  
					List<Overlay> overlays = mMapView.getOverlays();  
					overlays.add(positionOverlay);
							
					Geocoder mGeocoder = new Geocoder(NearbyActivity.this);
					int x = geo.getLatitudeE6(); // 得到geo纬度，单位微度 (度 * 1E6)
					double x1 = ((double) x) / 1000000;
					int y = geo.getLongitudeE6(); // 得到geo经度，单位微度 (度 * 1E6)
					double y1 = ((double) y) / 1000000;
					//得到逆理编码，参数分别为：纬度，经度，最大结果集
					List<Address> lstAddress = mGeocoder
							.getFromRawGpsLocation(x1, y1, 1);
						if (lstAddress.size()!=0) {
						//Toast输出geo编码得到的地名
						address="";
						for (int i = 0; i < lstAddress.size(); ++i) {
							Address adsLocation = lstAddress.get(i);
							if(i==0)address+=adsLocation.getFeatureName().toString();
							Toast.makeText(getApplicationContext(),
								           adsLocation.getFeatureName().toString(),
										   Toast.LENGTH_LONG).show();
							Log.i(TAG, "Address found = "+ adsLocation.toString());
						}
					} else {
						Log.i(TAG, "Address GeoPoint NOT Found.");
					}
					handler.post(updateUIThread);//当位置改变后，立即更新天气预报中的位置信息
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "连接错误！",
						Toast.LENGTH_SHORT).show();

			}
		}
	}

	
	Handler handler = new Handler();
	/**
	 * 更新天气界面UI
	 */
	Runnable updateUIThread = new Runnable() {

		public void run() {
			weatherCurrent="当前位置：";//+address+" ";
			// 获取天气预报
			dbHelper = new DatabaseHelper(NearbyActivity.this, "bottle_db"); 
			sqliteDatabase = dbHelper.getReadableDatabase(); 
			Cursor cursor = sqliteDatabase.query("weather", null, null, null, null, null, null);
			if (cursor.moveToNext()) {
				weatherCurrent += cursor.getString(cursor.getColumnIndex("current"));
            }
			cursor.close();
			sqliteDatabase.close();
			weatherTextView = (AlwaysMarqueeTextView) findViewById(R.id.app_weather_content);
			weatherTextView.setText(weatherCurrent);
			weatherTextView.setTransformationMethod(SingleLineTransformationMethod.getInstance());
			weatherTextView.setFocusable(true);
			handler.postDelayed(updateUIThread, 1000*60*5);
		}
	};
}
