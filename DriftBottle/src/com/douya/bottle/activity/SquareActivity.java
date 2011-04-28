package com.douya.bottle.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.SingleLineTransformationMethod;

import com.douya.bottle.R;
import com.douya.utils.AlwaysMarqueeTextView;
import com.douya.utils.WeatherUtils;

public class SquareActivity extends Activity{
	private AlwaysMarqueeTextView weatherTextView = null;
	String weatherCurrent="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby);
		
		handler.post(updateThread);
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
