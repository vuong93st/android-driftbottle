/**
 * @(#)com.douya.base.util.WebUtils.java
 * 版权声明 山东益信通科贸有限公司, 版权所有 违者必究
 *
 *<br> Copyright:Copyright (c) 2010-2011
 *<br> Company： 山东益信
 *<br> Author： 葛云杰(geyj@douya.cn)
 *<br> Date：2011-09-17
 *<br> Version：1.0
 */
package com.douya.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
/**
 * Web工具类
 * @author GeYunjie
 *
 */
public class WebUtils {
	private static final String TAG = "WebUtils";

	private static int timeoutRead = 10 * 1000;
	private static int timeoutConnection = 10000;
	private static int timeoutSocket = 10000;

	// 上传方法
	public static String post(String actionUrl, Map<String, String> params,
			Map<String, File> files) throws IOException {
		System.out.println("actionUrl=" + actionUrl);
		String boundary = UUID.randomUUID().toString();
		String prefix = "--", linend = "\r\n";
		String multipart_from_data = "multipart/form-data";
		String charset = "UTF-8";

		URL url = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// conn.setReadTimeout(5 * 1000);// 缓存的最长时间
		conn.setConnectTimeout(timeoutConnection);
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false);// 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charset", charset);
		conn.setRequestProperty("Content-Type", multipart_from_data
				+ ";boundary=" + boundary);

		StringBuilder strBuilder = new StringBuilder();
		if (null != params) {
			// 首先组拼文本类型的数据
			for (Map.Entry<String, String> entry : params.entrySet()) {
				System.out.println("key=" + entry.getKey() + ",value="
						+ entry.getValue());
				strBuilder.append(prefix);
				strBuilder.append(boundary);
				strBuilder.append(linend);
				strBuilder.append("Content-Disposition:form-data;name=\""
						+ entry.getKey() + "\"" + linend);
				strBuilder.append("Content-Type:text/plain;charset=" + charset
						+ linend);
				strBuilder.append("Content-Transfer-Encoding:8bit;" + linend);
				strBuilder.append(linend);
				strBuilder.append(entry.getValue());
				strBuilder.append(linend);
			}
		}
		// if(null != files){
		// for (Map.Entry<String, File> file : files.entrySet()) {
		// strBuilder.append(prefix);
		// strBuilder.append(boundary);
		// strBuilder.append(linend);
		// strBuilder.append("Content-Disposition:form-data;name=\""
		// + file.getKey() + "\"" + linend);
		// strBuilder.append("Content-Type:text/plain;charset=" + charset
		// + linend);
		// strBuilder.append("Content-Transfer-Encoding:8bit;" + linend);
		// strBuilder.append(linend);
		// strBuilder.append(file.getValue().getName());
		// strBuilder.append(linend);
		// }
		// }
		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
		dos.write(strBuilder.toString().getBytes());

