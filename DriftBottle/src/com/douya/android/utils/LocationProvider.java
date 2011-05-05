package com.douya.android.utils;

import android.app.Activity;
import android.location.Criteria;
import android.location.LocationManager;

public class LocationProvider extends Activity{
	/**
	 *  获取 Location Provider
	 * @return
	 */
	public String getProvider(LocationManager locationManager) {
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
		return locationManager.getBestProvider(criteria, true);

	}
	
	/**
	 * 判断是否开启 GPS ，若未开启，打开 GPS 设置界面
	 */
	public boolean openGPS(LocationManager locationManager) {

		if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)
		|| locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
			return true;
		}
		return false;
	}

}
