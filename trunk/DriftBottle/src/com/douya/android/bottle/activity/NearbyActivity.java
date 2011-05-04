package com.douya.android.bottle.activity;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.widget.Toast;

import com.autonavi.mapapi.GeoPoint;
import com.autonavi.mapapi.Geocoder;
import com.autonavi.mapapi.MapActivity;
import com.autonavi.mapapi.MapView;
import com.douya.android.R;
import com.douya.android.bottle.activity.component.AlwaysMarqueeTextView;
import com.douya.android.core.dao.DatabaseHelper;

public class NearbyActivity extends MapActivity{
	private AlwaysMarqueeTextView weatherTextView = null;
	String weatherCurrent = "";
	DatabaseHelper dbHelper; 
	SQLiteDatabase sqliteDatabase;
	
	
	private String TAG = "HIPPO_GEO_DEBUG";
	private MapView mMapView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby);
		
		handler.post(updateUIThread);
		
		mMapView = ((MapView) findViewById(R.id.geocodingview));
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		
		double mLat = 39.907723;
		double mLon = 116.397741;
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		GeoPoint geo = new GeoPoint((int) (mLat * 1E6),
				(int) (mLon * 1E6));
		try {
			if (geo.toString() != "") {
				Geocoder mGeocoder01 = new Geocoder(NearbyActivity.this);
				int x = geo.getLatitudeE6(); // 得到geo纬度，单位微度 (度 * 1E6)
				double x1 = ((double) x) / 1000000;
				int y = geo.getLongitudeE6(); // 得到geo经度，单位微度 (度 * 1E6)
				double y1 = ((double) y) / 1000000;
				//得到逆理编码，参数分别为：纬度，经度，最大结果集
				List<Address> lstAddress = mGeocoder01
						.getFromRawGpsLocation(x1, y1, 3);
					if (lstAddress.size()!=0) {
					//Toast输出geo编码得到的地名
					for (int i = 0; i < lstAddress.size(); ++i) {
						Address adsLocation = lstAddress.get(i);
						Toast.makeText(getApplicationContext(),
							           adsLocation.getFeatureName().toString(),
									   Toast.LENGTH_LONG).show();
						Log.i(TAG, "Address found = "+ adsLocation.toString());
					}
				} else {
					Log.i(TAG, "Address GeoPoint NOT Found.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "连接错误！",
					Toast.LENGTH_SHORT).show();

		}

	}


	Handler handler = new Handler();
	/**
	 * 更新天气界面UI
	 */
	Runnable updateUIThread = new Runnable() {

		public void run() {
			weatherCurrent="";
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
