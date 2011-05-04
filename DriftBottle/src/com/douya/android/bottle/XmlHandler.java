package com.douya.android.bottle;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.douya.android.bottle.model.CurrentWeather;
import com.douya.android.bottle.model.ForecastWeather;

public class XmlHandler extends DefaultHandler {
    private List<ForecastWeather> forecastWeatherList;//天气预报
    private List<CurrentWeather> currentWeatherList;//实时天气

    private final String FORECAST_CONDITIONS="forecast_conditions";//预报天气
    private final String CURRENT_CONDITIONS="current_conditions";//实时天气
    private boolean isForcast=false;
    private boolean isCurrent=false;
    
    private ForecastWeather forecastWeather;
    private CurrentWeather currentWeather;
    
    public List<ForecastWeather> getForecastWeatherList() {
        return forecastWeatherList;
    }

    public void setForecastWeatherList(List<ForecastWeather> forecastWeatherList) {
        this.forecastWeatherList = forecastWeatherList;
    }

    public List<CurrentWeather> getCurrentWeatherList() {
		return currentWeatherList;
	}

	public void setCurrentWeatherList(List<CurrentWeather> currentWeatherList) {
		this.currentWeatherList = currentWeatherList;
	}

	public XmlHandler() {
        
		forecastWeatherList = new ArrayList<ForecastWeather>();
        currentWeatherList = new  ArrayList<CurrentWeather>();
        isForcast = false;
        isCurrent = false;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        
        String tagName = localName.length() != 0 ? localName : qName;
        tagName = tagName.toLowerCase();
        
        if(tagName.equals(FORECAST_CONDITIONS)) {
        	isForcast = true;
        	forecastWeather = new ForecastWeather();
        }
        if(tagName.equals(CURRENT_CONDITIONS)) {
        	isCurrent = true;
            currentWeather = new CurrentWeather();
        }
        if(isForcast) {
            if(tagName.equals("day_of_week")) {                
            	forecastWeather.setDay(attributes.getValue("data"));
            }else if(tagName.equals("low")) {
            	forecastWeather.setLowTemp(attributes.getValue("data"));
            }else if(tagName.equals("high")) {
            	forecastWeather.setHighTemp(attributes.getValue("data"));
            }else if(tagName.equals("icon")) {
            	forecastWeather.setImageUrl(attributes.getValue("data"));
            }else if(tagName.equals("condition")) {
            	forecastWeather.setCondition(attributes.getValue("data"));
            }
        }
        if(isCurrent){
        	if(tagName.equals("condition")) {                
            	currentWeather.setCondition(attributes.getValue("data"));
            }else if(tagName.equals("temp_f")) {//华氏
            	currentWeather.setCondition(attributes.getValue("data"));
            }else if(tagName.equals("temp_c")) {//摄氏
            	currentWeather.setCondition(attributes.getValue("data"));
            }else if(tagName.equals("humidity")) {                
            	currentWeather.setCondition(attributes.getValue("data"));
            }else  if(tagName.equals("icon")) {                
            	currentWeather.setCondition(attributes.getValue("data"));
            }else  if(tagName.equals("wind_condition")) {                
            	currentWeather.setCondition(attributes.getValue("data"));
            }
        }
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        String tagName = localName.length() != 0 ? localName : qName;
        tagName = tagName.toLowerCase();
        
        if(tagName.equals(FORECAST_CONDITIONS)) {
        	isForcast = false;
        	forecastWeatherList.add(forecastWeather);
        }
        
        if(tagName.equals(CURRENT_CONDITIONS)) {
        	isCurrent = false;
        	currentWeatherList.add(currentWeather);
        }
    }

}

