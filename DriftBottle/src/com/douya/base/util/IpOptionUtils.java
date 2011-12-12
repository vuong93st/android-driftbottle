/**
 * 
 */
package com.douya.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.douya.R;

/**
 * @author ZhangDq
 */
public class IpOptionUtils {
	/**
	 * 保存ip地址文件
	 * 
	 * @param context
	 * @param ipAddress
	 *            ip地址
	 * @param portStr
	 *            端口号
	 * @param projectStr
	 *            项目名称
	 */
	public static void save(Context context, String ipAddress, String portStr,
			String projectStr) {
		Editor sharedata = PreferenceManager.getDefaultSharedPreferences(
				context).edit();

		sharedata.putString("ip", ipAddress);
		sharedata.putString("port", portStr);
		sharedata.putString("project", projectStr);
		sharedata.putString("serverFullAddress", "");
		sharedata.commit();
	}

	/**
	 * 
	 * TODO: 读取ip地址内容.
	 * 
	 * @param context
	 * @return
	 */
	public static String bulidActionUrl(Context context) {
		SharedPreferences dsp = PreferenceManager
				.getDefaultSharedPreferences(context);
		String serverUrl = dsp.getString("serverFullAddress", "");
		if (null != serverUrl && !"".equals(serverUrl)) {
			return serverUrl;
		}
		String url = dsp.getString("ip",
				context.getResources().getString(R.string.default_server_url));
		String port = dsp.getString("port",
				context.getResources().getString(R.string.default_server_port));
		String project = dsp.getString("project", context.getResources()
				.getString(R.string.default_server_project));
		if (null != url) {
			url = url.replaceFirst("http://", "");
			int mark = url.indexOf("/");
			if (mark > -1) {
				url = url.replaceFirst("/", ":" + port + "/");
			} else {
				url = url + ":" + port + "/" + project + "/";
			}
			url = "http://" + url;
		}
		dsp.edit().putString("serverFullAddress", url).commit();
		Log.i("tag", "serverUrl=" + url);
		return url;
	}
}
