package com.douya.base.util.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.douya.android.R;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "DatabaseHelper";

	private static final String dbName = "/data/data/com.douya.htgg/databases/htgg.db";
	private static final String DATABASE_PATH = "/data/data/com.douya.htgg/databases";
	private static final String DATABASE_FILENAME = "htgg.db";
	private static int VERSION = 1;
	String databaseFileName = DATABASE_PATH + "/" + DATABASE_FILENAME;

	private Context mContext = null;

	private File file = null;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.mContext = context;

		checkDBExists();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e(TAG, "onCreate");
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		checkDBExists();
		if (null == file) {
			file = new File(databaseFileName);
		}
		if (file.exists()) {
			return super.getReadableDatabase();
			// SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
			// databaseFileName, null);
		} else {
			return null;
		}
	}

	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		checkDBExists();
		if (new File(databaseFileName).exists()) {
			// SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
			// databaseFileName, null);
			return super.getWritableDatabase();
		} else {
			return null;
		}
	}

	private void checkDBExists() {
		// 从资源文件中将数据库创建/data/data/com.douya.htgg/databases/htgg.db
		// 1,获得路径
		File dir = new File(DATABASE_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 2,获得资源文件
		if (!(new File(databaseFileName).exists())) {
			try {
				// 3,读取资源并创建流
				InputStream is = mContext.getResources().openRawResource(
						R.raw.driftbottle);
				FileOutputStream fos = new FileOutputStream(databaseFileName);
				// 4,复制
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				// 5,关闭流
				fos.close();
				is.close();
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade");
		onCreate(db);
	}
}
