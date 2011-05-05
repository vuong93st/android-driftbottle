package com.douya.android.core.activity;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.douya.android.R;

public class LoadingActivity extends Activity{
	private LocationManager locationManager;
	private String provider;
	private Location location;
	
	private TextView loadingTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		loadingTextView = (TextView)findViewById(R.id.loading_info_tv);
	}
/*	public void initLocation() {
		LocationProvider locationProvider= new LocationProvider();
		loadingTextView.setText("正在启动位置服务");
		// 获取 LocationManager 服务
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		loadingTextView.setText("正在获取");
		// 获取 Location Provider
		provider = locationProvider.getProvider(locationManager);
		// 如果未设置位置源，打开 GPS 设置界面
		locationProvider.openGPS(locationManager);
	}*/
}
