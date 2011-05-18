package com.douya.android.core.activity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.ContentValues;
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
import android.provider.Settings;
import android.widget.Toast;

import com.autonavi.mapapi.MapActivity;
import com.douya.android.bottle.XmlHandler;
import com.douya.android.bottle.model.CurrentWeather;
import com.douya.android.bottle.model.ForecastWeather;
import com.douya.android.bottle.model.Weather;
import com.douya.android.core.dao.DatabaseHelper;

public class LocationActivity extends MapActivity {
	private DatabaseHelper dbHelper; 
	private SQLiteDatabase sqliteDatabase;
	private StringBuffer weatherStr = new StringBuffer();   //滚动天气内容

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
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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
		public void onStatusChanged(String provider, int status,Bundle extras) {
		}

	};

	// Gps 监听器调用，处理位置信息
	public void updateWithNewLocation(Location location) {
		searchWeather(location);//调用天气预报
	}

	//////////////Google天气预报////////////////////
	public void searchWeather(Location location) {
		int lat = 0;
		int lng = 0;
		if (location == null) return;
		lat = (int)(location.getLatitude()*1000000);
		lng = (int)(location.getLongitude()*1000000);

		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			XMLReader reader = sp.getXMLReader();

			XmlHandler handler = new XmlHandler();
			reader.setContentHandler(handler);
			URL url = new URL("http://www.google.com/ig/api?hl=zh-cn&weather=,,,"
					+ lat+","+lng);
			InputStream is = url.openStream();
			InputStreamReader isr = new InputStreamReader(is, "GBK");
			InputSource source = new InputSource(isr);
			
			reader.parse(source);
			if(location!=null){
				weatherStr.delete(0, weatherStr.length());
				//weatherStr.append(" 纬度=" + (double)((int)(location.getLatitude()*1000000))/1000000 + " 经度=" + (double)((int)(location.getLongitude()*1000000))/1000000+"；");
			}
			List<CurrentWeather> currentWeatherList = handler.getCurrentWeatherList();
			for(CurrentWeather currentWeather : currentWeatherList){
				if(currentWeather.getCondition()!=null)weatherStr.append("天气实况："+currentWeather.getCondition());
				if(currentWeather.getTemp_f()!=null)weatherStr.append(" 温度：华氏 "+currentWeather.getTemp_f());
				if(currentWeather.getTemp_c()!=null)weatherStr.append(" 摄氏："+currentWeather.getTemp_c());
				if(currentWeather.getHumidity()!=null)weatherStr.append(" 温度："+currentWeather.getHumidity());
				if(currentWeather.getWind_condition()!=null)weatherStr.append(" 风力："+currentWeather.getWind_condition()+"；");
			}
			List<ForecastWeather> weatherList = handler.getForecastWeatherList();

			for (ForecastWeather weather : weatherList) {
				if(weather.getDay()!=null)weatherStr.append(weather.getDay());
				if(weather.getCondition()!=null)weatherStr.append("天气："+weather.getCondition()+"； ");
				if(weather.getLowTemp()!=null)weatherStr.append(" 最低气温："+weather.getLowTemp() + "℃ ");
				if(weather.getHighTemp()!=null)weatherStr.append(" 最高气温："+weather.getHighTemp() + "℃");
			}
			System.out.println(weatherStr);
            if(weatherStr==null||"".equalsIgnoreCase(weatherStr.toString())){
            	weatherStr.append("无法连接到天气预报服务器，暂时无法提供天气信息。");
            }
		} catch (Exception e) {
			weatherStr.append("无法连接到天气预报服务器，暂时无法提供天气信息。");

			System.out.println(weatherStr+e.getMessage());
		}
		try{
			Weather.Current = weatherStr.toString();
			dbHelper = new DatabaseHelper(LocationActivity.this, "bottle_db"); 
			sqliteDatabase = dbHelper.getWritableDatabase(); 
			ContentValues values = new ContentValues(); 
	        values.put("CURRENT", weatherStr.toString());
	        
	        Cursor cursor = sqliteDatabase.query("weather", null, null, null, null, null, null); 
	        if(cursor.moveToNext()){
	        	sqliteDatabase.update("weather", values, null, null);
	        }else{
	        	sqliteDatabase.insert("weather", null, values);   
	        }
	        cursor.close();
	        sqliteDatabase.close();
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage());
        }
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
}
