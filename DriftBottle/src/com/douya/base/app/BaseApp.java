package com.douya.base.app;

import java.io.File;

import android.app.Application;
import android.os.Environment;

/**
 * 应用程序基类
 * @author GeYunjie
 *
 */
public class BaseApp extends Application {

	public static final String DEFAULT_FILE_EXPAND = ".jpg";
	// 默认的Html文件保存路径
	public static final String DEFAULT_HTML_DIR;
	// 默认图片文件保存路径
	public static final String DEFAULT_IMAGE_DIR;

	static {
		String defaultDir = Environment.getExternalStorageDirectory().getPath()
				+ File.separator + "Htgg" + File.separator;
		DEFAULT_IMAGE_DIR = defaultDir + "images" + File.separator;
		DEFAULT_HTML_DIR = defaultDir + "htmls" + File.separator;
	}
}
