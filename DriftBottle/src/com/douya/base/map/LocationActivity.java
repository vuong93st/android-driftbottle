package com.douya.base.map;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.autonavi.mapapi.GeoPoint;
import com.autonavi.mapapi.MapActivity;

public class LocationActivity extends MapActivity {
	private static final String TAG = "LocationActivity";

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

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
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
		// 注册监听器 locationListener ，第 2 、 3 个参数可以控制接收 gps 消息的频度以节省电力。第 2 个参数为毫秒，
		// 表示调用 listener 的周期，第 3 个参数为米 , 表示位置移动指定距离后就调用 listener
		locationManager.requestLocationUpdates(provider, 1000 * 60 * 5, 10,
				locationListener);
	}

	// 判断是否开启 GPS ，若未开启，打开 GPS 设置界面
	private void openGPS() {

		if (locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
				|| locationManager
						.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
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
		// 查询精度：粗
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
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
			// updateWithNewLocation(location);
		}

		// provider 被用户关闭后调用
		public void onProviderDisabled(String provider) {
			// updateWithNewLocation(null);
		}

		// provider 被用户开启后调用
		public void onProviderEnabled(String provider) {
		}

		// provider 状态变化时调用
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	};

	// Gps 监听器调用，处理位置信息
	public void updateWithNewLocation(double mLat, double mLon, String markerTip) {
		
	}
	
	public void updateWithNewLocation(GeoPoint[] points, String[] markerTip){
		
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
}
