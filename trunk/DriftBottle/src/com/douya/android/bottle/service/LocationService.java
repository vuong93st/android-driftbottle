package com.douya.android.bottle.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.douya.android.utils.LocationProvider;

public class LocationService extends Service{

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
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void initLocation() {
		LocationProvider locationProvider= new LocationProvider();
		// 获取 LocationManager 服务
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		// 获取 Location Provider
		provider = locationProvider.getProvider(locationManager);
		// 如果未设置位置源，打开 GPS 设置界面
		locationProvider.openGPS(locationManager);
		// 获取位置
		location = locationManager.getLastKnownLocation(provider);
		// 显示位置信息到文字标签
		updateWithNewLocation(location);
		// 注册监听器 locationListener ，第 2 、 3 个参数可以控制接收 gps 消息的频度以节省电力。第 2 个参数为毫秒，
		// 表示调用 listener 的周期，第 3 个参数为米 , 表示位置移动指定距离后就调用 listener
		locationManager.requestLocationUpdates(provider, 1000*60*5, 10,locationListener);
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
		public void onStatusChanged(String provider, int status,Bundle extras) {
		}

	};

	// Gps 监听器调用，处理位置信息
	public void updateWithNewLocation(Location location) {
		//searchWeather(location);//调用天气预报
	}
}
