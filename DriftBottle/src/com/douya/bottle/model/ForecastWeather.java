package com.douya.bottle.model;

public class ForecastWeather {
	private String day;
    private String lowTemp;
    private String highTemp;
    private String imageUrl;
    private String condition;
    
    public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getLowTemp() {
		return lowTemp;
	}

	public void setLowTemp(String lowTemp) {
		this.lowTemp = lowTemp;
	}

	public String getHighTemp() {
		return highTemp;
	}

	public void setHighTemp(String highTemp) {
		this.highTemp = highTemp;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}


