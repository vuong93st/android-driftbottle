package com.douya.bottle.service;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import com.douya.bottle.Bottle;
import com.douya.bottle.R;
import com.douya.utils.DatabaseHelper;
/**
 * 天气预报Service
 */
public class WeatherService extends Service{

	private static String LOG_TAG = "Weather";
	private static boolean DEBUG = false;
	private static final String NAMESPACE = "http://WebXml.com.cn/";
	//WebService地址
	private static String URL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";
	private static final String METHOD_NAME = "getWeatherbyCityName";
	private static String SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName";
	
	private String weatherToday;//今日天气
	private String weatherTomorrow;//明日天气
	private String weatherAfterday;//后天天气
	private String weatherCurrent;//当前天气
	
	
	private int iconToday[] = new int[2];
	private int iconTomorrow[] = new int[2];
	private int iconAfterday[] = new int[2];
	
	private SoapObject detail;  
    
	DatabaseHelper dbHelper; 
	SQLiteDatabase sqliteDatabase;
    @Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
    	System.out.println("onBind============");
		return null;
	}

	@Override
	public void onCreate() {
		System.out.println("onCreate============");
		dbHelper = new DatabaseHelper(WeatherService.this, "bottle_db"); 
		sqliteDatabase = dbHelper.getReadableDatabase();   
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		System.out.println("onDestroy============");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("onStartCommand============");
		Thread thread = new Thread(update);// getWeather("济南");	
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	Runnable update = new Runnable() {

		public void run() {
			try{
				getWeather("济南");
				Thread.sleep(1000*60*30);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	};
	
	public void getWeather(String cityName) {   
        try {   
            SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);   
            rpc.addProperty("theCityName", cityName);   
  
            AndroidHttpTransport ht = new AndroidHttpTransport(URL);   
            ht.debug = true;   
  
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(   
                    SoapEnvelope.VER11);   
               
            envelope.bodyOut = rpc;   
            envelope.dotNet = true;   
            envelope.setOutputSoapObject(rpc);   
  
            ht.call(SOAP_ACTION, envelope);   
               
            debug(LOG_TAG, "DUMP>> " + ht.requestDump);   
            debug(LOG_TAG, "DUMP<< " + ht.responseDump);   
  
            SoapObject result = (SoapObject) envelope.bodyIn;   
             detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");   
               
            parseWeather(detail);
            System.out.println(weatherCurrent);
            ContentValues values = new ContentValues(); 
            values.put(Bottle.Bottles.TODAY, weatherToday);
            values.put(Bottle.Bottles.TOMORROW, weatherTomorrow);
            values.put(Bottle.Bottles.AFTERDAY, weatherAfterday);
            values.put(Bottle.Bottles.CURRENT, weatherCurrent);
            Cursor cursor = sqliteDatabase.query("weather", null, null, null, null, null, null); 
            if(cursor.moveToNext()){
            	System.out.println("更新数据");
            	sqliteDatabase.update("weather", values, null, null);
            }else{
            	System.out.println("插入数据");
            	sqliteDatabase.insert("weather", null, values);   
            }
            return;   
        } catch (Exception e) {   
            e.printStackTrace();  
            System.out.println("error:===="+e.getMessage());
        }   
    }   
       
    private void parseWeather(SoapObject detail) {   
        String date = detail.getProperty(6).toString();   
        weatherToday = "今天：" + date.split(" ")[0];   
        weatherToday = weatherToday + " 天气：" + date.split(" ")[1];   
        weatherToday = weatherToday + " 气温：" + detail.getProperty(5).toString();   
        weatherToday = weatherToday + " 风力：" + detail.getProperty(7).toString() + "";   
        iconToday[0] = parseIcon(detail.getProperty(8).toString());   
        iconToday[1] = parseIcon(detail.getProperty(9).toString());   
           
        weatherCurrent = detail.getProperty(10).toString();   
           
        date = detail.getProperty(13).toString();   
        weatherTomorrow = "明天：" + date.split(" ")[0];   
        weatherTomorrow = weatherTomorrow + " 天气：" + date.split(" ")[1];   
        weatherTomorrow = weatherTomorrow + " 气温：" + detail.getProperty(12).toString();   
        weatherTomorrow = weatherTomorrow + " 风力：" + detail.getProperty(14).toString() + "";   
        iconTomorrow[0] = parseIcon(detail.getProperty(15).toString());   
        iconTomorrow[1] = parseIcon(detail.getProperty(16).toString());   
           
        date = detail.getProperty(18).toString();   
        weatherAfterday = "后天：" + date.split(" ")[0];   
        weatherAfterday = weatherAfterday + "天气：" + date.split(" ")[1];   
        weatherAfterday = weatherAfterday + "气温：" + detail.getProperty(17).toString();   
        weatherAfterday = weatherAfterday + "风力：" + detail.getProperty(19).toString() + "";   
        iconAfterday[0] = parseIcon(detail.getProperty(20).toString());   
        iconAfterday[1] = parseIcon(detail.getProperty(21).toString());   
    }   
       
    public String getWeatherToday() {   
        debug(LOG_TAG, "weatherToday: " + weatherToday);   
        return weatherToday;   
    }   
       
    public String getWeatherTomorrow() {   
        debug(LOG_TAG, "weatherTomorrow: " + weatherTomorrow);   
        return weatherTomorrow;   
    }   
       
    public String getWeatherAfterday() {   
        debug(LOG_TAG, "weatherAfterday: " + weatherAfterday);   
        return weatherAfterday;   
    }   
       
    public String getWeatherCurrent() {   
        debug(LOG_TAG, "weatherCurrent: " + weatherCurrent);   
        return weatherCurrent;   
    }   
       
    public int getIconToday (int index) {   
        return iconToday[index];   
    }   
       
    public int getIconTomorrow (int index) {   
        return iconTomorrow[index];   
    }   
       
    public int getIconAfterday (int index) {   
        return iconAfterday[index];   
    }   
       
    private int parseIcon(String strIcon) {   
        if (strIcon == null) return -1;   
           
        if ("0.gif".equals(strIcon)) return R.drawable.a_0;   
        if ("1.gif".equals(strIcon)) return R.drawable.a_1;   
        if ("2.gif".equals(strIcon)) return R.drawable.a_2;   
        if ("3.gif".equals(strIcon)) return R.drawable.a_3;   
        if ("4.gif".equals(strIcon)) return R.drawable.a_4;   
        if ("5.gif".equals(strIcon)) return R.drawable.a_5;   
        if ("6.gif".equals(strIcon)) return R.drawable.a_6;   
        if ("7.gif".equals(strIcon)) return R.drawable.a_7;   
        if ("8.gif".equals(strIcon)) return R.drawable.a_8;   
        if ("9.gif".equals(strIcon)) return R.drawable.a_9;   
        if ("10.gif".equals(strIcon)) return R.drawable.a_10;   
        if ("11.gif".equals(strIcon)) return R.drawable.a_11;   
        if ("12.gif".equals(strIcon)) return R.drawable.a_12;   
        if ("13.gif".equals(strIcon)) return R.drawable.a_13;   
        if ("14.gif".equals(strIcon)) return R.drawable.a_14;   
        if ("15.gif".equals(strIcon)) return R.drawable.a_15;   
        if ("16.gif".equals(strIcon)) return R.drawable.a_16;   
        if ("17.gif".equals(strIcon)) return R.drawable.a_17;   
        if ("18.gif".equals(strIcon)) return R.drawable.a_18;   
        if ("19.gif".equals(strIcon)) return R.drawable.a_19;   
        if ("20.gif".equals(strIcon)) return R.drawable.a_20;   
        if ("21.gif".equals(strIcon)) return R.drawable.a_21;   
        if ("22.gif".equals(strIcon)) return R.drawable.a_22;   
        if ("23.gif".equals(strIcon)) return R.drawable.a_23;   
        if ("24.gif".equals(strIcon)) return R.drawable.a_24;   
        if ("25.gif".equals(strIcon)) return R.drawable.a_25;   
        if ("26.gif".equals(strIcon)) return R.drawable.a_26;   
        if ("27.gif".equals(strIcon)) return R.drawable.a_27;   
        if ("28.gif".equals(strIcon)) return R.drawable.a_28;   
        if ("29.gif".equals(strIcon)) return R.drawable.a_29;   
        if ("30.gif".equals(strIcon)) return R.drawable.a_30;   
        if ("31.gif".equals(strIcon)) return R.drawable.a_31;   
           
        return 0;   
    }   
       
    private static void debug(String tag, String msg) {   
        if (DEBUG) Log.d(tag, msg);   
    }
}
