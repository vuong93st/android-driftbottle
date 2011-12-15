package com.douya.bottle.app;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;

import android.util.Log;

import com.douya.base.app.BaseApp;
import com.douya.base.app.CrashHandler;
import com.douya.base.entity.exception.ExceptionEntity;
import com.douya.base.xml.handler.DefaultSaxHandler;
import com.douya.base.xml.parser.SaxXmlParser;

/**
 * Bottle应用程序
 * @author GeYunjie
 *
 */
public class BottleApp extends BaseApp {


	public static final String DEBUG_TAG = "bottle_debug";// 调试时标签

	private static final String TAG = "BottleApp";
	public static final String SharedPreferences = "HtggSP";

	/**
	 *  SharedPreferences名称
	 */
	public static final String SPNAME = "spname";


	// xml数据解析器
	private SaxXmlParser saxXmlParser;
	
	private static Map<String, ExceptionEntity> exceptions;

	// 令牌
	private static String tokenRing;
	// 用户名
	private static String userName;
	// 密码
	private static String password;

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		super.onCreate();

		/* 注册异常捕获 */
		CrashHandler crashHandler = CrashHandler.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());

		exceptions = new HashMap<String, ExceptionEntity>();
		// 加载异常值
		loadExceptions();

		tokenRing = null;
		userName = null;
		password = null;
	}

	@SuppressWarnings("unchecked")
	private void loadExceptions() {
		/* 加载异常值信息 */
		int id = getResources().getIdentifier("exceptions", "raw",
				getPackageName());
		if (null == saxXmlParser) {
			saxXmlParser = new SaxXmlParser();
			saxXmlParser.setInputSource(new InputSource(getResources()
					.openRawResource(id)));
			saxXmlParser.setHandler(new DefaultSaxHandler());
		} else {
			saxXmlParser.setInputSource(new InputSource(getResources()
					.openRawResource(id)));
		}
		saxXmlParser.parseInpuSource();
		exceptions = (Map<String, ExceptionEntity>) saxXmlParser.getContent();
	}

	public static Map<String, ExceptionEntity> getExceptions() {
		return exceptions;
	}

	public SaxXmlParser getSaxXmlParser() {
		return saxXmlParser;
	}

	public void setSaxXmlParser(SaxXmlParser saxXmlParser) {
		this.saxXmlParser = saxXmlParser;
	}
	
	public static String getTokenRing() {
		return tokenRing;
	}

	public static void setTokenRing(String tokenRing) {
		BottleApp.tokenRing = tokenRing;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		BottleApp.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		BottleApp.password = password;
	}
}
