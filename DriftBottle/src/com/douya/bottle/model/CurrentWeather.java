package com.douya.bottle.model;

public class CurrentWeather {
	private String condition;
	private String temp_f;//华氏
	private String temp_c;//摄氏
	private String humidity;//湿度
	private String icon;//图标
	private String wind_condition;//风力
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getTemp_f() {
		return temp_f;
	}
	public void setTemp_f(String temp_f) {
		this.temp_f = temp_f;
	}
	public String getTemp_c() {
		return temp_c;
	}
	public void setTemp_c(String temp_c) {
		this.temp_c = temp_c;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getWind_condition() {
		return wind_condition;
	}
	public void setWind_condition(String wind_condition) {
		this.wind_condition = wind_condition;
	}
	
	
}
