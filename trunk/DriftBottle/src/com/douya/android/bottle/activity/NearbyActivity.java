package com.douya.android.bottle.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
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
	
	private LocationManager locationManager;
	private String provider;
	private Location location;
	
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
		
		initLocation();

	}

	public void initLocation() {
		// 获取 LocationManager 服务
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// 获取 Location Provider
		getProvider();
		// 如果未设置位置源，打开 GPS 设置界面
		openGPS();
		// 获取位置
		location = locationManager.getLastKnownLocation(provider);
		// 显示位置信息到文字标签
		updateWithNewLocation(location);
		// 注册监听器 locationListener ，第 2 、 3 个参数可以控制接收 gps 消息的频度以节省电力。第 2 个参数为毫秒，
		// 表示调用 listener 的周期，第 3 个参数为米 , 表示位置移动指定距离后就调用 listener
		locationManager.requestLocationUpdates(provider, 1000*60*5, 10,locationListener);
	}

	// 判断是否开启 GPS ，若未开启，打开 GPS 设置界面
	private void openGPS() {

		if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
		|| locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
			//Toast.makeText(this, " 位置源已设置！ ", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(this, " 位置源未设置！ ", Toast.LENGTH_SHORT).show();

		// 转至 GPS 设置界面
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		startActivityForResult(intent, 0);
	}

	// 获取 Location Provider
	private void getProvider() {
		// 构建位置查询条件
		Criteria criteria = new Criteria();
		// 查询精度：高
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 是否查询海拨：否
		criteria.setAltitudeRequired(false);
		// 是否查询方位角 : 否
		criteria.setBearingRequired(false);
		// 是否允许付费：是
		criteria.setCostAllowed(true);
		// 电量要求：低
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 返回最合适的符合条件的 provider ，第 2 个参数为 true 说明 , 如果只有一个 provider 是有效的 , 则返回当前
		// provider
		provider = locationManager.getBestProvider(criteria, true);

	}

	// Gps 消息监听器
	private final LocationListener locationListener = new LocationListener() {
		// 位置发生改变后调用
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		// provider 被用户关闭后调用
		public void onProviderDisabled(String provider) {
			updateWithNewLocation(null);
		}

		// provider 被用户开启后调用
		public void onProviderEnabled(String provider) {
		}

		// provider 状态变化时调用
		public void onStatusChanged(String provider, int status,
		Bundle extras) {
		}

	};

	// Gps 监听器调用，处理位置信息
	private void updateWithNewLocation(Location location) {
		if (location != null) {
			double mLat = location.getLatitude();
			double mLon = location.getLongitude();
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