		if (null != files) {
			int i = 0;
			for (Map.Entry<String, File> file : files.entrySet()) {
				System.out.println("key=" + file.getKey() + ",value="
						+ file.getValue().getAbsolutePath());
				StringBuilder sb2 = new StringBuilder();
				sb2.append(prefix);
				sb2.append(boundary);
				sb2.append(linend);
				// sb2.append("Content-Disposition:form-data;name=\"file" +
				// (i++)
				// + "\";filename=\"" + file.getValue().getName() + "\""
				// + linend);
				sb2.append("Content-Disposition:form-data;name=\"filesFileName"
						+ "\";filename=\"" + file.getValue().getName() + "\""
						+ linend);
				sb2.append("Content-Type:application/octet-stream;charset="
						+ charset + linend);
				sb2.append(linend);
				dos.write(sb2.toString().getBytes());

				InputStream is = new FileInputStream(file.getValue()
						.getAbsolutePath());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					dos.write(buffer, 0, len);
				}
				is.close();
				dos.write(linend.getBytes());
			}
		}
		// 请求结束标记
		byte[] end_data = (prefix + boundary + prefix + linend).getBytes();
		dos.write(end_data);
		dos.flush();

		// 得到响应码
		int res = conn.getResponseCode();
		InputStream in = null;
		StringBuilder sb2 = new StringBuilder();
		if (res == 200) {
			System.out.println("WebUtils ,responseCode=" + res);
			in = conn.getInputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
		} else {
			System.out.println("WebUtils request error ,responseCode=" + res);
		}
		System.out.println("WebUtils,result=" + sb2.toString());
		return sb2.toString();
	}

	/**
	 * 发送指定的xml数据到actionUrl指定的位置
	 * 
	 * @param actionUrl
	 * @param xml
	 * @return
	 * @throws IOException
	 */
	public static String postXML(String actionUrl, String xml)
			throws IOException {
		Log.i(TAG, "postXML actionUrl=" + actionUrl);
		URL url = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false);// 不允许使用缓存
		conn.setReadTimeout(10 * 1000);
		conn.setConnectTimeout(10 * 1000);
		conn.setRequestMethod("POST");
		// conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("User-Agent", "Mobile XML");
		conn.setRequestProperty("Content-Type", "application/octet-stream");
		// conn.setRequestProperty("Content-Length",
		// String.valueOf(xml.getBytes().length));
		// conn.setRequestProperty("Charset", "UTF-8");
		// conn.setRequestProperty("Content-Type", "text/xml");

		conn.connect();

		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(xml.getBytes("UTF-8"));

		outputStream.flush();
		outputStream.close();
		outputStream = null;

		// 得到响应码
		int res = conn.getResponseCode();
		InputStream in = null;
		StringBuilder sb2 = new StringBuilder();
		if (res == 200) {
			in = conn.getInputStream();
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(in));
			String ch;
			while ((ch = buffReader.readLine()) != null) {
				sb2.append(ch);
			}
			buffReader.close();
			in.close();
			in = null;
			buffReader = null;
		} else {
			System.out.println("WebUtils request error ,responseCode=" + res);
			Log.e(TAG, "request error ,responseCode=" + res);
		}
		conn.disconnect();
		conn = null;
		System.out.println("http response,result=" + sb2.toString());
		return sb2.toString();
	}

	/**
	 * 发送指定的xml数据到actionUrl指定的位置
	 * 
	 * @param actionUrl
	 * @param xml
	 * @return
	 * @throws IOException
	 */
	public static String postImg(Context context, String actionUrl,
			String imgFile) throws IOException {
		System.out.println("actionUrl=" + actionUrl + ",imgFile=" + imgFile);
		URL url = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false);// 不允许使用缓存
		conn.setReadTimeout(timeoutRead);
		conn.setConnectTimeout(timeoutConnection);
		conn.setRequestMethod("POST");
		// conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("User-Agent", "Mobile XML");
		conn.setRequestProperty("Content-Type", "application/octet-stream");
		// conn.setRequestProperty("Content-Length",
		// String.valueOf(xml.getBytes().length));
		// conn.setRequestProperty("Charset", "UTF-8");
		// conn.setRequestProperty("Content-Type", "text/xml");

		conn.connect();

		OutputStream outputStream = conn.getOutputStream();

		File file = new File(imgFile);
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024 * 8];
		while (fis.read(buffer) != -1) {
			outputStream.write(buffer);
		}
		fis.close();
		fis = null;

		outputStream.flush();
		outputStream.close();
		outputStream = null;

		// 得到响应码
		int res = conn.getResponseCode();
		InputStream in = null;
		StringBuilder sb2 = new StringBuilder();
		if (res == 200) {
			in = conn.getInputStream();

			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(in));
			String ch;
			while ((ch = buffReader.readLine()) != null) {
				sb2.append(ch);
			}
			buffReader.close();
			in.close();
			in = null;
			buffReader = null;
		} else {
			System.out.println("WebUtils request error ,responseCode=" + res);
		}
		conn.disconnect();
		conn = null;
		System.out.println("http response,result=" + sb2.toString());
		return sb2.toString();
	}

	public static String httpPost(String actionUrl, Map<String, String> fields) {
		System.out.println("WebUtils-->httpPost,actionUrl=" + actionUrl);
		String strResult = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(actionUrl);
			HttpParams httpParams = new BasicHttpParams();
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

			// 设置超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, timeoutSocket);
			// 读取超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, timeoutSocket);
			if (null != fields) {
				for (Map.Entry<String, String> entry : fields.entrySet()) {
					nameValuePair.add(new BasicNameValuePair(entry.getKey(),
							entry.getValue()));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
					HTTP.UTF_8));
			httpPost.setParams(httpParams);

			HttpResponse httpResponse = httpClient.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得返回的字符串
				strResult = EntityUtils.toString(httpResponse.getEntity(),
						HTTP.UTF_8);
			}
			System.out.println("HttpStatus StatusCode="
					+ httpResponse.getStatusLine().getStatusCode());
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			strResult = "exceptionCode=0001";
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			strResult = "exceptionCode=0001";
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
			strResult = "exceptionCode=0002";
		} catch (Exception e) {
			e.printStackTrace();
			strResult = "exceptionCode=0002";
		}
		httpClient.getConnectionManager().shutdown();
		System.out.println(TAG + ",http result=" + strResult);
		return strResult;
	}

	public static String httpPost(String actionUrl, Map<String, String> fields,
			List<String> files) {
		System.out.println("WebUtils-->httpPost,actionUrl=" + actionUrl);
		String strResult = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(actionUrl);
			HttpParams httpParams = new BasicHttpParams();
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

			// 设置超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, timeoutSocket);
			// 读取超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, timeoutSocket);
			if (null != fields) {
				for (Map.Entry<String, String> entry : fields.entrySet()) {
					System.out.println("key=" + entry.getKey() + ",value="
							+ entry.getValue());
					nameValuePair.add(new BasicNameValuePair(entry.getKey(),
							entry.getValue()));
				}
			}
			if (null != files) {
				for (int i = 0; i < files.size(); i++) {
					System.out.println("fileName=" + files.get(i));
					nameValuePair.add(new BasicNameValuePair("fileName", files
							.get(i)));
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
					HTTP.UTF_8));
			httpPost.setParams(httpParams);
			HttpResponse httpResponse = httpClient.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得返回的字符串
				strResult = EntityUtils.toString(httpResponse.getEntity(),
						HTTP.UTF_8);
			}
			System.out.println("HttpStatus StatusCode="
					+ httpResponse.getStatusLine().getStatusCode());
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			strResult = "exceptionCode=0001";
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			strResult = "exceptionCode=0001";
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
			strResult = "exceptionCode=0002";
		} catch (Exception e) {
			e.printStackTrace();
			strResult = "exceptionCode=0002";
		}
		httpClient.getConnectionManager().shutdown();
		System.out.println(TAG + ",http result=" + strResult);
		return strResult;
	}

	/**
	 * 保存指定的Html文件到本地filePath路径
	 * 
	 * @param url
	 *            html文件的url
	 * @param filePath
	 *            文件目录
	 * @param fileName
	 *            文件名(带扩展名)
	 * @return 是否保存成功
	 */
	public static boolean saveHtml(Context context, String url,
			String filePath, String fileName) {
		try {
			URL url2 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
			/******** 以下将url指定的html文件保存到本地 *********/
			// 获取url文件输入流
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));

			// 创建文件目录
			FileUtils.createSDDir(filePath);
			// 在filePath目录下创建文件
			File htmlFile = FileUtils.createSDFile(filePath + fileName);
			// 实例化本地html文件输出流
			BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(
					htmlFile, false));
			String htmlStr;
			while (null != (htmlStr = buffReader.readLine())) {
				if (htmlStr.contains("alt=\"infoimage\"")) {
					// 所有的图片添加name为infoimage,方便获取图片数量
					htmlStr = htmlStr
							.replace("alt=\"infoimage\"",
									"alt=\"infoimage\" name=\"infoimage\" width=\"300\" height=\"272\" ");
				} else if (htmlStr.contains("</body>")) {
					// 在html所有元素加载完成后,执行javascript脚本,记录当前产品的图片数量
					htmlStr = " <script language=\"javascript\" type=\"text/javascript\">initial();addDelete();</script> "
							+ htmlStr;
				}
				bufferWriter.write(htmlStr, 0, htmlStr.length());
				if (htmlStr.contains("</title>")) {
					/***** 以下将javascript文件写入到本地html文件<head>标签中 *****/
					InputStream jsInputStream = context.getAssets().open(
							"productmanager/manageImg.js");
					BufferedReader jsBufferedReader = new BufferedReader(
							new InputStreamReader(jsInputStream));
					while (null != (htmlStr = jsBufferedReader.readLine())) {
						bufferWriter.write(htmlStr + "\r\n", 0,
								htmlStr.length());
					}
					jsBufferedReader.close();
					jsBufferedReader = null;
					jsInputStream.close();
					jsInputStream = null;
				}

			}
			buffReader.close();
			buffReader = null;

			conn.disconnect();
			conn = null;

			bufferWriter.flush();
			bufferWriter.close();
			bufferWriter = null;

			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 检查是否有可用的网络
	 * 
	 * @param context
	 * @param showTip
	 *            是否显示提示
	 * @return
	 */
	public static boolean checkNetwork(final Context context, boolean showTip) {
		boolean flag = false;
		if (null == context) {
			return false;
		}
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null != connectivityManager.getActiveNetworkInfo()) {
			flag = connectivityManager.getActiveNetworkInfo().isAvailable();
		}
		if (showTip) {
			if (!flag) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("没有可用的网络");
				builder.setMessage("请开启GPRS或WIFI网络连接");
				builder.setPositiveButton("确定",
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent("/");

								ComponentName componentName = new ComponentName(
										"com.android.settings",
										"com.android.settings.WirelessSettings");
								intent.setComponent(componentName);
								intent.setAction("android.intent.action.VIEW");
								context.startActivity(intent);
							}
						});
				builder.setNegativeButton("取消",
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				builder.create().show();
			}
		}
		return flag;
	}
}
