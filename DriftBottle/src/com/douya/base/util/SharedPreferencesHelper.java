/**
 * @(#)com.douya.base.util.SharedPreferencesHelper.java
 * 版权声明 山东益信通科贸有限公司, 版权所有 违者必究
 *
 *<br> Copyright:Copyright (c) 2010-2011
 *<br> Company： 山东益信
 *<br> Author： 葛云杰(geyj@douya.cn)
 *<br> Date：2011-09-17
 *<br> Version：1.0
 */
package com.douya.base.util;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 软件参数设置器
 * 默认全部使用String类型存储数据，如有其他类型数据请自行转换。
 * @author EwinLive
 */
public class SharedPreferencesHelper{
	
	/**
	 * 设置参数
	 * @param activity
	 * @param date
	 * @return
	 */
	public static boolean setMap(Activity activity, Map<String, String> date){
		
		Editor editor = activity.getSharedPreferences("dw", Context.MODE_PRIVATE).edit();
		
		for(String key : date.keySet()){
			editor.putString(key, date.get(key));
		}  
		editor.commit();
		
		return true;
	}
	
	/**
	 * 获取参数
	 * @param activity
	 * @param key
	 * @return
	 */
	public static String getValue(Activity activity, String key){
		return activity.getSharedPreferences("dw", Context.MODE_PRIVATE).getString(key, null);
	}
	
	/**
	 * 访问其他应用的配置
	 * 请确认其他应用允许被访问，
	 * @param activity
	 * @param key
	 * @param packageName 要访问程序的包名
	 * @param fileName 文件名
	 * @return
	 * @throws NameNotFoundException
	 */
	public static String getValueFromOtherApp(Activity activity, String key, String packageName, String fileName) throws NameNotFoundException{
		Context context = activity.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
		return context.getSharedPreferences(fileName, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE).getString(key, null);
	}
	
}
