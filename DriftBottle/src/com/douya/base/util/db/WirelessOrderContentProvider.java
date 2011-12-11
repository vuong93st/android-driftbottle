package com.douya.base.util.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class WirelessOrderContentProvider extends ContentProvider {
	private static final String TAG = "WirelessOrderContentProvider";
	
	public static final String DEFAULT_SORT_ORDER = " _ID DESC";
	private SQLiteDatabase mDb;
	private static final String DATABASE_NAME = "htgg.db";
	public static String AUTHORITY = "com.douya.htgg";
	private static final int DATABASE_VERSION = 1;

	DatabaseHelper mDbHelper;
	static UriMatcher sUriMatcher;

	static {
	}

	@Override
	public boolean onCreate() {
		Log.i(TAG, "onCreate()");
		try {
			mDbHelper = new DatabaseHelper(this.getContext(), DATABASE_NAME,
					null, DATABASE_VERSION);
			mDb = mDbHelper.getReadableDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		Log.i(TAG, "uri=" + uri.toString() + ",sUriMatcher.match="
				+ sUriMatcher.match(uri));
		Log.i(TAG, "delete whereClause=" + whereClause + ",whereArgs=");
		if (null != whereArgs) {
			for (int i = 0; i < whereArgs.length; i++) {
				Log.e(TAG, "whereArgs " + i + "=" + whereArgs[i]);
			}
		}
		SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
		int rownum = 0;

		Log.i(TAG, "the number of rows affected " + rownum);
		return rownum;
	}

	@Override
	public String getType(Uri uri) {
		Log.i(TAG, "getType uri=" + uri.toString());
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.i(TAG, "insert uri=" + uri.toString());
		long rowId = -1;
		SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

		Uri newUri = Uri.withAppendedPath(uri, "" + rowId);
		Log.i(TAG, "newUri=" + newUri.toString());
		return newUri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase mDb = mDbHelper.getReadableDatabase();
		if (null == sortOrder) {
			sortOrder = DEFAULT_SORT_ORDER;
		}
		Log.i(TAG, "query");
		Log.i(TAG, "uri = " + uri);
		Log.i(TAG, "sUriMatcher.match(uri)=" + sUriMatcher.match(uri));
		Cursor c = null;
		/*
		 * SQLiteQueryBuilder is the helper class that creates the proper SQL
		 * syntax for us.
		 */
		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

		c = qBuilder.query(mDb, projection, selection, selectionArgs, null,
				null, sortOrder);
		Log.i(TAG, "get records");
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String whereClause,
			String[] whereArgs) {
		Log.i(TAG, "update uri=" + uri.toString());
		Log.i(TAG, "update whereClause(" + whereClause + ")");
		SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
		int rownum = 0;

		Log.i(TAG, "the number of rows affected " + rownum);
		// Notify any listeners and return the updated row count.
		getContext().getContentResolver().notifyChange(uri, null);
		return rownum;
	}

}