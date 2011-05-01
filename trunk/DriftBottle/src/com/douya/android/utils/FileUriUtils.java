package com.douya.android.utils;

import java.io.File;

import android.net.Uri;

public class FileUriUtils {

	/**
	 * 转换文件为URI.
	 * @param file
	 * @return uri
	 */
	public static Uri getUri(File file) {
		if (file != null) {
			return Uri.fromFile(file);
		}
		return null;
	}


	/**
	 * 将URI转换为文件.
	 * @param uri
	 * @return file
	 */
	public static File getFile(Uri uri) {
		if (uri != null) {
			String filepath = uri.getPath();
			if (filepath != null) {
				return new File(filepath);
			}
		}
		return null;
	}

	/**
	 * 将字符串转换为Uri.
	 * @param file
	 * @return uri
	 */
	public static Uri getUri(String filename) {
		return getUri(new File(filename));
	}

	/**
	 * 将Uri转换为字符串.
	 * @param uri
	 * @return file
	 */
	public static String getFilename(Uri uri) {
		File file = getFile(uri);
		if (file != null) {
			return file.getAbsolutePath();
		}
		return null;
	}

	/**
	 * 从一个路径和文件名创建文件
	 * @param curdir
	 * @param file
	 * @return
	 */
	public static File getFile(String curdir, String file) {
		String separator = "/";
		  if (curdir.endsWith("/")) {
			  separator = "";
		  }
		   File clickedFile = new File(curdir + separator
		                       + file);
		return clickedFile;
	}
	
	public static File getFile(File curdir, String file) {
		return getFile(curdir.getAbsolutePath(), file);
	}

}
