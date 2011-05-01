package com.douya.android.core.activity;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.douya.android.bottle.service.WeatherService;
import com.douya.android.core.dao.DatabaseHelper;

public class LocationActivity extends Activity {
	DatabaseHelper dbHelper; 
	SQLiteDatabase sqliteDatabase;
	
	private LocationManager locationManager;
	private String provider;
	private Location location;
	private Address address;

	public Location getLocation() {
		return location;
	}
	public Address getAddress() {
		return address;
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
		if(location!=null){
			saveLocation(location.getLatitude(),location.getLongitude());
		}
		// 显示位置信息到文字标签
		updateWithNewLocation(location);
		// 注册监听器 locationListener ，第 2 、 3 个参数可以控制接收 gps 消息的频度以节省电力。第 2 个参数为毫秒，
		// 表示调用 listener 的周期，第 3 个参数为米 , 表示位置移动指定距离后就调用 listener
		locationManager.requestLocationUpdates(provider, 2000, 10,locationListener);
		
		Intent intent = new Intent();
        intent.setClass(LocationActivity.this, WeatherService.class);
        System.out.println("onCreate=========启动天气Service");
        startService(intent);
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
		String latLongString;
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			saveLocation(lat,lng);
			latLongString = " 纬度 :" + lat + "\n 经度 :" + lng;
		} else {
			latLongString = " 无法获取地理信息 ";
		}
		System.out.println(latLongString);
	}

	// 获取地址信息

	private List<Address> getAddressbyGeoPoint(Location location) {

		List<Address> result = null;
		// 先将 Location 转换为 GeoPoint
		// GeoPoint gp =getGeoByLocation(location);
		try {
			if (location != null) {
				// 获取 Geocoder ，通过 Geocoder 就可以拿到地址信息
				Geocoder gc = new Geocoder(this, Locale.getDefault());
				result = gc.getFromLocation(location.getLatitude(),
						location.getLongitude(), 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void saveLocation(double lat,double lng){
		ContentValues values = new ContentValues(); 
        values.put("latitude", (int)(lat*1000000));
        values.put("longitude", (int)(lng*1000000));
        Cursor cursor = sqliteDatabase.query("gps_info", null, null, null, null, null, null); 
        if(cursor.moveToNext()){
        	System.out.println("更新位置数据");
        	sqliteDatabase.update("gps_info", values, null, null);
        }else{
        	System.out.println("插入位置数据");
        	sqliteDatabase.insert("gps_info", null, values);   
        }
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		System.out.println("finish=========停止天气Service");
		Intent intent = new Intent();
		intent.setClass(LocationActivity.this, WeatherService.class);
		stopService(intent);
		super.finish();
	}
}
