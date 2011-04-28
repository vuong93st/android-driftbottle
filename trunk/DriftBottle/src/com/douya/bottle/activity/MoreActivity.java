package com.douya.bottle.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.SingleLineTransformationMethod;
import android.widget.SimpleAdapter;

import com.douya.bottle.R;
import com.douya.utils.AlwaysMarqueeTextView;
import com.douya.utils.WeatherUtils;

public class MoreActivity extends ListActivity{
	private AlwaysMarqueeTextView weatherTextView = null;
	String weatherCurrent="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		
		handler.post(updateThread);
		
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		HashMap<String, String> map4 = new HashMap<String, String>();
		HashMap<String, String> map5 = new HashMap<String, String>();
		HashMap<String, String> map6 = new HashMap<String, String>();
		HashMap<String, String> map7 = new HashMap<String, String>();
		map1.put("moreLogo", "1");
		map1.put("moreTitle", getString(R.string.moreitem1));
		map1.put("moreMore", "2");
		map2.put("moreLogo", "1");
		map2.put("moreTitle", getString(R.string.moreitem2));
		map2.put("moreMore", "2");
		map3.put("moreLogo", "1");
		map3.put("moreTitle", getString(R.string.moreitem3));
		map3.put("moreMore", "2");
		map4.put("moreLogo", "1");
		map4.put("moreTitle", getString(R.string.moreitem4));
		map4.put("moreMore", "2");
		map5.put("moreLogo", "1");
		map5.put("moreTitle", getString(R.string.moreitem5));
		map5.put("moreMore", "2");
		map6.put("moreLogo", "1");
		map6.put("moreTitle", getString(R.string.moreitem6));
		map6.put("moreMore", "2");
		map7.put("moreLogo", "1");
		map7.put("moreTitle", getString(R.string.moreitem7));
		map7.put("moreMore", "2");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		SimpleAdapter sap = new SimpleAdapter(this, list, R.layout.moreitem,
				new String[] { "moreLogo", "moreTitle", "moreMore" }, new int[] {
						R.id.moreLogo, R.id.moreTitle, R.id.moreMore });
		setListAdapter(sap);
	}
 
	Handler handler = new Handler();
	   Runnable updateThread = new Runnable(){
		
			public void run() {
				//获取天气预报
				WeatherUtils weatherUtils = new WeatherUtils();
				weatherUtils.getWeather("济南");
				weatherCurrent+=weatherUtils.getWeatherCurrent()!=null?weatherUtils.getWeatherCurrent()+"      " :"无法连接到天气预报服务器，暂时无法获取天气预报！" ;
				weatherCurrent+=weatherUtils.getWeatherTomorrow()!=null?weatherUtils.getWeatherTomorrow()+"      " :"" ;
				weatherCurrent+=weatherUtils.getWeatherAfterday()!=null?weatherUtils.getWeatherAfterday()+"      " :"" ;
				handler.post(updateUIThread);
				handler.postDelayed(updateThread, 1000*60*60);
			}
		};

	Runnable updateUIThread = new Runnable(){
		
		public void run() {
			//获取天气预报
			weatherTextView = (AlwaysMarqueeTextView)findViewById(R.id.app_weather_content);
			weatherTextView.setText(weatherCurrent);
			weatherTextView.setTransformationMethod(SingleLineTransformationMethod.getInstance());
			weatherTextView.setFocusable(true);
			handler.postDelayed(updateThread, 1000*60*5);
		}
	};
 

}
