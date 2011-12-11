package com.douya.base.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {
	private static final String TAG = "DatabaseAdapter";

	private static final String DATABASE_NAME = "htgg.db";
	private static final int DATABASE_VERSION = 1;

	private SQLiteDatabase mDb;

	private DatabaseHelper dbHelper = null;
	private Context mContext = null;

	public DatabaseAdapter(Context context) {
		mContext = context;
	}

	/**
	 * 打开数据库
	 */
	public void open() {
		if (null == dbHelper) {
			dbHelper = new DatabaseHelper(mContext, DATABASE_NAME, null,
					DATABASE_VERSION);
		}
		// mDb = dbHelper.getWritableDatabase();
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		if (null != dbHelper)
			dbHelper.close();
	}

}