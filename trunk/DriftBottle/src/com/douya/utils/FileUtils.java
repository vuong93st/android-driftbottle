package com.douya.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
	private String SDCardRoot;

	public FileUtils() {
		// 得到当前外部存储设备的目录
		SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator;
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public File createFileInSDCard(String fileName, String dir)
			throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		System.out.println("file---->" + file);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 */
	public File creatSDDir(String dir) {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		System.out.println(dirFile.mkdirs());
		return dirFile;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 */
	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream input) {

		File file = null;
		OutputStream output = null;
		try {
			creatSDDir(path);
			file = createFileInSDCard(fileName, path);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp;
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 文本文件编码自动转换
	 * @param str_filepath
	 * @return
	 */
	public String convertCodeAndGetText(String str_filepath) {// 转码

        File file = new File(str_filepath);
        BufferedReader reader;
        String text = "";
        try {
                // FileReader f_reader = new FileReader(file);
                // BufferedReader reader = new BufferedReader(f_reader);
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream in = new BufferedInputStream(fis);
                in.mark(4);
                byte[] first3bytes = new byte[3];
                in.read(first3bytes);//找到文档的前三个字节并自动判断文档类型。
                in.reset();
                if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                                && first3bytes[2] == (byte) 0xBF) {// utf-8

                        reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

                } else if (first3bytes[0] == (byte) 0xFF
                                && first3bytes[1] == (byte) 0xFE) {

                        reader = new BufferedReader(
                                        new InputStreamReader(in, "unicode"));
                } else if (first3bytes[0] == (byte) 0xFE
                                && first3bytes[1] == (byte) 0xFF) {

                        reader = new BufferedReader(new InputStreamReader(in,
                                        "utf-16be"));
                } else if (first3bytes[0] == (byte) 0xFF
                                && first3bytes[1] == (byte) 0xFF) {

                        reader = new BufferedReader(new InputStreamReader(in,
                                        "utf-16le"));
                } else {

                        reader = new BufferedReader(new InputStreamReader(in, "GBK"));
                }
                String str = reader.readLine();

                while (str != null) {
                        text = text + str + "\n";
                        str = reader.readLine();

                }
                reader.close();

        } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return text;
}

}