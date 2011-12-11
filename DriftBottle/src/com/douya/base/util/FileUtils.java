/**
 * @(#)com.douya.base.util.FileUtils.java
 * 版权声明 山东益信通科贸有限公司, 版权所有 违者必究
 *
 *<br> Copyright:Copyright (c) 2010-2011
 *<br> Company： 山东益信
 *<br> Author： 葛云杰(geyj@douya.cn)
 *<br> Date：2011-09-17
 *<br> Version：1.0
 */
package com.douya.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.douya.base.app.BaseApp;
/**
 * 文件工具类
 * @author GeYunjie
 *
 */
public final class FileUtils {
	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createSDFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	/**
	 * 在SD卡上创建文件夹
	 * 
	 * @param dirName
	 * @return
	 */
	public static File createSDDir(String dirName) {
		File dir = new File(dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExists(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param path
	 * @param fileName
	 * @param is
	 * @return
	 */
	public File write2SDFromInput(String path, String fileName, InputStream is) {
		File file = null;
		OutputStream os = null;
		try {
			createSDDir(path);
			file = createSDFile(path + fileName);
			os = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			while (is.read(buffer) != -1) {
				os.write(null);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 生成随机文件名
	 * @return
	 */
	public static String getFileName() {
		String fileNameRandom = getCharacterAndNumber(8);
		return BaseApp.DEFAULT_IMAGE_DIR + fileNameRandom
				+ BaseApp.DEFAULT_FILE_EXPAND;
	}

	/**
	 * 生成指定长度的的随机数字和字母
	 * 
	 * @param length
	 * @return
	 */
	public static String getCharacterAndNumber(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int charOrNum = random.nextInt(2) % 2 == 0 ? 0 : 1; // 输出字母还是数字
			if (charOrNum == 0) { // 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val = val + (char) (choice + random.nextInt(26));
			} else if (charOrNum == 1) {// 数字
				val = val + String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	/**
	 * 获取指定path目录下指定expand扩展名的文件列表,不包含子目录中的文件
	 * 
	 * @param path
	 * @param expand
	 * @return
	 */
	public static List<String> getAllFile(String path, String expand) {
		List<String> fileList = null;
		File file = new File(path);
		File[] allFiles = file.listFiles();
		if (null != allFiles) {
			int len = allFiles.length;
			File tmp = null;
			if (len > 0) {
				fileList = new ArrayList<String>();
				for (int i = 0; i < len; i++) {
					tmp = allFiles[i];
					if (tmp.isFile() && tmp.getName().endsWith(expand)) {
						fileList.add(tmp.getName());
					}
				}
				return fileList;
			}
		}
		return Collections.emptyList();
	}

	public static boolean delFile(String fullName) {
		File file = new File(fullName);
		try {
			return file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 得到文件List
	 * @param path
	 *            文件目录
	 * @param suffix
	 *            文件扩展名
	 * @return
	 */
	public static List<String> getRecordFiles(String path, String suffix) {
		List<String> recordFiles = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		if (null != files) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().indexOf(".") >= 0) {
					/* 只取suffix文件 */
					String fileS = files[i].getName().substring(
							files[i].getName().indexOf("."));
					if (fileS.toLowerCase().equals(suffix)) {
						recordFiles.add(files[i].getName());
					}
				}
			}
		}
		return recordFiles;
	}

	/**
	 * 删除指定目录下的所以文件
	 * 
	 * @param path
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		File[] listFiles = file.listFiles();
		if (null != listFiles) {
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isFile()) {
					listFiles[i].delete();
				}
			}
		}
	}

}
